import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BostonFoodInspection {
    public static void main(String[] args) {
        BostonFoodInspection bostonFoodInspection = new BostonFoodInspection();
        bostonFoodInspection.fetchData();
    }

    public void fetchData() {

        try {
            URL url = new URL("http://data.cityofboston.gov/resource/427a-3cn5.json?$limit=10");
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
            JsonArray jsonArray = (JsonArray) jsonParser.parse(output.toString());
            System.out.println(jsonArray);
            /*JsonArray jsonArray = jsonObject.getAsJsonObject("response").getAsJsonArray("venues");*/

            for (JsonElement jElement : jsonArray) {
                JsonObject jObject = jElement.getAsJsonObject();
                //System.out.println(jObject);
                System.out.println(jObject.get("businessname"));
                System.out.println(jObject.get("viollevel"));
                System.out.println(jObject.get("violstatus"));
                System.out.println(jObject.get("violdttm"));
                System.out.println(jObject.get("comments"));
                System.out.println(jObject.get("licenseno"));
            }
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
