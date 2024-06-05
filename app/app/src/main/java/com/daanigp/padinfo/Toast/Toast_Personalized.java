package com.daanigp.padinfo.Toast;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daanigp.padinfo.R;

public class Toast_Personalized {
    private String message;
    private Activity activity;
    private View message_layout;

    public Toast_Personalized(String message, Activity activity, View message_layout){
        this.message = message;
        this.activity = activity;
        this.message_layout = message_layout;
    }

    public void CreateToast() {
        Toast toast = new Toast(activity);
        toast.setView(message_layout);

        TextView text = (TextView) message_layout.findViewById(R.id.toastMessage);
        text.setText(message);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
