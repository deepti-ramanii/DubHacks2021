package components.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;


public class CORSFilter {
    /**
     * Contains all the headers that need to be added
     */
    private final HashMap<String, String> corsHeaders = new HashMap<>();

    /**
     * Prepares the filter to be applied to the Spark system by initialized the headers
     * that need to be used.
     */
    public CORSFilter() {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers",
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
    }

    /**
     * Applies the filter globally to all Spark responses. Anytime this Spark server responds
     * to a request following the invocation of this method, completely-permissive CORS headers
     * will be added to the response headers.
     */
    public void apply() {
        Filter filter = new Filter() {
            @Override
            public void handle(Request request, Response response) {
                corsHeaders.forEach(response::header);
            }
        };
        Spark.afterAfter(filter); // Applies this filter even if there's a halt() or exception.
        //
        Logger logger = LoggerFactory.getLogger("CampusPaths Server");
        logger.info("Listening on: http://localhost:" + Spark.port());
    }
}
