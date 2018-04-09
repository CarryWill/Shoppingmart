package com.uu.service.impl;

import com.uu.bean.OrderItem;
import com.uu.dao.OrderItemDao;
import com.uu.dao.impl.OrderItemDaoImpl;
import com.uu.service.OrderItemService;
import com.uu.utils.TransactionManager;

import java.util.List;



public class OrderItemServiceImpl implements OrderItemService {

	private OrderItemDao orderItemDao = new OrderItemDaoImpl();
	
	public boolean addOrderItem(OrderItem orderItem) {
		TransactionManager.startTransaction();
		int placeOrder = orderItemDao.saveOrderItem(orderItem);
		if(placeOrder > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public List<OrderItem> findOrderItemsByOrderid(String oid) {
		// TODO Auto-generated method stub
		return orderItemDao.findOrderItemsByOrderid(oid);
		
		
	}

}
