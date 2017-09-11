package project.dbm0204.org.meditimer;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by bmathew2014 on 6/20/2015.
 *
 * This service will start the alarm manager.
 * This service should allow for a new activity to launch
 * after an alarm has gone off
 */
public class AlarmService extends Service {

    public static String TAG = AlarmService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /***
     * this method will call the setAlarms from the helper class
     * it will set the next alarm
     *
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent alarmIntent = new Intent(getBaseContext(), AlarmScreen.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtras(intent);
        getApplication().startActivity(alarmIntent);

        AlarmManagerHelper.setAlarms(this);

        return super.onStartCommand(intent, flags, startId);
    }

}
