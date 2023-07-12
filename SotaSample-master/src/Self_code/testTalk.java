package Self_code;

import java.awt.Color;
import jp.vstone.RobotLib.CPlayWave;
import jp.vstone.RobotLib.CRobotMem;
import jp.vstone.RobotLib.CRobotPose;
import jp.vstone.RobotLib.CSotaMotion;
import jp.vstone.RobotLib.CRobotUtil; 
import jp.vstone.sotatalk.SpeechRecog;
import jp.vstone.sotatalk.TextToSpeechSota;
import jp.vstone.sotatalk.SpeechRecog.RecogResult;
import jp.vstone.sotatalk.MotionAsSotaWish;


public class testTalk {
	static final String TAG = "SpeechRecSample";
	public static void main(String[] args) {
		//VSMDと通信ソケット・メモリアクセス用クラス
		CRobotMem mem = new CRobotMem();
		//Sota用モーション制御クラス
		CSotaMotion motion = new CSotaMotion(mem);
		SpeechRecog recog = new SpeechRecog(motion);
		CRobotPose pose = new CRobotPose();

		//CRobotMotion motionRobo;
		MotionAsSotaWish wish = new MotionAsSotaWish(motion);
		
		if(mem.Connect()){
			//Sota仕様にVSMDを初期化
			motion.InitRobot_Sota();

			//サーボモータを現在位置でトルクOnにする
			CRobotUtil.Log(TAG, "Servo On");
			motion.ServoOn();

            pose.setLED_Sota(Color.RED, Color.RED, 255, Color.MAGENTA);	
			motion.play(pose, 1000);
			motion.waitEndinterpAll();
            pose.setLED_Sota(Color.GRAY, Color.GRAY, 255, Color.MAGENTA);	
			motion.play(pose, 1000);
			CRobotUtil.wait(5000);
            pose.setLED_Sota(Color.YELLOW, Color.YELLOW, 255, Color.MAGENTA);	
			CRobotUtil.wait(5000);
			// motion.waitEndinterpAll();  //補間完了まで待つ

			// CRobotUtil.Log(TAG, "Say Hello");
			// wish.setLEDColorMot(Color.RED); // LEDの色を赤に設定
			// wish.call_4s_A();
			// wish.Say("こんにちは");

			for(int i=0; i < 5; i++){
				CRobotUtil.Log(TAG, "Say thank you");
				wish.setLEDColorMot(Color.GREEN);
				wish.Say("ありがとう", MotionAsSotaWish.MOTION_TYPE_HELLO);

				CRobotUtil.Log(TAG, "Say Hello");
				wish.setLEDColorMot(Color.MAGENTA);
				wish.Say("さようなら", MotionAsSotaWish.MOTION_TYPE_HELLO);
			}
			
			
			
		}
	}
}
