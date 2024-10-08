package com.daanigp.padinfo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Initiate extends AppCompatActivity implements MediaController.MediaPlayerControl {
    MediaController mc;
    MediaPlayer mp;
    VideoView video;

    public static int USER_LOGIN = 1;
    private static final String TAG = "Activity_Initiate";
    TextView txtWelcome, txtInfoApp, txtInfoApp_webs;
    boolean registredUser;
    String username;
    ArrayList<Long> rolesId;
    View message_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialite);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        txtWelcome = (TextView) findViewById(R.id.txtBienvenida);
        txtInfoApp = (TextView) findViewById(R.id.txtInfoApp);
        txtInfoApp_webs = (TextView) findViewById(R.id.txtInfoApp_webs);
        video = (VideoView) findViewById(R.id.bestShotsVideo);

        rolesId = new ArrayList<>();
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        setDayNight();
        getRolesByUserId();
        completeAppInfo();
        completeTheWebsInfo();
        putVideoTopHighlights();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (registredUser) {
            getMenuInflater().inflate(R.menu.dinamicmenu_user_admin, menu);
        } else {
            getMenuInflater().inflate(R.menu.dinamicmenu_guest, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opcionID = item.getItemId();

        switch (opcionID) {
            /*case R.id.itemPerfil:
                showToast("Perfil");

                Intent intentPerfilUsuario = new Intent(Activity_Initiate.this, ActivityUserProfile.class);
                startActivity(intentPerfilUsuario);
                return true;
            case R.id.itemListado:
                showToast("Listado tournaments");

                Intent intentListadoTorneos = new Intent(Activity_Initiate.this, ActivityList_Tournament.class);
                startActivity(intentListadoTorneos);
                return true;
            case R.id.itemTop5:
                showToast("Top 5 jugadores/as");

                Intent intentRanking = new Intent(Activity_Initiate.this, ActivityList_Ranking.class);
                startActivity(intentRanking);
                return true;*/
            /*case R.id.itemRegistrarPartidos:
                showToast("Partidos");

                Intent intentPartidos = new Intent(Activity_Initiate.this, ActivityList_Games.class);
                startActivity(intentPartidos);
                return true;*/
            case R.id.itemCerrarSesion:
                showToast("Cerrando sesión...");

                putUserDisconnected();
                SharedPreferencesManager.getInstance(Activity_Initiate.this).clear();
                Intent intentInicioSes = new Intent(Activity_Initiate.this, ActivityLogin.class);
                startActivity(intentInicioSes);
                return true;
            case R.id.itemInicioSesion:
                SharedPreferencesManager.getInstance(Activity_Initiate.this).clear();
                Intent intentInicioSesion = new Intent(Activity_Initiate.this, ActivityLogin.class);
                startActivityForResult(intentInicioSesion, USER_LOGIN);
                return true;
            case R.id.itemDeleteAccount:
                showPopupMenu();
                return true;
            case R.id.itemSalir:
                showToast("Saliendo de la aplicación...");

                putUserDisconnected();
                SharedPreferencesManager.getInstance(Activity_Initiate.this).clear();
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
                invalidateOptionsMenu();
            }
        }
    }

    private void completeAppInfo() {
        username = SharedPreferencesManager.getInstance(this).getUsername();
        txtWelcome.setText("¡Bienvenido " + username + "!");

        InputStream ins = getResources().openRawResource(R.raw.info_app);
        BufferedReader br = new BufferedReader(new InputStreamReader(ins));

        String line = "";
        boolean firstLine = true;
        try {
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    txtInfoApp.setText(line);
                    firstLine = false;
                } else {
                    txtInfoApp.setText(txtInfoApp.getText().toString() + "\n" + line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showToast("No se ha podido leer el fichero de texto");
        } catch (IOException ioex) {
            ioex.printStackTrace();
            showToast("No se ha podido leer el fichero de texto");
        }
    }

    private void completeTheWebsInfo() {
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
                Intent intentRankFem = new Intent(Intent.ACTION_VIEW, Uri.parse(rankingFemenino));
                startActivity(intentRankFem);
            }
        };
        rankFemSpanneable.setSpan(clickableSpanRankFem, textoRankFem.length(), textoRankFemCompleto.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(premierPadelSpanneable);
        builder.append("\n");
        builder.append(rankMascSpanneable);
        builder.append("\n");
        builder.append(rankFemSpanneable);

        txtInfoApp_webs.setText(builder);
        txtInfoApp_webs.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void putVideoTopHighlights() {
        mc = new MediaController(this);
        mc.setMediaPlayer(this);
        mc.setAnchorView(video);

        video.setMediaController(mc);
        video.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.best_shots));

        Handler h = new Handler();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        mc.show();
                    }
                });
            }
        });
    }

    private void getRolesByUserId() {
        String token = SharedPreferencesManager.getInstance(Activity_Initiate.this).getToken();
        long userId = SharedPreferencesManager.getInstance(Activity_Initiate.this).getUserId();

        Log.v(TAG, "TOKEN -> " + token);
        Log.v(TAG, "userId -> " + userId);

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Long>> call = padinfoApi.getRolesByUserId(token, userId);

        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (getRolesByUserId) -> response - getRolesByUserId - Activity_Initiate" + response.body());
                    showToast("Código error: " + response.code());
                    return;
                }

                List<Long> rolIdAPI = response.body();

                if (rolIdAPI != null && !rolIdAPI.isEmpty()) {
                    SharedPreferencesManager.getInstance(Activity_Initiate.this).saveRolesId(rolIdAPI);
                    rolesId = (ArrayList<Long>) rolIdAPI;

                    Log.e(TAG, "Listado ROLES -> " + rolesId);

                    selectTypeMenuByUserRole();

                    invalidateOptionsMenu();
                } else {
                    showToast("No hay roles asociados al id : " + userId);
                }

            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (getIdUser)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void selectTypeMenuByUserRole() {
        if (rolesId.size() > 0 && (rolesId.contains(1L) || rolesId.contains(2L))) {
            registredUser = true;
            Log.e(TAG, "USUARIO REGISTRADO -> TRUE");
        } else {
            registredUser = false;
            Log.e(TAG, "USUARIO REGISTRADO -> FALSE");
        }
    }

    private void putUserDisconnected() {
        long id = SharedPreferencesManager.getInstance(Activity_Initiate.this).getUserId();
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.updateIsConnected(id);
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if(!response.isSuccessful()) {
                    showToast("1-Código error - (putUserDisconnected): " + response.code());
                    return;
                }

                ResponseEntity res = response.body();

                if (res == null || !res.getMessege().equalsIgnoreCase("IsConnected actualizado correctamente")) {
                    showToast("Error en la respuesta del servidor");
                }

            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (putUserDisconnected)", t);
                showToast("2- Código error - (putUserDisconnected): " + t.getMessage());
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
                showToast("¡CALMA! No has eliminado nada, todo sigue igual.");
            }
        });

        builder.show();
    }

    private void deleteAccount() {
        String token = SharedPreferencesManager.getInstance(Activity_Initiate.this).getToken();
        long idUser = SharedPreferencesManager.getInstance(Activity_Initiate.this).getUserId();

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.deleteUserById(token, idUser);

        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (delete) -> response - Activity_Initiate" + response.message());
                    showToast("1-Código error - (deleteAccount): " + response.code());
                    return;
                }

                ResponseEntity res = response.body();

                if (res.getMessege().equalsIgnoreCase("Usuario eliminado correctamente")) {
                    showToast(res.getMessege());
                    finish();
                } else {
                    showToast("No se ha podido eliminar el usuario.");
                }

            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (deleteAccount)", t);
                showToast("2- Código error - (deleteAccount): " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void setDayNight() {
        int theme = SharedPreferencesManager.getInstance(this).getTheme();
        if (theme == 0) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, Activity_Initiate.this, message_layout);
        toast.CreateToast();
    }

    @Override
    public void start() {
        if(!mp.isPlaying())
            mp.start();
    }

    @Override
    public void pause() {
        if(mp.isPlaying())
            mp.pause();
    }

    @Override
    public int getDuration() {
        return mp.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mp.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mp.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mp.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mp.getAudioSessionId();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            if (!mc.isShowing())
                mc.show();
            else
                mc.hide();

        return false;
    }
}