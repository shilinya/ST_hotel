package com.lin.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.lease.model.entity.RoomFacility;
import com.lin.lease.web.app.service.RoomFacilityService;
import com.lin.lease.web.app.mapper.RoomFacilityMapper;
import org.springframework.stereotype.Service;

/**
* @author liubo
* @description 针对表【room_facility(房间&配套关联表)】的数据库操作Service实现
* @createDate 2023-07-26 11:12:39
*/
@Service
public class RoomFacilityServiceImpl extends ServiceImpl<RoomFacilityMapper, RoomFacility>
    implements RoomFacilityService{

}




