package com.lin.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.lease.model.entity.LeaseAgreement;
import com.lin.lease.web.admin.mapper.LeaseAgreementMapper;
import com.lin.lease.web.admin.service.LeaseAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.lin.lease.web.admin.vo.agreement.AgreementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {
    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;

    @Override
    public IPage<AgreementVo> listAgreementVo(IPage<AgreementVo> page, AgreementQueryVo queryVo) {
        return leaseAgreementMapper.listAgreementVo(page,queryVo);
    }

    @Override
    public AgreementVo selectAgreementVo(Long id) {
        return leaseAgreementMapper.selectAgreementVo(id);
    }
}




