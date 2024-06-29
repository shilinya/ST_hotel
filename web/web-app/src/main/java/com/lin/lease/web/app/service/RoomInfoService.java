package com.lin.lease.web.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.lease.model.entity.RoomInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.lease.web.app.vo.room.RoomDetailVo;
import com.lin.lease.web.app.vo.room.RoomItemVo;
import com.lin.lease.web.app.vo.room.RoomQueryVo;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface RoomInfoService extends IService<RoomInfo> {
    /**
     * 根据queryVo分页查询房间详细信息（RoomItemVo）
     * @param page
     * @param queryVo
     * @return
     */
    IPage<RoomItemVo> pageItem(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    /**
     * 根据公寓id查询房间详细信息
     * @param page
     * @param id
     * @return
     */
    IPage<RoomItemVo> pageItemByApartmentId(IPage<RoomItemVo> page, Long id);

    /**
     * 根据房间id查询房间所有信息
     * @param id
     * @return
     */
    RoomDetailVo getDetailById(Long id);
}
