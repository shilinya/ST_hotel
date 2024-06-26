package com.lin.lease.web.admin.mapper;

import com.lin.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_term(租期)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.lin.lease.model.LeaseTerm
*/
public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {
    /**
     * 根据房间id查询房间的可选租期列表
     * @param id
     * @return
     */
    List<LeaseTerm> listTermsById(Long id);
}




