package com.arvoron.scherpel.arvoron;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener{
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        CardView treeCatalog= findViewById(R.id.cadastro);
        CardView treeList = findViewById(R.id.list);
        CardView treeMap = findViewById(R.id.map);
        CardView treeReport = findViewById(R.id.chat);
        treeCatalog.setOnClickListener(this);
        treeList.setOnClickListener(this);
        treeMap.setOnClickListener(this);
        treeReport.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cadastro: i = new Intent(this, Catalog.class); startActivity(i); break;
            case R.id.list: i = new Intent(this, Catalog.class); startActivity(i);break;
            case R.id.map: i = new Intent(this, Catalog.class); startActivity(i);break;
            case R.id.chat: i = new Intent(this, Catalog.class); startActivity(i);break;
            default:break;
        }



    }
}
