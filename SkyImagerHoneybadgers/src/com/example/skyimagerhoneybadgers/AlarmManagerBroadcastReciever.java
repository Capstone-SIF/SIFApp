package com.example.skyimagerhoneybadgers;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;


public class AlarmManagerBroadcastReciever extends BroadcastReceiver {
	final public static String ONE_TIME = "onetime";
	//*******************************************Daily Timer****************************************
	//Precondition: Alarm is set, intent triggered at time set
	//Postcondition: SkyImagerActivity is started
	@Override
	public void onReceive(Context context, Intent intent) {
		 PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         
         //Acquire the lock
         wl.acquire();

         Intent alarmPopup = new Intent(context, MainActivity.class);
         alarmPopup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(alarmPopup);
         
         //Release the lock
         wl.release();
         
	}
	//****************************************************Set Daily timer*****************************
	//Precondition: This will need to be manually set before application is installed using cal.set() function
	//Postcondition:  Pending intent is created to call onReceive above
	//Notes: defined in SkyImagerActivity as follows: 
	//alarm = new AlarmManagerBroadcastReceiver();
	//alarm.SetAlarm(topContext);
	//AlarmManagerBroadcastReceiver must also be defined in manifest:  <receiver android:name="AlarmManagerBroadcastReceiver"></receiver>
	public void SetAlarm(Context context)
    {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,16);
		cal.set(Calendar.MINUTE, 27);
		cal.set(Calendar.SECOND, 0);
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReciever.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 30 seconds
        am.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
       // am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 10, pi); 
    }
	//************************Cancel Pending Alarms**************************************************
	//Precondition: An alarm has previously been set
	//Postcondition: All alarms are now canceled
    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReciever.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
    //**************************Set a One Time Alarm*************************************************
    //Precondition: This will need to be manually set before application is installed using cal.set() function
  	//Postcondition:  Pending intent is created to call onReceive above
  	//Notes: defined in SkyImagerActivity as follows: 
  	//alarm = new AlarmManagerBroadcastReceiver();
  	//alarm.SetAlarm(topContext);
  	//AlarmManagerBroadcastReceiver must also be defined in manifest:  <receiver android:name="AlarmManagerBroadcastReceiver"></receiver>
    public void setOnetimeTimer(Context context){
    	AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReciever.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }

}
