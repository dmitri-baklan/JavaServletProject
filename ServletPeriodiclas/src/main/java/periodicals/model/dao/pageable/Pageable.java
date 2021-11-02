package periodicals.model.dao.pageable;

public class Pageable {
    Integer offset;
    Integer limit;
    String sortField;
    boolean ascending;

    public Pageable(String sortField, boolean ascending, int page, int limit) {
        this.offset = limit * (page - 1);
        this.limit = limit;
        this.sortField = sortField;
        this.ascending = ascending;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
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
