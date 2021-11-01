package periodicals.model.dao.pageable;

public class Pageable {
    Integer offset;
    Integer limit;
    String orderBy;
    String direction;

//    private Pageable(Integer offset, Integer limit, String orderBy, String direction) {
//        this.offset = offset;
//        this.limit = limit;
//        this.orderBy = orderBy;
//        this.direction = direction;
//    }

    public PageableBuilder builder(){
        return new Pageable().new PageableBuilder();
    }
    class PageableBuilder{
        public Pageable build(){
            return Pageable.this;
        }
        public PageableBuilder offset(Integer offset){
            Pageable.this.offset = offset;
            return this;
        }
        public PageableBuilder limit(Integer limit){
            Pageable.this.limit = limit;
            return this;
        }
        public PageableBuilder orderBy(String orderBy){
            Pageable.this.orderBy = orderBy;
            return this;
        }
        public PageableBuilder direction(String direction){
            Pageable.this.direction = direction;
            return this;
        }
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
