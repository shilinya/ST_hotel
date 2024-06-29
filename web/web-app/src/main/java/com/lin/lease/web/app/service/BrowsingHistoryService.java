package com.lin.lease.web.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.lease.model.entity.BrowsingHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.lease.web.app.vo.history.HistoryItemVo;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface BrowsingHistoryService extends IService<BrowsingHistory> {
    /**
     * 根据用户id查询用户浏览历史
     *
     * @param page
     * @param userId
     * @return
     */
    IPage<HistoryItemVo> listHistory(IPage<HistoryItemVo> page, Long userId);

    void saveHistory(Long userId, Long id);
}
