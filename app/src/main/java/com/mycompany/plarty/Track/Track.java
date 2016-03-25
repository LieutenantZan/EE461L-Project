package com.mycompany.plarty.Track;

import java.util.List;

/**
 * Created by Antonio on 3/22/2016.
 */
public class Track {
    private String href;
    private List<Item> itemList;
    private Integer limit;
    private String next;
    private Integer offset;
    private String previous;
    private Integer total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Item getItemList(int index) {
        return itemList.get(index);
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


}