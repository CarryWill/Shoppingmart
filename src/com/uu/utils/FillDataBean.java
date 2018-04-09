package com.uu.utils;

import com.uu.control.AddProductServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FillDataBean {
    public static <T> T fillData(Class<T> clazz, HttpServletRequest request)  {

        T bean = null;

//
//        try{
//            return sd.parse(str);
//        }
//        catch(ParseException e)
//        {
//            throw new RuntimeException(e);
//        }

        if (ServletFileUpload.isMultipartContent(request)) {

            Map<String, String> map = new HashMap<>();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = new ArrayList<FileItem>(0);
            String storeDirectory = request.getServletContext().getRealPath("/files");
            String v=null;
            try {
            items = upload.parseRequest(request);
            for (FileItem fileItem : items) {
                if(fileItem.isFormField()){
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString("UTF-8");
                     map.put(name,value);
                }else{
                    String name=fileItem.getFieldName();
                    String path = UplodForm.processUplodForm(fileItem,storeDirectory);
                    map.put(name,path);
                }
                    bean = clazz.newInstance();
                    BeanUtils.populate(bean, map);

                System.out.println(bean);
            }
            } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (FileUploadException e) {
                e.printStackTrace();
            }

        }
        else {
            Map<String, String[]> map = new HashMap<>();
            map = request.getParameterMap();
            try {
                bean = clazz.newInstance();
                BeanUtils.populate(bean, map);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
            return bean;
        }
}
