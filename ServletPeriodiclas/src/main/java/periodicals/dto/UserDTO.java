package periodicals.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

    public UserDTO(){
//        String name, String surname, String email, String password, String role
//        this.name = name;
//        this.surname = surname;
//        this.email = email;
//        this.password = password;
//        this.role = role;
    }

    private String name;

    private String surname;

    private String email;

    private String password;

    private String role;

    public static UserDTOBuilder builder(){
        return new UserDTO().new UserDTOBuilder();
    }

    public class UserDTOBuilder {

        public UserDTO build(){
            return UserDTO.this;
        }

        public UserDTOBuilder name(String name){
            UserDTO.this.name = name;
            return this;
        }

        public UserDTOBuilder surname(String surname){
            UserDTO.this.surname = surname;
            return this;
        }

        public UserDTOBuilder email(String email){
            UserDTO.this.email = email;
            return this;
        }

        public UserDTOBuilder password(String password){
            UserDTO.this.password = password;
            return this;
        }

        public UserDTOBuilder role(String role){
            UserDTO.this.role = role;
            return this;
        }
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
