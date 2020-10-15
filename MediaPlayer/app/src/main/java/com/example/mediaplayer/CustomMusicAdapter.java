package com.example.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomMusicAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Canciones> arrayList;
    private MediaPlayer mediaPlayer;

    public CustomMusicAdapter(Context context, int layout, ArrayList<Canciones> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
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

       final Canciones canciones = arrayList.get(position);

       viewHolder.textName.setText(canciones.getTitulo());
       viewHolder.textArtist.setText(canciones.getArtista());

       viewHolder.textName.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mediaPlayer = MediaPlayer.create(context, canciones.getSong());
               mediaPlayer.start();
           }
       });

       return convertview;
    }
}
