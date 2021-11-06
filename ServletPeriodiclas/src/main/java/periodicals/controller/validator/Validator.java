package periodicals.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
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

    public static boolean checkNumberRegexAndRange(String number){
       return number.matches(Regex.REGEX_NUMBERS)? checkNumberValue(1, 1000, number) : false;
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

    public static Integer getDigitParam(HttpServletRequest request, String key, String defaultVal) {
        try {
            return Integer.parseInt(request.getParameter(key));
        }catch(Exception ex) {
            return Integer.parseInt(defaultVal);
        }
    }
    public static String getSortFilterParam(HttpServletRequest request, String key, String defaultVal) {
        String field = request.getParameter(key);
        return (field != null && List.of("p_name","p_price","p_subscribers").contains(field)) ? field : defaultVal;
    }

    public static String getFieldFilterParam(HttpServletRequest request, String key, String defaultVal) {
        String field = request.getParameter(key);
        return (field != null && List.of("NEWS","SPORT","SCIENCE").contains(field)) ? field : defaultVal;
    }

    public static boolean isDirectionAsc(HttpServletRequest request, String key, boolean defaultVal) {
        String field = request.getParameter(key);
        return (field != null && field.equals("false"))? false : defaultVal;
    }

    public static String getSearchQueryParam(HttpServletRequest request, String key, String defaultVal) {
        String field = request.getParameter(key);
        return (field != null) ? new String(field.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8) : defaultVal;
    }

    public static Long getIdFromRequset(HttpServletRequest request) {
        String field = request.getRequestURI();
        Pattern p = Pattern.compile(".*?(\\d+).*");
//        final String theString = "Incident #492 -  The Title Description";
        String substring = new String();
        Matcher m = p.matcher(field);
        if (m.matches()) {
            substring = m.group(1);
        }
        return Long.parseLong(substring);
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
