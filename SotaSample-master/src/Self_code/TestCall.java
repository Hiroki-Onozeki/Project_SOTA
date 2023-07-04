package Self_code;
import Self_code.ResponseFromGPT;

public class TestCall {
    public static void main(String[] args) {
        String input_content = "あなたは誰ですか";
        String response = ResponseFromGPT.outputResponse(input_content);
        System.out.println(response);
    }
}
