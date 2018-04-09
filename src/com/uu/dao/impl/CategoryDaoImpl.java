package com.uu.dao.impl;

import com.uu.bean.Category;
import com.uu.dao.CategoryDao;
import com.uu.utils.MyC3PODataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {


    @Override
    public int findTotalNumber() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());

        Long query = (Long) qr.query("select count(*) from category", new ScalarHandler());

        return query.intValue();
    }

    @Override
    public List<Category> findPartCategory(int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());

        List<Category> query = qr.query("select * from category limit ? offset ?",
                new BeanListHandler<Category>(Category.class)
                ,limit,
                offset);

        return query;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        String sql = "update category set cname=? where cid=?";
        int update = queryRunner.update(sql, category.getCname(),category.getCid());
        return update==1;
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
        List<Category> query = qr.query("select * from category", new BeanListHandler<Category>(Category.class));

        return query;

    }


    @Override
    public boolean addCategory(String cname) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        int update = queryRunner.update("insert into category values (null,?);", cname);
        return update==1;
    }

    @Override
    public boolean deleteMultiGategory(int cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        int update = queryRunner.update("delete  from category where cid=?;",cid);
        return update==1;
    }
}
