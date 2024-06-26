package com.lin.lease.web.admin.service;

import com.lin.lease.model.entity.FeeKey;
import com.lin.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface FeeKeyService extends IService<FeeKey> {
    /**
     * 查询所有杂费名称及其所对应的所有收费标准
     * @return
     */
    List<FeeKeyVo> listFeeInfo();
}
