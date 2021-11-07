package periodicals.controller.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.command.CommandUtility;
import periodicals.controller.validator.Validator;
import periodicals.dto.UserDTO;
import periodicals.exception.DataBaseException;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;
import periodicals.model.service.UserService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditUserCommand.class.getName());
    private final UserService userService;
    public EditUserCommand() {
        this(UserService.getInstance());
    }
    public EditUserCommand(UserService userService) {
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
//            String email = (String)request.getSession().getAttribute("email");
            userDTO.setEmail((String)request.getSession().getAttribute("email"));
            userDTO.setName((String)request.getSession().getAttribute("name"));
            userDTO.setSurname((String)request.getSession().getAttribute("surname"));
            request.setAttribute("userDTO", userDTO);
            return "/user/editUser.jsp";
        }

        if(!Validator.checkNameRegex(userDTO.getName(), userDTO.getSurname())){
            LOGGER.error("Name[{}] and Surname[{}] are not valid", userDTO.getEmail(), userDTO.getPassword());
            request.setAttribute(AttributeKey.ERROR_PATTERN_NAME, "valid.user.name.regex");
            request.setAttribute("userDTO", userDTO);
            return "/user/editUser.jsp";
        }
        if(Validator.checkStringLength(6,20, userDTO.getPassword())){
            LOGGER.error("Password[{}] are not valid", userDTO.getPassword());
            request.setAttribute(AttributeKey.ERROR_PATTERN_PASSWORD, "valid.user.password.size");
            request.setAttribute("userDTO", userDTO);
            return "/user/editUser.jsp";
        }
        LOGGER.info("UserDTO are valid:{}", userDTO);
        try{
            User user = userService.updateUser(userDTO);
            CommandUtility.setUserRole(request, response, user);
        }catch (Exception ex){
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            //TODO change setUserRole!
            User user = new User();
            user.setRole(Role.GUEST);
            CommandUtility.setUserRole(request, response, user);
            request.setAttribute("userDTO", userDTO);
            return "/user/editUser.jsp";
        }
        return "redirect:/profile";
    }
}
