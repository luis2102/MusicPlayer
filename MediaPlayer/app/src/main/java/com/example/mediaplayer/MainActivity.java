package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.media.MediaMetadataRetriever;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;

    MediaMetadataRetriever mediaMetadataRetriever, mediaMetadataRetriever2;

    String filename = "cancion1";

    private ArrayList<Canciones> arrayList;
    private CustomMusicAdapter adapter;
    private ListView songList;
    String[] Lista = new String[5];
    private ArrayList<String> can;
    int contador = 0;
    TextView cancionActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*InputStream inStream = getResources().openRawResource(R.raw.in_your_eyes);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[10240];
        int i = Integer.MAX_VALUE;
        while (true) {
            try {
                if (!((i = inStream.read(buff, 0, buff.length)) > 0)) break;
            } catch (IOException e) { e.printStackTrace(); }
            baos.write(buff, 0, i);
        }
        FileOutputStream out = null;
        try {
            out = openFileOutput(filename, Context.MODE_APPEND);
            out.write(baos.toByteArray());
        } catch (FileNotFoundException e) { e.printStackTrace();
        } catch (IOException e) { e.printStackTrace(); }
        if (out != null) {
            try { out.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }*/


        songList = (ListView) findViewById(R.id.songList);
        arrayList = new ArrayList<>();

        mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/black");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE), mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)));
            cancionActual = (TextView) findViewById(R.id.cancion);
            cancionActual.setText(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) + " - " + mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion2");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE), mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion3");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE), mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion4");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE), mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion5");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE), mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        /*arrayList.add(new Canciones("aaaaaaaa", "aaaaaaaaaaaaaaa"));
        Cursor cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null
        );
        if(cursor != null && cursor.moveToFirst()) {
            int songTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            do {
                String cTitle = cursor.getString(songTitle);
                String cArtist = cursor.getString(songArtist);
                arrayList.add(new Canciones(cTitle, cArtist));
            } while (cursor.moveToNext());
        }*/

        adapter = new CustomMusicAdapter(this, R.layout.music_item, arrayList);
        songList.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        //getMenuInflater().inflate(R.menu.my_menu, menu);

        if (menu.findItem(R.id.ordenar_canciones) == findViewById(R.id.ordenar_canciones)) {
            inflater.inflate(R.menu.my_menu, menu);
            String ordenar_canciones = (String) menu.findItem(R.id.ordenar_canciones).getTitle();
            Log.i("aaaaa", ordenar_canciones);
            return super.onCreateOptionsMenu(menu);
        } else if(menu.findItem(R.id.ordenar_artista) == findViewById(R.id.ordenar_artista)) {
            inflater.inflate(R.menu.my_menu, menu);
            String ordenar_artistas = (String) menu.findItem(R.id.ordenar_artista).getTitle();
            Log.i("aaaaa", ordenar_artistas);
            return super.onCreateOptionsMenu(menu);
        } else {
            MenuItem menuItem = menu.findItem(R.id.search_icon);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("Buscar cancion");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    arrayAdapter.getFilter().filter(s);
                    return true;
                }
            });

            return super.onCreateOptionsMenu(menu);
        }
    }

    public void reproducir(View view) {
        //mediaPlayer.start();
        Toast.makeText(getApplicationContext(), "Cancion seleccionada", Toast.LENGTH_SHORT).show();
    }

}
