package com.uu.service.impl;


import com.uu.bean.ShoppingCar;
import com.uu.dao.CartDao;
import com.uu.dao.impl.CartDaoImpl;
import com.uu.service.CartService;
import com.uu.utils.TransactionManager;

public class CartServiceImpl implements CartService {

	private CartDao cartDao = new CartDaoImpl();
	
	public ShoppingCar findCart(int uid) {
		ShoppingCar sc = cartDao.findCart(uid);
		return sc;
	}
	
	public boolean addCart(int uid) {
		TransactionManager.startTransaction();
		int saveCar = cartDao.saveCar(uid);
		if(saveCar > 0){
			TransactionManager.commit();
			TransactionManager.release();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	

}
