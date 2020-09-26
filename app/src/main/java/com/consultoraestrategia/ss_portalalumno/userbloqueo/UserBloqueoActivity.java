package com.consultoraestrategia.ss_portalalumno.userbloqueo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.entities.Persona;
import com.consultoraestrategia.ss_portalalumno.entities.Persona_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.estadocuenta2.EstadoCuenta2Activity;
import com.consultoraestrategia.ss_portalalumno.global.ICRMEduListener;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserBloqueoActivity extends AppCompatActivity implements ICRMEduListener {
    @BindView(R.id.imageView12)
    CircleImageView imageView12;
    @BindView(R.id.btn_atras)
    Button btnAtras;
    @BindView(R.id.btn_pago_linea)
    CardView btnPagoLinea;
    @BindView(R.id.btn_salir)
    Button btnSalir;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo_acceso);
        ButterKnife.bind(this);
        iCRMEdu.getiCRMEdu(this).addiCRMEduListener(this);
        setupImage();
        setupBtnAtras();
    }

    private void setupBtnAtras() {
        btnAtras.setVisibility(iCRMEdu.variblesGlobales.getBloqueoAcceso() ? View.INVISIBLE : View.VISIBLE);
        btnSalir.setVisibility(iCRMEdu.variblesGlobales.getBloqueoAcceso() ? View.VISIBLE : View.INVISIBLE);
    }

    private void setupImage() {
        try {
            int perosonaId = SessionUser.getCurrentUser().getPersonaId();
            Persona persona = SQLite.select()
                    .from(Persona.class)
                    .where(Persona_Table.personaId.eq(perosonaId))
                    .querySingle();
            Glide.with(imageView12)
                    .load(persona.getUrlPicture())
                    .apply(UtilsGlide.getGlideRequestOptions())
                    .into(imageView12);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.btn_atras, R.id.btn_pago_linea, R.id.btn_salir})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_salir:
                finishAffinity();
                break;
            case R.id.btn_atras:
                finish();
                break;
            case R.id.btn_pago_linea:
                try {
                    int perosonaId = SessionUser.getCurrentUser().getPersonaId();
                    Persona persona = SQLite.select()
                            .from(Persona.class)
                            .where(Persona_Table.personaId.eq(perosonaId))
                            .querySingle();

                    EstadoCuenta2Activity.start(this, persona.getPersonaId(), persona.getNumDoc());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onChangeBloqueo() {
        if (iCRMEdu.variblesGlobales.getHabilitarAcceso()) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBtnAtras();
        if (iCRMEdu.variblesGlobales.getHabilitarAcceso()) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        iCRMEdu.getiCRMEdu(this).removeCore2Listener(this);
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        if (!iCRMEdu.variblesGlobales.getBloqueoAcceso()) {
            super.onBackPressed();
        }
    }


}
