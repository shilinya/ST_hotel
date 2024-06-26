package com.lin.lease.web.admin.mapper;

import com.lin.lease.model.entity.PaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【payment_type(支付方式表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.lin.lease.model.PaymentType
 */
public interface PaymentTypeMapper extends BaseMapper<PaymentType> {
    /**
     * 根据房间id查询房间的支付方式
     * @return
     */
    List<PaymentType> listPaymentTypeById(Long id);

}




