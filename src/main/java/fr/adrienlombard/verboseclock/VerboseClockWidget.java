package fr.adrienlombard.verboseclock;

import android.app.AlarmManager;
import android.os.Bundle;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import fr.desconspareils.clock.R;

import java.util.Calendar;

public class VerboseClockWidget extends AppWidgetProvider {

    public static String CLOCK_WIDGET_UPDATE = "fr.adrienlombard.verboseclock.VerboseClockWidget.CLOCK_WIDGET_UPDATE";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5000, createClockTickIntent(context));
    }

	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
        final int N = appWidgetIds.length;
 
        // Perform this loop procedure for each App Widget that belongs to this
        // provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
 
    /**
     * Update the widget
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {


        // Prepare widget views
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_verbose_clock_widget);

        String heure = ClockActivity.getHourString(context);

        views.setTextViewText(R.id.hourTv, heure.substring(0, heure.indexOf("heure") - 1).replace("\n", "").toUpperCase());
        views.setTextViewText(R.id.hourLabelTv, heure.substring(heure.indexOf("heure")).replace("\n", ""));

        if(!ClockActivity.getMinutesString(context).equals("")) {
            String minute = ClockActivity.getMinutesString(context);
            views.setTextViewText(R.id.minTv, minute.substring(0, minute.indexOf("minute") - 1).replace("\n", "").toUpperCase());
            views.setTextViewText(R.id.minLabelTv, minute.substring(minute.indexOf("minute")).replace("\n", ""));
        }
        else {
            views.setTextViewText(R.id.minTv, "");
            views.setTextViewText(R.id.minLabelTv, "");
        }

        Intent intent = new Intent(context, VerboseClockWidget.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.clock_widget, pendingIntent);
        
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override 
    public void onReceive(Context context, Intent intent) 
    { 
        super.onReceive(context, intent);

        if(CLOCK_WIDGET_UPDATE.equals(intent.getAction())) {

            Bundle extras = intent.getExtras();

            if(extras!=null) {

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisAppWidget = new ComponentName(context.getPackageName(), VerboseClockWidget.class.getName());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

                onUpdate(context, appWidgetManager, appWidgetIds);

            }

        }
        else {
            Bundle extras = intent.getExtras();

            if(extras!=null) {

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisAppWidget = new ComponentName(context.getPackageName(), VerboseClockWidget.class.getName());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

                onUpdate(context, appWidgetManager, appWidgetIds);

            }
        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createClockTickIntent(context));
    }

    private PendingIntent createClockTickIntent(Context context) {
        Intent intent = new Intent(CLOCK_WIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
    
}
