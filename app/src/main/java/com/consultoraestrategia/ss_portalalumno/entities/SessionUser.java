package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by @stevecampos on 19/01/2018.
 */

@Table(database = AppDatabase.class)
public class SessionUser extends BaseModel {
    @PrimaryKey
    private int userId;

    @Column
    int personaId;

    @Column
    private String name;
    @Column
    private String urlPicture;

    @Column
    private String username;
    @Column
    private String passwordEncrypted;

    @Column
    private long timestampLogin;
    @Column
    private long timestampLastSeen;

    @Column
    private int state;

    @Column
    private boolean dataImported;

    @Column
    private long fechaServidor;

    @Column
    private long fechaUsoApp;

    @Column
    private boolean estadoSync;

    @Column(defaultValue = "-1")
    private int hourSync;

    @Column(defaultValue = "-1")
    private int minuteSync;


    public static final int STATE_INACTIVE = 0;
    public static final int STATE_ACTIVE = 1;

    public SessionUser() {
    }

    public SessionUser(int userId, String name, String urlPicture, String username, String passwordEncrypted, long timestampLogin, long timestampLastSeen) {
        this.userId = userId;
        this.name = name;
        this.urlPicture = urlPicture;
        this.username = username;
        this.passwordEncrypted = passwordEncrypted;
        this.timestampLogin = timestampLogin;
        this.timestampLastSeen = timestampLastSeen;
        this.state = STATE_ACTIVE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordEncrypted() {
        return passwordEncrypted;
    }

    public void setPasswordEncrypted(String passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTimestampLogin() {
        return timestampLogin;
    }

    public void setTimestampLogin(long timestampLogin) {
        this.timestampLogin = timestampLogin;
    }

    public long getTimestampLastSeen() {
        return timestampLastSeen;
    }

    public void setTimestampLastSeen(long timestampLastSeen) {
        this.timestampLastSeen = timestampLastSeen;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isDataImported() {
        return dataImported;
    }

    public void setDataImported(boolean dataImported) {
        this.dataImported = dataImported;
    }

    public static SessionUser getCurrentUser() {
        SessionUser sessionUser = null;
        try {
            sessionUser = SQLite.select()
                    .from(SessionUser.class)
                    .where(SessionUser_Table.state.is(STATE_ACTIVE))
                    .querySingle();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sessionUser;
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "userId=" + userId +
                ", personaId=" + personaId +
                ", name='" + name + '\'' +
                ", urlPicture='" + urlPicture + '\'' +
                ", username='" + username + '\'' +
                ", passwordEncrypted='" + passwordEncrypted + '\'' +
                ", timestampLogin=" + timestampLogin +
                ", timestampLastSeen=" + timestampLastSeen +
                ", state=" + state +
                ", dataImported=" + dataImported +
                ", fechaServidor=" + fechaServidor +
                ", estadoSync=" + estadoSync +
                '}';
    }

    public long getFechaServidor() {
        return fechaServidor;
    }

    public void setFechaServidor(long fechaServidor) {
        this.fechaServidor = fechaServidor;
    }

    public boolean isEstadoSync() {
        return estadoSync;
    }

    public void setEstadoSync(boolean estadoSync) {
        this.estadoSync = estadoSync;
    }

    public int getHourSync() {
        return hourSync;
    }

    public void setHourSync(int hourSync) {
        this.hourSync = hourSync;
    }

    public int getMinuteSync() {
        return minuteSync;
    }

    public void setMinuteSync(int minuteSync) {
        this.minuteSync = minuteSync;
    }
    public long getFechaUsoApp() {
        return fechaUsoApp;
    }

    public void setFechaUsoApp(long fechaUsoApp) {
        this.fechaUsoApp = fechaUsoApp;
    }
}
