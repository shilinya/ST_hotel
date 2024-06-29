package com.lin.lease.web.admin.service.impl;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.lease.common.constant.RedisConstant;
import com.lin.lease.model.entity.*;
import com.lin.lease.model.enums.ItemType;
import com.lin.lease.model.enums.ReleaseStatus;
import com.lin.lease.web.admin.mapper.*;
import com.lin.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.lease.web.admin.vo.attr.AttrValueVo;
import com.lin.lease.web.admin.vo.graph.GraphVo;
import com.lin.lease.web.admin.vo.room.RoomDetailVo;
import com.lin.lease.web.admin.vo.room.RoomItemVo;
import com.lin.lease.web.admin.vo.room.RoomQueryVo;
import com.lin.lease.web.admin.vo.room.RoomSubmitVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private RoomFacilityService roomFacilityService;
    @Autowired
    private RoomAttrValueService   roomAttrValueService;
    @Autowired
    private RoomLabelService roomLabelService;
    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;
    @Autowired
    private RoomLeaseTermService roomLeaseTermService;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private FacilityInfoService facilityInfoService;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo) {
        //保存房间的基本信息
        super.saveOrUpdate(roomSubmitVo);
        //判断是新建房间还是更新房间信息，id为null即为新增
        boolean isUpdate=(roomSubmitVo.getId()!=null);
        if(isUpdate){
            //如果不为空，先删除所有的关系表信息，在根据信息插入新的

            //删除图片
            LambdaQueryWrapper<GraphInfo> graphWrapper = new LambdaQueryWrapper<>();
            graphWrapper.eq(GraphInfo::getItemType, ItemType.ROOM).eq(GraphInfo::getItemId, roomSubmitVo.getId());
            graphInfoService.remove(graphWrapper);
            //删除配套
            LambdaQueryWrapper<RoomFacility> wrapper = new LambdaQueryWrapper<RoomFacility>().eq(RoomFacility::getRoomId, roomSubmitVo.getId());
            roomFacilityService.remove(wrapper);
            //删除属性
            LambdaQueryWrapper<RoomAttrValue> attrWrapper = new LambdaQueryWrapper<RoomAttrValue>().eq(RoomAttrValue::getRoomId, roomSubmitVo.getId());
            roomAttrValueService.remove(attrWrapper);
            //删除标签
            LambdaQueryWrapper<RoomLabel> Labelwrapper = new LambdaQueryWrapper<RoomLabel>().eq(RoomLabel::getRoomId, roomSubmitVo.getId());
            roomLabelService.remove(Labelwrapper);
            //删除支付方式
            LambdaQueryWrapper<RoomPaymentType> payWrapper = new LambdaQueryWrapper<RoomPaymentType>().eq(RoomPaymentType::getRoomId, roomSubmitVo.getId());
            roomPaymentTypeService.remove(payWrapper);
            //删除可选租期
            LambdaQueryWrapper<RoomLeaseTerm> leaseWrapper = new LambdaQueryWrapper<RoomLeaseTerm>().eq(RoomLeaseTerm::getRoomId, roomSubmitVo.getId());
            roomLeaseTermService.remove(leaseWrapper);


            //删除缓存
            String key= RedisConstant.APP_ROOM_PREFIX+roomSubmitVo.getId();
            redisTemplate.delete(key);
        }
        //新建房间的相关信息
        //插入图片
        List<GraphVo> graphVoList=roomSubmitVo.getGraphVoList();
        if (!CollectionUtils.isEmpty(graphVoList)){
            List<GraphInfo> graphInfoList=new ArrayList<>();
            for (GraphVo graphVo:graphVoList){
                graphInfoList.add(GraphInfo.builder().name(graphVo.getName()).url(graphVo.getUrl()).itemId(roomSubmitVo.getId()).itemType(ItemType.ROOM).build());

            }
            graphInfoService.saveBatch(graphInfoList);
        }
        //插入配套
        List<Long> facilityId=roomSubmitVo.getFacilityInfoIds();
        if(!CollectionUtils.isEmpty(facilityId)){
            //如果不为空且不为null，插入
            List<RoomFacility> facilityList=new ArrayList<>();
            for(Long f:facilityId){
                facilityList.add(RoomFacility.builder().roomId(roomSubmitVo.getId()).facilityId(f).build());
            }
            roomFacilityService.saveBatch(facilityList);
        }
        //插入属性
        List<Long> attrValueId=roomSubmitVo.getAttrValueIds();
        if(!CollectionUtils.isEmpty(attrValueId)){
            List<RoomAttrValue> attrValueList=new ArrayList<>();
            for(Long a:attrValueId){
                attrValueList.add(RoomAttrValue.builder().roomId(roomSubmitVo.getId()).attrValueId(a).build());
            }
            roomAttrValueService.saveBatch(attrValueList);
        }
        //插入标签
        List<Long> labelId=roomSubmitVo.getLabelInfoIds();
        if(!CollectionUtils.isEmpty(labelId)){
            List<RoomLabel> labelList=new ArrayList<>();
            for(Long l:labelId){
                labelList.add(RoomLabel.builder().roomId(roomSubmitVo.getId()).labelId(l).build());

            }
            roomLabelService.saveBatch(labelList);
        }
        //插入支付方式
        List<Long> paymentTypeId=roomSubmitVo.getPaymentTypeIds();
        if(!CollectionUtils.isEmpty(paymentTypeId)){
            List<RoomPaymentType> paymentTypeList=new ArrayList<>();
            for(Long t:paymentTypeId){
                paymentTypeList.add(RoomPaymentType.builder().paymentTypeId(t).build());

            }
            roomPaymentTypeService.saveBatch(paymentTypeList);
        }
        //插入可选租期
        List<Long> leaseTermId=roomSubmitVo.getLeaseTermIds();
        if(!CollectionUtils.isEmpty(leaseTermId)){
            List<RoomLeaseTerm> leaseTermList=new ArrayList<>();
            for(Long t:leaseTermId){
                leaseTermList.add(RoomLeaseTerm.builder().leaseTermId(t).build());
            }
            roomLeaseTermService.saveBatch(leaseTermList);
        }
    }

    @Override
    public IPage<RoomItemVo> pagserviceeRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.pageRoomItemByQuery(page, queryVo);

    }

    @Override
    public RoomDetailVo selectRommDetailsById(Long id) {
        RoomDetailVo roomDetailVo = new RoomDetailVo();
        RoomInfo roomInfo = this.getById(id);
        if (roomInfo == null) {
            //没有此房间，返回null
            return null;
        }
        //查询所属公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(roomInfo.getApartmentId());
        //查询图片列表
        List<GraphVo> graphVoList=graphInfoMapper.listGraphVoById(ItemType.ROOM,id);
        //查询属性信息列表
        List<AttrValueVo> attrValueVoList=attrValueMapper.listAttrValueVoById(id);
        //查询配套信息列表
        List<FacilityInfo> facilityInfoList=facilityInfoMapper.selectListByRoomId(id);
        //查询标签信息列表
        List<LabelInfo> labelInfoList=labelInfoMapper.selectListByRoomId(id);
        //查询支付方式列表
        List<PaymentType> paymentTypeList= paymentTypeMapper.listPaymentTypeById(id);
        //查询可选租期列表
        List<LeaseTerm> termList=leaseTermMapper.listTermsById(id);
        //组装结果
        BeanUtils.copyProperties(roomInfo,roomDetailVo);
        roomDetailVo.setApartmentInfo(apartmentInfo);
        roomDetailVo.setGraphVoList(graphVoList);
        roomDetailVo.setAttrValueVoList(attrValueVoList);
        roomDetailVo.setFacilityInfoList(facilityInfoList);
        roomDetailVo.setLabelInfoList(labelInfoList);
        roomDetailVo.setPaymentTypeList(paymentTypeList);
        roomDetailVo.setLeaseTermList(termList);
        return roomDetailVo;
    }

    @Override
    public List<RoomInfo> listRoomInfoByApartmentId(Long id) {
        LambdaQueryWrapper<RoomInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoomInfo::getApartmentId, id);
        return roomInfoMapper.selectList(wrapper);
    }

    @Override
    public void updateStatusById(Long id, ReleaseStatus status) {
        LambdaUpdateWrapper<RoomInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(RoomInfo::getId, id);
        wrapper.set(RoomInfo::getIsRelease,status);
        this.update(wrapper);
    }

    @Override
    public void removeRoomById(Long id) {
        //删除房间基本信息
        this.removeById(id);
        //删除图片
        LambdaQueryWrapper<GraphInfo> graphWrapper = new LambdaQueryWrapper<>();
        graphWrapper.eq(GraphInfo::getItemType, ItemType.ROOM).eq(GraphInfo::getItemId, id);
        graphInfoService.remove(graphWrapper);
        //删除配套
        LambdaQueryWrapper<RoomFacility> wrapper = new LambdaQueryWrapper<RoomFacility>().eq(RoomFacility::getRoomId, id);
        roomFacilityService.remove(wrapper);
        //删除属性
        LambdaQueryWrapper<RoomAttrValue> attrWrapper = new LambdaQueryWrapper<RoomAttrValue>().eq(RoomAttrValue::getRoomId, id);
        roomAttrValueService.remove(attrWrapper);
        //删除标签
        LambdaQueryWrapper<RoomLabel> Labelwrapper = new LambdaQueryWrapper<RoomLabel>().eq(RoomLabel::getRoomId, id);
        roomLabelService.remove(Labelwrapper);
        //删除支付方式
        LambdaQueryWrapper<RoomPaymentType> payWrapper = new LambdaQueryWrapper<RoomPaymentType>().eq(RoomPaymentType::getRoomId, id);
        roomPaymentTypeService.remove(payWrapper);
        //删除可选租期
        LambdaQueryWrapper<RoomLeaseTerm> leaseWrapper = new LambdaQueryWrapper<RoomLeaseTerm>().eq(RoomLeaseTerm::getRoomId, id);
        roomLeaseTermService.remove(leaseWrapper);

        //删除缓存
        String key= RedisConstant.APP_ROOM_PREFIX+id;
        redisTemplate.delete(key);

    }


}




