package com.lin.lease.web.admin.controller.system;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.lease.common.result.Result;
import com.lin.lease.model.entity.SystemUser;
import com.lin.lease.model.enums.BaseStatus;
import com.lin.lease.web.admin.service.SystemPostService;
import com.lin.lease.web.admin.service.SystemUserService;
import com.lin.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.lin.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "后台用户信息管理")
@RestController
@RequestMapping("/admin/system/user")
public class SystemUserController {
    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemPostService systemPostService;

    @Operation(summary = "根据条件分页查询后台用户列表")
    @GetMapping("page")
    public Result<IPage<SystemUserItemVo>> page(@RequestParam long current, @RequestParam long size, SystemUserQueryVo queryVo) {
        IPage<SystemUser> page=new Page<>(current,size);
       IPage<SystemUserItemVo> result= systemUserService.listSUserItemVo(page,queryVo);
        return Result.ok(result);
    }

    @Operation(summary = "根据ID查询后台用户信息")
    @GetMapping("getById")
    public Result<SystemUserItemVo> getById(@RequestParam Long id) {
        SystemUser user = systemUserService.getById(id);
        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
        BeanUtils.copyProperties(user,systemUserItemVo);
        String name = systemPostService.getById(user.getPostId()).getName();
        systemUserItemVo.setPostName(name);
        return Result.ok(systemUserItemVo);
    }

    @Operation(summary = "保存或更新后台用户信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SystemUser systemUser) {//注意密码应该处理后保存

        if(systemUser.getPassword()!=null){
            String s = DigestUtils.md5Hex(systemUser.getPassword());
            systemUser.setPassword(s);
        }
        systemUserService.saveOrUpdate(systemUser);
        return Result.ok();
    }

    @Operation(summary = "判断后台用户名是否可用")
    @GetMapping("isUserNameAvailable")
    public Result<Boolean> isUsernameExists(@RequestParam String username) {
        LambdaQueryWrapper<SystemUser> wrapper = new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getUsername, username);
        List<SystemUser> list = systemUserService.list(wrapper);
        boolean result= list.isEmpty();

        return Result.ok(result);
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据ID删除后台用户信息")
    public Result removeById(@RequestParam Long id) {
        systemUserService.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "根据ID修改后台用户状态")
    @PostMapping("updateStatusByUserId")
    public Result updateStatusByUserId(@RequestParam Long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<SystemUser> wrapper = new LambdaUpdateWrapper<SystemUser>().eq(SystemUser::getId, id).set(SystemUser::getStatus, status);
        systemUserService.update(wrapper);
        return Result.ok();
    }
}
