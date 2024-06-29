package com.lin.lease.web.admin.mapper;

import com.lin.lease.model.entity.LabelInfo;
import com.lin.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.lin.lease.model.LabelInfo
*/
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    List<LabelInfo> selectListByItemId(Long id);

    /**
     * 根据房间id查询房间所有的标签信息
     * @param id
     * @return
     */
    List<LabelInfo> selectListByRoomId(Long id);

}




