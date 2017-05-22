package nmmu.wrap302.Task01;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by s214108503 on 2017/05/21.
 */
public class Alerts_Activity extends Activity {

    private boolean Sunday = false, Monday = false, Tuesday = false, Wednesday = false,
            Thursday = false, Friday = false, Saturday = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_alerts_layout);

        final EditText edit_alerts_start_time = (EditText) findViewById(R.id.edit_alerts_start_time);
        final EditText edit_alerts_end_time = (EditText) findViewById(R.id.edit_alerts_end_time);
        final EditText edit_alerts_frequency = (EditText) findViewById(R.id.edit_alerts_frequency);

        Button button_alerts_sunday = (Button) findViewById(R.id.button_alerts_sunday);
        Button button_alerts_monday = (Button) findViewById(R.id.button_alerts_monday);
        Button button_alerts_tuesday = (Button) findViewById(R.id.button_alerts_tuesday);
        Button button_alerts_wednesday = (Button) findViewById(R.id.button_alerts_wednesday);
        Button button_alerts_thursday = (Button) findViewById(R.id.button_alerts_thursday);
        Button button_alerts_friday = (Button) findViewById(R.id.button_alerts_friday);
        Button button_alerts_saturday = (Button) findViewById(R.id.button_alerts_saturday);

        Button button_alerts_save = (Button) findViewById(R.id.button_alerts_save);
        Button button_alerts_cancel = (Button) findViewById(R.id.button_alerts_cancel);

        button_alerts_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_alerts_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertsSaveOnClickMethod(edit_alerts_start_time, edit_alerts_frequency, edit_alerts_end_time);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 20);

                Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);

                PendingIntent pendingIntent = PendingIntent.
                        getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }});


        setOnClickListenersOnDateButtons(button_alerts_sunday, button_alerts_monday,
                button_alerts_tuesday, button_alerts_wednesday, button_alerts_thursday,
                button_alerts_friday, button_alerts_saturday);

        loadFromPrefsAndInitialise(edit_alerts_start_time, edit_alerts_end_time,
                edit_alerts_frequency, button_alerts_sunday, button_alerts_monday,
                button_alerts_tuesday, button_alerts_wednesday, button_alerts_thursday,
                button_alerts_friday, button_alerts_saturday);
    }

    private void loadFromPrefsAndInitialise(EditText edit_alerts_start_time, EditText edit_alerts_end_time,
                                            EditText edit_alerts_frequency, Button button_alerts_sunday,
                                            Button button_alerts_monday, Button button_alerts_tuesday,
                                            Button button_alerts_wednesday, Button button_alerts_thursday,
                                            Button button_alerts_friday, Button button_alerts_saturday) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String  start_time = sharedPref.getString("start_time", "00:00"),
                end_time = sharedPref.getString("end_time", "00:01"),
                frequency = sharedPref.getString("frequency", "0"),
                days = sharedPref.getString("days", "default");

        edit_alerts_start_time.setText(start_time);
        edit_alerts_end_time.setText(end_time);
        edit_alerts_frequency.setText(frequency);
        String[] sDays = days.split(",");
        for(String s: sDays) {
            switch (s) {
                case "Sunday":
                    Sunday = true;
                    button_alerts_sunday.setBackgroundColor(Color.BLUE);
                    break;
                case "Monday":
                    Monday = true;
                    button_alerts_monday.setBackgroundColor(Color.BLUE);
                    break;
                case "Tuesday":
                    Tuesday = true;
                    button_alerts_tuesday.setBackgroundColor(Color.BLUE);
                    break;
                case "Wednesday":
                    Wednesday = true;
                    button_alerts_wednesday.setBackgroundColor(Color.BLUE);
                    break;
                case "Thursday":
                    Thursday = true;
                    button_alerts_thursday.setBackgroundColor(Color.BLUE);
                    break;
                case "Friday":
                    Friday = true;
                    button_alerts_friday.setBackgroundColor(Color.BLUE);
                    break;
                case "Saturday":
                    Saturday = true;
                    button_alerts_saturday.setBackgroundColor(Color.BLUE);
                    break;
            }
        }
    }

    private void alertsSaveOnClickMethod(EditText edit_alerts_start_time, EditText edit_alerts_frequency,
                                         EditText edit_alerts_end_time) {
        String StartTime = edit_alerts_start_time.getText().toString(),
                Frequency =edit_alerts_frequency.getText().toString(),
                EndTime = edit_alerts_end_time.getText().toString(),
                Days = "";

        if (Sunday) Days = Days.concat("Sunday,");
        else if (Monday) Days = Days.concat("Monday,");
        else if (Tuesday) Days = Days.concat("Tuesday,");
        else if (Wednesday) Days =Days.concat("Wednesday,");
        else if (Thursday) Days = Days.concat("Thursday,");
        else if (Friday) Days = Days.concat("Friday,");
        else if (Saturday) Days = Days.concat("Saturday");

        final String PREFS_NAME = "MyAlertsPrefFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        SharedPreferences.Editor editor = settings.edit();

        editor.putString("start_time", StartTime);
        editor.putString("end_time", EndTime);
        editor.putString("frequency", Frequency);
        editor.putString("days", Days);
        editor.apply();

        Toast.makeText(Alerts_Activity.this, "Alerts Updated ", Toast.LENGTH_SHORT).show();

        finish();

    }

    private void setOnClickListenersOnDateButtons(Button button_alerts_sunday, Button button_alerts_monday,
                                                  Button button_alerts_tuesday, Button button_alerts_wednesday,
                                                  Button button_alerts_thursday, Button button_alerts_friday,
                                                  Button button_alerts_saturday) {
        button_alerts_sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Sunday) {
                    Sunday = true;
                    v.setBackgroundColor(Color.GRAY);
                } else {
                    Sunday = false;
                    v.setBackgroundColor(Color.WHITE);
                }
            }
        });

        button_alerts_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Monday) {
                    Monday = true;
                    v.setBackgroundColor(Color.GRAY);
                } else {
                    Monday = false;
                    v.setBackgroundColor(Color.WHITE);
                }
            }
        });

        button_alerts_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tuesday) {
                    Tuesday = true;
                    v.setBackgroundColor(Color.GRAY);
                } else {
                    Tuesday = false;
                    v.setBackgroundColor(Color.WHITE);
                }
            }
        });

        button_alerts_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Wednesday) {
                    Wednesday = true;
                    v.setBackgroundColor(Color.GRAY);
                } else {
                    Wednesday = false;
                    v.setBackgroundColor(Color.WHITE);
                }
            }
        });

        button_alerts_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Thursday) {
                    Thursday = true;
                    v.setBackgroundColor(Color.GRAY);
                } else {
                    Thursday = false;
                    v.setBackgroundColor(Color.WHITE);
                }
            }
        });

        button_alerts_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Friday) {
                    Friday = true;
                    v.setBackgroundColor(Color.GRAY);
                } else {
                    Friday = false;
                    v.setBackgroundColor(Color.WHITE);
                }
            }
        });


        button_alerts_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Saturday) {
                    Saturday = true;
                    v.setBackgroundColor(Color.GRAY);
                } else {
                    Saturday = false;
                    v.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }
}