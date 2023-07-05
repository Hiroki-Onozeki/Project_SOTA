package Self_code;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import jp.vstone.RobotLib.*;
import jp.vstone.sotatalk.MotionAsSotaWish;


public class testMotion {
    static final String TAG = "MotionSample";
	public static void main(String args[]){
		CRobotUtil.Log(TAG, "Start " + TAG);

		CRobotPose pose;
		//VSMDと通信ソケット・メモリアクセス用クラス
		CRobotMem mem = new CRobotMem();
		//Sota用モーション制御クラス
		CSotaMotion motion = new CSotaMotion(mem);

        //CRobotMotion motionRobo;
        MotionAsSotaWish tenplateMotion;
		
		if(mem.Connect()){
			//Sota仕様にVSMDを初期化
			motion.InitRobot_Sota();
			CRobotUtil.Log(TAG, "Rev. " + mem.FirmwareRev.get());
			
			//サーボモータを現在位置でトルクOnにする
			CRobotUtil.Log(TAG, "Servo On");
			motion.ServoOn();

            // 色確認
            pose = new CRobotPose();
            pose.setLED_Sota(Color.GRAY, Color.GRAY, 255, Color.MAGENTA);			
			//遷移時間1000msecで動作開始。
			CRobotUtil.Log(TAG, "play:" + motion.play(pose,5000));
			//補間完了まで待つ
            motion.waitEndinterpAll();


            // テンプレートモーション確認
            tenplateMotion = new MotionAsSotaWish(motion);
            tenplateMotion.call_4s_A();
            //CRobotUtil.Log(TAG, "play:" + motionRobo.play(tenplateMotion,1000));
            
        }
    }
}
