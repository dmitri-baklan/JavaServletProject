package periodicals.model.entity.user;

import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.user.authority.Role;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class User {

    public User(){
    }

    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private Long balance;
    private boolean isActive = true;
    private Long subscriptions;
    Set<Periodical> periodicals;

    public static UserBuilder builder(){
        return new User().new UserBuilder();
    }

    public class UserBuilder{

        public User build(){
            periodicals = new LinkedHashSet<>();
            return User.this;
        }

        public UserBuilder id(Long id){
            User.this.id = id;
            return this;
        }
        public UserBuilder email(String email){
            User.this.email = email;
            return this;
        }
        public UserBuilder password(String password){
            User.this.password = password;
            return this;
        }
        public UserBuilder name(String name){
            User.this.name = name;
            return this;
        }
        public UserBuilder surname(String surname){
            User.this.surname = surname;
            return this;
        }
        public UserBuilder role(Role role){
            User.this.role = role;
            return this;
        }
        public UserBuilder balance(Long balance){
            User.this.balance = balance;
            return this;
        }
        public UserBuilder subscriptions(Long subscriptions){
            User.this.subscriptions = subscriptions;
            return this;
        }
        public UserBuilder isActive(boolean isActive){
            User.this.isActive = isActive;
            return this;
        }
        public UserBuilder periodicals(Set<Periodical> periodicals){
            User.this.periodicals = periodicals;
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Role getRole() {
        return role;
    }

    public Long getBalance() {
        return balance;
    }

    public Long getSubscriptions() {
        return subscriptions;
    }

    public boolean isActive() {
        return isActive;
    }

    public Set<Periodical> getPeriodicals() {
        return periodicals;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPeriodicals(Set<Periodical> periodicals) {
        this.periodicals = periodicals;
    }

    public void setSubscriptions(Long subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", balance=" + balance +
                ", isActive=" + isActive +
                ", subscriptions=" + subscriptions +
                ", periodicals=" + periodicals +
                '}';
    }
}
