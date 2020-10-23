package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_playlists extends AppCompatActivity {

    /////////////////////////////7BOTON IR A CANCIONES
    private Button ircancio;
    Button btncrear;
    ListView lv;
    EditText name;

    ArrayList<String> arrayList;
    CustomPlaylistAdapter adapter;
    //ArrayAdapter<String> madapter;

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
        //DESCOMENTAR PARA NO MOSTRAR PLAYLIST PRECARFADAS
        adapter = new CustomPlaylistAdapter(this, R.layout.playlist_item, arrayList);

        //ARRAYADAPTER VIDEO3 COUNTRIES
        /*adapter = new ArrayAdapter<String> (activity_playlists.this,
                android.R.layout.simple_list_item_1,
               getResources().getStringArray(R.array.canciones));*/
////////////////////////////////////////////////////////////////////////////////

        lv.setAdapter(adapter);
        //lv.setAdapter(madapter);


        //////////////////////////////////////////////////CLICK ON ITEM LISTVIEW
        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(activity_playlists.this, "" + adapter.getItem(i), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity_playlists.this, activity_select_playlist.class);
                //DESCOMENTAR MOSTRAR PLAYLISTS PRECARGADAS
                //intent.putExtra("canciones", lv.getItemAtPosition(i).toString());
                //
                startActivity(intent);
            }
        });*/
        //lv.setAdapter(madapter);
        onBtnClick();

        ////////////////////////////////////////////////////////////////////////////////////////////////7BOTON PLAYLIST
        ircancio = findViewById(R.id.cancones);
        ircancio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irCanciones();
            }
        });

    }

    public void irCancion(View view){
        Intent intent = new Intent(activity_playlists.this, activity_select_playlist.class);
        startActivity(intent);
    }
    private void irCanciones(){
        Intent intent = new Intent(activity_playlists.this, MainActivity.class);
        startActivity(intent);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////MIO LUIS BOTON PLAYLIST

    public void onBtnClick(){
        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = name.getText().toString();
                arrayList.add(result);
                adapter.notifyDataSetChanged();
            }
        });
        //adapter = new ArrayAdapter<String> (activity_playlists.this,
          //      android.R.layout.simple_list_item_2,
            //    getResources().getStringArray(R.array.canciones));
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