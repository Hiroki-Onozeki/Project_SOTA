package Self_code;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import okhttp3.*;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



public class ResponseFromGPT {
    public static void main(String[] args) {

    }

    public static String outputResponse(String input_content) {
        // APIKey
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();
        String API_KEY = dotenv.get("API_KEY");
        String apiKey = API_KEY;
        OkHttpClient client = new OkHttpClient();

        // プロンプト作成
        Path file = Paths.get("./Self_code/Prompt.txt");
        String promptAll = "";
        try {
            List<String> promptList = Files.readAllLines(file);
            System.out.println(promptList);
            for (int i=0; i < promptList.size(); i++){
                String promptElem = promptList.get(i);
                //promptAll += promptElem;
            }
        } catch(IOException ex) {
			ex.printStackTrace();
            //System.out.println("Error");
		}

        JsonObject json = new JsonObject();
        json.addProperty("model", "gpt-3.5-turbo-0613");
        json.add("messages", new JsonArray());
        //json.get("messages").getAsJsonArray().add(buildMessage("system", "You are a helpful assistant."));
        //json.get("messages").getAsJsonArray().add(buildMessage("user", "Translate the following English text to French: '{text}'"));
        json.get("messages").getAsJsonArray().add(buildMessage("system", promptAll));
        json.get("messages").getAsJsonArray().add(buildMessage("user", input_content));

        // 設定
        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        // GPTの出力を文字列にし、contentのみを取得して出力する
        try {
            Response response = client.newCall(request).execute();
            String content = extractContentFromOutput(response.body().string());
            System.out.println(content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        String Null = "null";
        return Null;
    }

    // プロンプトを追加
    private static JsonObject buildMessage(String role, String content) {
        JsonObject message = new JsonObject();
        message.addProperty("role", role);
        message.addProperty("content", content);
        return message;
    }

    // contentのみを取得
    private static String extractContentFromOutput(String chatGPTOutput) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(chatGPTOutput).getAsJsonObject();

        // choices配列の最初の要素からcontentを抽出
        String content = json.getAsJsonArray("choices")
                .get(0).getAsJsonObject()
                .getAsJsonObject("message")
                .get("content").getAsString();

        return content;
    }
}
