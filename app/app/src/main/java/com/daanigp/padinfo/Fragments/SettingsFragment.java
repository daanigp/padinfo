package com.daanigp.padinfo.Fragments;

import static android.content.Intent.getIntent;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.daanigp.padinfo.Activities.ActivityLogin;
import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Interfaces.BackPressHandler;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, BackPressHandler {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static final String TAG = "Settings_Fragment";
    private DrawerLayout drawerLayout;
    private TextView txtSettings;
    private Spinner spinnerLanguages;
    private View message_layout;
    private Switch switchTheme;
    private ShapeableImageView imgPerfil;
    private TextView txtUsuario, txtEmail;
    private NavigationView navigationView;

    private ArrayList<Long> rolesId = new ArrayList<>();
    private boolean registredUser;

    private boolean isSpinnerInitialized = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        drawerLayout = root.findViewById(R.id.drawer_layout_settings);
        txtSettings = root.findViewById(R.id.txtSettings);
        spinnerLanguages = root.findViewById(R.id.spinnerLanguages);
        switchTheme = root.findViewById(R.id.switchTheme);
        navigationView = root.findViewById(R.id.nav_view_settings);

        Toolbar toolbarSet = root.findViewById(R.id.toolbar_settings);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarSet);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbarSet, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        txtUsuario = headerView.findViewById(R.id.usuario_nav_header);
        txtEmail = headerView.findViewById(R.id.email_nav_header);
        imgPerfil = headerView.findViewById(R.id.img_profile_nav_menu);

        txtSettings.setText(txtSettings.getText().toString().toUpperCase());

        String lang = SharedPreferencesManager.getInstance(getContext()).getLanguage();
        int languageSpinner;
        if (lang.equalsIgnoreCase("en")) {
            languageSpinner = 1;
        } else {
            languageSpinner = 0;
        }

        spinnerLanguages.setSelection(languageSpinner);
        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isSpinnerInitialized) {
                    // Evita la ejecución la primera vez que se carga el Spinner
                    isSpinnerInitialized = true;
                    return;
                }

                String language = spinnerLanguages.getItemAtPosition(position).toString();
                showToast("Idioma seleccionado: " + language);
                selectLanguage(language);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*
            False -> Light
            True -> Dark
         */
        boolean isDarkTheme = SharedPreferencesManager.getInstance(getContext()).getTheme();
        Log.v("SHAREDPREFERENCES_THEME", "Theme -> " + isDarkTheme);
        switchTheme.setChecked(isDarkTheme);
        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //showToast("Tema oscuro");
                    showToast("En mantenimiento");
                } else {
                    //showToast("Tema claro");
                    showToast("En mantenimiento");
                }
                /*SharedPreferencesManager.getInstance(getContext()).saveTheme(isChecked);
                Log.v("SHAREDPREFERENCES_THEME", "Theme -> " + SharedPreferencesManager.getInstance(getContext()).getTheme());
                AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                requireActivity().recreate();*/
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRolesByUserId();
    }

    private void selectLanguage(String lang) {
        //Seleccionar el idioma
        String language;
        if (lang.equalsIgnoreCase("Inglés") || lang.equalsIgnoreCase("English")) {
            language = "en";
        } else {
            language = "es";
        }

        SharedPreferencesManager.getInstance(getContext()).saveLanguage(language);
        Log.v("SHAREDPREFERENCES_LANGUAGE", "Language -> " + SharedPreferencesManager.getInstance(getContext()).getLanguage());
        setLocale(language);
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, getActivity(), message_layout);
        toast.CreateToast();
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);

        //Actualizar el contexto globalmente
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        //Reiniciar el fragment para aplicar el cambio
        Intent intent = requireActivity().getIntent();
        intent.putExtra("navigateToFragment", "settingsFragment");
        requireActivity().finish();
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        switch(item.getItemId()) {
            case R.id.nav_home:
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.setReorderingAllowed(true);
                transaction1.replace(R.id.fcv_main_container, new MainFragment());
                transaction1.addToBackStack("principal");
                transaction1.commit();
                break;
            case R.id.nav_settings:
                FragmentTransaction transaction2 = manager.beginTransaction();
                transaction2.setReorderingAllowed(true);
                transaction2.replace(R.id.fcv_main_container, new SettingsFragment());
                transaction2.addToBackStack("principal");
                transaction2.commit();
                break;
            case R.id.nav_info:
                FragmentTransaction transaction3 = manager.beginTransaction();
                transaction3.setReorderingAllowed(true);
                transaction3.replace(R.id.fcv_main_container, new AboutFragment());
                transaction3.addToBackStack("principal");
                transaction3.commit();
                break;
            case R.id.nav_logout:
                showToast("Cerrando sesión...");

                putUserDisconnected();
                SharedPreferencesManager.getInstance(getActivity()).clear();
                Intent intentInicioSes = new Intent(getActivity(), ActivityLogin.class);
                startActivity(intentInicioSes);
                break;
            case R.id.nav_delete_account:
                //showPopupMenu();
                //showToast("Eliminar cuenta");
                showToast("Eliminar cuenta\nEn mantenimiento");
                break;
            case R.id.nav_leave:
                showToast("Saliendo de la aplicación...");

                putUserDisconnected();
                SharedPreferencesManager.getInstance(getActivity()).clear();
                finishAffinity(getActivity());
                break;
            case R.id.nav_login:
                showToast("LOGIN");
                SharedPreferencesManager.getInstance(getActivity()).clear();
                Intent intentLogin = new Intent(getActivity(), ActivityLogin.class);
                //startActivityForResult(intentLogin, USER_LOGIN);
                startActivity(intentLogin);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }

    private void loadUserInfo() {
        String img = SharedPreferencesManager.getInstance(getActivity()).getImage();

        int imageResourceId = requireContext().getResources().getIdentifier(img, "drawable", requireContext().getPackageName());
        imgPerfil.setImageResource(imageResourceId);
        if (imgPerfil != null) {
            imgPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("IMÁGEN DE PERFIL\nEn mantenimiento");
                   // showToast("IMÁGEN DE PERFIL");
                   // loadFragmentBottomMenu(new ProfileFragment());
                   // bottomNavigation.setSelectedItemId(R.id.profile);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });
        }

        String username = SharedPreferencesManager.getInstance(getActivity()).getUsername();
        String email = SharedPreferencesManager.getInstance(getActivity()).getEmail();
        txtUsuario.setText(username);
        txtEmail.setText(email);
    }

    private void getRolesByUserId() {
        String token = SharedPreferencesManager.getInstance(getActivity()).getToken();
        long userId = SharedPreferencesManager.getInstance(getActivity()).getUserId();

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
                    SharedPreferencesManager.getInstance(getActivity()).saveRolesId(rolIdAPI);
                    rolesId = (ArrayList<Long>) rolIdAPI;

                    Log.e(TAG, "Listado ROLES -> " + rolesId);

                    selectTypeMenuByUserRole();
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
            navigationView.inflateMenu(R.menu.nav_menu_admin_user);
            loadUserInfo();
        } else {
            Log.e(TAG, "NOO REGISTRAO ->" + registredUser);
            navigationView.inflateMenu(R.menu.nav_menu_guest);
            txtUsuario.setText("");
            txtEmail.setText("");
        }
        navigationView.setCheckedItem(R.id.nav_settings);
    }

    private void putUserDisconnected() {
        long id = SharedPreferencesManager.getInstance(getActivity()).getUserId();
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

    /*private void showPopupMenu() {
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
    } */
}