package ua.ies.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class ServletJetty {
    private static final String DEF_REPLY = "<h1>New Hello Simple Servlet</h1>";
    public static void main(String[] args) throws Exception {

        Server server = new Server(8680);

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(HelloServlet.class, "/");

        server.start();
        server.join();

    }

    public static class HelloServlet extends HttpServlet
    {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            String msg = request.getParameter("msg") != null ? request.getParameter("msg") : DEF_REPLY;
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>" + msg + "</h1>");
        }
    }
}