package com.daanigp.padinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class Activity_Inicio extends AppCompatActivity {

    TextView txtInfoApp, txtInfoApp_webs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        txtInfoApp = (TextView) findViewById(R.id.txtInfoApp);
        txtInfoApp_webs = (TextView) findViewById(R.id.txtInfoApp_webs);

        String premierPadel, rankingMasculino, rankingFemenino, textoPremierPadel, textoRankMasc, textoRankFem, textoPremierPadelCompleto, textoRankMascCompleto, textoRankFemCompleto;
        premierPadel = "https://premierpadel.com/";
        rankingMasculino = "https://www.padelfip.com/ranking-male/";
        rankingFemenino = "https://www.padelfip.com/ranking-female/";

        textoPremierPadel = "Premier padel: ";
        textoRankMasc = "Ranking masculino: ";
        textoRankFem = "Ranking femenino: ";

        textoPremierPadelCompleto = textoPremierPadel + premierPadel;
        textoRankMascCompleto = textoRankMasc + rankingMasculino;
        textoRankFemCompleto = textoRankFem + rankingFemenino;

        SpannableString premierPadelSpanneable, rankMascSpanneable, rankFemSpanneable;
        ClickableSpan clickableSpanPremierPadel, clickableSpanRankMasc, clickableSpanRankFem;

        // Link de Premier Padel
        premierPadelSpanneable = new SpannableString(textoPremierPadelCompleto);
        clickableSpanPremierPadel = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Acción al hacer clic en el enlace (por ejemplo, abrir en un navegador)
                Intent intentPremierPadel = new Intent(Intent.ACTION_VIEW, Uri.parse(premierPadel));
                startActivity(intentPremierPadel);
            }
        };
        premierPadelSpanneable.setSpan(clickableSpanPremierPadel, textoPremierPadel.length(), textoPremierPadelCompleto.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Link del ranking masculino (actual)
        rankMascSpanneable = new SpannableString(textoRankMascCompleto);
        clickableSpanRankMasc = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Acción al hacer clic en el enlace (por ejemplo, abrir en un navegador)
                Intent intentRankMasc = new Intent(Intent.ACTION_VIEW, Uri.parse(rankingMasculino));
                startActivity(intentRankMasc);
            }
        };
        rankMascSpanneable.setSpan(clickableSpanRankMasc, textoRankMasc.length(), textoRankMascCompleto.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Link del rango femenino (actual)
        rankFemSpanneable = new SpannableString(textoRankFemCompleto);
        clickableSpanRankFem = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Acción al hacer clic en el enlace (por ejemplo, abrir en un navegador)
                Intent intentRankFem = new Intent(Intent.ACTION_VIEW, Uri.parse(rankingFemenino));
                startActivity(intentRankFem);
            }
        };
        rankFemSpanneable.setSpan(clickableSpanRankFem, textoRankFem.length(), textoRankFemCompleto.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Creamos un SpannableStringBuilder, para poner los enlaces uno encima del otro
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(premierPadelSpanneable);
        builder.append("\n"); // Nueva línea entre los enlaces
        builder.append(rankMascSpanneable);
        builder.append("\n"); // Nueva línea entre los enlaces
        builder.append(rankFemSpanneable);

        // Añadimos los spanneables al text view
        txtInfoApp_webs.setText(builder);
        txtInfoApp_webs.setMovementMethod(LinkMovementMethod.getInstance());

        /*try {

            URL urlPremierPadel = new URL(premierPadel);
            URL urlRankingMasculino = new URL(rankingMasculino);
            URL urlRankingFemenino = new URL(rankingFemenino);

            txtInfoApp_webs.setText("Premier padel: " + urlPremierPadel);
            txtInfoApp_webs.append("\nRanking masculino: " + urlRankingMasculino);
            txtInfoApp_webs.append("\nRanking femenino: " + urlRankingFemenino);




        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dinamico, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opcionID = item.getItemId();

        switch (opcionID) {
            case R.id.itemEditarUsuario:
                Toast.makeText(getApplicationContext(), "Editar Usuario", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemListado:
                Toast.makeText(getApplicationContext(), "Listado torneos", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemTop5:
                Toast.makeText(getApplicationContext(), "Top 5 jugadores/as", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemRegistrarPartidos:
                Toast.makeText(getApplicationContext(), "Registrar Partidos", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemCerrarSesion:
                Toast.makeText(getApplicationContext(), "Cerrar Sessión", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}