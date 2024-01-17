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
		CRoboCamera cam = new CRoboCamera("/dev/video0", motion);
        tenplateMotion = new MotionAsSotaWish(motion);

        // 顔を認識したら話しかけてくる用の色々
        Boolean is_meet = true;
        ArrayList<String> hello_contents_list = new ArrayList<String>(5);
        hello_contents_list.add("こんにちは。僕とお話しようよ！");
        hello_contents_list.add("僕の名前はSOTAだよ。");
        hello_contents_list.add("おーい、聞こえてる？");
        hello_contents_list.add("一緒におしゃべりしようよ。");
        hello_contents_list.add("ヘイ！そこのイケメン！");

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

                    // 初期状態なら話しかかけてくる
                    if(is_meet == true){
                        Random rnd = new Random();
                        int index = rnd.nextInt(hello_contents_list.size());
                        String hello_content = hello_contents_list.get(index);
                        tenplateMotion.Say(hello_content, MotionAsSotaWish.MOTION_TYPE_HELLO);
                    }

                    // 音声認識
                    tenplateMotion.setLEDColorMot(Color.CYAN);
                    RecogResult result = recog.getRecognition(20000);
                    if(result.recognized){

                        // 考えるモーション
                        motion.ServoOn();
                        pose.SetPose(new Byte[] {1   ,2   ,3   ,4   ,5   ,6   ,7   ,8}  //id
                                    ,  new Short[]{220, -442, -907, 25, 945,   -103,  99, -253}   //target pos
                                    );
                        pose.setLED_Sota(Color.MAGENTA, Color.MAGENTA, 255, Color.MAGENTA);	
						motion.play(pose,1000);
                        motion.waitEndinterpAll();

                        // GPTを使用して応答を得る
                        String user_utt = result.getBasicResult();

                        //　初期化
                        if(user_utt.contains("はじめまして")){
                            ResponseFromGPT.initDilogueContext();
                            is_meet = true;
                            continue;
                        }
                        // 終了
                        if(user_utt.contains("終了して")){
                            tenplateMotion.Say("終了するよ");
                            System.out.println("STOP PROGRAM");
                            ResponseFromGPT.initDilogueContext();
                            cam.StopFaceTraking();
                            motion.ServoOff();
                            break;
                        }

                        // 応答から感情を抽出する、なければ「無」にする
                        String response = ResponseFromGPT.outputResponse(user_utt);
                        String[] responses = response.split(";");
                        is_meet = false;
                        String response_emotion;
                        String response_content;
                        if(responses.length == 2){
                            response_content = responses[0];
                            response_emotion = responses[1];
                        }else{
                            response_content = responses[0];
                            response_emotion = "無";
                        }
                        System.out.println("GPT出力感情: " + response_emotion);

                        // 感情に合わせたモーション・色で発話する
                        if(response_emotion.contains("嬉")){
                            tenplateMotion.Say(response_content, MotionAsSotaWish.MOTION_TYPE_CALL);
                            System.out.println("嬉しい");
                        }else if(response_emotion.contains("怒")){
                            tenplateMotion.Say(response_content, MotionAsSotaWish.MOTION_TYPE_LOW);
                            System.out.println("怒り");
                        }else if(response_emotion.contains("哀")){
                            tenplateMotion.Say(response_content, MotionAsSotaWish.MOTION_TYPE_LOW);
                            System.out.println("悲しい");
                        }else if(response_emotion.contains("楽")){
                            tenplateMotion.Say(response_content, MotionAsSotaWish.MOTION_TYPE_TALK);
                            System.out.println("楽しい");
                        }else{
                            tenplateMotion.Say(response_content, MotionAsSotaWish.MOTION_TYPE_HELLO);
                            System.out.println("ニュートラル");
                        }
                    }
                }
            }
		}
	}
}
