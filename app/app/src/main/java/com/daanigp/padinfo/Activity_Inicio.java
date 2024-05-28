package com.daanigp.padinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Inicio extends AppCompatActivity {

    public static int USER_LOGIN = 1;
    private static final String TAG = "Activity_Inicio";
    TextView txtInfoApp, txtInfoApp_webs;
    boolean registredUser;
    String username;
    ArrayList<Long> rolesId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        txtInfoApp = (TextView) findViewById(R.id.txtInfoApp);
        txtInfoApp_webs = (TextView) findViewById(R.id.txtInfoApp_webs);

        rolesId = new ArrayList<>();


        /*if (userIsConnected()) {
            usuarioRegistrado = true;
        } else {
            usuarioRegistrado = false;
        }*/

        getRolesByUserId();

        selectTypeMenuByUserRole();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!registredUser) {
            getMenuInflater().inflate(R.menu.menu_dinamico_usuario, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_dinamico_invitado, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opcionID = item.getItemId();

        switch (opcionID) {
            case R.id.itemPerfil:
                Toast.makeText(getApplicationContext(), "Perfil", Toast.LENGTH_SHORT).show();
                Intent intentPerfilUsuario = new Intent(Activity_Inicio.this, ActivityPerfilUsuario.class);
                startActivity(intentPerfilUsuario);
                return true;
            case R.id.itemListado:
                Toast.makeText(getApplicationContext(), "Listado tournaments", Toast.LENGTH_SHORT).show();
                Intent intentListadoTorneos = new Intent(Activity_Inicio.this, ActivityListTorneos.class);
                startActivity(intentListadoTorneos);
                return true;
            case R.id.itemTop5:
                Toast.makeText(getApplicationContext(), "Top 5 jugadores/as", Toast.LENGTH_SHORT).show();
                Intent intentRanking = new Intent(Activity_Inicio.this, ActivityRanking.class);
                startActivity(intentRanking);
                return true;
            case R.id.itemRegistrarPartidos:
                Toast.makeText(getApplicationContext(), "Registrar Partidos", Toast.LENGTH_SHORT).show();
                Intent intentPartidos = new Intent(Activity_Inicio.this, ActivityListPartidos.class);
                startActivity(intentPartidos);
                return true;
            case R.id.itemCerrarSesion:
                Toast.makeText(getApplicationContext(), "Cerrar Sessión", Toast.LENGTH_SHORT).show();
                //usuarioRegistrado = false;
                //invalidateOptionsMenu();
                putUserDisconnected();
                SharedPreferencesManager.getInstance(Activity_Inicio.this).clear();
                Intent intentInicioSes = new Intent(Activity_Inicio.this, ActivityInicioSesion.class);
                startActivity(intentInicioSes);
                return true;
            case R.id.itemInicioSesion:
                Toast.makeText(getApplicationContext(), "Iniciar Sessión", Toast.LENGTH_SHORT).show();
                SharedPreferencesManager.getInstance(Activity_Inicio.this).clear();
                Intent intentInicioSesion = new Intent(Activity_Inicio.this, ActivityInicioSesion.class);
                startActivityForResult(intentInicioSesion, USER_LOGIN);
                return true;
            case R.id.itemDeleteAccount:
                Toast.makeText(getApplicationContext(), "ELIMINAR CUENTA", Toast.LENGTH_SHORT).show();
                showPopupMenu();
                return true;
            case R.id.itemSalir:
                Toast.makeText(getApplicationContext(), "Saliendo de la aplicación...", Toast.LENGTH_SHORT).show();
                //usuarioRegistrado = false;
                //invalidateOptionsMenu();
                putUserDisconnected();
                SharedPreferencesManager.getInstance(Activity_Inicio.this).clear();
                finishAffinity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USER_LOGIN) {
            if (resultCode == RESULT_OK) {
                getRolesByUserId();
                selectTypeMenuByUserRole();
                invalidateOptionsMenu();
            }
        }
    }

    private void getRolesByUserId() {
        String token = SharedPreferencesManager.getInstance(Activity_Inicio.this).getToken();
        long userId = SharedPreferencesManager.getInstance(Activity_Inicio.this).getUserId();

        Log.v(TAG, "TOKEN -> " + token);
        Log.v(TAG, "userId -> " + userId);

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Long>> call = padinfoApi.getRolesByUserId(token, userId);

        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (getIdUser) -> response - getRolesByUserId - Activity_Inicio");
                    Toast.makeText(Activity_Inicio.this, "Código error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Long> rolIdAPI = response.body();

                if (rolIdAPI != null && !rolIdAPI.isEmpty()) {
                    // Save the rolesID and in SharedPreferences
                    SharedPreferencesManager.getInstance(Activity_Inicio.this).saveRolesId(rolIdAPI);
                    rolesId = (ArrayList<Long>) rolIdAPI;
                } else {
                    Toast.makeText(Activity_Inicio.this, "No hay roles asociados al id : " + userId, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (getIdUser)", t);
                Toast.makeText(Activity_Inicio.this, "Código error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectTypeMenuByUserRole() {
        if (rolesId.size() > 0 && (rolesId.contains(1L) || rolesId.contains(2L))) {
            registredUser = true;
        } else {
            registredUser = false;
        }
    }

    private void putUserDisconnected() {
        long id = SharedPreferencesManager.getInstance(Activity_Inicio.this).getUserId();
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.updateIsConnected(id);
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(Activity_Inicio.this, "1-Código error - (putUserDisconnected): " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ResponseEntity res = response.body();

                if (res == null || !res.getMessege().equalsIgnoreCase("IsConnected actualizado correctamente")) {
                    Toast.makeText(Activity_Inicio.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_Inicio.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (putUserDisconnected)", t);
                Toast.makeText(Activity_Inicio.this, "2- Código error - (putUserDisconnected): " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void showPopupMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar cuenta");
        builder.setMessage("¿Estás seguro de eliminar la cuenta?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAccount();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "¡CALMA! No has eliminado nada, todo sigue igual.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void deleteAccount() {
        String token = SharedPreferencesManager.getInstance(Activity_Inicio.this).getToken();
        long idUser = SharedPreferencesManager.getInstance(Activity_Inicio.this).getUserId();

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.deletePlayerById(token, idUser);

        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(Activity_Inicio.this, "1-Código error - (deleteAccount): " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ResponseEntity res = response.body();

                if (res.getMessege().equalsIgnoreCase("Usuario eliminado correctamente")) {
                    Toast.makeText(Activity_Inicio.this, res.getMessege(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Activity_Inicio.this, "No se ha podido eliminar el usuario.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (deleteAccount)", t);
                Toast.makeText(Activity_Inicio.this, "2- Código error - (deleteAccount): " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}