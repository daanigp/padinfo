package com.daanigp.padinfo.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.daanigp.padinfo.R;
import com.daanigp.padinfo.Toast.Toast_Personalized;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements MediaController.MediaPlayerControl {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    private MediaController mc;
    private MediaPlayer mp;
    private VideoView video;
    private TextView txtWelcome, txtInfoApp, txtInfoApp_webs;
    private View message_layout;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        message_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        txtWelcome = root.findViewById(R.id.txtBienvenida);
        txtInfoApp = root.findViewById(R.id.txtInfoApp);
        txtInfoApp_webs = root.findViewById(R.id.txtInfoApp_webs);
        video = root.findViewById(R.id.bestShotsVideo);

        completeAppInfo();
        completeTheWebsInfo();
        putVideoTopHighlights();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ScrollView scrollView = view.findViewById(R.id.scrollView_home);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });
    }

    private void completeAppInfo() {
        //username = SharedPreferencesManager.getInstance(getContext()).getUsername();
        username = "DANI123";
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

    @SuppressLint("ClickableViewAccessibility")
    private void putVideoTopHighlights() {
        mc = new MediaController(getContext());
        mc.setMediaPlayer(this);
        mc.setAnchorView(video);

        video.setMediaController(mc);
        video.setVideoURI(Uri.parse("android.resource://"+getContext().getPackageName()+"/"+R.raw.best_shots));

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                HomeFragment.this.mp = mp;
            }
        });

        video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!mc.isShowing()) {
                        mc.show(3000);
                    } else {
                        mc.hide();
                    }
                }
                return true;
            }
        });
    }

    private void showToast(String message) {
        Toast_Personalized toast = new Toast_Personalized(message, getActivity(), message_layout);
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
}