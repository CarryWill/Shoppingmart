package com.uu.control;

import com.uu.bean.Category;
import com.uu.bean.Product;
import com.uu.service.CategoryService;
import com.uu.service.ProductService;
import com.uu.service.impl.CategoryServiceImpl;
import com.uu.service.impl.ProductServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "MainServlet",urlPatterns = "/MainServlet")
public class MainServlet extends HttpServlet {

	
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		System.out.println("MainServlet.doPost() start from here!");

		try {
			Hope(request);
			System.out.println("开始转发！");
//			response.sendRedirect("/index.jsp");
			request.getRequestDispatcher("/index.jsp").forward(request,response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}


	}

	private void Hope(HttpServletRequest request) throws SQLException {
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.findAllCategory();
		request.getSession().setAttribute("categories", categories);

		ProductService productService = new ProductServiceImpl();
		List<Product> productTop = productService.selectTop(4);
		request.getSession().setAttribute("productTop", productTop);

		List<Product> hotProducts = productService.selectTop(6, 5);
		request.getSession().setAttribute("hotProducts", hotProducts);
	}

}
