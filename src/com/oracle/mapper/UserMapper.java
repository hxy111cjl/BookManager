package com.oracle.mapper;

import org.apache.ibatis.annotations.Param;
import com.oracle.entity.User;

public interface UserMapper {
	// 添加用户
	public int addUser(User user);
	//根据用户名与密码查找用户
	public User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
		 
}
