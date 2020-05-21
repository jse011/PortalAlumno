package com.consultoraestrategia.ss_portalalumno.entities;

import androidx.annotation.Nullable;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class GlobalSettings extends BaseModel {
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private String urlServer;

    public GlobalSettings() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlServer() {
        return urlServer;
    }

    public void setUrlServer(String urlServer) {
        this.urlServer = urlServer;
    }

    public @Nullable
    static GlobalSettings getCurrentSettings() {
        GlobalSettings globalSettings = null;
        try{
            globalSettings = SQLite.select()
                    .from(GlobalSettings.class)
                    .querySingle();
        }catch (Exception e){
            e.printStackTrace();
        }
        return globalSettings;
    }

    public static String getServerUrl() {
        String serverUrl = null;
        GlobalSettings settings = getCurrentSettings();
        if (settings != null) {
            serverUrl = settings.getUrlServer();
        }
        return serverUrl;
    }


}
