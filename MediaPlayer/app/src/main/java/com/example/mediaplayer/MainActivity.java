package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.media.MediaMetadataRetriever;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;

    MediaMetadataRetriever mediaMetadataRetriever, mediaMetadataRetriever2;

    String filename = "cancion1";

    private ArrayList<Canciones> arrayList, listaCanciones;
    private CustomMusicAdapter adapter;
    private ListView songList;
    String[] Lista = new String[5];
    private ArrayList<MediaPlayer> can;
    int contador = 0;
    TextView cancionActual, artistaActual;

    MediaPlayer mediaPlayer;
    Canciones canciones;
    int position = 0, position2;
    private ImageView reanudar;
    private int pActual;
    private final static int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent();
        //position = intent.getIntExtra("index", -1);
        //pActual = intent.getIntExtra("pos", -1);
        mediaPlayer = MediaPlayer.create(this, R.raw.in_your_eyes);

        /*
        InputStream inStream = getResources().openRawResource(R.raw.in_your_eyes);
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
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion1");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM), 2131689474,
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION), mediaMetadataRetriever.getEmbeddedPicture()));
            cancionActual = (TextView) findViewById(R.id.cancion);
            artistaActual = (TextView) findViewById(R.id.cancion_artista);
            cancionActual.setText(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) + " - " );
            artistaActual.setText(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion2");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM), 2131689476,
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION), mediaMetadataRetriever.getEmbeddedPicture()));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion3");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM), 2131689472,
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION), mediaMetadataRetriever.getEmbeddedPicture()));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion4");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM), 2131689475,
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION), mediaMetadataRetriever.getEmbeddedPicture()));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion5");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM), 2131689473,
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION),
                    mediaMetadataRetriever.getEmbeddedPicture()));
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

        listaCanciones = arrayList;
        adapter = new CustomMusicAdapter(this, R.layout.music_item, arrayList);
        songList.setAdapter(adapter);

        /*Log.i("aaaa", String.valueOf(R.raw.black_eyed_peas_vida_loca));
        Log.i("aaaa", String.valueOf(R.raw.daft_punk_one_more_time));
        Log.i("aaaa", String.valueOf(R.raw.in_your_eyes));
        Log.i("aaaa", String.valueOf(R.raw.savage_love));
        Log.i("aaaa", String.valueOf(R.raw.welcome));*/
        //mediaPlayer = MediaPlayer.create(this, 2131689472);
       // mediaPlayer.start();

        reanudar = (ImageView) findViewById(R.id.play_pause);
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

        /*mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        //getMenuInflater().inflate(R.menu.my_menu, menu);

        if (menu.findItem(R.id.ordenar_canciones) == findViewById(R.id.ordenar_canciones)) {
            inflater.inflate(R.menu.my_menu, menu);
            String ordenar_canciones = (String) menu.findItem(R.id.ordenar_canciones).getTitle();
            //Log.i("aaaaa", ordenar_canciones);
            return super.onCreateOptionsMenu(menu);
        } else if(menu.findItem(R.id.ordenar_artista) == findViewById(R.id.ordenar_artista)) {
            inflater.inflate(R.menu.my_menu, menu);
            String ordenar_artistas = (String) menu.findItem(R.id.ordenar_artista).getTitle();
            //Log.i("aaaaa", ordenar_artistas);
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
        arrayList = listaCanciones;
        position = songList.getPositionForView((LinearLayout)view.getParent());
        if (mediaPlayer != null) mediaPlayer.release();

        if (position >= 0) {
            canciones = arrayList.get(position);
            mediaPlayer = new MediaPlayer();
            Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
            try {
                mediaPlayer.setDataSource(MainActivity.this, u);
                //mediaPlayer.prepareAsync();
                mediaPlayer.prepare();
                mediaPlayer.start();
                reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                cancionActual.setText(canciones.getTitulo() + " - ");
                artistaActual.setText(canciones.getArtista());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void siguiente(View view) {
        position2 = position + 1;
        if (position2 >= arrayList.size()) {
            Toast.makeText(this, "Última canción", Toast.LENGTH_SHORT).show();
        } else {
            if (mediaPlayer != null) mediaPlayer.release();
            canciones = arrayList.get(position2);
            mediaPlayer = new MediaPlayer();
            Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
            try {
                mediaPlayer.setDataSource(MainActivity.this, u);
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

    public void reproduciendo(View view) {
        Intent intent = new Intent(this, ReproduciendoCancion.class);
        //Canciones cancion = arrayList.get(position);
        int posicion = mediaPlayer.getCurrentPosition();
        int index = position;
        intent.putExtra("canciones", arrayList);
        intent.putExtra("pos", posicion);
        intent.putExtra("index", index);
        mediaPlayer.release();
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if((requestCode == REQUEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            position = intent.getIntExtra("index", 0);
            pActual = intent.getIntExtra("pos", 0);
            arrayList = (ArrayList<Canciones>) intent.getSerializableExtra("canciones");
            if (mediaPlayer != null) mediaPlayer.release();
            if (position >= 0) {
                canciones = arrayList.get(position);
                mediaPlayer = new MediaPlayer();
                Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
                try {
                    mediaPlayer.setDataSource(MainActivity.this, u);
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
}
