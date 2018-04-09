package com.uu.service;

import java.util.List;


import com.uu.bean.Order;
import com.uu.utils.Page;

public interface OrderService {

	/**
	 * 下单
	 * @param order
	 * @return
	 */
	boolean placeOrder(Order order);
	
	/**
	 * 修改订单状态
	 * @param oid
	 * @param state
	 * @return 
	 */
	boolean updateOrderState(String oid, int state);
	
	boolean deleteOrder(String oid);
	
	/**
	 * 查询所有订单
	 * @return
	 */
	List<Order> findAllOrder();

	/**
	 * 根据订单号查询订单
	 * @param oid
	 */
	Order findOrderById(String oid);

	/**
	 * 分页查询
	 * @param num
	 * @return
	 */
	Page findPageRecodes(String num);
	
	/**
	 * 根据用户id查询订单
	 * @param uid
	 * @return
	 */
	List<Order> findOrderByUid(int uid);
	
}
