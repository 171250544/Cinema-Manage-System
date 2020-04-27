package com.example.cinema.bl.user;

import com.example.cinema.vo.StaffVO;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;


/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     * @return
     */
    ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     * @return
     */
    UserVO login(UserForm userForm);

    /***
     * 返回所有管理员信息
     * @return
     */
    ResponseVO getStaff();

    /***
     * 更改管理员信息
     * @param staffVO
     * @return
     */
    ResponseVO updateStaff(StaffVO staffVO);

    /***
     * 删除一个管理员
     * @param staffVO
     * @return
     */
    ResponseVO deleteStaff(StaffVO staffVO);
}
