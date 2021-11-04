package periodicals.model.entity;

import periodicals.model.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

public class Replenishment {

    private Long id;
    private Long sum;
    private User user;
    private LocalDateTime time;

    public static ReplenishmentBuidler builder(){
        return new Replenishment().new ReplenishmentBuidler();
    }
    public class ReplenishmentBuidler{
        public Replenishment build(){
            return Replenishment.this;
        }

        public ReplenishmentBuidler id(Long id){
            Replenishment.this.id = id;
            return this;
        }
        public ReplenishmentBuidler sum(Long sum){
            Replenishment.this.sum = sum;
            return this;
        }
        public ReplenishmentBuidler user(User user){
            Replenishment.this.user = user;
            return this;
        }
        public ReplenishmentBuidler time(LocalDateTime time){
            Replenishment.this.time = time;
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Replenishment{" +
                "id=" + id +
                ", sum=" + sum +
                ", user=" + user +
                ", time=" + time +
                '}';
    }
}
