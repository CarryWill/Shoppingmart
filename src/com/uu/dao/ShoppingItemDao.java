package com.uu.dao;

import com.uu.bean.ShoppingItem;

import java.util.List;

public interface ShoppingItemDao {

	/**
	 * 添加商品到购物车项
	 * @param pid
	 * @param sid
	 * @param snum
	 * @return
	 */
	public int addShoppingItem(String pid, int sid, int snum);


	public List<ShoppingItem> findshoppingitemsbysid(String pid, int sid);
	/**
	 * 从购物车删除某一项
	 * @param itemid
	 * @return
	 */
	public int deleteShoppingItem(int itemid);
	public int updateShoppingItem(String pid, int sid, int snum);
}
