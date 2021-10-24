package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * SparkServer is a server that provides information about the campus map
 */
public class SparkServer {
    // gson used to parse from Java object to JSON
    private static final Gson gson = new Gson();

    // the model to retrieve campus data from
    private static final ModelAPI campusMap = new CampusMap();

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.

        // SPARK ROUTES
        setupGetBuildingNamesRoute();
        setupGetPathRoute();
    }

    /**
     * Sets up the route which retrieves the map of campus building names
     */
    private static void setupGetBuildingNamesRoute(){
        Spark.get("/getBuildingNames", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return gson.toJson(campusMap.buildingNames());
            }
        });
    }

    /**
     * Sets up the route which retrieves the shortest path from two buildings on campus
     *
     * Note: the query-provided start and end names should be valid short names
     */
    private static void setupGetPathRoute(){
        Spark.get("/getPath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startName = request.queryParams("start");
                String endName = request.queryParams("end");

                if(startName == null || endName == null
                        || !campusMap.shortNameExists(startName) || !campusMap.shortNameExists(endName)){
                    // halt current request, provide status code
                    Spark.halt(400);
                }

                return gson.toJson(campusMap.findShortestPath(startName, endName));
            }
        });
    }
}
