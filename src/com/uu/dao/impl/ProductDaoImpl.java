package com.uu.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.uu.bean.Category;
import com.uu.bean.Product;
import com.uu.dao.ProductDao;
import com.uu.utils.MyC3PODataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;



     
public class ProductDaoImpl implements ProductDao {


	@Override
	public int queryCountbyMulitCondition(String pid, String cid, String name, int minprice, int maxprice) throws Exception {
		QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());

		String sql= "select count(*) from product where 1=1 ";

		ArrayList parameterlist= new ArrayList();

		if (pid!=null&&!pid.isEmpty()){
			sql =sql +" and pid = ?";
			parameterlist.add(pid);
		}
		if(cid!=null&&!cid.isEmpty()){
			sql =sql +" and cid = ?";
			parameterlist.add(cid);

		}
		if(name!=null&&!name.isEmpty()){
			sql =sql +" and pname = ?";
			parameterlist.add(name);
		}
		if (minprice >0 ){
			sql =sql +" and estoreprice >= ?";
			parameterlist.add(minprice);
		}
		if (maxprice >0 ){
			sql =sql +" and estoreprice <= ?";
			parameterlist.add(maxprice);
		}

		System.out.println("sql=" +sql);
		Long num =(Long) queryRunner.query(sql,
				new ScalarHandler(),
				parameterlist.toArray());

		return  num.intValue();
	}

	@Override
	public List<Product> queryProductsbyMulitCondition(String pid, String cid, String name, int minpriceint, int maxpriceint, int limit, int offset) throws Exception {
		QueryRunner qRunner=new QueryRunner(MyC3PODataSouce.getDataSource());


		String sql = "select * from product  where 1=1";

		ArrayList parmsArrayList = new ArrayList<>();

		if (pid!=null&&!pid.isEmpty()){

			sql=sql+" and pid = ?" ;
			parmsArrayList.add(pid);

		}
		if (cid!=null&&!cid.isEmpty()){

			sql=sql+" and cid =? ";
			parmsArrayList.add(cid);

		}

		if (name!=null&&!name.isEmpty()){

			sql=sql+" and pname like   ?  ";
			parmsArrayList.add("%"+name+"%");

		}

		if (minpriceint!=-1) {
			sql=sql+" and estoreprice > ?";
			parmsArrayList.add(minpriceint);

		}

		if (maxpriceint!=-1) {
			sql=sql+" and estoreprice < ?";
			parmsArrayList.add(maxpriceint);

		}


		sql=sql+" limit ? offset ?";
		parmsArrayList.add(limit);
		parmsArrayList.add(offset);



		Object[] array = parmsArrayList.toArray();

		//满足条件的所有商品
		List<Product> products = qRunner.query(sql, new BeanListHandler<Product>(Product.class),array);


		//关联每一个商品的category信息
		if(products != null || products.size() > 0){
			for(int i = 0; i < products.size(); i++){
				sql = "select * from category where cid=?";
				int cid_int = products.get(i).getCid();
				Category category = qRunner.query(sql, new BeanHandler<Category>(Category.class),cid_int);
				products.get(i).setCategory(category);
			}
		}

		return products ;
	}

	public int saveProduct(Product product) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "insert into product(pid,pname,estoreprice,markprice,pnum,cid,imgurl,description) values(?,?,?,?,?,?,?,?)";
			int update = qr.update(sql, product.getPid(), product.getPname(), product.getEstoreprice(), product.getMarkprice(),
					product.getPnum(), product.getCid(), product.getImgurl(), product.getDescription());
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int deleteProduct(String pid) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "delete from product where pid=?";
			int update = qr.update(sql, pid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int updateProduct(Product product) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "update product set pname=?,estoreprice=?,markprice=?,pnum=?,cid=?,imgurl=?,description=? where pid=?";
			int update = qr.update(sql, product.getPname(), product.getEstoreprice(), product.getMarkprice(),
					product.getPnum(), product.getCid(), product.getImgurl(), product.getDescription(), product.getPid());
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findProductByPname(String pname) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select * from product where pname like ?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class), "%"+pname+"%");

			System.out.println("ProductDaoImpl.findProductByPname()" +products);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findAllProduct() {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select * from product";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class));
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Product findProductByPid(String pid) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select * from product where pid=?";
			Product product = qr.query(sql, new BeanHandler<Product>(Product.class), pid);
			return product;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int findRecordCount() {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select count(pid) count from product";
			List query = qr.query(sql,new ColumnListHandler("count"));
			return Integer.parseInt(query.get(0).toString());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findPageRecords(int startIndex, int recordCount) {
		try {
			String sql = "select * from product limit ?,?";
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class), startIndex,recordCount);
			if(products != null || products.size() > 0){
				for(int i = 0; i < products.size(); i++){
					sql = "select * from category where cid=?";
					int cid = products.get(i).getCid();
					Category category = qr.query(sql, new BeanHandler<Category>(Category.class),cid);
					products.get(i).setCategory(category);
				}
			}
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e+"...查询失败");
		}
	}

	public List<Product> findTop(int count) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select * from product limit ?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class),count);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findTop(int count, int start) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select * from product limit ?,?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class),start,count);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findProductByCid(int cid) {
		try {
			QueryRunner qr = new QueryRunner(MyC3PODataSouce.getDataSource());
			String sql = "select * from product where cid=?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class),cid);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}