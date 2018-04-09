package com.uu.test;

import com.uu.dao.impl.ShoppingItemDaoImpl;
import org.junit.Test;

public class ItemTest
{

    @Test
     public void test(   ) {
        ShoppingItemDaoImpl dao = new ShoppingItemDaoImpl();
        int i = dao.addShoppingItem("9", 1, 1);
        System.out.println("aa="+i);
    }
}
