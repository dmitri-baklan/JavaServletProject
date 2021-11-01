package periodicals.dto;

import java.util.List;

public class Page<T> {
    List<T> items;
    Integer number;

    public Page(List<T> items,  Integer number) {
        this.items = items;
        this.number = number;
    }

    public Boolean hasContent(){
        return !items.isEmpty();
    }
    public List<T> getItems() {
        return items;
    }

    public Integer getNumber() {
        return number;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
