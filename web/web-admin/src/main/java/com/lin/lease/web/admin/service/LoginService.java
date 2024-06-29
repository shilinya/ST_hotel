package com.lin.lease.web.admin.service;

import com.lin.lease.web.admin.vo.login.CaptchaVo;
import com.lin.lease.web.admin.vo.login.LoginVo;
import com.lin.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {
    /**
     * 获得验证码图片
     * @return
     */
    CaptchaVo getCapthcha();

    String login(LoginVo loginVo);

    /**
     * 回显，根据id获取登录用户信息
     * @param userId
     * @return
     */
    SystemUserInfoVo getLoginUserInfoById(Long userId);
}
