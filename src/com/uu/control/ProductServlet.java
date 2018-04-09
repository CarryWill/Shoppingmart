package com.uu.control;

import com.uu.bean.Product;
import com.uu.service.ProductService;
import com.uu.service.impl.ProductServiceImpl;
import com.uu.utils.FillDataBean;
import com.uu.utils.Page;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ProductServlet",urlPatterns = "/admin/ProductServlet")
public class ProductServlet extends HttpServlet {

	ProductService productService =new ProductServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		 String op = request.getParameter("op");
		  
		  if (op!=null) {
			  
			  switch (op) {
				  case "updateProduct":
					  updateProduct(request, response);
				  	break;


			  case "findAllProduct":
				  findAllProduct(request,response);
					break;

				  case "findProductByPId":
					  findProductByPid(request,response);
					  break;

			case "deleteOneProduct":
				deleteOneProduct(request, response);
				break;

			case "showEditOneProduct":
				showEditOneProduct(request, response);
				break;	
				
			case "deleteMulti":
				deleteMulti(request,response);
				break;	
			 
			case "multiCondictionSearch":
				multiCondictionSearch(request, response);
				break;

				  case "findProductsbyCid":
					  findProductsByCid(request, response);
					  break;
				  case "findProByName":
					  findProByName(request,response);
				  	break;

			default:
				break;
			}
			
		}

	}

	private void multiCondictionSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String name = request.getParameter("pname");
		String minprice = request.getParameter("minprice");
		String maxprice = request.getParameter("maxprice");

		String num = request.getParameter("num");
		if (num==null||num.isEmpty()){
			num="1";
		}

		try {
			Page page=	productService.findProductByMultiContdiction(pid,cid,name,minprice,maxprice,num);

			request.setAttribute("page", page);
			request.setAttribute("pid", pid);
			request.setAttribute("cid", cid);
			request.setAttribute("pname", name);
			request.setAttribute("minprice", minprice);
			request.setAttribute("maxprice", maxprice);

			request.getRequestDispatcher("/admin/product/searchproductList.jsp").forward(request, response);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			response.getWriter().println("多条件查询失败，请重新填写条件!<br>"+e.getMessage());

			response.setHeader("refresh", "3;url="+request.getServletContext().getContextPath()+"/admin/product/searchProduct.jsp");

		}
	}



	private void showEditOneProduct(HttpServletRequest request, HttpServletResponse response) {

	}




	private void findProByName(HttpServletRequest request,
							   HttpServletResponse response) throws ServletException, IOException{
		request.removeAttribute("products");
		String pname = request.getParameter("pname");
		if(pname == null){
			request.getRequestDispatcher("/products.jsp").forward(request, response);
			return ;
		}

		List<Product> products = productService.findProductByPname(pname);
		request.setAttribute("products", products);
		request.getRequestDispatcher("/products.jsp").forward(request, response);
	}

	private void findProductsByCid(HttpServletRequest request,
								  HttpServletResponse response) throws ServletException, IOException {
		request.removeAttribute("products");
		String cidStr = request.getParameter("cid");
		int cid = Integer.parseInt(cidStr.trim());
		//ProductService productService = new ProductServiceImpl();
		List<Product> products = productService.findProductByCid(cid);
		System.out.println(products);
		request.setAttribute("products", products);
		request.getRequestDispatcher("/products.jsp").forward(request, response);
	}

	private void findProductByPid(HttpServletRequest request,
								 HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");

		Product product = productService.findProductByPid(pid);

		request.setAttribute("product", product);
		request.getRequestDispatcher("/productdetail.jsp").forward(request, response);
		return ;
	}

	private void deleteMulti(HttpServletRequest request,
							 HttpServletResponse response) throws ServletException, IOException {
		String[] pids = request.getParameterValues("pid");
		if (pids == null || pids.length == 0) {
			response.getWriter().write("商品删除失败");
			response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");
			return;
		}

		for (int i = 0; i < pids.length; i++) {
			productService.deleteProduct(pids[i]);
		}
		response.getWriter().write("商品删除成功<br/>");
		response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");
	}

	private void deleteOneProduct(HttpServletRequest request,
						   HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");

		boolean delete = productService.deleteProduct(pid);
		if (delete) {
			//删除成功
			response.getWriter().write("商品删除成功<br/>");
			response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");
			return;
		} else {
			response.sendRedirect(request.getContextPath()+"/admin/product/productList.jsp");
			return;
		}
	}

	private void updateProduct(HttpServletRequest request,
							   HttpServletResponse response) throws ServletException, IOException {
		String oldimgurl=request.getParameter("oldimgurl");
		Product product = FillDataBean.fillData(Product.class, request);
		if(product.getImgurl().length()==37) {
			product.setImgurl(oldimgurl);
		}
		boolean update = productService.updateProduct(product);
		if (update) {
			//更新成功
			System.out.println("更新成功");
			response.sendRedirect(request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");
			return;
		} else {
			//更新失败
			System.out.println("更新失败");
			response.sendRedirect(request.getContextPath()+"/CategoryServlet?op=findCategoryByUpdate");
			return;
		}
	}

	private void findAllProduct(HttpServletRequest request,
								HttpServletResponse response) throws ServletException, IOException {

		String num = request.getParameter("num");
		Page products = productService.findPageRecodes(num);
		request.setAttribute("page", products);
		request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);
		return ;
	}

}
