package com.daanigp.padinfo.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daanigp.padinfo.Activities.ActivityEdit_User;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Retrofit.RetrofitClient;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    //Interfaz para actualizar la información del menú desplegable
    public interface OnUserInfoUpdatedListener {
        void onUserInfoUpdated();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    private static int EDIT_USER = 2;
    private static final String TAG = "PerfilFragment";

    private TextView txtNombre, txtApellidos, txtEmail;
    private Button btnEditar;
    private ImageView imgPerfil;
    private View message_layout;
    private String token;
    private long userId;

    private OnUserInfoUpdatedListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        txtNombre = root.findViewById(R.id.txtNombreUsuario);
        txtApellidos = root.findViewById(R.id.txtApellidos);
        txtEmail = root.findViewById(R.id.txtEmail);
        btnEditar = root.findViewById(R.id.btnEditar);
        imgPerfil = root.findViewById(R.id.imgPerfil);

        userId = SharedPreferencesManager.getInstance(getActivity()).getUserId();
        token = SharedPreferencesManager.getInstance(getActivity()).getToken();
        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        autocompleteUserInfo();

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Editar");
                Intent intent = new Intent(getActivity(), ActivityEdit_User.class);
                //startActivity(intent);
                startActivityForResult(intent, EDIT_USER);
            }
        });
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Para verificar que MainActivity tenga implementada la interfaz
        if (context instanceof OnUserInfoUpdatedListener) {
            listener = (OnUserInfoUpdatedListener) context;
        } else {
            Log.e(TAG, "Error con la intervar OnUserInfoUpdatedListener");
            throw new RuntimeException(context.toString()
                    + " debe implementar OnUserInfoUpdatedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_USER && resultCode == RESULT_OK) {
            autocompleteUserInfo();

            //Notificar a MainActivity que la información se ha actualizado
            if (listener != null) {
                listener.onUserInfoUpdated();
            }
        }
    }

    private void autocompleteUserInfo() {
        IPadinfo_API padinfoApi = RetrofitClient.getPadinfoAPI();
        Call<UserEntity> call = padinfoApi.getUserInfoByUserID(token, userId);

        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(!response.isSuccessful()) {
                    Log.v(TAG, "No va (autocompleteUserInfo) -> response" + response);
                    showToast("Código error: " + response.code());
                    return;
                }

                UserEntity user = response.body();

                if (user != null) {
                    txtNombre.setText(user.getName());
                    txtApellidos.setText(user.getLastname());
                    txtEmail.setText(user.getEmail());
                    Log.e("PROFILEFRAGMENT", "imgURL ->  " + user.getImageURL());
                    int imageResourceId = requireContext().getResources().getIdentifier(user.getImageURL(), "drawable", requireContext().getPackageName());
                    imgPerfil.setImageResource(imageResourceId);
                } else {
                    showToast("Error en la respuesta del servidor");
                    txtNombre.setText("vacío");
                    txtApellidos.setText("vacío");
                    txtEmail.setText("vacío");
                    imgPerfil.setImageResource(R.drawable.padinfo_logo);
                }

            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                Log.e(TAG, "Error en la llamada Retrofit - (autocompleteUserInfo)", t);
                showToast("Código error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, getActivity(), message_layout);
        toast.CreateToast();
    }
}