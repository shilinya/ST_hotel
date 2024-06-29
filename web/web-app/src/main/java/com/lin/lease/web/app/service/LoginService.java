package com.lin.lease.web.app.service;

import com.lin.lease.web.app.vo.user.LoginVo;
import com.lin.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {
    /**
     * 发送短信验证码给手机
     * 验证码自己生成并保存，时效性
     * 限制短信发送评率
     * @param phone
     */
    void getMessage(String phone);

    /**
     * 根据手机号＋短信验证码验证登录
     * 如果是新用户自动完成注册
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 根据id返回前端所需用户数据
     * @param userId
     * @return
     */
    UserInfoVo getLoginUserById(Long userId);

}
