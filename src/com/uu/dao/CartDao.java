package com.uu.dao;


import com.uu.bean.ShoppingCar;

public interface CartDao {

	ShoppingCar findCart(int uid);
	
	int saveCar(int uid);
	
	
	
}
