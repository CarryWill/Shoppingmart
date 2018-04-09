package com.uu.control;



import com.uu.bean.Category;
import com.uu.bean.Product;
import com.uu.service.CategoryService;
import com.uu.service.ProductService;
import com.uu.service.impl.CategoryServiceImpl;
import com.uu.service.impl.ProductServiceImpl;
import com.uu.utils.FillDataBean;
import com.uu.utils.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "CategoryServlet",urlPatterns = "/CategoryServlet")
public class CategoryServlet extends HttpServlet {


    CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doPost(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8"); //需求在取数据之前设置
        String op = req.getParameter("op");
        if (op!=null){
            switch (op){
                case "addProduct":
                    addProduct(req, resp);
                case  "addCategory":
                    addCategory(req,resp);
                    break;
                case "findAllCategory":
                    findAllCategory( req,  resp);
                    break;
                case "updateCategory":
                    updateGategory(req, resp);
                    break;
                case "deleteMulti":
                    deleteMultiGategory(req, resp);
                    break;
                    case "findCategoryByUpdate":
                        findCategoryByUpdate(req, resp);
                        break;
                case "deleteOne":
                    deleteCategory(req, resp);
                    break;

            }

        }
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cid_string = req.getParameter("cid");

        try {
            int cid = Integer.parseInt(cid_string);
            boolean ret = categoryService.deleteMultiGategory(cid);
            System.out.println("CategoryServlet.updateCategory()"+ret);
            if (ret) {
                resp.getWriter().println("删除成功！");

                resp.setHeader("Refresh", "1;URL="+req.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");

                //delete ok update cache
                updateCategoryListInCache();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resp.getWriter().print(e.getMessage());
        }
    }
    private void  updateCategoryListInCache() throws SQLException {
        List<Category> categorylist = categoryService.findAllCategory();
        getServletContext().setAttribute("categorylist", categorylist);
    }

    private void findCategoryByUpdate(HttpServletRequest req, HttpServletResponse resp) {

        List<Category> categories = null;
        try {
            categories = categoryService.findAllCategory();
            ProductService productService = new ProductServiceImpl();
            Product product = productService.findProductByPid(req.getParameter("pid"));

            req.setAttribute("categories", categories);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    private void addProduct(HttpServletRequest req, HttpServletResponse resp) {
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> categories = null;
        try {
            categories = categoryService.findAllCategory();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/admin/product/addProduct.jsp").forward(req, resp);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateGategory(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String cid = req.getParameter("cid");
        String cname = req.getParameter("cname");
        if(cid == null || cid.trim().length()==0){
            resp.sendRedirect(req.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");
        }


        if(cname == null || "".equals(cname.trim())){
            req.setAttribute("msg", "分类名不能为空");
            req.getRequestDispatcher("/updateCategory.jsp").forward(req, resp);
            return ;
        }
        Category category = FillDataBean.fillData(Category.class, req);
        CategoryService categoryService = new CategoryServiceImpl();
        try {
            boolean ret = categoryService.updateCategory(category);
            if (ret){
                //插入成功！
                resp.getWriter().println("更新成功！");
                resp.setHeader("Refresh", "1;URL="+req.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");

            }
        } catch (Exception e) {
            e.printStackTrace();
            //插入发生异常
            resp.getWriter().println("更新发生异常！e="+e.getCause());
        }

    }



    private void findAllCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String num = req.getParameter("num");
        try {
          Page page =categoryService.findPageRecodes(num);

           req.setAttribute("page",page);
            req.getRequestDispatcher("/admin/category/categoryList.jsp").forward(req,resp);

        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    private void deleteMultiGategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] cids = req.getParameterValues("cid");//获取选框
        for (String c : cids) { //遍历元素
            int cid = Integer.parseInt(c.trim());//类型转换
            try {
                boolean ret = categoryService.deleteMultiGategory(cid);
                if (ret) {
                    if(c.equals(cids[cids.length-1])) { //判断是否遍历完
                        resp.getWriter().println("删除成功！");
                        resp.setHeader("Refresh", "1;URL="+req.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");
                        return;
                    }
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.getWriter().println("删除发生异常！e=" + e.getCause());
            }
        }

    }



    private void addCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String cname = req.getParameter("cname");

        try {
            boolean ret = categoryService.addCategory(cname);
            if (ret){
                //插入成功！
                resp.getWriter().println("插入成功！");
                resp.setHeader("Refresh", "1;URL="+req.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");

            }
        } catch (Exception e) {
              e.printStackTrace();
            //插入发生异常
            resp.getWriter().println("插入发生异常！e="+e.getCause());
        }

    }
}
