package Self_code;

import jp.vstone.sotatalk.MotionAsSotaWish;
import jp.vstone.sotatalk.SpeechRecog;
import jp.vstone.sotatalk.SpeechRecog.RecogResult;
import jp.vstone.sotatalk.TextToSpeechSota;
import Self_code.ResponseFromGPT;
import java.awt.Color;
import jp.vstone.RobotLib.*;
import jp.vstone.camera.CRoboCamera;
import jp.vstone.camera.FaceDetectResult;


public class Talk {
    static final String TAG = "Talk";
	public static void main(String[] args) {
		//VSMDと通信ソケット・メモリアクセス用クラス
		CRobotMem mem = new CRobotMem();
		//Sota用モーション制御クラス
		CSotaMotion motion = new CSotaMotion(mem);
		SpeechRecog recog = new SpeechRecog(motion);

		CRobotPose pose = new CRobotPose();
		MotionAsSotaWish tenplateMotion;
		
		if(mem.Connect()){
			//Sota仕様にVSMDを初期化
			motion.InitRobot_Sota();
            //顔検索有効
			cam.setEnableFaceSearch(true);
			//フェイストラッキング開始
			cam.StartFaceTraking();

			while(true){
				pose = new CRobotPose();
            	pose.setLED_Sota(Color.BLACK, Color.BLACK, 255, Color.BLACK);			
				CRobotUtil.Log(TAG, "setLED:" + motion.play(pose,20000));;
				RecogResult result = recog.getRecognition(20000);

				if(result.recognized){
                    // GPTを使用して応答を得る
                    String user_utt = result.getBasicResult();

					//　初期化
					if(user_utt.contains("はじめまして")){
						
					}
                    String response = ResponseFromGPT.outputResponse(user_utt);
					//CPlayWave.PlayWave(TextToSpeechSota.getTTSFile(response),true);

					// 発話時間に合わせてランダムにモーションをつける
					tenplateMotion = new MotionAsSotaWish(motion);
					tenplateMotion.setLEDColorMot(Color.MAGENTA);
    				tenplateMotion.Say(response);
            		motion.waitEndinterpAll();

					if(result.getBasicResult().contains("おわり")){		
						CPlayWave.PlayWave(TextToSpeechSota.getTTSFile("終了するよ"),true);
						break;
					}
				}
			}
		}
	}
}
