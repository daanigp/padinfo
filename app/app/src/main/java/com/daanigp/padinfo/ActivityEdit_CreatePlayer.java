package com.daanigp.padinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class ActivityEdit_CreatePlayer extends AppCompatActivity {

    ImageView imgPlayer;
    EditText txtName, txtRankingPos, txtPoints;
    Spinner spinner;
    Button btnCancel, btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_player);
    }
}