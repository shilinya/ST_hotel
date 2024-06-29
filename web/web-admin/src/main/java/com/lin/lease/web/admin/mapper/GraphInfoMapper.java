package com.lin.lease.web.admin.mapper;

import com.lin.lease.model.entity.GraphInfo;
import com.lin.lease.model.enums.ItemType;
import com.lin.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.lin.lease.model.GraphInfo
*/
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    /**
     * 跟剧id查询公寓或房间的照片
     * @param apartment
     * @param id
     * @return
     */
    List<GraphVo> graphVoListByItemTypeAndId(ItemType apartment, Long id);
}




