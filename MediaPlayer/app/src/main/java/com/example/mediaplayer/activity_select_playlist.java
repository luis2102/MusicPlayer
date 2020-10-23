package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.os.Bundle;

public class activity_select_playlist extends AppCompatActivity {

    private Button playli; //////////////BOTON PLAYLIST
    private int MENU1 = 0;
    private int MENU_1_ITEM = Menu.FIRST;

    ArrayAdapter<String> arrayAdapter;
    PopupMenu popupMenu;

    MediaMetadataRetriever mediaMetadataRetriever, mediaMetadataRetriever2;

    //String filename = "cancion1";
    private ArrayList<Canciones> arrayList, listaCanciones, arrayList2;
    private CustomMusicAdapter adapter;
    private ListView songList;
    String[] Lista = new String[5];
    private ArrayList<MediaPlayer> can;
    int contador = 0;
    int posicion, index, index2;
    TextView cancionActual, artistaActual;

    MediaPlayer mediaPlayer;
    Canciones canciones;
    int position = 0, position2;
    private ImageView reanudar, imagen1, imagen2, imagen3, imagen4;
    private int pActual;
    private String nombre;
    private final static int REQUEST_CODE5 = 4000;
    private Bitmap bitmap1, bitmap2, bitmap3, bitmap4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_playlist);

        mediaPlayer = MediaPlayer.create(this, R.raw.in_your_eyes);

        Intent intent = getIntent();
        arrayList2 = (ArrayList<Canciones>) intent.getSerializableExtra("canciones");
        posicion = intent.getIntExtra("pos", 0);
        index = intent.getIntExtra("index", 0);
        nombre = intent.getStringExtra("nombre");
        setTitle(nombre);

        canciones = arrayList2.get(index);
        mediaPlayer = new MediaPlayer();
        Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
        try {
            mediaPlayer.setDataSource(activity_select_playlist.this, u);
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


        songList = (ListView) findViewById(R.id.songList);
        arrayList = new ArrayList<>();

        /*mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion1");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM), 2131689474,
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION), mediaMetadataRetriever.getEmbeddedPicture()));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion2");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM), 2131689476,
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION), mediaMetadataRetriever.getEmbeddedPicture()));
            mediaMetadataRetriever.setDataSource("/data/data/com.example.mediaplayer/files/cancion3");
            arrayList.add(new Canciones(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM), 2131689472,
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION), mediaMetadataRetriever.getEmbeddedPicture()));
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }*/

        listaCanciones = arrayList;
        adapter = new CustomMusicAdapter(this, R.layout.music_item, listaCanciones);
        songList.setAdapter(adapter);

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

        ////////////////////////////////////////////////////////////////////////////////////////////////7BOTON PLAYLIST
        playli = findViewById(R.id.button);
        playli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPopupMenu_onClick(view);
            }
        });

        imagen1 = (ImageView) findViewById(R.id.imagen1);
        imagen2 = (ImageView) findViewById(R.id.imagen2);
        imagen3 = (ImageView) findViewById(R.id.imagen3);
        imagen4 = (ImageView) findViewById(R.id.imagen4);

        if (listaCanciones.size() == 1) {
            bitmap1 = BitmapFactory.decodeByteArray(listaCanciones.get(0).getImagen(), 0, listaCanciones.get(0).getImagen().length);
            imagen1.setImageBitmap(bitmap1);
        } else if (listaCanciones.size() == 2) {
            bitmap1 = BitmapFactory.decodeByteArray(listaCanciones.get(0).getImagen(), 0, listaCanciones.get(0).getImagen().length);
            imagen1.setImageBitmap(bitmap1);
            bitmap2 = BitmapFactory.decodeByteArray(listaCanciones.get(1).getImagen(), 0, listaCanciones.get(1).getImagen().length);
            imagen2.setImageBitmap(bitmap2);
        } else if (listaCanciones.size() == 3) {
            bitmap1 = BitmapFactory.decodeByteArray(listaCanciones.get(0).getImagen(), 0, listaCanciones.get(0).getImagen().length);
            imagen1.setImageBitmap(bitmap1);
            bitmap2 = BitmapFactory.decodeByteArray(listaCanciones.get(1).getImagen(), 0, listaCanciones.get(1).getImagen().length);
            imagen2.setImageBitmap(bitmap2);
            bitmap3 = BitmapFactory.decodeByteArray(listaCanciones.get(2).getImagen(), 0, listaCanciones.get(2).getImagen().length);
            imagen3.setImageBitmap(bitmap3);
        } else if (listaCanciones.size() >= 4) {
            bitmap1 = BitmapFactory.decodeByteArray(listaCanciones.get(0).getImagen(), 0, listaCanciones.get(0).getImagen().length);
            imagen1.setImageBitmap(bitmap1);
            bitmap2 = BitmapFactory.decodeByteArray(listaCanciones.get(1).getImagen(), 0, listaCanciones.get(1).getImagen().length);
            imagen2.setImageBitmap(bitmap2);
            bitmap3 = BitmapFactory.decodeByteArray(listaCanciones.get(2).getImagen(), 0, listaCanciones.get(2).getImagen().length);
            imagen3.setImageBitmap(bitmap3);
            bitmap4 = BitmapFactory.decodeByteArray(listaCanciones.get(3).getImagen(), 0, listaCanciones.get(3).getImagen().length);
            imagen4.setImageBitmap(bitmap4);
        }
    }

    private void buttonPopupMenu_onClick(View view) {
        if (popupMenu == null) {
            popupMenu = new PopupMenu(this, playli);
            for (Canciones cancion : arrayList2) {
                popupMenu.getMenu().add(MENU1, MENU_1_ITEM, MENU_1_ITEM, cancion.getTitulo() + " - " + cancion.getArtista());
                MENU_1_ITEM++;
            }
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Toast.makeText(getApplicationContext(), String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
                listaCanciones.add(arrayList2.get(item.getItemId() - 1));
                if (listaCanciones.size() == 1) {
                    bitmap1 = BitmapFactory.decodeByteArray(listaCanciones.get(0).getImagen(), 0, listaCanciones.get(0).getImagen().length);
                    imagen1.setImageBitmap(bitmap1);
                } else if (listaCanciones.size() == 2) {
                    bitmap2 = BitmapFactory.decodeByteArray(listaCanciones.get(1).getImagen(), 0, listaCanciones.get(1).getImagen().length);
                    imagen2.setImageBitmap(bitmap2);
                } else if (listaCanciones.size() == 3) {
                    bitmap3 = BitmapFactory.decodeByteArray(listaCanciones.get(2).getImagen(), 0, listaCanciones.get(2).getImagen().length);
                    imagen3.setImageBitmap(bitmap3);
                } else if (listaCanciones.size() >= 4) {
                    bitmap4 = BitmapFactory.decodeByteArray(listaCanciones.get(3).getImagen(), 0, listaCanciones.get(3).getImagen().length);
                    imagen4.setImageBitmap(bitmap4);
                }
                    adapter.notifyDataSetChanged();
                return false;
            }
        });
        popupMenu.show();
    }

    public void irPlaylists(View view){
        Intent intent = new Intent();
        int posicion = mediaPlayer.getCurrentPosition();
        intent.putExtra("canciones", arrayList);
        intent.putExtra("pos", posicion);
        intent.putExtra("index", index);
        setResult(Activity.RESULT_OK, intent);
        mediaPlayer.release();
        finish();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////MIO LUIS BOTON PLAYLIST


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Buscar");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.ordenar_canciones) {
            Collections.sort(listaCanciones, new Comparator<Canciones>() {
                @Override
                public int compare(Canciones canciones, Canciones t1) {
                    return canciones.getTitulo().compareTo(t1.getTitulo());
                }
            });
            adapter.cancionesOrdenadas(listaCanciones);
        } else if(item.getItemId() == R.id.ordenar_artista) {
            Collections.sort(listaCanciones, new Comparator<Canciones>() {
                @Override
                public int compare(Canciones canciones, Canciones t1) {
                    return canciones.getArtista().compareTo(t1.getArtista());
                }
            });
            adapter.ArtistasOrdenadas(listaCanciones);
        } else if(item.getItemId() == R.id.ordenar_normal) {
            adapter.OrdenarNormal();
        }

        return super.onOptionsItemSelected(item);
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
                mediaPlayer.setDataSource(activity_select_playlist.this, u);
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
                mediaPlayer.setDataSource(activity_select_playlist.this, u);
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
        startActivityForResult(intent, REQUEST_CODE5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if((requestCode == REQUEST_CODE5) && (resultCode == Activity.RESULT_OK)) {
            position = intent.getIntExtra("index", 0);
            pActual = intent.getIntExtra("pos", 0);
            arrayList = (ArrayList<Canciones>) intent.getSerializableExtra("canciones");
            if (mediaPlayer != null) mediaPlayer.release();
            if (position >= 0) {
                canciones = arrayList.get(position);
                mediaPlayer = new MediaPlayer();
                Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
                try {
                    mediaPlayer.setDataSource(activity_select_playlist.this, u);
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

    public void irCanciones(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        int posicion = mediaPlayer.getCurrentPosition();
        intent.putExtra("canciones", arrayList);
        intent.putExtra("pos", posicion);
        intent.putExtra("index", index);
        mediaPlayer.release();
        startActivity(intent);
    }


}

