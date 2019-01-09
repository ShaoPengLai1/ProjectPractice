package com.bawei.shaopenglai.bean;

/**
 *
 * @author Peng
 * @time 2018/12/30 11:26
 */

public class ShoppingCarBean {

    int commodityId;
    int count;

    public ShoppingCarBean(int commodityId, int count) {
        this.commodityId = commodityId;
        this.count = count;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
