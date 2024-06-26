package com.daanigp.padinfo.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesManager {
    private static final String SH_PREF_NAME = "padinfo_preferences";
    private static final String KEY_USER_ID = "userID";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLES_ID = "rolesID";
    private static final String KEY_THEME = "theme";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMAGE = "image";

    private static SharedPreferencesManager instance;
    private static Context context;

    private SharedPreferencesManager(Context ctx) {
        context = ctx;
    }

    public static synchronized SharedPreferencesManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new SharedPreferencesManager(ctx);
        }
        return instance;
    }

    public void saveUserID(long userId) {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(KEY_USER_ID, userId);
        editor.apply();
    }

    public long getUserId() {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getLong(KEY_USER_ID, -1);
    }

    public void saveToken(String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_TOKEN, null);
    }

    public void saveUsername(String username) {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_USERNAME, null);
    }

    public void saveRolesId(List<Long> rolesID) {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String jsonRolesId = gson.toJson(rolesID);
        editor.putString(KEY_ROLES_ID, jsonRolesId);
        editor.apply();
    }

    public List<Long> getRolesId() {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonRolesId = sharedPref.getString(KEY_ROLES_ID, null);
        if (jsonRolesId == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Long>>() {}.getType();
        return gson.fromJson(jsonRolesId, type);
    }

    public void saveTheme(int theme) {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putInt(KEY_THEME, theme);
        editor.putInt(KEY_THEME, 1);
        editor.apply();
    }

    public int getTheme() {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getInt(KEY_THEME, 1);
    }

    public void saveEmail(String email) {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_EMAIL, null);
    }

    public void saveImage(String img) {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_IMAGE, img);
        editor.apply();
    }

    public String getImage() {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_IMAGE, null);
    }

    public void clear() {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_ROLES_ID);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_IMAGE);
        editor.apply();
    }

}
