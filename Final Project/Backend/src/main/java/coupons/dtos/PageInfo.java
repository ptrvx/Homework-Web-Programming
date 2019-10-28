package coupons.dtos;

public class PageInfo {

    private long total;
    private long pages;
    private long current;

    public PageInfo(long total, long pages, long currentPage) {
        this.pages = pages;
        this.current = currentPage;
        this.total = total;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public void setCurrent(long currentPage) {
        this.current = currentPage;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public long getCurrent() {
        return current;
    }

    public long getTotal() {
        return total;
    }
}
