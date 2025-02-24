package org.example.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.Database.DBservice;
import org.example.Account.User;


import java.io.IOException;

@RequiredArgsConstructor
public class SignUpServlet extends HttpServlet {

    private final DBservice dBservice;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");

        if (login.isEmpty() || pass.isEmpty()) {
            resp.getWriter().println("No params");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (dBservice.checkUser(login)) {
            User user = User.builder()
                    .login(login)
                    .password(pass)
                    .build();
            if (dBservice.addNewUser(user) == 0) {
                resp.getWriter().println("Error Registered: " + login);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            resp.getWriter().println("Registered: " + login);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.getWriter().println("Already created: " + login);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
