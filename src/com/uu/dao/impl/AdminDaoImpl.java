package com.uu.dao.impl;

import com.uu.bean.Admin;
import com.uu.dao.AdminDao;
import com.uu.utils.MyC3PODataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
    public Admin findAdminByUsernameAndPassword(String username, String password) {
        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "select * from admin where username=? and password=?";
            Admin admin = qr.query(sql, new BeanHandler<Admin>(Admin.class), username,password);
            if(admin != null){
                return admin;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveAdmin(Admin admin) {
        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "insert into admin(username,password) values(?,?)";
            int update = qr.update(sql, admin.getUsername(), admin.getPassword());
            if(update > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateAdminByAid(Admin admin) {
        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "update admin set username=?,password=? where aid=?";
            int update = qr.update(sql, admin.getUsername(), admin.getPassword(), admin.getAid());
            if(update > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Admin> findAllAdmin() {
        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "select * from admin";
            List<Admin> admins = qr.query(sql, new BeanListHandler<Admin>(Admin.class));
            return admins;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Admin> findPageRecords(int startIndex, int recordCount) {
        try {
            String sql = "select * from admin limit ?,?";
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            List<Admin> admins = qr.query(sql, new BeanListHandler<Admin>(Admin.class), startIndex,recordCount);
            return admins;
        } catch (SQLException e) {
            throw new RuntimeException(e+"...查询失败");
        }
    }

    public int findRecordCount() {
        try {
            QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
            String sql = "select count(*) count from admin";
            List query = qr.query(sql, new ColumnListHandler("count"));
            return Integer.parseInt(query.get(0).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
