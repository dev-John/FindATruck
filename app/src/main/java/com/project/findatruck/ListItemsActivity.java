package com.project.findatruck;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.findatruck.database.DAO.LocalizacaoDAO;
import com.project.findatruck.models.Localizacoes;

import java.util.ArrayList;

public class ListItemsActivity extends AppCompatActivity {

    ListView list;
    String enderecosList[];
    ArrayList<Localizacoes> locList;
    LocalizacaoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        locList = new ArrayList<Localizacoes>();
        dao = new LocalizacaoDAO(getBaseContext());
        list = (ListView) findViewById(R.id.dynamic_list);
        locList = dao.getLocalizacoes();
        /*ArrayAdapter<Localizacoes> localizacoes = new ArrayAdapter<Localizacoes>(this,android.R.id.activity_list_items,locList);*/

    }
}
