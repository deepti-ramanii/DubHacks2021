package server;

import server.data.*;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class SparkServer {
    private static PlayerInfoDatabaseHelper dbHelper = PlayerInfoDatabaseHelper.getInstance();
    private static PlayerMatcher matcher = PlayerMatcher.getInstance();
    private static Gson gson;

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        gson = new Gson();

        Spark.get("/check-user", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // get input and check validity
                String user_id = request.queryParams("user_id");
                if (user_id == null) {
                    Spark.halt(400, "user id must be valid");
                }

                boolean getUserSuccessful = dbHelper.isInTable(user_id);
                return gson.toJson(getUserSuccessful);
            }
        });

        //query looks like /add-user?user_id=ID&player_age=AGE&competitive=COMPETITIVE&uses_vc=USESVC&likes_anime=LIKESANIME
        Spark.get("/add-user", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // get input and check validity
                String user_id = request.queryParams("user_id");
                int age = Integer.parseInt(request.queryParams("player_age"));
                boolean competitive = Boolean.parseBoolean(request.queryParams("competitive"));
                boolean uses_vc = Boolean.parseBoolean(request.queryParams("uses_vc"));
                boolean likes_anime = Boolean.parseBoolean(request.queryParams("likes_anime"));
                if (user_id == null) {
                    Spark.halt(400, "user id must be valid");
                }

                // try adding to table
                UserInfo user_info = new UserInfo(age, new  boolean[] { competitive, uses_vc }, new boolean[] { likes_anime });
                boolean addUserSuccessful = dbHelper.addUser(user_id, user_info);
                return gson.toJson(addUserSuccessful);
            }
        });

        // query looks like /update-preferences?user_id=ID&min_age=MIN&max_age=MAX&competitive=COMPETITIVE&uses_vc=USESVC&use_hobbies=USEHOBBIES
        Spark.get("/update-preferences", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // get input and check validity
                String user_id = request.queryParams("user_id");
                int min_age = Integer.parseInt(request.queryParams("min_age"));
                int max_age = Integer.parseInt(request.queryParams("max_age"));
                boolean competitive = Boolean.parseBoolean(request.queryParams("competitive"));
                boolean uses_vc = Boolean.parseBoolean(request.queryParams("uses_vc"));
                boolean use_hobbies = Boolean.parseBoolean(request.queryParams("use_hobbies"));
                if (user_id == null) {
                    Spark.halt(400, "user id must be valid");
                }

                // try adding to table
                UserPreferences user_prefs = new UserPreferences(min_age, max_age, new boolean[] { competitive, uses_vc }, use_hobbies);
                boolean updatePrefsSuccessful = dbHelper.updatePreferences(user_id, user_prefs);
                return gson.toJson(updatePrefsSuccessful);
            }
        });

        //query looks like /update-info?user_id=ID&player_age=AGE&competitive=COMPETITIVE&uses_vc=USESVC&likes_anime=LIKESANIME
        Spark.get("/update-info", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // get input and check validity
                String user_id = request.queryParams("user_id");
                int age = Integer.parseInt(request.queryParams("player_age"));
                boolean competitive = Boolean.parseBoolean(request.queryParams("competitive"));
                boolean uses_vc = Boolean.parseBoolean(request.queryParams("uses_vc"));
                boolean likes_anime = Boolean.parseBoolean(request.queryParams("likes_anime"));
                if (user_id == null) {
                    Spark.halt(400, "user id must be valid");
                }

                // try adding to table
                UserInfo user_info = new UserInfo(age, new  boolean[] { competitive, uses_vc }, new boolean[] { likes_anime });
                boolean updateInfoSuccessful = dbHelper.updateInfo(user_id, user_info);
                return gson.toJson(updateInfoSuccessful);
            }
        });

        Spark.get("get-match", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // get input and check validity
                String user_id = request.queryParams("user_id");
                if (user_id == null) {
                    Spark.halt(400, "user id must be valid");
                }

                // try getting a match
                String foundMatch = matcher.matchUsingPrefs(user_id);
                return gson.toJson(foundMatch);
            }
        });

        Spark.get("get-is-matched", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // get input and check validity
                String user_id = request.queryParams("user_id");
                if (user_id == null) {
                    Spark.halt(400, "user id must be valid");
                }

                // try getting a match
                boolean isMatched = dbHelper.isMatched(user_id);
                return gson.toJson(isMatched);
            }
        });

        Spark.get("update-is-matched", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // get input and check validity
                String user_id = request.queryParams("user_id");
                boolean is_matched = Boolean.parseBoolean(request.queryParams("is_matched"));
                if (user_id == null) {
                    Spark.halt(400, "user id must be valid");
                }

                // try getting a match
                boolean updateMatchSuccessful = dbHelper.setMatched(user_id, is_matched);
                return gson.toJson(updateMatchSuccessful);
            }
        });
    }
}
