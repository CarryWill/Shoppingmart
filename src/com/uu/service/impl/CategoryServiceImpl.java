package com.uu.service.impl;



import com.uu.bean.Category;
import com.uu.dao.CategoryDao;
import com.uu.dao.impl.CategoryDaoImpl;
import com.uu.service.CategoryService;
import com.uu.utils.Page;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();


    public List<Category> findAllCategory() {
        List<Category> categories = null;
        try {
            categories = categoryDao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }



    public Page findPageRecodes(String num) {
        //        private  int totalRecordsNum;  //总记录数
        int totalNumber= 0;
        try {
            totalNumber = categoryDao.findTotalNumber();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        当前处于第几页. 请求参数里应该告诉后台要去显示的页码
        int num_int = Integer.parseInt(num);
        Page page = new Page(num_int,totalNumber,4);


//        private int totalPageNum; //总的页码数
        int i = totalNumber /page.getPageSize();
        int totalPageNum=   totalNumber%page.getPageSize()==0?i:i+1;
        page.setTotalPageNum(totalPageNum);

        //查询当前页的数据集合

        List<Category> categories= null;
        try {
            categories = categoryDao.findPartCategory(page.getPageSize(),(num_int-1)*page.getPageSize());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        page.setRecords(categories);

        return page;

    }



    @Override
    public boolean updateCategory(Category category) throws SQLException {
        return categoryDao.updateCategory(category);
    }


    @Override
    public boolean addCategory(String cname) throws SQLException {

        return categoryDao.addCategory(cname);
    }

    @Override
    public boolean deleteMultiGategory(int cid) throws SQLException {
        return categoryDao.deleteMultiGategory(cid);
    }



}
