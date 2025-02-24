package org.example.Main;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.ResourceFactory;
import org.example.Database.DBservice;


import org.example.Servlets.SignInServlet;
import org.example.Servlets.SignUpServlet;
import org.eclipse.jetty.util.resource.Resource;


import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {
        DBservice dBservice = new DBservice();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpServlet(dBservice)), "/api/signup");
        context.addServlet(new ServletHolder(new SignInServlet(dBservice)), "/api/signin");


        ContextHandlerCollection handlers = new ContextHandlerCollection();
        handlers.addHandler(context);


        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        server.join();
    }

}