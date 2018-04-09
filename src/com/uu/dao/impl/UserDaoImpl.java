package com.uu.dao.impl;

import com.uu.bean.User;
import com.uu.dao.UserDao;
import com.uu.utils.MyC3PODataSouce;
import com.uu.utils.bean.UserFormBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {

        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "select * from user where username=? and password=?";
            User user = qr.query(sql, new BeanHandler<User>(User.class), username,password);

            System.out.println("UserDaoImpl.findUserByUsernameAndPassword():" +user);
            if(user != null){
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveUser(User user) {

        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "insert into user(username,nickname,password,email,birthday,updatetime,state,activecode) values(?,?,?,?,?,?,?,?)";
            int update = qr.update(sql, user.getUsername(),user.getNickname(),user.getPassword(),user.getEmail(),user.getBirthday(),user.getUpdatetime(),user.getState(),user.getActivecode());
            if(update > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "update user set nickname=?,password=?,email=?,birthday=?,updatetime=? where uid=? ";
            int update = qr.update(sql,user.getNickname(),user.getPassword(),user.getEmail(),user.getBirthday(),user.getUpdatetime(),user.getUid());
            System.out.println(user);
            if(update > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAllUser() {
        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "select * from user";
            List<User> users = qr.query(sql, new BeanListHandler<User>(User.class));
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findRecordCount() {
        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "select count(*) count from user";
            List query = qr.query(sql, new ColumnListHandler("count"));
            return Integer.parseInt(query.get(0).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findPageRecords(int startIndex, int pageSize) {

        try {
            String sql = "select * from user limit ?,?";
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            List<User> users = qr.query(sql, new BeanListHandler<User>(User.class), startIndex,pageSize);
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e+"...查询失败");
        }
    }

    @Override
    public boolean findActiveCode(String activecode) {
        QueryRunner qr = null;
        try {
            qr = new QueryRunner(MyC3PODataSouce.getDataSource());
        String sql = "select * from user where activecode=? ";
        User user = qr.query(sql, new BeanHandler<User>(User.class), activecode);

        System.out.println("UserDaoImpl.findUserByUsernameAndPassword():" +user);
        if(user != null){
            int update = qr.update("update user set state=1 where activecode=?",user.getActivecode());
            return update>0;
        }
            return false;
    } catch (SQLException e) {
            throw new RuntimeException(e+"...查询失败");
        }
    }
}
