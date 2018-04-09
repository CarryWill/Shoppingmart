package com.uu.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.uu.bean.Product;
import com.uu.bean.ShoppingCar;
import com.uu.bean.ShoppingItem;
import com.uu.dao.CartDao;
import com.uu.utils.MyC3PODataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;



public class CartDaoImpl implements CartDao {

	public ShoppingCar findCart(int uid) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select * from shoppingcar where uid=?";
			ShoppingCar shoppingCar = qr.query(sql, new BeanHandler<ShoppingCar>(ShoppingCar.class), uid);
			if(shoppingCar != null){
				sql = "select * from shoppingItem where sid=?";
				List<ShoppingItem> shoppingItems = qr.query(sql, new BeanListHandler<ShoppingItem>(ShoppingItem.class), shoppingCar.getSid());
				for(int i  = 0; i < shoppingItems.size(); i++){
					sql = "select * from product where pid=?";
					Product product = qr.query(sql, new BeanHandler<Product>(Product.class), shoppingItems.get(i).getPid());
					shoppingItems.get(i).setProduct(product);
				}
				shoppingCar.setShoppingItems(shoppingItems);
			}
//			System.out.println(shoppingCar);
			return shoppingCar;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int saveCar(int uid) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "insert into shoppingcar(uid) values(?)";
			int update = qr.update(sql,uid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
