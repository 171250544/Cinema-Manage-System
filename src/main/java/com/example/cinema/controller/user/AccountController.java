package com.example.cinema.controller.user;

import com.example.cinema.blImpl.user.AccountServiceImpl;
import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.vo.StaffVO;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author huwen
 * @date 2019/3/23
 */
@RestController()
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";
    @Autowired
    private AccountServiceImpl accountService;
    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm, HttpSession session){
        UserVO user = accountService.login(userForm);
        if(user==null){
           return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //注册session
        session.setAttribute(InterceptorConfiguration.SESSION_KEY,userForm);
        return ResponseVO.buildSuccess(user);
    }
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserForm userForm){
        return accountService.registerAccount(userForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }

    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    public ResponseVO getStaffs(){
        return accountService.getStaff();
    }

    @RequestMapping(value = "/staff/update", method = RequestMethod.POST)
    public ResponseVO updateStaff(@RequestBody StaffVO staffVO){
        return accountService.updateStaff(staffVO);
    }

    @RequestMapping(value = "/staff/delete", method = RequestMethod.POST)
    public ResponseVO deleteStaff(@RequestBody StaffVO staffVO){
        return accountService.deleteStaff(staffVO);
    }
}
