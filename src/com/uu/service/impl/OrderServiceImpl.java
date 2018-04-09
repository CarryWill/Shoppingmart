package com.uu.service.impl;

import java.util.List;


import com.uu.bean.Order;
import com.uu.dao.OrderDao;
import com.uu.dao.OrderItemDao;
import com.uu.dao.impl.OrderDaoImpl;
import com.uu.dao.impl.OrderItemDaoImpl;
import com.uu.service.OrderService;
import com.uu.utils.Page;
import com.uu.utils.TransactionManager;

public class OrderServiceImpl implements OrderService {
	
	private OrderDao orderDao = new OrderDaoImpl();
	private OrderItemDao orderItemDao = new OrderItemDaoImpl();

	public boolean placeOrder(Order order) {
		TransactionManager.startTransaction();
		int placeOrder = orderDao.placeOrder(order);
		if(placeOrder > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public boolean updateOrderState(String oid, int state) {
		TransactionManager.startTransaction();
		int updateOrder = orderDao.updateOrderStateByOid(oid, state);
		if(updateOrder > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}
	
	public Order findOrderById(String oid) {
		Order findOrderById = orderDao.findOrderById(oid);
		return findOrderById;
	}

	public List<Order> findAllOrder() {
		List<Order> orders = orderDao.findAllOrder();
		return orders;
	}

	public Page findPageRecodes(String num) {
		int pageNum = 1;
		if(num != null){
			pageNum = Integer.parseInt(num);
		}
		int totalRecordNum = orderDao.findRecordCount();
		Page page = new Page(pageNum,totalRecordNum,5);
		List<Order> records = orderDao.findPageRecords(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;
	}

	public List<Order> findOrderByUid(int uid) {
		List<Order> orders = orderDao.findOrderByUid(uid);
		return orders;
	}

	public boolean deleteOrder(String oid) {
		// TODO Auto-generated method stub
		TransactionManager.startTransaction();
		
		int delete = orderItemDao.deleteOrderItemByOid(oid);		
		int placeOrder = orderDao.deleteOrderByOid(oid); 
		
		if(delete>0&&placeOrder > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

}
