import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FourSquareAPI {
    private static final String CLIENT_ID = "COCCBEW3ED41FVPLQQT2VAQTKH5W0I23GZT5IKAKTFRCNDV1";
    private static final String CLIENT_SECRET = "VWL23BGYSMP4HR2BVJQH3JHVDAYBEO41VYU5Z1UOP4EUNF0R";

    public static void main(String[] args) {
        FourSquareAPI fourSquareAPI = new FourSquareAPI();
        fourSquareAPI.getRestaurants("Bar", "Boston");
    }

    public void getRestaurants(String restaurantType, String city) {
        try {
            URL url = new URL("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret="
                    + CLIENT_SECRET + "&ll=42.3442399,-71.0310313&v=20171006&query=" + restaurantType +
                    "&radius=2000&limit=10");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder output = new StringBuilder();
            String nextLine = br.readLine();
            System.out.println("Output from Server .... \n");
            while (nextLine != null) {
                output.append(nextLine);
                nextLine = br.readLine();
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(output.toString());
            JsonArray jsonArray = jsonObject.getAsJsonObject("response").getAsJsonArray("venues");

            for (JsonElement jElement : jsonArray) {
                JsonObject jObject = jElement.getAsJsonObject();
                System.out.println(jObject.get("name"));
            }
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
