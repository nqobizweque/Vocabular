package nmmu.wrap302.Task01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BL3SS3D on 08 May 2017.
 */
public class Browser extends Activity {
    protected static TermAdapter termAdapter;
    public List<Term> termList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_main);

        final ImageView btnFLoatAdd = (ImageView) findViewById(R.id.btnFloatAdd),
                ivSearchButton = (ImageView) findViewById(R.id.ivSearchButton),
                ivSearchClear = (ImageView) findViewById(R.id.ivSearchClear);
        final EditText etSearch = (EditText) findViewById(R.id.etSearch);

        ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDB(etSearch.getText().toString());
            }
        });

        ivSearchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.getText().clear();
                readFile();
            }
        });

        btnFLoatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked(v);
            }
        });

        termAdapter = new TermAdapter(this, termList);
        lvTerms = (ListView) findViewById(R.id.lvTerms);
        lvTerms.setAdapter(termAdapter);
        this.readFile();
    }

    static ListView lvTerms;

    public void searchDB(String searchTerm){
        Cursor resultSet = MyActivity.db.getSearch(searchTerm);
        try {
            if(resultSet.getCount() == 0){
                Toast.makeText(termAdapter.getContext(), "No terms found", Toast.LENGTH_SHORT).show();
            }else if(resultSet.moveToFirst()) {
                termAdapter.clear();
                do {
                    String term = resultSet.getString(resultSet.getColumnIndex(TermDB.COL_2)),
                            definition = resultSet.getString(resultSet.getColumnIndex(TermDB.COL_3)),
                            synonyms = resultSet.getString(resultSet.getColumnIndex(TermDB.COL_4)),
                            antonyms = resultSet.getString(resultSet.getColumnIndex(TermDB.COL_5));
                    termAdapter.add(new Term(term, definition, synonyms, antonyms));
                } while (resultSet.moveToNext());
                resultSet.close();
                lvTerms.refreshDrawableState();
            }else{
                Toast.makeText(termAdapter.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(termAdapter.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public static void readFile(){
        Cursor resultSet = MyActivity.db.getCursor();
        try {
            if(resultSet.moveToFirst()) {
                termAdapter.clear();
                do {
                    String term = resultSet.getString(resultSet.getColumnIndex(TermDB.COL_2)),
                            definition = resultSet.getString(resultSet.getColumnIndex(TermDB.COL_3)),
                            synonyms = resultSet.getString(resultSet.getColumnIndex(TermDB.COL_4)),
                            antonyms = resultSet.getString(resultSet.getColumnIndex(TermDB.COL_5));
                    termAdapter.add(new Term(term, definition, synonyms, antonyms));
                } while (resultSet.moveToNext());
                resultSet.close();
                lvTerms.refreshDrawableState();
            }else{
                Toast.makeText(termAdapter.getContext(), "No terms found", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(termAdapter.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

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
                    if(MyActivity.db.addTerm(newTerm)) {
                        Browser.termAdapter.add(newTerm);
                        Toast.makeText(Browser.this, newTerm.term + " Inserted", Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(Browser.this, newTerm.term + " not inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
        alertDialog.show();
    }
}