package Self_code;

import jp.vstone.RobotLib.CPlayWave;
import jp.vstone.RobotLib.CRobotMem;
import jp.vstone.RobotLib.CSotaMotion;
import jp.vstone.sotatalk.SpeechRecog;
import jp.vstone.sotatalk.SpeechRecog.RecogResult;
import jp.vstone.sotatalk.TextToSpeechSota;
import Self_code.ResponseFromGPT;

public class Talk {
    static final String TAG = "Talk";
	public static void main(String[] args) {
		//VSMDと通信ソケット・メモリアクセス用クラス
		CRobotMem mem = new CRobotMem();
		//Sota用モーション制御クラス
		CSotaMotion motion = new CSotaMotion(mem);
		SpeechRecog recog = new SpeechRecog(motion);
        ChatGPT gpt = new ChatGPT();
		
		if(mem.Connect()){
			//Sota仕様にVSMDを初期化
			motion.InitRobot_Sota();
			while(true){
				RecogResult result = recog.getRecognition(20000);
				if(result.recognized){

                    // GetResonse
                    String user_utt = result.getBasicResult();
                    //String input_content = "あなたは誰ですか？";
                    String response = ResponseFromGPT.outputResponse(user_utt);
					CPlayWave.PlayWave(TextToSpeechSota.getTTSFile(response),true);
					if(result.getBasicResult().contains("おわり")){		
						CPlayWave.PlayWave(TextToSpeechSota.getTTSFile("終了するよ"),true);
						break;
					}
				}
			}
		}
	}
}
