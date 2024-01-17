package Self_code;

import jp.vstone.sotatalk.MotionAsSotaWish;
import jp.vstone.sotatalk.SpeechRecog;
import jp.vstone.sotatalk.SpeechRecog.RecogResult;
import jp.vstone.sotatalk.TextToSpeechSota;
import Self_code.ResponseFromGPT;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import jp.vstone.RobotLib.*;
import jp.vstone.camera.CRoboCamera;
import jp.vstone.camera.FaceDetectResult;


public class Desk_lab {
    static final String TAG = "Talk";
	public static void main(String[] args) {
		//VSMDと通信ソケット・メモリアクセス用クラス
		CRobotMem mem = new CRobotMem();
		//Sota用モーション制御クラス
		CSotaMotion motion = new CSotaMotion(mem);
		SpeechRecog recog = new SpeechRecog(motion);

		CRobotPose pose = new CRobotPose();
		MotionAsSotaWish tenplateMotion;
		CRoboCamera cam = new CRoboCamera("/dev/video0", motion);
        tenplateMotion = new MotionAsSotaWish(motion);

        // 説明文
        String desc_content = "こんにちは！稲葉研究室にようこそ！"
                            + "僕はロボットのSOTAだよ！よろしくね！"
                            + "稲葉研究室では対話システムについて研究を行っているよ"
                            + "今、研究室内でいくつか対話システムのデモを行っているから、是非体験していってね！"
                            + "もし研究の内容や、研究活動、大学生活のことについて質問があったら、稲葉研究室の学生になんでも聞いてね！"
                            + "それでは、楽しんでいってね！";

		if(mem.Connect()){
			//Sota仕様にVSMDを初期化
			motion.InitRobot_Sota();
            //顔検索有効
			cam.setEnableFaceSearch(true);
			//フェイストラッキング開始
			cam.StartFaceTraking();

			while(true){
                // 顔認識
                FaceDetectResult result_faceDetect = cam.getDetectResult();
				if(result_faceDetect.isDetect()){
                    tenplateMotion.setLEDColorMot(Color.CYAN);
                    tenplateMotion.Say(desc_content, MotionAsSotaWish.MOTION_TYPE_TALK);
                        }
                    }
                }
            }
		}
	


