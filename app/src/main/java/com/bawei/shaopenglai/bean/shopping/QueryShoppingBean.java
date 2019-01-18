package com.bawei.shaopenglai.bean.shopping;

public class QueryShoppingBean {
    int commodityId;
    int amount;

    public QueryShoppingBean(int commodityId, int count) {
        this.commodityId = commodityId;
        this.amount = count;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return amount;
    }

    public void setCount(int count) {
        this.amount = count;
    }
}
