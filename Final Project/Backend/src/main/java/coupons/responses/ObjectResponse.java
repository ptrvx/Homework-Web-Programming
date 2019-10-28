package coupons.responses;

import coupons.dtos.PageInfo;

import java.util.List;

public class ObjectResponse<T> {

    private List<T> data;
    private Object info;

    public ObjectResponse(List<T> data, Object info) {
        this.data = data;
        this.info = info;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public List<T> getData() {
        return data;
    }

    public Object getInfo() {
        return info;
    }
}
