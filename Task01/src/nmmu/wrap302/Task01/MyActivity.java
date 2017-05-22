package nmmu.wrap302.Task01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = new TermDB(this);
        final ImageButton btnBrowse = (ImageButton)findViewById(R.id.btnBrowse),
<<<<<<< HEAD
                btnAdd = (ImageButton)findViewById(R.id.btnAdd), btnAlerts = (ImageButton) findViewById(R.id.btnAlerts);
=======
                btnAdd = (ImageButton)findViewById(R.id.btnAdd),
                btnAlerts = (ImageButton) findViewById(R.id.btnAlerts);
>>>>>>> origin/master

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked(v);
            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBrowseClicked(v);
            }
        });

        btnAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                onAlertsClicked(v);
            }
        });

=======
                onAlertClicked(v);
            }
        });

        if(db.getCursor().getCount() == 0){
            db.addTerm(new Term("Rentless", "unceasingly intense; harsh or inflexible", "persistent, unstoppable", "merciful, yielding"));
            db.addTerm(new Term("Irresolute", "showing or feeling hesitancy; uncertain", "indecisive, hesitant, tentative", ""));
            db.addTerm(new Term("Hedonist", "a person who believes that the pursuit of pleasure is the most important thing in life; a pleasure-seeker", "sybarite, sensualist, voluptuary, pleasure seeker", "puritan, ascetic"));
        }
>>>>>>> origin/master
    }

    public void onBrowseClicked(View view){
        Intent intent = new Intent(this, Browser.class);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public void onAlertClicked(View view){
        Intent intent = new Intent(this, Alerts.class);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public static TermDB db;

    public void onAddClicked(final View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final EditText term = new EditText(this),
                definition = new EditText(this),
                synonym = new EditText(this),
                antonym = new EditText(this);
        definition.setScroller(new Scroller(this));
        definition.setVerticalScrollBarEnabled(true);
        definition.setMaxLines(2);
        definition.setMinLines(2);
        definition.setGravity(Gravity.TOP);
        LinearLayout vlContent = new LinearLayout(this);
        vlContent.setOrientation(1);

        term.setHint("Term");
        definition.setHint("Definition");
        synonym.setHint("Synonyms e.g: Term1, Term2");
        antonym.setHint("Antonyms e.g: Term1, Term2");

        vlContent.addView(term);
        vlContent.addView(definition);
        vlContent.addView(synonym);
        vlContent.addView(antonym);

        alertDialog.setView(vlContent);
        alertDialog.setCancelable(true);
        alertDialog.setNeutralButton("Pull Definition", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setPositiveButton("Add Term", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(term != null){
                    Term newTerm = new Term(term.getText().toString(), definition.getText().toString(),
                            synonym.getText().toString(), antonym.getText().toString());
                    Browser.termAdapter.add(newTerm);
                    if(db.addTerm(newTerm))
                        Toast.makeText(MyActivity.this, newTerm.term + " Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MyActivity.this, newTerm.term + " not inserted", Toast.LENGTH_LONG).show();
                }
                onBrowseClicked(view);
            }
        });
        alertDialog.show();
    }

    public void onAlertsClicked(final View view) {

        final String PREFS_NAME = "MyAlertsPrefFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            SharedPreferences.Editor editor = settings.edit();

            editor.putBoolean("my_first_time", false);
            editor.putString("start_time", "09:00");
            editor.putString("end_time", "14:00");
            editor.putString("frequency", "5");
            editor.putString("days", "Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday");
            editor.apply();
        }

        Intent intent = new Intent(this, Alerts_Activity.class);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

}
