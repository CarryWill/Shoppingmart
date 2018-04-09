package com.uu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(filterName = "GlobalEncodingFilter",urlPatterns = "/admin/*")

public class AdminFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	//是否以管理员登录，登录后就放行
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
 
		HttpServletRequest req =   (HttpServletRequest) request ;
		HttpServletResponse resp=(HttpServletResponse) response;
		
		HttpSession session = req.getSession(false);
		
		System.out.println("AdminFIlter.doFilter()"+req.getRequestURI());
		
		if (session!=null&&session.getAttribute("admin")!=null) {
			//登录过
			
			chain.doFilter(request, response);
			
			System.out.println("AdminFIlter.doFilter() 放行1");
		}
		else if (req.getRequestURI().endsWith("index.jsp")||
				req.getRequestURI().endsWith(".gif")||
				req.getRequestURI().endsWith(".js")||
				req.getRequestURI().endsWith(".css")||
				req.getRequestURI().contains("ProductServlet")||
				req.getRequestURI().endsWith(".png")||
				req.getRequestURI().endsWith(".jpg")||
				req.getRequestURI().endsWith(".jpeg")
        ) {
			chain.doFilter(request, response);
			System.out.println("AdminFIlter.doFilter() 放行2");
		} else if ( 
				req.getRequestURI().endsWith("AdminServlet")&&
				"login".equals(req.getParameter("op"))
		) {

			chain.doFilter(request, response);
			System.out.println("AdminFIlter.doFilter() 放行3");
			
		}  
		
		else {
			
			System.out.println("AdminFIlter.doFilter() 拦截");
			request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
			
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
