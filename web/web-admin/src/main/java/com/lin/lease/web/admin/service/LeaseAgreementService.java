package com.lin.lease.web.admin.service;

import com.lin.lease.model.entity.LeaseAgreement;
import com.lin.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.lin.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {
    /**
     * 根据租约queryVo分页查询租约详细信息
     * @return
     */
    IPage<AgreementVo> listAgreementVo(IPage<AgreementVo> page, AgreementQueryVo queryVo);

    /**
     * 根据id查询租约信息
     * @param id
     * @return
     */
    AgreementVo selectAgreementVo(Long id);
}
