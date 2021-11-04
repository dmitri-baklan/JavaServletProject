package periodicals.dto;

public class ReplenishmentDTO {
    private Long value;

    public static ReplenishmentDTOBuilder builder(){
        return new ReplenishmentDTO().new ReplenishmentDTOBuilder();
    }

    public class ReplenishmentDTOBuilder{
        public ReplenishmentDTO build(){
            return ReplenishmentDTO.this;
        }
        public ReplenishmentDTOBuilder value(Long value){
            ReplenishmentDTO.this.value = value;
            return this;
        }
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
