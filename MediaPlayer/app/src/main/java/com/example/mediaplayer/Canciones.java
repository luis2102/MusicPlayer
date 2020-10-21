package com.example.mediaplayer;

import java.io.Serializable;

public class Canciones implements Serializable {

    private String titulo;
    private String artista;
    private byte[] imagen;
    private int song;
    private String duracion;

    public Canciones(String titulo, String artista, int song, String duracion, byte[] imagen) {
        this.titulo = titulo;
        this.artista = artista;
        this.song = song;
        this.duracion = duracion;
        this.imagen = imagen;
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

    public String getDuracion() { return duracion; }

    public void setDuracion(String duracion) { this.duracion = duracion; }
}
