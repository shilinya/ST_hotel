package com.lin.lease.web.admin.service;

import com.lin.lease.model.entity.AttrKey;
import com.lin.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface AttrKeyService extends IService<AttrKey> {
    /**
     * 查询所有属性名称及每个属性名称对应的所有属性值
     * @return
     */
    List<AttrKeyVo> listAttrKeyVo();
}
