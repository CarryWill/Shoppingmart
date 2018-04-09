package com.uu.control;

import com.uu.bean.Product;
import com.uu.bean.ShoppingCar;
import com.uu.bean.ShoppingItem;
import com.uu.bean.User;
import com.uu.service.CartService;
import com.uu.service.ProductService;
import com.uu.service.ShoppingItemService;
import com.uu.service.impl.CartServiceImpl;
import com.uu.service.impl.ProductServiceImpl;
import com.uu.service.impl.ShoppingItemServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "CartServlet",urlPatterns = "/CartServlet")
public class CartServlet extends HttpServlet {
	ProductService productService =new ProductServiceImpl();
	ShoppingItemService shoppingItemService=new ShoppingItemServiceImpl();
	CartService cartService = new CartServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if("addCart".equals(op)){
			addCart(request,response);
		}else if("delItem".equals(op)){
			delItem(request,response);
		}else if("findCart".equals(op)){
			findCart(request,response);
		}
	}

	private void findCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		CartService cartService = new CartServiceImpl();
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			response.sendRedirect(request.getContextPath()+"/user/login.jsp");
			return ;
		}
		ShoppingCar shoppingCar = cartService.findCart(user.getUid());
		request.getSession().setAttribute("shoppingCar", shoppingCar);
		response.sendRedirect(request.getContextPath()+"/shoppingcart.jsp");
	}

	private void delItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String itemid = request.getParameter("itemid");
		String uid = request.getParameter("uid");
		ShoppingItemService shoppingItemService = new ShoppingItemServiceImpl();
		boolean result = shoppingItemService.deleteShoppingItem(Integer.parseInt(itemid));
		if(result){
			CartService cartService = new CartServiceImpl();
			ShoppingCar shoppingCar = cartService.findCart(Integer.parseInt(uid));
			request.getSession().setAttribute("shoppingCar", shoppingCar);
			request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
		}
	}

	private void addCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		String uid = request.getParameter("uid");
		String pid = request.getParameter("pid");
		String snumS = request.getParameter("snum");
		int snum = 1;
		if(snumS == null){
			snum = 1;
		}else{
			snum = Integer.parseInt(snumS);
		}


		ShoppingCar shoppingCar = cartService.findCart(Integer.parseInt(uid));

//		if(  ){
//			request.getSession().setAttribute("shoppingCar", shoppingCar);
//			request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
//			return;
//		}





        //List<ShoppingItem> shoppingItems=new ArrayList<ShoppingItem>();
        if(shoppingCar == null){
			boolean addCart = cartService.addCart(Integer.parseInt(uid));
			if(addCart){
				shoppingCar = cartService.findCart(Integer.parseInt(uid));
				boolean result = addItemToCart(pid,shoppingCar.getSid(),snum);
				if(result){
					shoppingCar = cartService.findCart(Integer.parseInt(uid));
                   // why(pid, shoppingCar,,snum);
					request.getSession().setAttribute("shoppingCar", shoppingCar);
					request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
				}
				return ;
			}else{
				response.sendRedirect(request.getContextPath()+"/MainServlet");
			}
		}

		boolean result = addItemToCart(pid,shoppingCar.getSid(),snum);
		if(result){
			shoppingCar = cartService.findCart(Integer.parseInt(uid));
            //why(pid,shoppingCar,snum);
            System.out.println(shoppingCar+"hhh");
			request.getSession().setAttribute("shoppingCar", shoppingCar);
			request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
		}
	}

    /*private void why(String pid, ShoppingCar shoppingCar, List<ShoppingItem> shoppingItems,int snum) {
        Product product = productService.findProductByPid(pid);
        ShoppingItem shoppingItem = new ShoppingItem();
    	shoppingItem.setSnum(snum);
        shoppingItem.setProduct(product);

		shoppingItems.add(shoppingItem);
        shoppingCar.setShoppingItems(shoppingItems);
    }*/

    private boolean addItemToCart(String pid,int sid,int snum){

        ShoppingItemService shoppingItemService = new ShoppingItemServiceImpl();
		boolean result = shoppingItemService.addShoppingItem(pid, sid, snum);
		if(result){
			return true;
		}
		return false;
	}

}
