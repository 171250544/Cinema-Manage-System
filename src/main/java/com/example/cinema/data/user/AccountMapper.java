package com.example.cinema.data.user;

import com.example.cinema.po.User;
import com.example.cinema.vo.StaffVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @return
     */
    public int createNewAccount(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
    public User getAccountByName(@Param("username") String username);

    /**
     * 根据用户ID查找账号
     * @param userid
     * @return
     */
    public User getAccountByID(@Param("userid") int userid);
    public List<User> getAllStaff();

    public int updateStaff(StaffVO staffVO);

    public int deleteStaff(StaffVO staffVO);
}
