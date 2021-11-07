package periodicals.model.entity.user.authority;


import java.util.List;

public class AccessMatcher {

    public static final List<String> PERMIT_ALL = List.of(
        "/welcome",
        "/periodicals/\\d+",
        "/periodicals"
    );

    public static final List<String> ANONYMOUS = List.of(
            "/registration",
            "/login"
    );

    public static final List<String> FULLY_AUTHENTICATED = List.of(
            "/profile",
            "/profile/edit",
            "/logout"
    );

    public static final List<String> AUTHORITY_READER = List.of(
            "/periodicals/\\d+/subscription",
            "/profile/replenishment",
            "/replenishments"
    );

    public static final List<String> AUTHORITY_ADMINISTRATOR = List.of(
            "/periodicals/\\d+/edit",
            "/periodicals/add",
            "/profile/readers",
            "/profile/readers/\\d+",
            "/periodicals/\\d+/delete"
    );

}
