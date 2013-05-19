package fr.desconspareils.clock;

import java.util.Calendar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ClockActivity extends Activity {

	TextView clock;
	String[] days, nombres;
	String ilest, passe, bientot, quart, moinsQuart, demi, heure, heures, minute, minutes, et;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clock);
		
		clock = (TextView) findViewById(R.id.clock);
		moinsQuart = getString(R.string.moinsQuart);
		demi = getString(R.string.demi);
		quart = getString(R.string.quart);
        nombres = getResources().getStringArray(R.array.nombres);
        heure = getString(R.string.heure);
        heures = getString(R.string.heures);
        minute = getString(R.string.minute);
        minutes = getString(R.string.minutes);
        et = getString(R.string.et);

        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        clock.setTypeface(font);
    }
	
	@Override
	protected void onStart() {
		
		super.onStart();

		clock.setText(getTimeString());
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();

		clock.setText(getTimeString());
	}

    public String getTimeString() {
        String hour = createHourString(nombres, heure, heures);
        String min = createMinuteString(nombres, minute, minutes, et);

        if(min.equals("")) {
            return hour;
        }
        else {
            return hour + et + "\n" + min;
        }
    }

    public static String getTimeString(Context context) {

        String[] nombres = context.getResources().getStringArray(R.array.nombres);
        String heure = context.getString(R.string.heure);
        String heures = context.getString(R.string.heures);
        String minute = context.getString(R.string.minute);
        String minutes = context.getString(R.string.minutes);
        String et = context.getString(R.string.et);

        String hour = createHourString(nombres, heure, heures);
        String min = createMinuteString(nombres, minute, minutes, et);

        if(min.equals("")) {
            return hour;
        }
        else {
            return hour + et + "\n" + min;
        }
    }

    public String getHourString() {
        return createHourString(nombres, heure, heures);
    }

    public static String getHourString(Context context) {
        String[] nombres = context.getResources().getStringArray(R.array.nombres);
        String heure = context.getString(R.string.heure);
        String heures = context.getString(R.string.heures);

        return createHourString(nombres, heure, heures);
    }

    public String getMinuteString() {
        return createMinuteString(nombres, minute, minutes, et);
    }

    public static String getMinutesString(Context context) {
        String[] nombres = context.getResources().getStringArray(R.array.nombres);
        String minute = context.getString(R.string.minute);
        String minutes = context.getString(R.string.minutes);
        String et = context.getString(R.string.et);

        return createMinuteString(nombres, minute, minutes, et);
    }

    public static String createHourString(String[] nombres, String heure, String heures) {
        int heureInt = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        return firstUpper(nombres[heureInt]) + " " + (heureInt > 1 ? heures : heure) + "\n";
    }

    public static String createMinuteString(String[] nombres, String minute, String minutes, String et) {

        int minuteInt = Calendar.getInstance().get(Calendar.MINUTE);
        int unite;

        if(minuteInt > 0) {
            if(minuteInt < 21) {
                return nombres[minuteInt] + " " + (minuteInt == 1 ? minute : minutes);
            }
            else if(minuteInt < 30) {
                unite = minuteInt - 20;
                if(unite == 0) {
                    return nombres[20] + " " + minutes;
                }
                else if(unite == 1) {
                    return nombres[20] + " " + et + " " + nombres[unite] + " " + minutes;
                }
                else {
                    return nombres[20] + "-" + nombres[unite] + " " + minutes;
                }
            }
            else if(minuteInt < 40) {
                unite = minuteInt - 30;
                if(unite == 0) {
                    return nombres[21] + " " + minutes;
                }
                else if(unite == 1) {
                    return nombres[21] + " " + et + " " + nombres[unite] + " " + minutes;
                }
                else {
                    return nombres[21] + "-" + nombres[unite] + " " + minutes;
                }
            }
            else if(minuteInt < 50) {
                unite = minuteInt - 40;
                if(unite == 0) {
                    return nombres[22] + " " + minutes;
                }
                else if(unite == 1) {
                    return nombres[22] + " " + et + " " + nombres[unite] + " " + minutes;
                }
                else {
                    return nombres[22] + "-" + nombres[unite] + " " + minutes;
                }
            }
            else {
                unite = minuteInt - 50;
                if(unite == 0) {
                    return nombres[23] + " " + minutes;
                }
                else if(unite == 1) {
                    return nombres[23] + " " + et + " " + nombres[unite] + " " + minutes;
                }
                else {
                    return nombres[23] + "-" + nombres[unite] + " " + minutes;
                }
            }
        }
        else {
            return "";
        }
    }

    public static String firstUpper(String s) {
        return s.replaceFirst(".",(s.charAt(0)+"").toUpperCase());
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_clock, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.apropos:
                break;
            case R.id.settings:
                Intent settingsIntent = new Intent(this, Config.class);
                startActivity(settingsIntent);
                break;
        }
        return true;
    }

}
