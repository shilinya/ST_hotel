package com.lin.lease.web.admin.mapper;

import com.lin.lease.model.entity.AttrValue;
import com.lin.lease.web.admin.vo.attr.AttrValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_value(房间基本属性值表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.lin.lease.model.AttrValue
*/
public interface AttrValueMapper extends BaseMapper<AttrValue> {
    /**
     * 根据房间id查询房间的属性信息
     * @param id
     * @return
     */
    List<AttrValueVo> listAttrValueVoById(Long id);
}




