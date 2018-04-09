package com.uu.service;

import com.uu.bean.Category;
import com.uu.utils.Page;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
      public Page findPageRecodes(String num);
      boolean updateCategory(Category category)throws SQLException;
      List<Category> findAllCategory() throws SQLException;
      boolean addCategory(String cname) throws SQLException;
      boolean deleteMultiGategory(int cid) throws SQLException;
}
