package com.uu.service.impl;

import com.uu.bean.Admin;
import com.uu.dao.AdminDao;
import com.uu.dao.impl.AdminDaoImpl;
import com.uu.service.AdminService;
import com.uu.utils.Page;
import com.uu.utils.TransactionManager;

import java.util.List;

public class AdminServiceImpl implements AdminService {


    private AdminDao adminDao = new AdminDaoImpl();

    public Admin login(String username, String password) {
        Admin admin = adminDao.findAdminByUsernameAndPassword(username, password);
        return admin;
    }

    public boolean regist(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("admin is null...");
        }
        TransactionManager.startTransaction();
        boolean addAdmin = adminDao.saveAdmin(admin);
        if (addAdmin) {
            TransactionManager.commit();
            return true;
        }
        TransactionManager.rollback();
        TransactionManager.release();
        return false;
    }

    public boolean updateAdminMsg(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("admin is null...");
        }
        TransactionManager.startTransaction();
        boolean updateAdmin = adminDao.updateAdminByAid(admin);
        if (updateAdmin) {
            TransactionManager.commit();
            return true;
        }
        TransactionManager.rollback();
        TransactionManager.release();
        return false;
    }

    public List<Admin> findAllAdmin() {
        List<Admin> admins = adminDao.findAllAdmin();
        return admins;
    }

    public Page findPageRecodes(String num) {
        int pageNum = 1;
        if(num != null){
            pageNum = Integer.parseInt(num);
        }
        int totalRecordNum = adminDao.findRecordCount();
       Page page = new Page(pageNum,totalRecordNum,2);
       List<Admin> records = adminDao.findPageRecords(page.getStartIndex(), page.getPageSize());
        page.setRecords(records);
        return page;
    }
}
