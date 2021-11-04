package periodicals.controller.validator;

public interface Regex {
    public static final String REGEX_NAME = "^[A-ZА-ЯІЄЮЇЩ][\\w'\\-,.а-яієющї][^0-9_!¡?÷?¿\\\\+=@#$%ˆ&*(){}|~<>;:]{1,18}$";
    public static final String REGEX_NUMBERS = "[\\d+]{1,10}";
    public static final String REGEX_EMAIL = "(([^<>()\\\\.,;:\\s@\\\"]+(\\.[^<>()\\\\.,;:\\s@\\\"]+)*)|(\\\"[^\\\"]+\\\"))@((([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";

}
