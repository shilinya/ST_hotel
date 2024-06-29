package com.lin.lease.web.app.mapper;

import com.lin.lease.model.entity.BrowsingHistory;
import com.lin.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.lin.lease.model.entity.BrowsingHistory
*/
public interface BrowsingHistoryMapper extends BaseMapper<BrowsingHistory> {
    /**
     * 根据用户id多表查询浏览历史的详细信息
     *
     * @param page
     * @param userId
     * @return
     */
    IPage<HistoryItemVo> listHistory(IPage<HistoryItemVo> page, Long userId);
}




