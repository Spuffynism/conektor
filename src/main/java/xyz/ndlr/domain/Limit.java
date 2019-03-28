package xyz.ndlr.domain;

public class Limit {
    int limit;
    int page;

    public static final Limit DEFAULT = new Limit(50, 0);

    public Limit(int limit, int page) {
        this.limit = limit;
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public int getPage() {
        return page;
    }
}