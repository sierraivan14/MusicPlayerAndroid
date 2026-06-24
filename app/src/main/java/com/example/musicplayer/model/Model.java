package com.example.musicplayer.model;

import android.content.Context;
import android.media.MediaPlayer;
import java.util.ArrayList;

public class Model {
    private ArrayList<String> lista;
    private MediaPlayer reproductor;

    public Model() {
        lista = new ArrayList<>();
        lista.add("Tommy Richman");
        lista.add("Sheppard");
        lista.add("Iron Maiden");
        lista.add("Kiss");
        lista.add("Guns n Roses");
        lista.add("Van Halen");
    }

    public MediaPlayer getReproductor() {
        return reproductor;
    }

    public String getCancion(int numero) {
        return lista.get(numero);
    }

    public int getCantidadCanciones() {
        return lista.size();
    }

    // Carga el archivo desde la carpeta raw
    public void cargarAudio(Context context, String song) {
        System.out.println("Cargando audio: " + song);

        if (reproductor != null) {
            if (reproductor.isPlaying()) {
                reproductor.stop();
            }
            reproductor.release();
        }

        String nombreArchivo = song.toLowerCase().replace(" ", "_");
        int resID = context.getResources().getIdentifier(nombreArchivo, "raw", context.getPackageName());

        if (resID != 0) {
            reproductor = MediaPlayer.create(context, resID);
        } else {
            System.out.println("No se encontró el archivo de audio en res/raw/: " + nombreArchivo);
        }
    }

    public void play() {
        if (reproductor != null) {
            reproductor.start();
        }
    }

    public void pause() {
        if (reproductor != null && reproductor.isPlaying()) {
            reproductor.pause();
        }
    }
}