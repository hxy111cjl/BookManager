package com.oracle.service;
import javax.security.auth.login.LoginException;

import com.oracle.entity.User;
import com.oracle.mapper.UserDao;

public class UserService {
	private UserDao dao = new UserDao();
	// 注册操作
	public int register(User user) {
		// 调用dao完成注册操作
		return   dao.addUser(user);
	}
	// 登录操作
	public User login(String username, String password) throws LoginException {
		//根据登录时表单输入的用户名和密码，查找用户
		return dao.findUserByUsernameAndPassword(username, password);
	}
}
