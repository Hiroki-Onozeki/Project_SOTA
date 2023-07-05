package Self_code;

import jp.vstone.sotatalk.MotionAsSotaWish;
import jp.vstone.sotatalk.SpeechRecog;
import jp.vstone.sotatalk.SpeechRecog.RecogResult;
import jp.vstone.sotatalk.TextToSpeechSota;
import Self_code.ResponseFromGPT;
import java.awt.Color;
import jp.vstone.RobotLib.*;


public class Talk {
    static final String TAG = "Talk";
	public static void main(String[] args) {
		//VSMDと通信ソケット・メモリアクセス用クラス
		CRobotMem mem = new CRobotMem();
		//Sota用モーション制御クラス
		CSotaMotion motion = new CSotaMotion(mem);
		SpeechRecog recog = new SpeechRecog(motion);

		CRobotPose pose;
		MotionAsSotaWish tenplateMotion;

		pose = new CRobotPose();		
		if(mem.Connect()){
			//Sota仕様にVSMDを初期化
			motion.InitRobot_Sota();
			while(true){
				RecogResult result = recog.getRecognition(20000);
				//LEDを点灯（左目：赤、右目：赤、口：Max、電源ボタン：赤）
				pose.setLED_Sota(Color.RED, Color.RED, 255, Color.CYAN);
				CRobotUtil.Log(TAG, "play:" + motion.play(pose,1000));
					
				try {
					Thread.sleep(3000); // 10秒(1万ミリ秒)間だけ処理を止める
				} catch (InterruptedException e) {
				}

				if(result.recognized){
					//サーボモータを現在位置でトルクOnにする
					CRobotUtil.Log(TAG, "Servo On");
					motion.ServoOn();
					
					//すべての軸を動作
					pose.SetPose(new Byte[] {1   ,2   ,3   ,4   ,5   ,6   ,7   ,8}	//id
					,  new Short[]{220, -442, -907,  25, 945, -103,  99, -253}				//target pos
							);
					//LEDを点灯（左目：赤、右目：赤、口：Max、電源ボタン：赤）
					pose.setLED_Sota(Color.CYAN, Color.CYAN, 255, Color.CYAN);
								
					//遷移時間1000msecで動作開始。
					CRobotUtil.Log(TAG, "play:" + motion.play(pose,1000));

					try {
						Thread.sleep(3000); // 10秒(1万ミリ秒)間だけ処理を止める
					} catch (InterruptedException e) {
					}
					

                    // GPTを使用して応答を得る
                    String user_utt = result.getBasicResult();


					//　初期化
					if(user_utt.contains("はじめまして")){
						
					}


                    String response = ResponseFromGPT.outputResponse(user_utt);
					//CPlayWave.PlayWave(TextToSpeechSota.getTTSFile(response),true);



					// 発話時間に合わせてランダムにモーションをつける
					tenplateMotion = new MotionAsSotaWish(motion);


					// tenplateMotion.setLEDColorMot(Color.MAGENTA);
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
