package angolodelleidee.catalogovirtuale.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import angolodelleidee.catalogovirtuale.Prodotto;
import angolodelleidee.catalogovirtuale.ProdottoImpl;


/**
 * Created by elia.dipasquale on 16/03/2017.
 */

/**
 * Per dettagli vedere: db-example.
 */
public class ExerciseDbManager {

    private ExerciseDbHelper dbHelper;


    public ExerciseDbManager(Context context) {
        dbHelper = new ExerciseDbHelper(context);
    }


    public boolean addProduct(ProdottoImpl product) {
            final List<String> productCodes = new LinkedList<>();

            for(ProdottoImpl p : getProductsInCart()){
                productCodes.add(p.getCodice());
            }
            if(productCodes.contains(product.getCodice())){
                for(ProdottoImpl p : getProductsInCart()){
                    if(p.getCodice().equals(product.getCodice())){
                        product.aumentaQuantita(p.getQuantita());
                    }
                }
                Log.d("Database",product.getCodice()+": "+ product.getQuantita());
                return updateProduct(product);
            }else {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                long row = db.insert(Prodotto.TABLE_NAME, null, product.getContentValues());
                return row > 0;
            }
    }

    public boolean updateProduct(ProdottoImpl product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.update(Prodotto.TABLE_NAME, product.getContentValues(),
                Prodotto.COLUMN_NAME + " = ? ", new String[]{product.getCodice()});
        return row > 0;
    }

    public boolean deleteProduct(ProdottoImpl product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete(Prodotto.TABLE_NAME,
                Prodotto.COLUMN_NAME + " = ? ", new String[]{product.getCodice()});
        return row > 0;
    }

    public List<ProdottoImpl> getProductsInCart() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<ProdottoImpl> productInCart = new ArrayList<>();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + Prodotto.TABLE_NAME +
                    " ORDER BY " + Prodotto.COLUMN_NAME + " ASC";
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                ProdottoImpl person = new ProdottoImpl(cursor);
                productInCart.add(person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return productInCart;

    }



}
