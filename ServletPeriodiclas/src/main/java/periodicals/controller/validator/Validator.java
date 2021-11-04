package periodicals.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class.getName());


    private Validator() {}
    // TODO:noneMatch!!!!!!!!!!

    public static boolean checkIsNotBlank(String... fields) {
        return Arrays.stream(fields).noneMatch(a -> a==null || a.isBlank());
    }

    public static boolean checkStringLength(int min, int max, String...fields) {
        return Arrays.stream(fields).anyMatch(a -> a.length() < min || a.length() > max);
    }

    public static boolean checkNumberValue(int min, int max, String field) {
        Long value = Long.parseLong(field);
        return value>=min && value<=max;
    }

    public static boolean checkNameRegex(String... fields) {
        return Arrays.stream(fields).anyMatch(a->a.matches(Regex.REGEX_NAME));
    }
    public static boolean checkEmailRegex(String... fields) {
        return Arrays.stream(fields).anyMatch(a->a.matches(Regex.REGEX_EMAIL));
    }


    public static boolean checkNumberIsPresent(String...fields) {
        for(String field : fields) {
            try {
                Long.parseLong(field);
            }catch(NumberFormatException ex) {
                return false;
            }
        }
        return true;
    }

}
