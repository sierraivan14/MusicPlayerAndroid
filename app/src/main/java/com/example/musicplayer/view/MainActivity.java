package com.example.musicplayer.view;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.musicplayer.R;

// clases del Modelo y Controlador
import com.example.musicplayer.model.Model;
import com.example.musicplayer.controller.Control;

public class MainActivity extends AppCompatActivity {

    // Componentes de la interfaz (Equivalentes a JButtons, JLabels, etc.)
    private TextView tvTitulo;
    private ImageButton btnAnt, btnPlay, btnSig, btnPower, btnLetra;
    private ImageView fondoGif;
    private Control controlador; //Variable Global

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Vincula esta clase de Java con el diseño XML
        setContentView(R.layout.activity_main);

        // 1. Enlazar los componentes del XML (Equivalente configure/assemble)
        fondoGif = findViewById(R.id.fondo_gif);
        tvTitulo = findViewById(R.id.tv_titulo);
        btnAnt = findViewById(R.id.btn_ant);
        btnPlay = findViewById(R.id.btn_play);
        btnSig = findViewById(R.id.btn_sig);
        btnPower = findViewById(R.id.btn_power);
        btnLetra = findViewById(R.id.btn_letra);

        // Por defecto colocamos la imagen estática al iniciar
        fondoGif.setImageResource(R.drawable.gof2);

        // 2. Inicialización de la arquitectura MVC

        Model modelo = new Model();
        controlador = new Control(this, modelo);
    }

    //Pausar cuando cambiamos de aplicación
    @Override
    protected void onPause() {
        super.onPause();
        if (controlador != null) {
            controlador.pausarDesdeSistema();
        }
    }

    public ImageButton getPlay() { return btnPlay; }
    public ImageButton getSig() { return btnSig; }
    public ImageButton getAnt() { return btnAnt; }
    public ImageButton getPower() { return btnPower; }
    public ImageButton getLetra() { return btnLetra; }

    // --- MÉTODOS DE ACTUALIZACIÓN DE INTERFAZ ---

    // Cambia el texto de la etiqueta del casete
    public void actualizarTitulo(String texto) {
        tvTitulo.setText(texto);
    }

    // Cierra la aplicación (Equivalente a ventana.dispose() o System.exit(0))
    public void cerrarVentana() {
        finish();
    }

    // Controla el comportamiento del fondo animado
    public void animarFondo(boolean reproduciendo) {
        if (reproduciendo) {
            // Glide carga el GIF animado (gof.gif) y lo pone en el fondo
            com.bumptech.glide.Glide.with(this)
                    .asGif()
                    .load(R.drawable.gof)
                    .placeholder(R.drawable.gof2) //corrige error de pantalla
                    .into(fondoGif);
        } else {
            // Glide limpia la animación y regresamos a la imagen fija (gof2.png)
            com.bumptech.glide.Glide.with(this).clear(fondoGif);
            fondoGif.setImageResource(R.drawable.gof2);
        }
    }
}