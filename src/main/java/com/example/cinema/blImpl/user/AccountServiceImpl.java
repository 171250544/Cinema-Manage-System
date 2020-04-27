package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.User;
import com.example.cinema.vo.StaffVO;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService ,accountServiceForBL{
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            accountMapper.createNewAccount(userForm.getUsername(), userForm.getPassword());
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return new UserVO(user);
    }

    /***
     * 获取所有职员的信息
     * @return
     */
    @Override
    public ResponseVO getStaff() {
        try {
            List<StaffVO> res = new ArrayList<>();
            List<User> staffs = accountMapper.getAllStaff();
//            List<User> staffs = getAllStaff();

            if (staffs.size() != 0) {
                for (User user : staffs) {
                    res.add(new StaffVO(user));
                }
            } else {
                return ResponseVO.buildFailure("没有更多管理员!");
            }
            return ResponseVO.buildSuccess(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /***
     * 更新管理员信息
     * @param staffVO 管理员vo
     * @return
     */
    @Override
    public ResponseVO updateStaff(StaffVO staffVO){
        try{
            //数据库是否匹配并更改信息成功
            if(accountMapper.updateStaff(staffVO) == 1) {
                return ResponseVO.buildSuccess();
            } else {
                return ResponseVO.buildFailure("用户名不存在或对方已是管理员");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("用户名已存在");
        }
    }

    /***
     * 将一个管理员账号改为观众
     * @param staffVO 管理员信息
     * @return
     */
    @Override
    public ResponseVO deleteStaff(StaffVO staffVO){
        try{
            //数据库是否匹配并更改角色成功
            if(accountMapper.deleteStaff(staffVO) == 1){
                return ResponseVO.buildSuccess();
            } else {
                return ResponseVO.buildFailure("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildSuccess("失败");
        }
    }

    @Override
    public User getAccountByID(int userId) {
        return accountMapper.getAccountByID(userId);
    }
    /**--------------------------------以下为此类中依赖的Mapper方法的桩-----------------------------------------------**/
//    List<User> getAllStaff() {
//        ArrayList<User> arrayList = new ArrayList<>();
//        arrayList.add(new User(1, "黄健", "123", "customer"));
//        arrayList.add(new User(2, "袁洋", "123", "admin"));
//        return arrayList;
//    }
}


