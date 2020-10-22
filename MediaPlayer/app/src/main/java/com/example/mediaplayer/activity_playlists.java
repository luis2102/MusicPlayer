package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_playlists extends AppCompatActivity {


    Button btncrear;
    ListView lv;
    EditText name;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        /*dialogCreatePlaylist = findViewById(R.id.crearplaylist);
        dialogCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity_playlists.this);
                builder.setTitle("Creación de Playlist");
                builder.setMessage("Ponle un nombre a tu playlist");
                builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });*/  //CODIGO POP UP DE MATERIAL

        name = (EditText) findViewById(R.id.editText);
        btncrear = (Button) findViewById(R.id.crearplaylist);

        //LISTVIEW VIDEO2
        lv = (ListView) findViewById(R.id.playlistsList);

        //ARRAY LIST
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(activity_playlists.this, android.R.layout.simple_list_item_1,
                arrayList);
        lv.setAdapter(adapter);

        //////////////////////////////////////////////////CLICK ON ITEM LISTVIEW
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(activity_playlists.this, "CLICK" + adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });
        onBtnClick();
    }
    public void onBtnClick(){
        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = name.getText().toString();
                arrayList.add(result);
                adapter.notifyDataSetChanged();
            }
        });
    }


///////////////////////////////////////////////////////////////////////////////////////7VIDEO DIALOG
        /*
        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
         nombre_playlist exampleDialog = new nombre_playlist();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyname(String username) {
            name.setText(username);
    }
    */
//////////////////////////////////////////////////////////////////////////////////////////////VIDEO1 DIALOG

}