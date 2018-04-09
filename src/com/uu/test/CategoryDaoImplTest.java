package com.uu.test;


import com.uu.dao.CategoryDao;
import com.uu.dao.impl.CategoryDaoImpl;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* CategoryDaoImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 12, 2018</pre> 
* @version 1.0 
*/ 
public class CategoryDaoImplTest { 

    CategoryDao dao ;

@Before
public void before() throws Exception { 
        String i="b44c1065-d057-4d2a-8d5a-06163e85685e_";
    System.out.println(i.length());
        dao= new CategoryDaoImpl();
}

@After
public void after() throws Exception { 


}

/** 
* 
* Method: addCategory(String cname) 
* 
*/ 
@Test
public void testAddCategory() throws Exception { 

    dao.addCategory("nike");

}
@Test
    public void testdeleteMultiGategory()throws Exception{

    dao.deleteMultiGategory(0);
}

} 
