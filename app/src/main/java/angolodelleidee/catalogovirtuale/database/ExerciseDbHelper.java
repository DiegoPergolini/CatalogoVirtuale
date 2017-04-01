package angolodelleidee.catalogovirtuale.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import angolodelleidee.catalogovirtuale.Prodotto;


/**
 * Per dettagli vedere: db-example.
 */
public class ExerciseDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "adi.db";
    private static final int DATABASE_VERSION = 1;


    public ExerciseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static final String CREATE_TABLE_PEOPLE = "CREATE TABLE "
            + Prodotto.TABLE_NAME + " (" +
            Prodotto.COLUMN_NAME + " TEXT PRIMARY KEY, " +
            Prodotto.COLUMN_QUANTITY + " INTEGER )";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PEOPLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
