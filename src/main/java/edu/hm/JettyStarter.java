package edu.hm;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Start the application without an AppServer like tomcat.
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 *
 */
public class JettyStarter {

    public static final String APP_URL = "/";
    public static final int PORT = 8082;
    public static final String WEBAPP_DIR = "./src/main/webapp/";
    public static final String RESOURCE_HANDLER_DIR = "edu/hm/huberneumeier/shareit/resources";

    /**
     * Deploy local directories using Jetty without needing a container-based deployment.
     * @param args unused
     * @throws Exception might throw for several reasons.
     */
    public static void main(String... args) throws Exception {
        //Define the resource configuration
        ResourceConfig config = new ResourceConfig();
        config.packages(RESOURCE_HANDLER_DIR);

        //Create the servlet holder objects
        ServletHolder mediaServlet = new ServletHolder(new ServletContainer(config));

        //Create the jetty server
        Server server = new Server(PORT);

        //Add all resource handler from config package
        ServletContextHandler defaultContext = new ServletContextHandler(server, "/shareit");
        //defaultContext.addServlet(mediaServlet, "/shareit");

        server.setHandler(new WebAppContext(WEBAPP_DIR, APP_URL));

        server.start();
        System.out.println("Jetty listening on port " + PORT);
        server.join();
    }

}
