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

		CRobotPose pose = new CRobotPose();
		MotionAsSotaWish tenplateMotion;
		
		if(mem.Connect()){
			//Sota仕様にVSMDを初期化
			motion.InitRobot_Sota();
			while(true){
				RecogResult result = recog.getRecognition(20000);

				if(result.recognized){
					CRobotUtil.Log(TAG, "Servo On");
					motion.ServoOn();
            		pose.SetPose(new Byte[] {1   ,2   ,3   ,4   ,5   ,6   ,7   ,8}  //id
                        		,  new Short[]{220, -442, -907, 25, 945,   -103,  99, -253}   //target pos
            					);
            		pose.setLED_Sota(Color.MAGENTA, Color.MAGENTA, 255, Color.BLUE);
            		motion.play(pose,1000);
            		motion.waitEndinterpAll();
                    // GPTを使用して応答を得る
                    String user_utt = result.getBasicResult();

					//　初期化
					if(user_utt.contains("はじめまして")){
						ResponseFromGPT.initDilogueContext();
					}
                    String response = ResponseFromGPT.outputResponse(user_utt);
					String[] responses = response.split(";");
					String response_content = responses[0];
        			String emotion = responses[1];
					String response_emotion = emotion.replace("\n", "");
					System.out.println("出力感情"); 
					System.out.println(response_emotion);
					//CPlayWave.PlayWave(TextToSpeechSota.getTTSFile(response),true);

					if(response_emotion.contains("嬉")){
						tenplateMotion = new MotionAsSotaWish(motion);
						tenplateMotion.setLEDColorMot(Color.YELLOW);
						tenplateMotion.Say(response_content);
            			motion.waitEndinterpAll();
						System.out.println("嬉しい");
					}else if(response_emotion.contains("怒")){
						tenplateMotion = new MotionAsSotaWish(motion);
						tenplateMotion.setLEDColorMot(Color.RED);
						tenplateMotion.Say(response_content);
            			motion.waitEndinterpAll();
						System.out.println("怒り");
					}else if(response_emotion.contains("哀")){
						tenplateMotion = new MotionAsSotaWish(motion);
						tenplateMotion.setLEDColorMot(Color.BLUE);
						tenplateMotion.Say(response_content);
            			motion.waitEndinterpAll();
						System.out.println("悲しい");
					}else if(response_emotion.contains("楽")){
						tenplateMotion = new MotionAsSotaWish(motion);
						tenplateMotion.setLEDColorMot(Color.GREEN);
						tenplateMotion.Say(response_content);
            			motion.waitEndinterpAll();
						System.out.println("楽しい");
					}else{
						tenplateMotion = new MotionAsSotaWish(motion);
						tenplateMotion.setLEDColorMot(Color.GRAY);
						tenplateMotion.Say(response_content);
            			motion.waitEndinterpAll();
						System.out.println("その他");
					}
					// 発話時間に合わせてランダムにモーションをつける
					//tenplateMotion = new MotionAsSotaWish(motion);
					//tenplateMotion.setLEDColorMot(Color.MAGENTA);
					//tenplateMotion.StartIdling();
    				//tenplateMotion.Say(response_content);
            		//motion.waitEndinterpAll();
					//tenplateMotion.StopIdling();

					if(result.getBasicResult().contains("おわり")){		
						CPlayWave.PlayWave(TextToSpeechSota.getTTSFile("終了するよ"),true);
						break;
					}
				}
			}
		}
	}
}
