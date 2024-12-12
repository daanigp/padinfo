package com.daanigp.padinfo.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Fragments.AboutFragment;
import com.daanigp.padinfo.Fragments.GamesListFragment;
import com.daanigp.padinfo.Fragments.HomeFragment;
import com.daanigp.padinfo.Fragments.PlayersListFragment;
import com.daanigp.padinfo.Fragments.ProfileFragment;
import com.daanigp.padinfo.Fragments.SettingsFragment;
import com.daanigp.padinfo.Fragments.TournamentsListFragment;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pruebas_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    public static int USER_LOGIN = 1;
    private static final String TAG = "MAIN_ACTIVITY";
    private static final String SELECTED_ITEM_KEY = "KEY_SELECTED_ITEM";
    int selectedItemId;
    BottomNavigationView bottomNavigation;
    View message_layout;
    private boolean registredUser;
    ArrayList<Long> rolesId = new ArrayList<>();
    private ShapeableImageView imgPerfil;
    private TextView txtUsuario, txtEmail;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);
        getRolesByUserId();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Obtener el headerView del NavigationView
        View headerView = navigationView.getHeaderView(0);

        txtUsuario = headerView.findViewById(R.id.usuario_nav_header);
        txtEmail = headerView.findViewById(R.id.email_nav_header);
        imgPerfil = headerView.findViewById(R.id.img_profile_nav_menu);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(m0nNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.home);
            navigationView.setCheckedItem(R.id.nav_home);
        } else {
            selectedItemId = savedInstanceState.getInt(SELECTED_ITEM_KEY);
            bottomNavigation.setSelectedItemId(selectedItemId);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.frame_container, new HomeFragment());
                transaction1.commit();
                break;
            case R.id.nav_settings:
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.frame_container, new SettingsFragment());
                transaction2.commit();
                break;
            case R.id.nav_info:
                FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.frame_container, new AboutFragment());
                transaction3.commit();
                break;
            case R.id.nav_logout:
                showToast("Cerrando sesión...");

                putUserDisconnected();
                SharedPreferencesManager.getInstance(Pruebas_Activity.this).clear();
                Intent intentInicioSes = new Intent(Pruebas_Activity.this, ActivityLogin.class);
                startActivity(intentInicioSes);
                break;
            case R.id.nav_delete_account:
                showPopupMenu();
                break;
            case R.id.nav_leave:
                showToast("Saliendo de la aplicación...");

                putUserDisconnected();
                SharedPreferencesManager.getInstance(Pruebas_Activity.this).clear();
                finishAffinity();
                break;
            case R.id.nav_login:
                showToast("LOGIN");
                deleteAccount();
                SharedPreferencesManager.getInstance(Pruebas_Activity.this).clear();
                Intent intentInicioSesion = new Intent(Pruebas_Activity.this, ActivityLogin.class);
                startActivityForResult(intentInicioSesion, USER_LOGIN);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_KEY, bottomNavigation.getSelectedItemId());
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (registredUser) {
            getMenuInflater().inflate(R.menu.dinamicmenu_user_admin, menu);
        } else {
            getMenuInflater().inflate(R.menu.dinamicmenu_guest, menu);
        }

        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opcionID = item.getItemId();

        switch (opcionID) {
            case R.id.itemRegistrarPartidos:
                showToast("Partidos");

                Intent intentPartidos = new Intent(Pruebas_Activity.this, ActivityList_Games.class);
                startActivity(intentPartidos);
                return true;
            case R.id.itemCerrarSesion:
                showToast("Cerrando sesión...");

                putUserDisconnected();
                SharedPreferencesManager.getInstance(Pruebas_Activity.this).clear();
                Intent intentInicioSes = new Intent(Pruebas_Activity.this, ActivityLogin.class);
                startActivity(intentInicioSes);
                return true;
            case R.id.itemInicioSesion:
                SharedPreferencesManager.getInstance(Pruebas_Activity.this).clear();
                Intent intentInicioSesion = new Intent(Pruebas_Activity.this, ActivityLogin.class);
                startActivityForResult(intentInicioSesion, USER_LOGIN);
                return true;
            case R.id.itemDeleteAccount:
                showPopupMenu();
                return true;
            case R.id.itemSalir:
                showToast("Saliendo de la aplicación...");

                putUserDisconnected();
                SharedPreferencesManager.getInstance(Pruebas_Activity.this).clear();
                finishAffinity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

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

    private void getRolesByUserId() {
        String token = SharedPreferencesManager.getInstance(Pruebas_Activity.this).getToken();
        long userId = SharedPreferencesManager.getInstance(Pruebas_Activity.this).getUserId();

        Log.v(TAG, "TOKEN -> " + token);
        Log.v(TAG, "userId -> " + userId);

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<List<Long>> call = padinfoApi.getRolesByUserId(token, userId);

        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (getRolesByUserId) -> response - getRolesByUserId - Pruebas_Activity" + response.body());
                    showToast("Código error: " + response.code());
                    return;
                }

                List<Long> rolIdAPI = response.body();

                if (rolIdAPI != null && !rolIdAPI.isEmpty()) {
                    SharedPreferencesManager.getInstance(Pruebas_Activity.this).saveRolesId(rolIdAPI);
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

        loadMenus();
    }

    private void loadMenus() {
        if (registredUser) {
            Log.e(TAG, "REGISTRAO ->" + registredUser);
            loadMenuUser_Admin();
            navigationView.inflateMenu(R.menu.nav_menu_admin_user);
            loadUserInfo();
        } else {
            Log.e(TAG, "NOO REGISTRAO ->" + registredUser);
            loadMenuGuest();
            navigationView.inflateMenu(R.menu.nav_menu_guest);
            txtUsuario.setText("");
            txtEmail.setText("");
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener m0nNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.tournamentList:
                    loadFragment(new TournamentsListFragment());
                    return true;
                case R.id.playersList:
                    loadFragment(new PlayersListFragment());
                    return true;
                case R.id.gamesList:
                    loadFragment(new GamesListFragment());
                    return true;
                case R.id.profile:
                    loadFragment(new ProfileFragment());
                    return true;
            }
            return false;
        }
    };

    private void loadMenuUser_Admin() {
        bottomNavigation.getMenu().clear();
        bottomNavigation.inflateMenu(R.menu.bottom_navigation_menu_admin_user);
        loadFragment(new HomeFragment()); // Cargar fragmento inicial del menú 1
    }

    private void loadMenuGuest() {
        bottomNavigation.getMenu().clear();
        bottomNavigation.inflateMenu(R.menu.bottom_navigation_menu_guest);
        loadFragment(new HomeFragment()); // Cargar fragmento inicial del menú 2
    }

    public void loadFragment(Fragment fragment) {
        if (!isFinishing() && !isDestroyed()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    private void putUserDisconnected() {
        long id = SharedPreferencesManager.getInstance(Pruebas_Activity.this).getUserId();
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
        String token = SharedPreferencesManager.getInstance(Pruebas_Activity.this).getToken();
        long idUser = SharedPreferencesManager.getInstance(Pruebas_Activity.this).getUserId();

        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<ResponseEntity> call = padinfoApi.deleteUserById(token, idUser);

        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (delete) -> response - Pruebas_Activity" + response.message() + " - " + response.code());
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

    private void loadUserInfo() {
        String img = SharedPreferencesManager.getInstance(this).getImage();

        int imageResourceId = getResources().getIdentifier(img, "drawable", getPackageName());
        imgPerfil.setImageResource(imageResourceId);
        if (imgPerfil != null) {
            imgPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("IMÁGEN DE PERFIL");
                }
            });
        }

        String username = SharedPreferencesManager.getInstance(this).getUsername();
        String email = SharedPreferencesManager.getInstance(this).getEmail();
        txtUsuario.setText(username);
        txtEmail.setText(email);
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, Pruebas_Activity.this, message_layout);
        toast.CreateToast();
    }

}