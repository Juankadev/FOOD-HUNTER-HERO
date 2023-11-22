package com.example.ffh_rep.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Usuario;
import com.google.gson.Gson;

public class SessionManager {

    private SharedPreferences sharedPrefs;
    private Gson gson;

    public SessionManager(Context ctx){
        sharedPrefs = ctx.getSharedPreferences("user_sessions", Context.MODE_PRIVATE);
        gson = new Gson();
    }
    public void saveUserSession(Usuario user){
        String userSessionJson = this.gson.toJson(user);
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        editor.putString("user_session_data", userSessionJson);
        editor.apply();
    }

    public void saveHunterSession(Hunter user){
        String userSessionJson = this.gson.toJson(user);
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        editor.putString("user_session_data", userSessionJson);
        editor.apply();
    }

    public void saveCommerceSession(Comercio user){
        String userSessionJson = this.gson.toJson(user);
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        editor.putString("user_session_data", userSessionJson);
        editor.apply();
    }

    public Usuario getUserSession() {
        String userSessionJson = this.sharedPrefs.getString("user_session_data", null);
        if (userSessionJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(userSessionJson, Usuario.class);
        }
        return null;
    }

    public Hunter getHunterSession() {
        String userSessionJson = this.sharedPrefs.getString("user_session_data", null);
        if (userSessionJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(userSessionJson, Hunter.class);
        }
        return null;
    }

    // Recupero la información del comercio almacenada en las preferencias compartidas
    public Comercio getCommerceSession() {
        String userSessionJson = this.sharedPrefs.getString("user_session_data", null);
        if (userSessionJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(userSessionJson, Comercio.class);
        }
        return null;
    }

    public void clearSession() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove("user_session_data");
        editor.apply();
    }
}
