package periodicals.model.entity.periodical;

import periodicals.model.entity.user.User;

import javax.persistence.*;
import java.util.Set;

public class Periodical {
    private Long id;
    private String name;
    private Subject subject;
    private Long price;
    private Long subscribers;
    private Set<User> users;

    public static PeriodicalBuilder builder(){
        return new Periodical().new PeriodicalBuilder();
    }
    public class PeriodicalBuilder{
        public Periodical build(){
            return Periodical.this;
        }
        public PeriodicalBuilder id(Long id){
            Periodical.this.id = id;
            return this;
        }
        public PeriodicalBuilder name(String name){
            Periodical.this.name = name;
            return this;
        }
        public PeriodicalBuilder subject(Subject subject){
            Periodical.this.subject = subject;
            return this;
        }
        public PeriodicalBuilder price(Long price){
            Periodical.this.price = price;
            return this;
        }
        public PeriodicalBuilder subscribers(Long subscribers){
            Periodical.this.subscribers = subscribers;
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Subject getSubject() {
        return subject;
    }

    public Long getPrice() {
        return price;
    }

    public Long getSubscribers() {
        return subscribers;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setSubscribers(Long subscribers) {
        this.subscribers = subscribers;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
