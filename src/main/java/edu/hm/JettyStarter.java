package edu.hm;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Start the application without an AppServer like tomcat.
 *
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 */
public final class JettyStarter {

    public static final String APP_URL = "/";
    public static final int PORT = 8082;
    public static final String WEBAPP_DIR = "./src/main/webapp/";

    /**
     * Private default constructor, it should not be possible to create a instance.
     */
    private JettyStarter() {
    }

    /**
     * Deploy local directories using Jetty without needing a container-based deployment.
     *
     * @param args unused
     * @throws Exception might throw for several reasons.
     */
    public static void main(String... args) throws Exception {
        Server server = new Server(PORT);

        server.setHandler(new WebAppContext(WEBAPP_DIR, APP_URL));

        server.start();
        System.out.println("Jetty listening on port " + PORT);
        server.join();
    }

}
