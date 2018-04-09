package com.uu.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.uu.bean.ShoppingItem;
import com.uu.dao.ShoppingItemDao;
import com.uu.utils.MyC3PODataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public class ShoppingItemDaoImpl implements ShoppingItemDao {

	public int addShoppingItem(String pid, int sid, int snum) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "insert into shoppingitem(pid,sid,snum) values(?,?,?);";

			int update = qr.update(sql,pid,sid,snum);

	    	return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

public List<ShoppingItem> findshoppingitemsbysid(String pid, int sid){
	try {
	QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
	String sql = "select * from shoppingitem where pid=? and sid=?";
	List<ShoppingItem> shoppingItems= qr.query(sql, new BeanListHandler<ShoppingItem>(ShoppingItem.class),pid,sid);
		return shoppingItems;
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}

}


	public int deleteShoppingItem(int itemid) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "delete from shoppingitem where itemid=?";
			int update = qr.update(sql,itemid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateShoppingItem(String pid, int sid, int snum) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "update shoppingitem set snum=? where sid=? and pid=?";
			int update = qr.update(sql,snum,sid,pid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
