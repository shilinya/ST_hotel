package com.lin.lease.web.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.lease.model.entity.RoomInfo;
import com.lin.lease.model.enums.ReleaseStatus;
import com.lin.lease.web.admin.vo.room.RoomDetailVo;
import com.lin.lease.web.admin.vo.room.RoomItemVo;
import com.lin.lease.web.admin.vo.room.RoomQueryVo;
import com.lin.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {
    /**
     * 根据提交的信息保存房间所有信息，包括房间的基本信息，图片，配套，属性，标签，支付方式，可选租期
     * @param roomSubmitVo
     */
    void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo);



    IPage<RoomItemVo> pagserviceeRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    /**
     * 根据id获取房间详细信息，包括图片，设施等
     * @param id
     * @return
     */
   RoomDetailVo selectRommDetailsById(Long id);

    /**
     * 根据公寓id查询公寓的房间
     * @param id
     * @return
     */
    List<RoomInfo> listRoomInfoByApartmentId(Long id);

    /**
     * 跟房间id修改房间状态
     * @param id
     * @param status
     */
    void updateStatusById(Long id, ReleaseStatus status);

    /**
     * 根据房间id删除房间相关信息
     * @param id
     */
    void removeRoomById(Long id);
}
