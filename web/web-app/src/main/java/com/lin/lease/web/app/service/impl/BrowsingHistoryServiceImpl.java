package com.lin.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.lease.model.entity.BrowsingHistory;
import com.lin.lease.web.app.mapper.BrowsingHistoryMapper;
import com.lin.lease.web.app.service.BrowsingHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.lease.web.app.vo.history.HistoryItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {
    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;

    @Override
    public IPage<HistoryItemVo> listHistory(IPage<HistoryItemVo> page, Long userId) {
        return browsingHistoryMapper.listHistory(page,userId);
    }
    @Async //异步操作，新线程执行
    @Override
    public void saveHistory(Long userId, Long id) {
        //查询该用户是否已经浏览过该房间
        LambdaQueryWrapper<BrowsingHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BrowsingHistory::getUserId, userId);
        wrapper.eq(BrowsingHistory::getRoomId, id);
        BrowsingHistory browsingHistory = browsingHistoryMapper.selectOne(wrapper);
        if (browsingHistory != null) {
            browsingHistory.setBrowseTime(new Date());//更新浏览时间
            browsingHistoryMapper.updateById(browsingHistory);
        }else{
            browsingHistory = new BrowsingHistory();
            browsingHistory.setUserId(userId);
            browsingHistory.setRoomId(id);
            browsingHistory.setBrowseTime(new Date());
            browsingHistoryMapper.insert(browsingHistory);
        }


        //如果首次，新增浏览历史
        //非首次，更新浏览时间
    }
}