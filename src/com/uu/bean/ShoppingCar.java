package com.uu.bean;

import java.io.Serializable;
import java.util.List;

public class ShoppingCar implements Serializable {
	
	private int sid;
	private int uid;
	
	private List<ShoppingItem> shoppingItems;
	
	/**
	 * 购物车id
	 * @return
	 */
	public int getSid() {
		return sid;
	}
	/**
	 * 购物车id
	 * @param sid
	 */
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	/**
	 * 用户id
	 * @return
	 */
	public int getUid() {
		return uid;
	}
	/**
	 * 用户id
	 * @param uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public List<ShoppingItem> getShoppingItems() {
		return shoppingItems;
	}
	public void setShoppingItems(List<ShoppingItem> shoppingItems) {
		this.shoppingItems = shoppingItems;
	}
	@Override
	public String toString() {
		return "ShoppingCar [sid=" + sid + ", uid=" + uid +", List<ShoppingItems>"+getShoppingItems()+ "]";
	}

}
