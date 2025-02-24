package org.example.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.Database.DBservice;


import java.io.IOException;

@RequiredArgsConstructor
public class SignInServlet extends HttpServlet {

    private final DBservice dBservice;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");

        if ((login == null || pass == null) || (login.isEmpty() || pass.isEmpty())) {
            resp.getWriter().println("No params");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!dBservice.checkUser(login)) {
            if (dBservice.authUser(login, pass)) {
                resp.getWriter().println("Authorized: " + login);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.getWriter().println("Unauthorized");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            resp.getWriter().println("Not registered");
            resp.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
        }
    }
}
