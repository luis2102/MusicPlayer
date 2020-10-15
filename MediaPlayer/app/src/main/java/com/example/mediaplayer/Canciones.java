package com.example.mediaplayer;

import android.media.Image;

public class Canciones {

    private String titulo;
    private String artista;
    private byte[] imagen;
    private int song;

    public Canciones(String titulo, String artista, byte[] imagen, int song) {
        this.titulo = titulo;
        this.artista = artista;
        this.imagen = imagen;
        this.song = song;
    }

    public Canciones(String titulo, String artista) {
        this.titulo = titulo;
        this.artista = artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getSong() { return song; }

    public  void setSong(int song) { this.song = song; }
}
