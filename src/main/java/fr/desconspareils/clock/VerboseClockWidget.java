package fr.desconspareils.clock;

import android.graphics.*;
import android.os.Bundle;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.RemoteViews;

public class VerboseClockWidget extends AppWidgetProvider {

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

        views.setTextViewText(R.id.hourTv, ClockActivity.getHourString(context));

        if(!ClockActivity.getMinutesString(context).equals("")) {
            views.setTextViewText(R.id.etTv, "et");
            views.setTextViewText(R.id.minTv, ClockActivity.getMinutesString(context));
        }

        Intent intent = new Intent(context, VerboseClockWidget.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.clock_widget, pendingIntent);
        
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static Bitmap createStringBitmap(Context context, String time, FrameLayout layout)
    {
        Bitmap myBitmap = Bitmap.createBitmap(layout.getWidth(), 60, Bitmap.Config.ARGB_4444);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
        Typeface clock = Typeface.createFromAsset(context.getAssets(),"Roboto-Thin.ttf");
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(clock);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        myCanvas.drawText(time, layout.getX(), layout.getY(), paint);
        return myBitmap;
    }

    @Override 
    public void onReceive(Context context, Intent intent) 
    { 
         super.onReceive(context, intent);
         
         Bundle extras = intent.getExtras();
         
         if(extras!=null) {
        	 
        	 AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			 ComponentName thisAppWidget = new ComponentName(context.getPackageName(), VerboseClockWidget.class.getName());
			 int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
			
			 onUpdate(context, appWidgetManager, appWidgetIds);
         }
         
    } 
    
}
