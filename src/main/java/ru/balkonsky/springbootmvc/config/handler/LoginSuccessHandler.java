package ru.balkonsky.springbootmvc.config.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.balkonsky.springbootmvc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private int id;

    @Autowired
    public LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        id = userService.getUserByUsername(httpServletRequest.getParameter("username")).getId();
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        }else if (roles.contains("ROLE_USER")){
            httpServletResponse.sendRedirect("/user/" + id);
        }
    }

    public int getId() {
        return id;
    }
}
