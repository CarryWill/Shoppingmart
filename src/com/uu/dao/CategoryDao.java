package com.uu.dao;

import com.uu.bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    int findTotalNumber() throws SQLException;

    List<Category> findPartCategory(int cagegoryNumPerPage, int i) throws SQLException;

    public  boolean updateCategory(Category category)throws SQLException;
    List<Category> findAllCategory() throws SQLException;
    public boolean addCategory(String cname) throws SQLException;
   public boolean deleteMultiGategory(int  cid) throws SQLException;
}
