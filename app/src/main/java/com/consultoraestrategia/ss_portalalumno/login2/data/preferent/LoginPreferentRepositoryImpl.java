package com.consultoraestrategia.ss_portalalumno.login2.data.preferent;

import android.content.Context;
import android.content.SharedPreferences;

import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class LoginPreferentRepositoryImpl implements LoginPreferentRepository {

    private Context context;
    private final String PREFERENCIA = "LoginPreferentRepository.Usuario";
    private final String KEY = "usuarios";
    public LoginPreferentRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<PersonaUi> getTodosUsuarios() {
        SharedPreferences miPreferencia = context.getSharedPreferences(PREFERENCIA, Context.MODE_PRIVATE);
        String json = miPreferencia.getString(KEY, "[]");
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<PersonaUi>>(){}.getType());

    }

    @Override
    public void guardarUsuario(PersonaUi usuarioUi, Callback<Boolean> usuarioCallback) {

        try {
            List<PersonaUi> usuarioUiList = getTodosUsuarios();
            int pocision = usuarioUiList.indexOf(usuarioUi);
            if(pocision==-1){
                SharedPreferences miPreferencia = context.getSharedPreferences(PREFERENCIA, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = miPreferencia.edit();
                Gson gson = new Gson();
                usuarioUiList.add(usuarioUi);
                String json = gson.toJson(usuarioUiList);
                editor.putString(KEY, json);
                editor.apply();
            }
            usuarioCallback.onLoad(true, true);
        }catch (Exception e){
            e.printStackTrace();
            usuarioCallback.onLoad(false, false);
        }
    }

    @Override
    public void eliminarUsuario(PersonaUi usuarioUi, Callback<Boolean> usuarioCallback) {
        try {
            List<PersonaUi> usuarioUiList = getTodosUsuarios();
            SharedPreferences miPreferencia = context.getSharedPreferences(PREFERENCIA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = miPreferencia.edit();
            Gson gson = new Gson();
            usuarioUiList.remove(usuarioUi);
            String json = gson.toJson(usuarioUiList);
            editor.putString(KEY, json);
            editor.apply();
            usuarioCallback.onLoad(true, true);
        }catch (Exception e){
            e.printStackTrace();
            usuarioCallback.onLoad(false, false);
        }
    }

    @Override
    public void elimarTodosUsuario(Callback<Boolean> usuarioCallback) {
        try {
            SharedPreferences miPreferencia = context.getSharedPreferences(PREFERENCIA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = miPreferencia.edit();
            editor.putString(KEY, "[]");
            editor.apply();
            usuarioCallback.onLoad(true, true);
        }catch (Exception e){
            e.printStackTrace();
            usuarioCallback.onLoad(false, false);
        }
    }
}
