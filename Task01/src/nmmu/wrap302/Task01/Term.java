package nmmu.wrap302.Task01;

import android.provider.ContactsContract;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BL3SS3D on 18 Apr 2017.
 */
public class Term {
    protected String term;
    private Date added, lastModified;
    private int views;
    protected String definition, synonyms, antonyms;

    public Term(String term, String definition, String synonyms, String antonyms) {
        this.term = term;
        this.definition = definition;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }
    public Term(int termID, String term) {
        this.term = term;
        this.definition = "";
    }

    public void update(Term other){
        this.term = other.term;
        this.definition = other.definition;
        this.synonyms = other.synonyms;
        this.antonyms = other.antonyms;
    }

    public boolean equals(Term other){
        if(this.term != other.term)
            return false;
        if(this.definition != other.definition)
            return false;
        if(this.synonyms != other.synonyms)
            return false;
        if(this.antonyms != other.antonyms)
            return false;
        return true;
    }

    public String synoString(){
        return (synonyms.toString().replace('[', ' ')).replace(']', ' ').trim();
    }

    public String antoString(){
        return (antonyms.toString().replace('[', ' ')).replace(']', ' ').trim();
    }
}
