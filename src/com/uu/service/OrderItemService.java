package com.uu.service;

import com.uu.bean.OrderItem;

import java.util.List;



public interface OrderItemService {

	boolean addOrderItem(OrderItem orderItem);
	
	public List<OrderItem> findOrderItemsByOrderid(String oid) ;

	
}
