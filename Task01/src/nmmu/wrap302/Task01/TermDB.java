package nmmu.wrap302.Task01;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by BL3SS3D on 13 May 2017.
 */
public class TermDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "Terms.db",
            TABLE_NAME = "TermTable", COL_2 = "Term",
            COL_3 = "Description", COL_4 = "Synonyms", COL_5 = "Antonyms";
    public static final int DB_VERSION = 1;

    private static final String DB_CREATE = "create table " + TABLE_NAME + "(" + COL_2 + " text primary key, "
            + COL_3 + " text, "
            + COL_4 + " text, "
            + COL_5 + " text);";
    SQLiteDatabase db;
    public TermDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
//        this.addTerm(new Term("Rentless", "unceasingly intense; harsh or inflexible", "persistent, unstoppable", "merciful, yielding"));
//        this.addTerm(new Term("Irresolute", "showing or feeling hesitancy; uncertain", "indecisive, hesitant, tentative", ""));
//        this.addTerm(new Term("Hedonist", "a person who believes that the pursuit of pleasure is the most important thing in life; a pleasure-seeker", "sybarite, sensualist, voluptuary, pleasure seeker", "puritan, ascetic"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean addTerm(Term newOne){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, newOne.term);
        contentValues.put(COL_3, newOne.definition);
        contentValues.put(COL_4, newOne.synonyms);
        contentValues.put(COL_5, newOne.antonyms);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        return true;
    }

    public Cursor getCursor(){
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }

    public boolean updateRecord(Term oldTerm, Term upTerm){
        if(oldTerm.equals(upTerm))
            return false;
        ContentValues content = new ContentValues();
        if(oldTerm.term == upTerm.term){
            if(deleteRecord(oldTerm.term))
                return addTerm(upTerm);
        }else{
            content.put(COL_3, upTerm.definition);
            content.put(COL_4, upTerm.synonyms);
            content.put(COL_5, upTerm.antonyms);
            int result = db.update(TABLE_NAME, content, COL_2 + " = ?", new String[]{oldTerm.term});
            if (result == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean deleteRecord(String term){
        int result = db.delete(TABLE_NAME,  COL_2 + " = ?", new String[]{term});
        if(result == -1)
            return false;
        return true;
    }
}
