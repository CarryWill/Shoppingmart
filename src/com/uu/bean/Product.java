package com.uu.bean;

import java.io.Serializable;

public class Product implements Serializable{


    private String pid;
    private String pname;
    private double estoreprice;
    private double markprice;
    private int pnum;
    private int cid; //categoryid
    private String imgurl;
    private String description;

    private Category category;

    /**
     * 商品id
     * @return
     */
    public String getPid() {
        return pid;
    }
    /**
     * 商品id
     * @param pid
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * 商品名
     * @return
     */
    public String getPname() {
        return pname;
    }
    /**
     * 商品名
     * @param pname
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * 商城价
     * @return
     */
    public double getEstoreprice() {
        return estoreprice;
    }
    /**
     * 商城价
     * @param estoreprice
     */
    public void setEstoreprice(double estoreprice) {
        this.estoreprice = estoreprice;
    }

    /**
     * 市场价
     * @return
     */
    public double getMarkprice() {
        return markprice;
    }
    /**
     * 市场价
     * @param markprice
     */
    public void setMarkprice(double markprice) {
        this.markprice = markprice;
    }

    /**
     * 库存
     * @return
     */
    public int getPnum() {
        return pnum;
    }
    /**
     * 库存
     * @param pnum
     */
    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    /**
     * 类别id
     * @return
     */
    public int getCid() {
        return cid;
    }
    /**
     * 类别id
     * @param cid
     */
    public void setCid(int cid) {
        this.cid = cid;
    }

    /**
     * 商品图片路径
     * @return
     */
    public String getImgurl() {
        return imgurl;
    }
    /**
     * 商品图片路径
     * @param imgurl
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    /**
     * 商品描述
     * @return
     */
    public String getDescription() {
        return description;
    }
    /**
     * 商品描述
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    @Override
    public String toString() {
        return "Product [pid=" + pid + ", pname=" + pname + ", estoreprice="
                + estoreprice + ", markprice=" + markprice + ", pnum=" + pnum
                + ", cid=" + cid + ", imgurl=" + imgurl + ", description=" + description
                + "]";
    }

}
