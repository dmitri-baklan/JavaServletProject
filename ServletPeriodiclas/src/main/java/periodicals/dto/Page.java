package periodicals.dto;

import java.util.Set;
import java.util.Set;

public class Page<T> {
    Set<T> items;
    Integer number;

    public Page(Set<T> items,  Integer number) {
        this.items = items;
        this.number = number;
    }

    public Boolean hasContent(){
        return !items.isEmpty();
    }
    public Set<T> getItems() {
        return items;
    }

    public Integer getNumber() {
        return number;
    }

    public void setItems(Set<T> items) {
        this.items = items;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Page{" +
                "items=" + items +
                ", number=" + number +
                '}';
    }
}
