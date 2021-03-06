package com.example.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomMusicAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Canciones> arrayList;
    private List<Canciones> nombre;
    private MediaPlayer mediaPlayer;

    public CustomMusicAdapter(Context context, int layout, ArrayList<Canciones> arrayList) {
        this.context = context;
        this.layout = layout;
        this.nombre = arrayList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(arrayList);
    }

    @Override
    public int getCount() {
        return nombre.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void cancionesOrdenadas(ArrayList<Canciones> listaCanciones) {
        nombre = listaCanciones;
        notifyDataSetChanged();
    }

    public void ArtistasOrdenadas(ArrayList<Canciones> listaCanciones) {
        nombre = listaCanciones;
        notifyDataSetChanged();
    }

    public void OrdenarNormal() {
        nombre = arrayList;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView textName, textArtist;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
       ViewHolder viewHolder;
       if(convertview == null) {
           viewHolder = new ViewHolder();
           LayoutInflater layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           convertview = layoutInflater.inflate(layout, null);
           viewHolder.textName = (TextView) convertview.findViewById(R.id.titulo);
           viewHolder.textArtist = (TextView) convertview.findViewById(R.id.artista);

           convertview.setTag(viewHolder);
       } else {
           viewHolder = (ViewHolder) convertview.getTag();
       }

       final Canciones canciones = nombre.get(position);

       viewHolder.textName.setText(canciones.getTitulo());
       viewHolder.textArtist.setText(canciones.getArtista());

       return convertview;
    }

    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        nombre.clear();
        if (text.length() == 0) {
            nombre.addAll(arrayList);
        } else {
            for(Canciones cancion : arrayList) {
                if(cancion.getTitulo().toLowerCase(Locale.getDefault()).contains(text) || cancion.getArtista().toLowerCase(Locale.getDefault()).contains(text)) {
                    nombre.add(cancion);
                }
            }
        }
        notifyDataSetChanged();
    }
}
