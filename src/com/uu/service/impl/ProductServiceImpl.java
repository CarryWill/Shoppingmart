package com.uu.service.impl;

import com.uu.bean.Product;
import com.uu.dao.ProductDao;
import com.uu.dao.impl.ProductDaoImpl;
import com.uu.service.ProductService;
import com.uu.utils.Page;
import com.uu.utils.TransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


 
public class ProductServiceImpl implements ProductService {

	ProductDao dao = new ProductDaoImpl();

	@Override
	public Page findProductByMultiContdiction(String pid, String cid, String name, String minprice, String maxprice, String num) throws Exception {
		Page productsPage = null;

		//总记录数
		//查数据库
		minprice=(minprice.matches("\\d"))?minprice:"0";
		maxprice=(maxprice.matches("\\d"))?maxprice:"10000";
		int totalNumber=dao.queryCountbyMulitCondition(pid,cid,name,Integer.parseInt(minprice),Integer.parseInt(maxprice));

		//每一页的记录数


		//当前的页码
		productsPage = new Page(Integer.parseInt(num),totalNumber,4);

		//分页的参数
		int limit =4;//4
		int offset=(Integer.parseInt(num)-1)*limit;


		List<Product> products = dao.queryProductsbyMulitCondition(pid,cid,name,Integer.parseInt(minprice),Integer.parseInt(maxprice),limit,offset);

		productsPage.setRecords(products);

		return productsPage;
	}

	public boolean addProduct(Product product) {
		TransactionManager.startTransaction();
		int saveProduct = dao.saveProduct(product);
		if(saveProduct > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public boolean deleteProduct(String pid) {
		TransactionManager.startTransaction();
		int deleteProduct = dao.deleteProduct(pid);
		if(deleteProduct > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public boolean updateProduct(Product product) {
		TransactionManager.startTransaction();
		int updateProduct = dao.updateProduct(product);
		if(updateProduct > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public List<Product> findProductByPname(String pname) {
		List<Product> products = dao.findProductByPname(pname);
		return products;
	}

	public List<Product> findAllProduct() {
		List<Product> products = dao.findAllProduct();
		return products;
	}

	public Product findProductByPid(String pid) {
		Product product = dao.findProductByPid(pid);
		return product;
	}

	public List<Product> findProductByPnameOrPid(String pidOrPname) {
		List<Product> products = new ArrayList<Product>();
		List<Product> findProductByPname = dao.findProductByPname(pidOrPname);
		if(findProductByPname!=null){
			products.addAll(findProductByPname);
		}
		Product product = dao.findProductByPid(pidOrPname);
		if(product != null){
			products.add(product);
		}
		if(products.size() > 0){
			return products;
		}
		return null;
	}

	public Page findPageRecodes(String num){
		int pageNum = 1;
		if(num != null){
			pageNum = Integer.parseInt(num);
		}
		int totalRecordNum = dao.findRecordCount();
		Page page = new Page(pageNum,totalRecordNum,5);
		List<Product> records = dao.findPageRecords(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;

	}

	public List<Product> selectTop(int count) {
		List<Product> products = dao.findTop(count);
		return products;
	}

	public List<Product> selectTop(int count, int start) {
		List<Product> products = dao.findTop(count,start);
		return products;
	}

	public List<Product> findProductByCid(int cid) {
		List<Product> products = dao.findProductByCid(cid);
		return products;
	}



}
