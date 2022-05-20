package com.example.selab4.controller.user;

import com.example.selab4.config.WebMvcConfig;
import com.example.selab4.service.user.UserService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController("UserUserController")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final HttpServletRequest request;

    @Autowired
    UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @PostMapping("/login")
    public Response<String> login(HttpSession session) {
        String type = request.getParameter("type");
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        Response<String> answer = userService.login(type, name, password);
        if (answer.getData().equals("true")) {    // 登录成功
            session.setAttribute("user", WebMvcConfig.SESSION_KEY); // 登录之后，session就有了user的值
        }
        return answer;
    }

}
