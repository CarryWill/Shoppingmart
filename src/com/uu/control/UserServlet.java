package com.uu.control;

import com.uu.bean.User;
import com.uu.service.UserService;
import com.uu.service.impl.UserServiceImpl;
import com.uu.utils.FillDataBean;
import com.uu.utils.Page;
import com.uu.utils.bean.UserFormBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "UserServlet",urlPatterns = "/user/UserServlet")
public class UserServlet extends HttpServlet {

            UserService userService=new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8"); //需求在取数据之前设置

        String op=request.getParameter("op");
        if(op!=null){
            switch (op){
                case "findAllUser":
                    findAllUser(request, response);
                    break;
                case "adduser":
                    addUser(request, response);
                    break;
                case "register":
                    register(request, response);
                    break;
                case "login":
                    login(request, response);
                    break;
                case "lgout":
                    logout(request, response);
                    break;
                case "activeUser":
                    activeUser(request, response);
                    break;
                case"editUser":
                    editUser(request, response);
                    break;
            }
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user1 = (User) request.getSession().getAttribute("user");//获取原来session
        UserFormBean userFormBean = FillDataBean.fillData(UserFormBean.class, request);
        boolean validate = userFormBean.validate();
        if(!validate){
            System.out.println("UserServlet.adduser() validate fail!");
            request.setAttribute("msg", userFormBean);
            request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
            return ;
        }
        User user=new User();
        try {
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(user, userFormBean);
        UserService userService = new UserServiceImpl();
            user.setUpdatetime(new Date());
            user.setUid(user1.getUid());//将原来UID返回
            user.setUsername(user1.getUsername());//将原来的username返回
        boolean update = userService.updateUserMsg(user);
        if (update) {
            //更新成功
            System.out.println("更新成功");
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath()+"/user/personal.jsp");
            return;
        } else {
            //更新失败
            System.out.println("更新失败");
            response.sendRedirect(request.getContextPath()+"/user/personal.jsp");
            return;
        }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String activecode=request.getParameter("activecode");
       if(activecode==null){
           request.setAttribute("msg", "激活码不能为空,请重新注册！");
           request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
           return;
       }
        boolean active = userService.active(activecode);
       if(active){
           response.getWriter().write("激活成功，请前去登录！");
       }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserFormBean userFormBean = FillDataBean.fillData(UserFormBean.class, request);
        boolean validate = userFormBean.validate();
        if(!validate){
            System.out.println("UserServlet.adduser() validate fail!");
            request.setAttribute("msg", userFormBean);
            request.getRequestDispatcher("/admin/user/addUser.jsp").forward(request, response);
            return ;
        }

        User user = new User();
        try {
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(user, userFormBean);
            UserService userService = new UserServiceImpl();
            user.setUpdatetime(new Date());
            boolean regist = userService.regist(user);
            if(regist){
                response.getWriter().write("添加成功，1秒后跳转");
                response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/user/userList.jsp");
                return;
            }else{
                response.getWriter().write("呃，添加失败，1秒后重新添加");
                response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/user/addUser.jsp");
            }
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserFormBean userFormBean = FillDataBean.fillData(UserFormBean.class, request);
        boolean validate = userFormBean.validate();
        if(!validate){
            request.setAttribute("msg", userFormBean);
            request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
            return ;
        }

        User user = new User();
        try {
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(user, userFormBean);

            user.setUpdatetime(new Date());
            boolean regist = userService.regist(user);
            if(regist){
                response.getWriter().write("注册成功,请马上去你的邮箱激活,1秒后转到登录页");
                response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/login.jsp");
                return;
            }else{
                response.getWriter().write("呃，注册失败了，重新注册吧,1秒后回到注册页");
                response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/regist.jsp");
            }
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void findAllUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String num = request.getParameter("num");
        Page users = userService.findPageRecodes(num);
        request.setAttribute("page", users);
        System.out.println("UserServlet.findAllUser()"+users);
        request.getRequestDispatcher("/admin/user/userList.jsp").forward(
                request, response);

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String vertifycode=request.getParameter("vertifycode");

        if(username == null || vertifycode==null || password==null) {
            request.setAttribute("msg", "请完善登录信息！");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            return;
        }


    String v = (String) request.getSession().getAttribute("checkcode_session");
    User user = userService.login(username, password);

    if (user != null && user.getState() != 0 && vertifycode.equals(v)) {
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        System.out.println("UserServlet.login() OK" + user);
        return;
    }
        request.setAttribute("username", username);
        request.setAttribute("password", password);

        if(user==null){
            request.setAttribute("msg", "用户名或密码错误！");
        }else if(user.getState()==0) {
            request.setAttribute("msg", "账号未激活！");
        }else if(!vertifycode.equals(v)){
            request.setAttribute("msg", "验证码错误！");
        }
        request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        return;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                doPost(request,response);
    }
}
