package Self_code;

import java.util.Scanner;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import jp.vstone.RobotLib.*;    //CRobotUtil
import jp.vstone.sotatalk.MotionAsSotaWish;
import jp.vstone.sotatalk.TextToSpeechSota;

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

		MotionAsSotaWish wish = new MotionAsSotaWish(motion);

		Scanner scanner = new Scanner(System.in);

		
		if(mem.Connect()){
			//Sota仕様にVSMDを初期化
			motion.InitRobot_Sota();
			CRobotUtil.Log(TAG, "Rev. " + mem.FirmwareRev.get());
			
			//サーボモータを現在位置でトルクOnにする
			CRobotUtil.Log(TAG, "Servo On");
			motion.ServoOn();

			while(true){
				System.out.println("call,hello,low,presen,talk,end");
    			String talk_class = scanner.next();

				// テンプレートモーション確認
				if(talk_class.equals("call")){
					wish.setLEDColorMot(Color.GREEN); // LEDの色を緑に設定
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("call_4s_A"),true);
					// wish.Say("こんにちは。これはcall_4s_Aです", "");
					wish.call_4s_A();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("call_4s_B"),true);
					// wish.Say("こんにちは。これはcall_4s_Bです");
					wish.call_4s_B();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("call_4s_C"),true);
					// wish.Say("こんにちは。これはcall_4s_Cです");
					wish.call_4s_C();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("call_4s_C1"),true);
					// wish.Say("こんにちは。これはcall_4s_C1です");
					wish.call_4s_C1();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("call_4s_C11"),true);
					// wish.Say("こんにちは。これはcall_4s_C11です");
					wish.call_4s_C11();
				}else if(talk_class.equals("hello")){
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("hello_2s_1"),true);
					// wish.Say("こんにちは。これはhello_2s_1です");
					wish.hello_2s_1();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("hello_2s_2"),true);
					// wish.Say("こんにちは。これはhello_2s_2です");
					wish.hello_2s_2();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("hello_2s_3"),true);
					// wish.Say("こんにちは。これはhello_2s_3です");
					wish.hello_2s_3();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("hello_2s_4"),true);
					// wish.Say("こんにちは。これはhello_2s_4です");
					wish.hello_2s_4();
				}else if(talk_class.equals("low")){
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_2s_1"),true);
					// wish.Say("こんにちは。これはlow_2s_1です");
					wish.low_2s_1();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_2s_2s"),true);
					// wish.Say("こんにちは。これはlow_2s_2sです");
					wish.low_2s_2s();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_2s_C"),true);
					// wish.Say("こんにちは。これはlow_2s_Cです");
					wish.low_2s_C();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_2s_C1"),true);
					// wish.Say("こんにちは。これはlow_2s_C1です");
					wish.low_2s_C1();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_4s_1"),true);
					// wish.Say("こんにちは。これはlow_4s_1です");
					wish.low_4s_1();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_4s_1"),true);
					// wish.Say("こんにちは。これはlow_4s_2です");
					wish.low_4s_2();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_4s_3"),true);
					// wish.Say("こんにちは。これはlow_4s_3です");
					wish.low_4s_3();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_4s_f1"),true);
					// wish.Say("こんにちは。これはlow_4s_f1です");
					wish.low_4s_f1();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_4s_f11"),true);
					// wish.Say("こんにちは。これはlow_4s_f11です");
					wish.low_4s_f11();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_6s_1"),true);
					// wish.Say("こんにちは。これはlow_6s_1です");
					wish.low_6s_1();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_6s_2"),true);
					// wish.Say("こんにちは。これはlow_6s_2です");
					wish.low_6s_2();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("low_6s_f1"),true);
					// wish.Say("こんにちは。これはlow_6s_f1です");
					wish.low_6s_f1();
				}else if(talk_class.equals("presen")){
					wish.Say("こんにちは。これはpresen_n_2s_aです");
					// wish.presen_n_2s_a();

					// wish.Say("こんにちは。これはpresen_n_2s_bです");
					// wish.presen_n_2s_b();
					
					// wish.Say("こんにちは。これはpresen_n_4s_aです");
					// wish.presen_n_4s_a();
					
					// wish.Say("こんにちは。これはpresen_n_4s_bです");
					// wish.presen_n_4s_b();

					// wish.Say("こんにちは。これはpresen_n_6s_aです");
					// wish.presen_n_6s_a();
					
					// wish.Say("こんにちは。これはpresen_n_6s_bです");
					// wish.presen_n_6s_b();


					// wish.Say("こんにちは。これはpresen_u_2s_aです");
					// wish.presen_u_2s_a();

					// wish.Say("こんにちは。これはpresen_u_2s_bです");
					// wish.presen_u_2s_b();
					
					// wish.Say("こんにちは。これはpresen_u_4s_aです");
					// wish.presen_u_4s_a();
					
					// wish.Say("こんにちは。これはpresen_u_4s_bです");
					// wish.presen_u_4s_b();

					// wish.Say("こんにちは。これはpresen_u_6s_aです");
					// wish.presen_u_6s_a();
					
					// wish.Say("こんにちは。これはpresen_u_6s_bです");
					// wish.presen_u_6s_b();
				}else if(talk_class.equals("talk")){
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_2s_1"),true);
					// wish.Say("こんにちは。これはtalk_2s_1です");
					wish.talk_2s_1();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_2s_2"),true);					
					// wish.Say("こんにちは。これはtalk_2s_2です");
					wish.talk_2s_2();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_2s_3"),true);
					// wish.Say("こんにちは。これはtalk_2s_3です");
					wish.talk_2s_3();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_2s_4"),true);
					// wish.Say("こんにちは。これはtalk_2s_4です");
					wish.talk_2s_4();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_4s_1"),true);
					// wish.Say("こんにちは。これはtalk_4s_1です");
					wish.talk_4s_1();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_4s_2"),true);
					// wish.Say("こんにちは。これはtalk_4s_2です");
					wish.talk_4s_2();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_4s_3"),true);
					// wish.Say("こんにちは。これはtalk_4s_3です");
					wish.talk_4s_3();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_4s_4"),true);
					// wish.Say("こんにちは。これはtalk_4s_4です");
					wish.talk_4s_4();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_4s_5"),true);
					// wish.Say("こんにちは。これはtalk_4s_5です");
					wish.talk_4s_5();
					
					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_6s_1"),true);
					// wish.Say("こんにちは。これはtalk_6s_1です");
					wish.talk_6s_1();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_6s_2"),true);
					// wish.Say("こんにちは。これはtalk_6s_2です");
					wish.talk_6s_2();

					CPlayWave.PlayWave(TextToSpeechSota.getTTS("talk_6s_3"),true);
					// wish.Say("こんにちは。これはtalk_6s_3です");
					wish.talk_6s_3();
				}else{
					break;
				}
			}
				
            
        }
    }
}
