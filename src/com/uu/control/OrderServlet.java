package com.uu.control;

import com.uu.bean.*;
import com.uu.service.OrderItemService;
import com.uu.service.OrderService;
import com.uu.service.ShoppingItemService;
import com.uu.service.impl.OrderItemServiceImpl;
import com.uu.service.impl.OrderServiceImpl;
import com.uu.service.impl.ShoppingItemServiceImpl;
import com.uu.utils.OrderIdUtils;
import com.uu.utils.Page;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OrderServlet",urlPatterns = "/OrderServlet")
public class OrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		String op = request.getParameter("op");
		if ("findAllOrder".equals(op)) {
			findAllOrder(request, response);
		} else if ("placeOrder".equals(op)) {
			processOrder(request, response);
		} else if ("myoid".equals(op)) {
			myOrder(request, response);
		} else if ("delOrder".equals(op)) {
			delOrder(request, response);
		}  else if ("cancelOrder".equals(op)) {
			cancelOrder(request, response);
		} else if ("orderDetail".equals(op)) {
		    showOrderDetial(request, response);
		    
		}
		
		else if ("lgout".equals(op)) {
			// lgout(request, response);
		}
	}

	
	private void cancelOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String stateStr = request.getParameter("state");
		OrderService orderService = new OrderServiceImpl();
		boolean result = orderService.updateOrderState(oid, Integer.parseInt(stateStr));
		if(result){
			User user = (User) request.getSession().getAttribute("user");
			List<Order> orders = orderService.findOrderByUid(user.getUid());
			request.setAttribute("orders", orders);
//			response.sendRedirect(request.getContextPath()+"/myOrders.jsp");
			request.getRequestDispatcher("/myOrders.jsp").forward(request, response);
			return ;
		}
	}
	
	
	private void delOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		OrderService orderService = new OrderServiceImpl();
		boolean result = orderService.deleteOrder(oid);
		
		if(result){
			
		  Page findPageRecodes = orderService.findPageRecodes("1");
			request.setAttribute("page", findPageRecodes);
			request.getRequestDispatcher("/admin/order/orderList.jsp").forward(request, response);
			return ;
		}
	}
	
	
	private void showOrderDetial(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		OrderItemService orderItemService = new OrderItemServiceImpl();
		List<OrderItem> orderitems = orderItemService.findOrderItemsByOrderid(oid);
		
		request.setAttribute("orderitems", orderitems);
		request.getRequestDispatcher("/admin/order/orderDetails.jsp")
				.forward(request, response);
		return;
	}

	private void myOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		OrderService orderService = new OrderServiceImpl();
		List<Order> orders = orderService.findOrderByUid(user.getUid());
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/myOrders.jsp")
				.forward(request, response);
		return;
	}

	private void processOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ShoppingCar shoppingCar = (ShoppingCar) request.getSession()
				.getAttribute("shoppingCar");
		List<ShoppingItem> shoppingItems = shoppingCar.getShoppingItems();
		String oid = OrderIdUtils.getOid();
		String uid = request.getParameter("uid");
		String money = request.getParameter("money");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");

		int state = 1;
		Date date = new Date();

		Order order = new Order();
		order.setOid(oid);
		order.setUid(Integer.parseInt(uid));
		order.setMoney(Double.parseDouble(money));
		order.setAddress(address);
		order.setTel(tel);
		order.setState(state);
		order.setOrdertime(date);

		OrderService orderService = new OrderServiceImpl();
		ShoppingItemService shoppingItemService = new ShoppingItemServiceImpl();

		boolean placeOrder = orderService.placeOrder(order);
		if (placeOrder) {
			String[] ids = request.getParameterValues("ids");
			if (ids == null || ids.length == 0) {
				response.sendRedirect("/shoppingcart.jsp");
			}
			for (int i = 0; i < ids.length; i++) {
				for (int j = 0; j < shoppingItems.size(); j++) {
					ShoppingItem shoppingItem = shoppingItems.get(j);
					if (shoppingItem.getPid().equals(ids[i])) {
						OrderItem orderItem = new OrderItem();
						orderItem.setBuynum(shoppingItem.getSnum());
						orderItem.setPid(shoppingItem.getPid());
						orderItem.setOid(oid);
						OrderItemService orderItemService = new OrderItemServiceImpl();
						boolean result1 = orderItemService
								.addOrderItem(orderItem);
						if (result1) {
							shoppingItems.remove(j);
							shoppingCar.setShoppingItems(shoppingItems);
							boolean result2 = shoppingItemService
									.deleteShoppingItem(shoppingItem
											.getItemid());
						} else {
							response.sendRedirect(request.getContextPath()
									+ "/shoppingcart.jsp");
						}
					}
				}
			}
			System.out.println("OrderServlet.processOrder() success!");
			response.sendRedirect(request.getContextPath()
					+ "/OrderServlet?op=myoid");
		} else {
			response.sendRedirect("/shoppingcart.jsp");
		}

	}

	private void findAllOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		OrderService orderService = new OrderServiceImpl();
		String num = request.getParameter("num");
		Page orders = orderService.findPageRecodes(num);
		request.setAttribute("page", orders);
		request.getRequestDispatcher("/admin/order/orderList.jsp").forward(request, response);
		return ;
		

	}

}
