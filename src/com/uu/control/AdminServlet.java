package com.uu.control;

import com.uu.bean.Admin;
import com.uu.service.AdminService;
import com.uu.service.impl.AdminServiceImpl;
import com.uu.utils.FillDataBean;
import com.uu.utils.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet",urlPatterns = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {

    AdminService adminService=new AdminServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8"); //需求在取数据之前设置

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "findAllAdmin":
                    findAllAdmin(request, response);
                    break;
                case "addAdmin":
                    addAdmin(request, response);
                    break;
                case "login":
                    login(request, response);
                    break;
                case "lgout":
                    lgout(request, response);
                    break;
                case "updateAdmin":
                    updateAdmin(request, response);
                    break;
            }
        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String aid = request.getParameter("aid");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");

        if (aid == null || aid.trim().length() == 0) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/AdminServlet?op=findAllAdmin&num=1");
        }
        if (username == null || "".equals(username.trim())) {
            request.setAttribute("msg", "系统用户名不能为空");
            request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
                    .forward(request, response);
            return;
        }
        if (password == null || "".equals(password.trim())) {
            request.setAttribute("msgPwd", "密码不能为空");
            request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
                    .forward(request, response);
            return;
        }
        if (password1 == null || "".equals(password1.trim())) {
            request.setAttribute("msgPwd1", "密码不能为空");
            request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
                    .forward(request, response);
            return;
        }
        if (!password.equals(password1)) {
            request.setAttribute("msgPwd2", "两次输入的密码不一致，请重新输入。");
            request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
                    .forward(request, response);
            return;
        }

        Admin admin = FillDataBean.fillData(Admin.class, request);



        boolean result = adminService.updateAdminMsg(admin);
        if (result) {
            response.getWriter().write("修改成功！<br/>");
            response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/AdminServlet?op=findAllAdmin&num=1");
            return;
        } else {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
                    .forward(request, response);
        }
    }

    private void lgout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("admin");
        response.sendRedirect(request.getContextPath()+"/admin/index.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        Admin admin = adminService.login(username, password);

        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            // 跳转到主页
            response.sendRedirect(request.getContextPath() + "/admin/main.jsp");
        } else {
            // 跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
        }
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");

        if (username == null || "".equals(username.trim())) {
            request.setAttribute("msgUname", "系统用户名不能为空");
            request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
                    request, response);
            return;
        }
        if (password == null || "".equals(password.trim())) {
            request.setAttribute("msgPwd", "密码不能为空");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
                    request, response);
            return;
        }
        if (password1 == null || "".equals(password1.trim())) {
            request.setAttribute("msgPwd1", "确认密码不能为空");
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
                    request, response);
            return;
        }
        if (!password.trim().equals(password1.trim())) {
            request.setAttribute("msgPwd2", "两次输入的密码不一致，请重新输入！");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
                    request, response);
            return;
        }

        Admin admin = FillDataBean.fillData(Admin.class, request);

        boolean result = adminService.regist(admin);
        if (result) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/AdminServlet?op=findAllAdmin&num=1");
            return;
        } else {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("password1", password1);
            request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
                    request, response);
            return;
        }



    }

    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String num = request.getParameter("num");
        Page admins = adminService.findPageRecodes(num);
        request.setAttribute("page", admins);
        request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(
                request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                    doPost(request,response);
    }
}
