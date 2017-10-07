import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;

import java.io.Reader;
import java.io.StringReader;

public class CommentAnalyzer {

    String userName = "3b5167aa-9c60-40ef-9b5d-a6c94d270726";
    String password = "P2q34jV5tZlX";

    public static void main(String[] args) {
        CommentAnalyzer commentAnalyzer = new CommentAnalyzer();
        String text = "I know the times are difficult! Our sales have been. Boston is great.";
        commentAnalyzer.analyzeComment(text);
    }

    public void analyzeComment(String comments) {
        final String VERSION_DATE = "2016-05-19";
        ToneAnalyzer service = new ToneAnalyzer(VERSION_DATE);
        service.setUsernameAndPassword(userName, password);

        // Call the service and get the tone
        ToneOptions toneOptions = new ToneOptions.Builder().html(comments).build();
        ToneAnalysis tone = service.tone(toneOptions).execute();
        //System.out.println(tone.toString());

        Reader reader = new StringReader(tone.toString());
        JsonReader jReader = new JsonReader(reader);
        JsonParser jParser = new JsonParser();
        JsonObject jObject = (JsonObject) jParser.parse(jReader);
        JsonArray emotionTones = jObject.getAsJsonObject("document_tone").getAsJsonArray("tone_categories").get(0).getAsJsonObject().getAsJsonArray("tones");
        System.out.println("Hello");
        for (JsonElement j : emotionTones) {
            JsonObject emotion = j.getAsJsonObject();
            System.out.println(emotion.get("tone_id") + ": " + emotion.get("score"));
        }
    }
}
