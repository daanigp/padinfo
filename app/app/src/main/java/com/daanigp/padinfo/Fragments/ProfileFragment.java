package com.daanigp.padinfo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Toast.Toast_Personalized;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

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

    private TextView txtNombre, txtApellidos, txtEmail;
    private Button btnEditar;
    private ImageView imgPerfil;
    private View message_layout;

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

        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        txtNombre.setText("Daniel");
        txtApellidos.setText("García Pascual");
        txtEmail.setText("daani@gmail.com");
        imgPerfil.setImageResource(R.drawable.admin_img);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Editar");
            }
        });
        return root;
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, getActivity(), message_layout);
        toast.CreateToast();
    }
}