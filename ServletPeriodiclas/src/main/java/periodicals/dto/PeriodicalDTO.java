package periodicals.dto;

import periodicals.model.entity.periodical.Subject;

import javax.validation.constraints.*;

public class PeriodicalDTO {
    private String name;
    private Subject subject;
    private Long price;
    private Long subscribers=0L;

    public static PeriodicalDTOBuilder builder(){
        return new PeriodicalDTO().new PeriodicalDTOBuilder();
    }

    public class PeriodicalDTOBuilder{
        public PeriodicalDTO build(){
            return PeriodicalDTO.this;
        }
        public PeriodicalDTOBuilder name(String name){
            PeriodicalDTO.this.name = name;
            return this;
        }
        public PeriodicalDTOBuilder subject(Subject subject){
            PeriodicalDTO.this.subject = subject;
            return this;
        }
        public PeriodicalDTOBuilder price(Long price){
            PeriodicalDTO.this.price = price;
            return this;
        }
        public PeriodicalDTOBuilder subscribers(Long subscribers){
            PeriodicalDTO.this.subscribers = subscribers;
            return this;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Long subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public String toString() {
        return "PeriodicalDTO{" +
                "name='" + name + '\'' +
                ", subject=" + subject +
                ", price=" + price +
                ", subscribers=" + subscribers +
                '}';
    }

}
