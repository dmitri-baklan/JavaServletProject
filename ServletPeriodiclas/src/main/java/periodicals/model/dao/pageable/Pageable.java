package periodicals.model.dao.pageable;

public class Pageable {
    int offset;
    int limit;
    String sortField;
    boolean ascending;

    public Pageable(String sortField, boolean ascending, int page, int limit) {
        this.offset = limit * (page - 1);
        this.limit = limit;
        this.sortField = sortField;
        this.ascending = ascending;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
