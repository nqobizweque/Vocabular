package nmmu.wrap302.Task01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import nmmu.wrap302.Task01.R;

import java.util.List;

/**
 * Created by BL3SS3D on 08 May 2017.
 */
public class TermAdapter extends ArrayAdapter<Term> {
    public TermAdapter(Context context, List<Term> terms) {
        super(context, R.layout.term_adapter_layout, terms);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = convertView = inflater.inflate(R.layout.term_adapter_layout, parent, false);
        convertView.setTag(getItem(position));
        final Term term = getItem(position);

        final TextView txtTerm = (TextView) convertView.findViewById(R.id.txtTerm),
        txtDefinition = (TextView) convertView.findViewById(R.id.txtDefinition),
        txtSynonymsLabel = (TextView) convertView.findViewById(R.id.txtSynonymLabel),
        txtSynonyms = (TextView) convertView.findViewById(R.id.txtSynonyms),
        txtAntonymLabel = (TextView) convertView.findViewById(R.id.txtAntonymLabel),
        txtAntonym = (TextView) convertView.findViewById(R.id.txtAntonyms);

        final ImageView ivPopMenu = (ImageView) convertView.findViewById(R.id.ivPopMenu);
        ivPopMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), ivPopMenu);

               popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.edit)
                            btnEdit(v, position);
                        else
                            btnDelete(v, term);
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        txtTerm.setText(term.term);
        txtDefinition.setText(term.definition);
        if(!term.synonyms.isEmpty()){
            txtSynonymsLabel.setVisibility(View.VISIBLE);
            txtSynonyms.setVisibility(View.VISIBLE);
            txtSynonyms.setText(term.synonyms);
        }

        if(!term.antonyms.isEmpty()){
            txtAntonymLabel.setVisibility(View.VISIBLE);
            txtAntonym.setVisibility(View.VISIBLE);
            txtAntonym.setText(term.antonyms);
        }

        return convertView;
    }

    public void btnEdit(final View view, final int position){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        final EditText term = new EditText(view.getContext()),
                definition = new EditText(view.getContext()),
                synonym = new EditText(view.getContext()),
                antonym = new EditText(view.getContext());

        term.setHint("Term");
        definition.setHint("Definition");
        synonym.setHint("Synonyms e.g: Term1, Term2");
        antonym.setHint("Antonyms e.g: Term1, Term2");

        definition.setScroller(new Scroller(view.getContext()));
        definition.setVerticalScrollBarEnabled(true);
        definition.setMaxLines(2);
        definition.setMinLines(2);
        definition.setGravity(Gravity.TOP);
        LinearLayout vlContent = new LinearLayout(view.getContext());
        vlContent.setOrientation(1);

        final Term curTerm = Browser.termAdapter.getItem(position);

        term.setText(curTerm.term);
        definition.setText(curTerm.definition);
        synonym.setText(curTerm.synonyms);
        antonym.setText(curTerm.antonyms);

        vlContent.addView(term);
        vlContent.addView(definition);
        vlContent.addView(synonym);
        vlContent.addView(antonym);

        alertDialog.setView(vlContent);
        alertDialog.setCancelable(true);
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Term newTerm = new Term(term.getText().toString(), definition.getText().toString(),
                        synonym.getText().toString(), antonym.getText().toString());
                if(MyActivity.db.updateRecord(curTerm, newTerm)){
                    Browser.readFile();
                    Toast.makeText(view.getContext(), curTerm.term.concat(" updated"), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(view.getContext(), "failed to update ".concat(curTerm.term), Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }

    public void btnDelete(final View view, final Term term){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        alertDialog.setTitle("Delete Term");
        final TextView prompt = new TextView(view.getContext());
        prompt.setText("Are you sure you want to delete " + term.term + "?");
        LinearLayout vlContent = new LinearLayout(view.getContext());
        vlContent.setOrientation(1);

        vlContent.addView(prompt);

        alertDialog.setView(vlContent);
        alertDialog.setCancelable(true);
        alertDialog.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try{
                    if(MyActivity.db.deleteRecord(term.term)){
                        Browser.termAdapter.remove(term);
                        Toast.makeText(view.getContext(), term.term.concat(" deleted"), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(view.getContext(), "failed to delete ".concat(term.term) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }
}