package periodicals.controller.command.guest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.command.CommandUtility;
import periodicals.controller.filter.AuthFilter;
import periodicals.controller.validator.Validator;
import periodicals.dto.UserDTO;
import periodicals.exception.DataBaseException;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;
import periodicals.model.service.UserService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCommand.class.getName());
    private final UserService userService;
    public RegistrationCommand() {
        this(UserService.getInstance());
    }
    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserDTO userDTO = UserDTO.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .name(request.getParameter("name"))
                .surname(request.getParameter("surname"))
                .role(request.getParameter("role"))
                .build();
        if(request.getMethod().equals("GET")){
            return "registration.jsp";
        }
        if(!Validator.checkNameRegex(userDTO.getName())){
            LOGGER.error("Name[{}] are not valid", userDTO.getName());
            request.setAttribute(AttributeKey.ERROR_PATTERN_NAME, "valid.user.name.regex");
            request.setAttribute("userDTO", userDTO);
            return "registration.jsp";
        }
        if(!Validator.checkNameRegex(userDTO.getSurname())){
            LOGGER.error("Surname[{}] are not valid", userDTO.getSurname());
            request.setAttribute(AttributeKey.ERROR_PATTERN_NAME, "valid.user.name.regex");
            request.setAttribute("userDTO", userDTO);
            return "registration.jsp";
        }
        if(!Validator.checkEmailRegex(userDTO.getEmail())){
            LOGGER.error("Email[{}] are not valid", userDTO.getEmail());
            request.setAttribute(AttributeKey.ERROR_PATTERN_EMAIL, "valid.user.email.regex");
            request.setAttribute("userDTO", userDTO);
            return "registration.jsp";
        }
        if(Validator.checkStringLength(6,20, userDTO.getPassword())){
            LOGGER.error("Password[{}] are not valid", userDTO.getPassword());
            request.setAttribute(AttributeKey.ERROR_PATTERN_PASSWORD, "valid.user.password.size");
            request.setAttribute("userDTO", userDTO);
            return "registration.jsp";
        }
        LOGGER.info("UserDTO are valid:{}", userDTO);
        try{
            userService.signUpUser(userDTO);
        }catch (Exception ex){
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            request.setAttribute(AttributeKey.ERROR_EXIST, "valid.user.email.exists");
            request.setAttribute("userDTO", userDTO);
            return "registration.jsp";
        }
        return "redirect:/login";
    }
}
