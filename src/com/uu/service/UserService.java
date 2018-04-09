package com.uu.service;

import com.uu.bean.User;
import com.uu.utils.Page;
import com.uu.utils.bean.UserFormBean;

public interface UserService {

	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return User 对象
	 */
	User login(String username, String password);
	
	/**
	 * 用户注册
	 * @param user User对象
	 * @return 是否成功
	 */
	boolean regist(User user);
	
	/**
	 * 用户信息修改（仅限修改昵称，密码，邮箱和出生日期）
	 * @param user User对象
	 * @return 是否成功
	 */
	boolean updateUserMsg(User user);
		boolean active(String activecode);
	Page findPageRecodes(String num);
	
	
	
}
