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
import java.util.ArrayList;
import java.util.List;



public class ResponseFromGPT {
    public static List<String> dialogue_context = new ArrayList<String>();
    public static List<Boolean> sepaker_list = new ArrayList<Boolean>();
    public static void main(String[] args) {
        System.out.println("!!!!!!");
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
            for (int i=0; i < promptList.size(); i++){
                String promptElem = promptList.get(i);
                promptAll += promptElem;
            }
        } catch(IOException ex) {
			ex.printStackTrace();
		}

        // 対話履歴追加
        addDialogueContext(input_content, true);
        System.out.println(dialogue_context);

        JsonObject json = new JsonObject();
        json.addProperty("model", "gpt-3.5-turbo-0613");
        json.add("messages", new JsonArray());
        json.get("messages").getAsJsonArray().add(buildMessage("system", promptAll));
        for(int i=0; i < dialogue_context.size(); i++){
            String input_utt = dialogue_context.get(i);
            Boolean isUser = sepaker_list.get(i);
            if(isUser == true){
                json.get("messages").getAsJsonArray().add(buildMessage("user", input_utt));
            }else{
                json.get("messages").getAsJsonArray().add(buildMessage("assistant", input_utt));
            }
        }
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
            String response_content = extractContentFromOutput(response.body().string()); 
            addDialogueContext(response_content, false);
            System.out.println(response_content);
            return response_content;
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

    public static void initDilogueContext() {
        dialogue_context.clear();
    }

    public static void addDialogueContext(String utt, Boolean isUser) {
        dialogue_context.add(utt);
        sepaker_list.add(isUser);
    }
}
