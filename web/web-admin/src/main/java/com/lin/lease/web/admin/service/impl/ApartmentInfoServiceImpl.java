package com.lin.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.lease.common.exception.LeaseException;
import com.lin.lease.common.result.ResultCodeEnum;
import com.lin.lease.model.entity.*;
import com.lin.lease.model.enums.ItemType;
import com.lin.lease.web.admin.mapper.*;
import com.lin.lease.web.admin.service.*;
import com.lin.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.lin.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.lin.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.lin.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.lin.lease.web.admin.vo.fee.FeeValueVo;
import com.lin.lease.web.admin.vo.graph.GraphVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {
    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;
    @Autowired
    private ApartmentLabelService apartmentLabelService;
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private GraphInfoMapper  graphInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private FeeValueMapper feeValueMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private ProvinceInfoService provinceInfoService;
    @Autowired
    private CityInfoService cityInfoService;
    @Autowired
    private DistrictInfoService districtInfoService;

    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate= apartmentSubmitVo.getId()!=null;//不等于null，更新，否则新存

        //根据省市区id为省市区name赋值
        String provinceName = provinceInfoService.getById(apartmentSubmitVo.getProvinceId()).getName();
        String cityName = cityInfoService.getById(apartmentSubmitVo.getCityId()).getName();
        String districtName = districtInfoService.getById(apartmentSubmitVo.getDistrictId()).getName();

        apartmentSubmitVo.setProvinceName(provinceName);
        apartmentSubmitVo.setCityName(cityName);
        apartmentSubmitVo.setDistrictName(districtName);

        super.saveOrUpdate(apartmentSubmitVo);//保存单独的公寓信息
        if (isUpdate){
            //删除图片列表
            LambdaQueryWrapper<GraphInfo> Graphwrapper = new LambdaQueryWrapper<>();
            Graphwrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT).eq(GraphInfo::getItemId, apartmentSubmitVo.getId());
            graphInfoService.remove(Graphwrapper);
            //删除配套
            LambdaQueryWrapper<ApartmentFacility> apartmentFacilityWrapper = new LambdaQueryWrapper<>();
            apartmentFacilityWrapper.eq(ApartmentFacility::getApartmentId, apartmentSubmitVo.getId());
            apartmentFacilityService.remove(apartmentFacilityWrapper);
            //删除标签
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelWrapper = new LambdaQueryWrapper<ApartmentLabel>().eq(ApartmentLabel::getApartmentId, apartmentSubmitVo.getId());
            apartmentLabelService.remove(apartmentLabelWrapper);
            //删除杂费
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeWrapper = new LambdaQueryWrapper<ApartmentFeeValue>().eq(ApartmentFeeValue::getApartmentId, apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(apartmentFeeWrapper);


        }
        //插入图片列表
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if (!CollectionUtils.isEmpty(graphVoList)){//CollectionUtils用于检查集合（Collection）是否为空或为null
            ArrayList<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                graphInfoList.add(GraphInfo.builder().itemType(ItemType.APARTMENT).itemId(apartmentSubmitVo.getId()).name(graphVo.getName()).url(graphVo.getUrl()).build());

            }
            graphInfoService.saveBatch(graphInfoList);
        }
        //插入配套列表
        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIds)){
            ArrayList<ApartmentFacility> apartmentFacilities = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds) {
                apartmentFacilities.add(ApartmentFacility.builder().apartmentId(apartmentSubmitVo.getId()).facilityId(facilityInfoId).build());
            }
            apartmentFacilityService.saveBatch(apartmentFacilities);
        }
        //插入标签列表
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if (!CollectionUtils.isEmpty(labelIds)) {
            ArrayList<ApartmentLabel> apartmentLabels = new ArrayList<>();
            for (Long labelId : labelIds) {
                apartmentLabels.add(ApartmentLabel.builder().apartmentId(apartmentSubmitVo.getId()).labelId(labelId).build());
            }
            apartmentLabelService.saveBatch(apartmentLabels);
        }
        //插入杂费列表
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIds)) {
            ArrayList<ApartmentFeeValue> apartmentFeeValues = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                apartmentFeeValues.add(ApartmentFeeValue.builder().apartmentId(apartmentSubmitVo.getId()).feeValueId(feeValueId).build());

            }
            apartmentFeeValueService.saveBatch(apartmentFeeValues);
        }

    }

    @Override
    public IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> page, ApartmentQueryVo queryVo) {
        IPage<ApartmentItemVo> result= apartmentInfoMapper.pageItem(page,queryVo);
        return result;
    }

    @Override
    public ApartmentDetailVo queryApartmentById(Long id) {
        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
        ApartmentInfo apartmentInfo = this.getById(id);
        if (apartmentInfo == null) {
            return null;
        }
        //查询图片列表
        List<GraphVo> graphVoList=graphInfoMapper.graphVoListByItemTypeAndId(ItemType.APARTMENT,id);
        //查询标签列表
        List<LabelInfo> labelInfoList=labelInfoMapper.selectListByItemId(id);
        //查询配套列表
        List<FacilityInfo> facilityInfoList=facilityInfoMapper.selectListByItemId(id);
        //查询杂费列表
        List<FeeValueVo> feeValueList=feeValueMapper.selectListByItemId(id);
        //组装结果
        BeanUtils.copyProperties(apartmentInfo,apartmentDetailVo);//spring提供的工具类
        apartmentDetailVo.setGraphVoList(graphVoList);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        apartmentDetailVo.setFeeValueVoList(feeValueList);
        return apartmentDetailVo;
    }

    @Override
    public void removeApartmentById(Long id) {
        LambdaQueryWrapper<RoomInfo> wrapper = new LambdaQueryWrapper<RoomInfo>().eq(RoomInfo::getApartmentId, id);
        Long roomNum = roomInfoMapper.selectCount(wrapper);
        if (roomNum > 0) {
            //公寓还有房间,终止删除
            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
        }
        //删除公寓基本信息
        super.removeById(id);
        //删除图片列表
        LambdaQueryWrapper<GraphInfo> Graphwrapper = new LambdaQueryWrapper<>();
        Graphwrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT).eq(GraphInfo::getItemId, id);
        graphInfoService.remove(Graphwrapper);
        //删除配套
        LambdaQueryWrapper<ApartmentFacility> apartmentFacilityWrapper = new LambdaQueryWrapper<>();
        apartmentFacilityWrapper.eq(ApartmentFacility::getApartmentId, id);
        apartmentFacilityService.remove(apartmentFacilityWrapper);
        //删除标签
        LambdaQueryWrapper<ApartmentLabel> apartmentLabelWrapper = new LambdaQueryWrapper<ApartmentLabel>().eq(ApartmentLabel::getApartmentId, id);
        apartmentLabelService.remove(apartmentLabelWrapper);
        //删除杂费
        LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeWrapper = new LambdaQueryWrapper<ApartmentFeeValue>().eq(ApartmentFeeValue::getApartmentId, id);
        apartmentFeeValueService.remove(apartmentFeeWrapper);


    }
}




