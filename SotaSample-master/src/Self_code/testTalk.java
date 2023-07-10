package Self_code;

import jp.vstone.RobotLib.CPlayWave;
import jp.vstone.RobotLib.CRobotMem;
import jp.vstone.RobotLib.CSotaMotion;
import jp.vstone.sotatalk.SpeechRecog;
import jp.vstone.sotatalk.TextToSpeechSota;
import jp.vstone.sotatalk.SpeechRecog.RecogResult;

public class testTalk {
	static final String TAG = "SpeechRecSample";
	public static void main(String[] args) {
		//VSMDと通信ソケット・メモリアクセス用クラス
		CRobotMem mem = new CRobotMem();
		//Sota用モーション制御クラス
		CSotaMotion motion = new CSotaMotion(mem);
		SpeechRecog recog = new SpeechRecog(motion);
		
		if(mem.Connect()){
			//Sota仕様にVSMDを初期化
			motion.InitRobot_Sota();
			while(true){
				RecogResult result = recog.getRecognition(20000);
				if(result.recognized){
					if(result.getBasicResult().contains("おわり")){		
						CPlayWave.PlayWave(TextToSpeechSota.getTTSFile("終了するよ"),true);
						break;
					}
					if(result.getBasicResult().contains("終わり")){		
						CPlayWave.PlayWave(TextToSpeechSota.getTTSFile("終了するよ"),true);
						break;
					}
				}
			}
		}
	}
}
