package com.uu.service.impl;

import com.uu.bean.User;
import com.uu.dao.UserDao;
import com.uu.dao.impl.UserDaoImpl;
import com.uu.service.UserService;
import com.uu.utils.Page;
import com.uu.utils.SendJMail;
import com.uu.utils.TransactionManager;
import com.uu.utils.bean.UserFormBean;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public User login(String username, String password) {
        User user = userDao.findUserByUsernameAndPassword(username, password);
        return user;
    }




    public List<User> findAllUser() {
        List<User> users = userDao.findAllUser();
        return users;
    }
    @Override
    public boolean regist(User user) {
        if(user == null){
            throw new IllegalArgumentException("用户为空");
        }
        TransactionManager.startTransaction();
        //发送邮件
        String activecode= UUID.randomUUID().toString();
        user.setActivecode(activecode);
        String content="<a href='http://localhost:8080/user/UserServlet?op=activeUser&activecode="+activecode+"'>点击我激活你注册的用户</a>";
        SendJMail.sendMail(user.getEmail(),content);


        userDao.saveUser(user);
        TransactionManager.commit();
        TransactionManager.release();
        return true;
    }

    @Override
    public boolean updateUserMsg(User user) {

        if(user == null){
            throw new IllegalArgumentException("用户为空");
        }
        TransactionManager.startTransaction();
        userDao.updateUser(user);
        TransactionManager.commit();
        TransactionManager.release();
        return true;
    }

    @Override
    public boolean active(String activecode) {
        return userDao.findActiveCode(activecode);
    }

    @Override
    public Page findPageRecodes(String num) {
        int pageNum = 1;
        if(num != null){
            pageNum = Integer.parseInt(num);
        }
        int totalRecordNum = userDao.findRecordCount();
        Page page = new Page(pageNum,totalRecordNum,3);
        List<User> records = userDao.findPageRecords(page.getStartIndex(), page.getPageSize());
        page.setRecords(records);
        return page;
    }
}
