package Self_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import okhttp3.*;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;

public class ResponseGPT {
    public static void main(String[] args) {
        try {
            // ChatGPTのAPIエンドポイント
            String apiEndpoint = "https://api.openai.com/v1/chat/completions";

            // OpenAI APIアクセストークン
            Dotenv dotenv = null;
            dotenv = Dotenv.configure().load();
            String API_KEY = dotenv.get("API_KEY");
            String apiKey = API_KEY;

            // リクエストのヘッダーを設定
            String authorizationHeader = "Bearer " + apiKey;

            // リクエストのボディを設定
            String prompt = "Hello, how are you?";
            String requestBody = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 50}";

            // APIリクエストを送信
            URL url = new URL(apiEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", authorizationHeader);
            connection.setDoOutput(true);
            connection.getOutputStream().write(requestBody.getBytes("UTF-8"));

            // レスポンスを取得
            int responseCode = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            // レスポンスの処理
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String response = responseBuilder.toString();
                System.out.println("Response: " + response);
                // レスポンスの処理を行う

            } else {
                System.out.println("Error: " + responseCode);
                // エラーレスポンスの処理を行う
            }

            // 接続を閉じる
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
