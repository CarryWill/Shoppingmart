package com.uu.service.impl;


import com.uu.bean.ShoppingItem;
import com.uu.dao.ShoppingItemDao;
import com.uu.dao.impl.ShoppingItemDaoImpl;
import com.uu.service.ShoppingItemService;
import com.uu.utils.TransactionManager;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ShoppingItemServiceImpl implements ShoppingItemService {

	private ShoppingItemDao shoppingItemDao = new ShoppingItemDaoImpl();



	public boolean addShoppingItem(String pid, int sid, int snum) {

		TransactionManager.startTransaction();
		ShoppingItem shoppingItem = null;
		List<ShoppingItem> shoppingItems=shoppingItemDao.findshoppingitemsbysid(pid,sid);
		for(int i=0;i<shoppingItems.size();i++){
			 shoppingItem=(ShoppingItem) shoppingItems.get(i);
		}
		if(shoppingItem!=null){
				snum=shoppingItem.getSnum()+snum;
				return updateShoppingitem(pid,sid,snum);
			}




		int addShoppingItem = shoppingItemDao.addShoppingItem(pid, sid, snum);
		if (addShoppingItem > 0) {
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}
	public boolean updateShoppingitem(String pid, int sid, int snum){
		TransactionManager.startTransaction();
		int updateShoppingItem = shoppingItemDao.updateShoppingItem(pid,sid,snum);
		if (updateShoppingItem > 0) {
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public boolean deleteShoppingItem(int itemid) {

		TransactionManager.startTransaction();
		int deleteShoppingItem = shoppingItemDao.deleteShoppingItem(itemid);
		if (deleteShoppingItem > 0) {
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

}
