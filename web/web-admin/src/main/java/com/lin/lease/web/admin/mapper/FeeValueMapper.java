package com.lin.lease.web.admin.mapper;

import com.lin.lease.model.entity.FeeValue;
import com.lin.lease.web.admin.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.lin.lease.model.FeeValue
*/
public interface FeeValueMapper extends BaseMapper<FeeValue> {

    /**
     * 根据公寓id查询公寓所有的杂费信息及其杂费名
     * @param id
     * @return
     */
    List<FeeValueVo> selectListByItemId(Long id);

}




