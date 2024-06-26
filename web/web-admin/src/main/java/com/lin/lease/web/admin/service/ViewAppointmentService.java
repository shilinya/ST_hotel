package com.lin.lease.web.admin.service;

import com.lin.lease.model.entity.ViewAppointment;
import com.lin.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.lin.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {
    /**
     * 分页查询预约信息
     * @param page
     * @param queryVo
     * @return
     */
    IPage<AppointmentVo> pageAppontment(IPage<AppointmentVo> page, AppointmentQueryVo queryVo);
}
