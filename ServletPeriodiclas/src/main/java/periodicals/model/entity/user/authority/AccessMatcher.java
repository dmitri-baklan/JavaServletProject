package periodicals.model.entity.user.authority;


import java.util.List;

public class AccessMatcher {

    public static final List<String> PERMIT_ALL = List.of(
        "/welcome"
    );

    public static final List<String> ANONYMOUS = List.of(
            "/registration",
            "/login"
    );

    public static final List<String> FULLY_AUTHENTICATED = List.of(
            "/profile",
            "/profile/edit",
            "/periodicals",
            "/periodicals/d+"
    );

    public static final List<String> AUTHORITY_READER = List.of(
            "/profile/replenishment",
            "/replenishments"
    );

    public static final List<String> AUTHORITY_ADMINISTRATOR = List.of(
            "/periodicals/d+/edit",
            "periodicals/add",
            "/profile/readers",
            "/profile/readers/d+"
    );
//    .antMatchers("/registration","/login").anonymous()
//                .antMatchers("/profile",
//                                     "/profile/edit",
//                                     "/periodicals",
//                                     "/periodicals/{\\d}").fullyAuthenticated()
//                .antMatchers("/profile/replenishment", "/replenishments").hasAuthority("READER")
//                .antMatchers("/periodicals/{\\d}/edit",
//                                     "periodicals/add",
//                                     "/profile/readers",
//                                     "/profile/readers/{\\d}").hasAuthority("ADMINISTRATOR")
//                .and()
//                .formLogin().permitAll()
//                .loginPage("/login")
//                .defaultSuccessUrl("/periodicals", true)
//                .and()
//                .logout().permitAll()
//                .logoutSuccessUrl("/login");


}
