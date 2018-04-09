package com.uu.service;

import com.uu.bean.Product;
import com.uu.utils.Page;

import java.sql.SQLException;
import java.util.List;


 
public interface ProductService {


	Page findProductByMultiContdiction(String pid, String cid,
												String name, String minprice, String maxprice, String num) throws Exception;

	/**
	 * 增加商品
	 * @param product
	 * @return
	 */
	boolean addProduct(Product product);

	/**
	 * 删除商品
	 * @param pid
	 * @return
	 */
	boolean deleteProduct(String pid);

	/**
	 * 修改商品
	 * @param product
	 * @return
	 */
	boolean updateProduct(Product product);

	/**
	 * 根据商品名称查询商品
	 * @param pname
	 * @param pid
	 * @return
	 */
	List<Product> findProductByPname(String pname);

	/**
	 * 根据商品号查询商品
	 * @param pid
	 * @return
	 */
	Product findProductByPid(String pid);

	/**
	 * 根据商品名称或商品号查询商品
	 * @param pidOrPname
	 * @return
	 */
	List<Product> findProductByPnameOrPid(String pidOrPname);

	/**
	 * 查询所有商品
	 * @return
	 */
	List<Product> findAllProduct();

	/**
	 * 分页查询
	 * @param num
	 * @return
	 */
	Page findPageRecodes(String num);

	/**
	 * 查询最前面count条记录
	 * @return
	 */
	List<Product> selectTop(int count);

	/**
	 * 查询从指定索引处开始的count条记录
	 * @return
	 */
	List<Product> selectTop(int count,int start);

	/**
	 * 根据分类查找商品
	 * @param cid 分类id
	 * @return
	 */
	List<Product> findProductByCid(int cid);
}
