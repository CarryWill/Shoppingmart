package com.uu.dao.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.uu.bean.Order;
import com.uu.bean.OrderItem;
import com.uu.bean.User;
import com.uu.dao.OrderDao;
import com.uu.utils.MyC3PODataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;



public class OrderDaoImpl implements OrderDao {

	public int placeOrder(Order order) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "insert into `order`(oid,money,recipients,tel,address,state,ordertime,uid) values(?,?,?,?,?,?,?,?)";
			int update = qr.update(sql, order.getOid(), order.getMoney(), order.getRecipients(), order.getTel(),
					order.getAddress(), order.getState(), new Date(order.getOrdertime().getTime()), order.getUid());
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int updateOrderStateByOid(String oid, int state) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "update `order` set state=? where oid=?";
			int update = qr.update(sql, state, oid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Order findOrderById(String oid) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select * from `order` where oid=?";
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
			if(order != null){
				return order;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Order> findAllOrder() {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select * from `order`";
			List<Order> order = qr.query(sql, new BeanListHandler<Order>(Order.class));
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int findRecordCount() {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select count(*) count from `order`";
			List query = qr.query(sql,new ColumnListHandler("count"));
			return Integer.parseInt(query.get(0).toString());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Order> findPageRecords(int startIndex, int pageSize) {
		try {
			String sql = "select * from `order` limit ?,?";
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			List<Order> orders = qr.query(sql, new BeanListHandler<Order>(Order.class), startIndex, pageSize);
			if(orders != null || orders.size() > 0){
				for(int i = 0; i < orders.size(); i++){
					sql = "select * from user where uid=?";
					int uid = orders.get(i).getUid();
					User user = qr.query(sql, new BeanHandler<User>(User.class),uid);
					orders.get(i).setUser(user);
				}
			}
			return orders;
		} catch (SQLException e) {
			throw new RuntimeException(e+"...查询失败");
		}
	}
	
	public List<Order> findPageRecords(int startIndex, int pageSize, int uid) {
		try {
			String sql = "select * from `order` where uid=? limit ?,?";
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			List<Order> orders = qr.query(sql, new BeanListHandler<Order>(Order.class), uid, startIndex, pageSize);
			if(orders != null || orders.size() > 0){
				for(int i = 0; i < orders.size(); i++){
					sql = "select * from user where uid=?";
					uid = orders.get(i).getUid();
					User user = qr.query(sql, new BeanHandler<User>(User.class),uid);
					orders.get(i).setUser(user);
				}
			}
			return orders;
		} catch (SQLException e) {
			throw new RuntimeException(e+"...查询失败");
		}
	}

	public List<Order> findOrderByUid(int uid) {
		try {
			String sql = "select * from `order` where uid=?";
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			List<Order> orders = qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
			if(orders != null || orders.size() > 0){
				for(int i = 0; i < orders.size(); i++){
					sql = "select * from orderitem where oid=?";
					String oid = orders.get(i).getOid();
					List<OrderItem> orderitems = qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class),oid);
					orders.get(i).setOrderitems(orderitems);
				}
			}
			return orders;
		} catch (SQLException e) {
			throw new RuntimeException(e+"...查询失败");
		}
	}

	public int deleteOrderByOid(String oid) {
		// TODO Auto-generated method stub
		
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String deleteOrder_sql = "delete from `order` where oid=?";
			int delete = qr.update(deleteOrder_sql,   oid);
			return delete;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		 
	}

}
