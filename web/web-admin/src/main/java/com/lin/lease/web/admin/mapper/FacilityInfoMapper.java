package com.lin.lease.web.admin.mapper;

import com.lin.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.lin.lease.model.FacilityInfo
*/
public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {
    /**
     * 根据公寓id查询公寓所有的配套信息
     * @param id
     * @return
     */
    List<FacilityInfo> selectListByItemId(Long id);

    /**
     * 根据房间id查询房间所有的信息
     * @param id
     * @return
     */
    List<FacilityInfo> selectListByRoomId(Long id);
}




