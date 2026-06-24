package com.example.musicplayer.controller;

import android.view.View;
import com.example.musicplayer.R;
import com.example.musicplayer.model.Model;
import com.example.musicplayer.view.MainActivity;

public class Control {
    private MainActivity view;
    private Model model;

    private boolean reproduciendo = false;
    private int actual = 0;

    public Control(MainActivity view, Model model) {
        this.view = view;
        this.model = model;

        // 1. Configuramos los clics de los botones
        configurarListeners();

        // 2. Cargamos la primera canción al iniciar
        cargarCancionActual();
    }

    private void configurarListeners() {
        view.getPlay().setOnClickListener(v -> {
            reproduciendo = !reproduciendo;
            if (reproduciendo) {
                view.getPlay().setImageResource(R.drawable.pause);
                model.play();
                view.animarFondo(true);
            } else {
                view.getPlay().setImageResource(R.drawable.play);
                model.pause();
                view.animarFondo(false);
            }
        });

        view.getSig().setOnClickListener(v -> siguienteCancion());

        view.getAnt().setOnClickListener(v -> {
            actual = (actual - 1 + model.getCantidadCanciones()) % model.getCantidadCanciones();
            cargarCancionActual();
            if (reproduciendo) {
                model.play();
                view.animarFondo(true);
            }
        });

        view.getPower().setOnClickListener(v -> {
            model.pause();
            view.cerrarVentana();
        });

        // El botón de letra sigue existiendo

        view.getLetra().setOnClickListener(v -> {
            System.out.println("Botón de letra presionado.");
        });
    }

    private void cargarCancionActual() {
        String nombreCancion = model.getCancion(actual);
        view.actualizarTitulo(nombreCancion);

        try {
            model.cargarAudio(view, nombreCancion);

            if (model.getReproductor() != null) {
                model.getReproductor().setOnCompletionListener(mp -> {
                    System.out.println("Canción terminada, cambiando automáticamente...");
                    siguienteCancion();
                });
            }
        } catch (Exception ex) {
            System.out.println("Error al cargar cancion: " + ex.getMessage());
        }
    }

    private void siguienteCancion() {
        actual = (actual + 1) % model.getCantidadCanciones();
        cargarCancionActual();
        if (reproduciendo) {
            model.play();
            view.animarFondo(true);
        }
    }

    public void pausarDesdeSistema() {
        if (reproduciendo) {
            reproduciendo = false;
            view.getPlay().setImageResource(R.drawable.play);
            model.pause();
            view.animarFondo(false);
        }
    }
}
