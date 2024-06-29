package com.lin.lease.web.admin.mapper;

import com.lin.lease.model.entity.LeaseAgreement;
import com.lin.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.lin.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.lin.lease.model.LeaseAgreement
*/
public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {

    IPage<AgreementVo> listAgreementVo(IPage<AgreementVo> page,AgreementQueryVo queryVo);

    AgreementVo selectAgreementVo(Long id);
}




