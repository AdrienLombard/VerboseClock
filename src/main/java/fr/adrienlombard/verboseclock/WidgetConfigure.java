package fr.adrienlombard.verboseclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class WidgetConfigure extends Activity {

    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;
 
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    /** Called when the activity is created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        // If the user closes window, don't create the widget
        setResult(RESULT_CANCELED);
 
        // Find widget id from launching intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        configureWidget(getApplicationContext());
 
        // Make sure we pass back the original appWidgetId before closing the activity
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
 
    /**
     * Configures the created widget
     * @param context
     */
    public void configureWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        VerboseClockWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
    }
    
    
}