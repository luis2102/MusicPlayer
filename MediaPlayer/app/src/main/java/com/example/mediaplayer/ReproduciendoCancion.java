    package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

    public class ReproduciendoCancion extends AppCompatActivity {

    private ImageView imagen;
    private TextView nombre, artista, inicio, fin;
    private Bitmap bitmap;
    private SeekBar seekBar;
    private double startTime = 0;
    private double finalTime = 0;
    private ImageView reanudar;

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private int posicion;
    private ArrayList<Canciones> arrayList;
    private int index, index2;
    private Canciones canciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproduciendo_cancion);

        Intent intent = getIntent();
        arrayList = (ArrayList<Canciones>) intent.getSerializableExtra("canciones");
        posicion = intent.getIntExtra("pos", 0);
        index = intent.getIntExtra("index", 0);

        canciones = arrayList.get(index);
        mediaPlayer = new MediaPlayer();
        Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
        try {
            mediaPlayer.setDataSource(ReproduciendoCancion.this, u);
            //mediaPlayer.prepareAsync();
            mediaPlayer.prepare();
            mediaPlayer.seekTo(posicion);
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.seekTo(posicion);
            }
        });*/

        imagen = (ImageView) findViewById(R.id.imagen);
        nombre = (TextView) findViewById(R.id.nombreCancion);
        artista = (TextView) findViewById(R.id.artistaCancion);
        inicio = (TextView) findViewById(R.id.inicioCancion);
        fin = (TextView) findViewById(R.id.finCancion);
        reanudar = (ImageView) findViewById(R.id.play_pausa);
        finalTime = Double.parseDouble(canciones.getDuracion());
        startTime = posicion;
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax((int) finalTime);
        seekBar.setProgress((int) startTime);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });
        handler.postDelayed(UpdateSongTime, 100);

        fin.setText(String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        inicio.setText(String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );

        bitmap = BitmapFactory.decodeByteArray(canciones.getImagen(), 0, canciones.getImagen().length);

        imagen.setImageBitmap(bitmap);
        nombre.setText(canciones.getTitulo());
        artista.setText(canciones.getArtista());
        reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            try {
                startTime = mediaPlayer.getCurrentPosition();
                inicio.setText(String.format("%d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                );
                seekBar.setProgress((int) startTime);
                handler.postDelayed(this, 100);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                //mediaPlayer.reset();
                //startTime = mediaPlayer.getCurrentPosition();
            }
        }
    };

        public void reanudar(View view) {
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                reanudar.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
            } else{
                mediaPlayer.start();
                reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
            }
        }

        public void cancionAnterior(View view) {
            index2 = index - 1;
            //Log.i("aaaaa", String.valueOf(index));
            if (index2 < 0) {
                Toast.makeText(this, "Esta es la primera canción", Toast.LENGTH_SHORT).show();
            } else {
                if (mediaPlayer != null) mediaPlayer.release();
                canciones = arrayList.get(index2);
                mediaPlayer = new MediaPlayer();
                Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
                try {
                    mediaPlayer.setDataSource(ReproduciendoCancion.this, u);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                    nombre.setText(canciones.getTitulo());
                    artista.setText(canciones.getArtista());
                    bitmap = BitmapFactory.decodeByteArray(canciones.getImagen(), 0, canciones.getImagen().length);
                    imagen.setImageBitmap(bitmap);
                    finalTime = Double.parseDouble(canciones.getDuracion());
                    startTime = finalTime - finalTime;
                    seekBar.setMax((int) finalTime);
                    fin.setText(String.format("%d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                    );
                    index = index2;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void cancionSiguiente(View view) {
            index2 = index + 1;
            //Log.i("aaaaa", String.valueOf(index));
            if (index2 >= arrayList.size()) {
                Toast.makeText(this, "Última canción", Toast.LENGTH_SHORT).show();
            } else {
                if (mediaPlayer != null) mediaPlayer.release();
                canciones = arrayList.get(index2);
                mediaPlayer = new MediaPlayer();
                Uri u = Uri.parse("android.resource://com.example.mediaplayer/raw/" + canciones.getSong());
                try {
                    mediaPlayer.setDataSource(ReproduciendoCancion.this, u);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    reanudar.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                    nombre.setText(canciones.getTitulo());
                    artista.setText(canciones.getArtista());
                    bitmap = BitmapFactory.decodeByteArray(canciones.getImagen(), 0, canciones.getImagen().length);
                    imagen.setImageBitmap(bitmap);
                    finalTime = Double.parseDouble(canciones.getDuracion());
                    startTime = finalTime - finalTime;
                    seekBar.setMax((int) finalTime);
                    fin.setText(String.format("%d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                    );
                    index = index2;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void cerrar(View view) {
            Intent intent = new Intent();
            //Canciones cancion = arrayList.get(position);
            int posicion = mediaPlayer.getCurrentPosition();
            int position = index;
            intent.putExtra("canciones", arrayList);
            intent.putExtra("pos", posicion);
            intent.putExtra("index", position);
            setResult(Activity.RESULT_OK, intent);
            mediaPlayer.release();
            //startActivity(intent);
            finish();
        }

        public void aleatorio(View view) {
            ArrayList<Canciones> aux = arrayList;
            aux.remove(index);
            Collections.shuffle(aux);
            aux.add(0, canciones);
            index = 0;
            arrayList = aux;
            Toast.makeText(this, "Canciones en aleatorio", Toast.LENGTH_SHORT).show();
        }
    }
