package com.uu.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uu.bean.Product;
import com.uu.service.ProductService;
import com.uu.service.impl.ProductServiceImpl;
import com.uu.utils.UplodForm;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;


@WebServlet(name = "AddProductServlet",urlPatterns = "/AddProductServlet")
public class AddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	



	private String processFieldForm(FileItem fileItem) throws UnsupportedEncodingException {
		String name = fileItem.getFieldName();
		String value = fileItem.getString("UTF-8");
		return value;
	}
	
	/**
	 * 生成文件保存的目录，使用哈希码的方式计算目录
	 * @param storeDirectory 文件存储的根目录
	 * @param filename
	 * @return
	 */



	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		addProduct(request, response);
		
	}


	private void addProduct(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		boolean multipartContent = ServletFileUpload.isMultipartContent(request);
		if(!multipartContent){
			throw new RuntimeException("your form's enctype attribute must be multipart/form-data");
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		List<String> param = new ArrayList<String>();
		List<FileItem> items = new ArrayList<FileItem>(0);
		String storeDirectory = getServletContext().getRealPath("/files");
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (FileItem fileItem : items) {
			if(fileItem.isFormField()){
				String value = processFieldForm(fileItem);
				param.add(value);
				// map.put(item.getFieldname, item.getString());
			}else{
				String path = UplodForm.processUplodForm(fileItem,storeDirectory);
				param.add(path);
			}
		}
		Product product = new Product();
		product.setCid(Integer.parseInt(param.get(0)));
		product.setPid(param.get(1));

		product.setPnum(Integer.parseInt(param.get(2)));
		product.setPname(param.get(3));
		product.setEstoreprice(Double.parseDouble(param.get(4)));
		product.setMarkprice(Double.parseDouble(param.get(5)));
		product.setImgurl(param.get(6));
		product.setDescription(param.get(7));
		System.out.println(product);
		ProductService productService = new ProductServiceImpl();
		boolean result = productService.addProduct(product);
		if(result){

			response.getWriter().write("商品添加成功!<br>");
			response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");
		}else{
			response.sendRedirect(request.getContextPath()+"/admin/product/productList.jsp");
			return;
		}
	}

}

