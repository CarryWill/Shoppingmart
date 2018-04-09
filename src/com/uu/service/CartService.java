package com.uu.service;


import com.uu.bean.ShoppingCar;

public interface CartService {

	/**
	 * 根据用户id查询购物车
	 * @param uid
	 * @return
	 */
	ShoppingCar findCart(int uid);
	
	/**
	 * 添加商品到购物车
	 * @param 用户id
	 * @return
	 */
	boolean addCart(int uid);
	
}
