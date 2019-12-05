package com.project.findatruck.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.project.findatruck.database.DbGateway;
import com.project.findatruck.models.Localizacoes;

import java.util.ArrayList;

public class LocalizacaoDAO {

    private final String TABLE_LOCALIZACOES = "localizacoes";
    private DbGateway gw;

    public LocalizacaoDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(String latitude, String longitude, String usuario_cadastrante, String endereco){
        ContentValues cv = new ContentValues();
        cv.put("latitude", latitude);
        cv.put("longitude", longitude);
        cv.put("usuario_cadastrante", usuario_cadastrante);
        cv.put("endereco", endereco);
        return gw.getDatabase().insert(TABLE_LOCALIZACOES, null, cv) > 0;
    }

    public ArrayList<Localizacoes> getLocalizacoes(){
        ArrayList<Localizacoes> locList = new ArrayList<Localizacoes>();
        String query = "SELECT * FROM LOCALIZACOES";
        Cursor cursor = gw.getDatabase().rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Localizacoes loc = new Localizacoes();
                loc.setId(cursor.getInt(0));
                loc.setLatitude(cursor.getString(1));
                loc.setLongitude(cursor.getString(2));
                loc.setUsuario_cadastrante(cursor.getString(3));
                loc.setEndereco(cursor.getString(4));

                locList.add(loc);
            }while (cursor.moveToNext());
        }

        return locList;
    }

    public void dropTable() {
        gw.getDatabase().execSQL("DROP TABLE IF EXISTS LOCALIZACOES");
    }

}
