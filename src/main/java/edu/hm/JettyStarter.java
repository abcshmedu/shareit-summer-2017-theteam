package edu.hm;

import edu.hm.huberneumeier.shareit.resources.MediaResource;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.*;
import org.eclipse.jetty.*;
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

    /**
     * Deploy local directories using Jetty without needing a container-based deployment.
     * @param args unused
     * @throws Exception might throw for several reasons.
     */
    public static void main(String... args) throws Exception {
        //Define the resource configuration
        ResourceConfig config = new ResourceConfig();
        config.packages("edu/hm/huberneumeier/shareit/resources");

        //Create the servlet holder objects
        ServletHolder mediaServlet = new ServletHolder(new ServletContainer(config));

        //Create the jetty server and add the the given website as handler
        Server server = new Server(PORT);
        server.setHandler(new WebAppContext(WEBAPP_DIR, APP_URL));

        //Add all resource handlers from config package
        ServletContextHandler defaultContext = new ServletContextHandler(server, "/*");
        defaultContext.addServlet(mediaServlet, "/*");

        server.start();
        System.out.println("Jetty listening on port " + PORT);
        server.join();
    }

}
