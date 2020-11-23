package pro.tasking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

public class SecondService extends Service {
    int i=0;
    public SecondService() {
    }

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
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void run() {
                secondtimer();
                Runtime.getRuntime().gc();/**/
                // minutetimer(i);

                handler.postDelayed(this, 1000);
            }
        });

        return START_STICKY;
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
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void secondtimer() {
        OptionDB optionDB = new OptionDB(getApplicationContext());
        if (optionDB.getjs().get(3).equals("pack1")) {

            BlockTask blockTask = new BlockTask();
            blockTask.blockk(getApplicationContext());
            //timer 1 second

            optionDB.close();
        }

    }

}
