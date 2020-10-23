package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class activity_playlists extends AppCompatActivity {

    /////////////////////////////7BOTON IR A CANCIONES
    private Button ircancio;
    Button btncrear;
    ListView lv;
    EditText name;

    ArrayList<String> arrayList;
    CustomPlaylistAdapter adapter;
    private int posicion;
    private ArrayList<Canciones> arrayList2;
    private int index, index2;
    private Canciones canciones;
    private MediaPlayer mediaPlayer;
    //ArrayAdapter<String> madapter;
    int position = 0, position2;
    private final static int REQUEST_CODE3 = 3000;
    private final static int REQUEST_CODE4 = 4000;
    private int pActual;
    TextView cancionActual, artistaActual;
    private ImageView reanudar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        Intent intent = getIntent();
        arrayList2 = (ArrayList<Canciones>) intent.getSerializableExtra("canciones");
        posicion = intent.getIntExtra("pos", 0);
        index = intent.getIntExtra("index", 0);

        canciones = arrayList2.get(index);
        mediaPlayer = new MediaPlayer();
        Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
        try {
            mediaPlayer.setDataSource(activity_playlists.this, u);
            mediaPlayer.prepare();
            mediaPlayer.seekTo(posicion);
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        cancionActual = (TextView) findViewById(R.id.cancion);
        artistaActual = (TextView) findViewById(R.id.cancion_artista);
        cancionActual.setText(arrayList2.get(index).getTitulo() + " - ");
        artistaActual.setText(arrayList2.get(index).getArtista());

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

        reanudar = (ImageView) findViewById(R.id.play_pause);
        reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
        reanudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    reanudar.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                } else{
                    mediaPlayer.start();
                    reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                }
            }
        });

    }

    public void irPlaylist(View view) {
        Intent intent = new Intent(this, activity_select_playlist.class);
        int posicion = mediaPlayer.getCurrentPosition();
        intent.putExtra("canciones", arrayList2);
        intent.putExtra("pos", posicion);
        intent.putExtra("index", index);
        intent.putExtra("nombre", arrayList.get(lv.getPositionForView((LinearLayout)view.getParent())));
        mediaPlayer.release();
        startActivityForResult(intent, REQUEST_CODE4);
    }

    private void irCanciones(){
        Intent intent = new Intent();
        int posicion = mediaPlayer.getCurrentPosition();
        int position = index;
        intent.putExtra("canciones", arrayList2);
        intent.putExtra("pos", posicion);
        intent.putExtra("index", position);
        setResult(Activity.RESULT_OK, intent);
        mediaPlayer.release();
        finish();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////MIO LUIS BOTON PLAYLIST

    public void onBtnClick(){
        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ponle un nombre a tu Playlist", Toast.LENGTH_SHORT).show();
                } else {
                    arrayList.add(name.getText().toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void reproduciendo(View view) {
        Intent intent = new Intent(this, ReproduciendoCancion.class);
        //Canciones cancion = arrayList.get(position);
        int posicion = mediaPlayer.getCurrentPosition();
        int index = position;
        intent.putExtra("canciones", arrayList2);
        intent.putExtra("pos", posicion);
        intent.putExtra("index", index);
        mediaPlayer.release();
        startActivityForResult(intent, REQUEST_CODE3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if((requestCode == REQUEST_CODE3) && (resultCode == Activity.RESULT_OK)) {
            position = intent.getIntExtra("index", 0);
            pActual = intent.getIntExtra("pos", 0);
            arrayList2 = (ArrayList<Canciones>) intent.getSerializableExtra("canciones");
            if (mediaPlayer != null) mediaPlayer.release();
            if (position >= 0) {
                canciones = arrayList2.get(position);
                mediaPlayer = new MediaPlayer();
                Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
                try {
                    mediaPlayer.setDataSource(activity_playlists.this, u);
                    //mediaPlayer.prepareAsync();
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(pActual);
                    mediaPlayer.start();
                    reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                    cancionActual.setText(canciones.getTitulo() + " - ");
                    artistaActual.setText(canciones.getArtista());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if((requestCode == REQUEST_CODE4) && (resultCode == Activity.RESULT_OK)) {
            position = intent.getIntExtra("index", 0);
            pActual = intent.getIntExtra("pos", 0);
            arrayList2 = (ArrayList<Canciones>) intent.getSerializableExtra("canciones");
            if (mediaPlayer != null) mediaPlayer.release();
            if (position >= 0) {
                canciones = arrayList2.get(position);
                mediaPlayer = new MediaPlayer();
                Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
                try {
                    mediaPlayer.setDataSource(activity_playlists.this, u);
                    //mediaPlayer.prepareAsync();
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(pActual);
                    mediaPlayer.start();
                    reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                    cancionActual.setText(canciones.getTitulo() + " - ");
                    artistaActual.setText(canciones.getArtista());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void siguiente(View view) {
        position2 = position + 1;
        if (position2 >= arrayList.size()) {
            Toast.makeText(this, "Última canción", Toast.LENGTH_SHORT).show();
        } else {
            if (mediaPlayer != null) mediaPlayer.release();
            canciones = arrayList2.get(position2);
            mediaPlayer = new MediaPlayer();
            Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
            try {
                mediaPlayer.setDataSource(activity_playlists.this, u);
                mediaPlayer.prepare();
                mediaPlayer.start();
                reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                cancionActual.setText(canciones.getTitulo() + " - ");
                artistaActual.setText(canciones.getArtista());
                position = position2;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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