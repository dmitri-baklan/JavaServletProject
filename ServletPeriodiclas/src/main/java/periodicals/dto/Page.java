package periodicals.dto;

import java.util.Set;
import java.util.Set;

public class Page<T> {
    Set<T> items;
    Integer number;
    Double totalPages;

    public Page(Set<T> items,  Integer number, Double totalPages) {
        this.items = items;
        this.number = number;
        this.totalPages = totalPages;
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

    public Double getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Double totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "Page{" +
                "items=" + items +
                ", number=" + number +
                ", totalPages=" + totalPages +
                '}';
    }
}
