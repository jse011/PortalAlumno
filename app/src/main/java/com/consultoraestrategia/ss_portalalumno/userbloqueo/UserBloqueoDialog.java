package com.consultoraestrategia.ss_portalalumno.userbloqueo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.entities.Persona;
import com.consultoraestrategia.ss_portalalumno.entities.Persona_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.estadocuenta2.EstadoCuenta2Activity;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserBloqueoDialog extends DialogFragment {
    @BindView(R.id.imageView12)
    CircleImageView imageView12;
    @BindView(R.id.btn_atras)
    Button btnAtras;
    @BindView(R.id.btn_pago_linea)
    CardView btnPagoLinea;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bloqueo_user, container);
        unbinder = ButterKnife.bind(this, view);
        setupStyleDialog();
        setupImage();

        return view;
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
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setupStyleDialog() {
        try {

            getDialog().requestWindowFeature(STYLE_NO_TITLE);
            // make dialog itself transparent
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }

            // remove background dim
            getDialog().getWindow().setDimAmount(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_atras, R.id.btn_pago_linea})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_atras:
                dismiss();
                break;
            case R.id.btn_pago_linea:
                try {
                    int perosonaId = SessionUser.getCurrentUser().getPersonaId();
                    Persona persona = SQLite.select()
                            .from(Persona.class)
                            .where(Persona_Table.personaId.eq(perosonaId))
                            .querySingle();

                    EstadoCuenta2Activity.start(getContext(), persona.getPersonaId(), persona.getNumDoc());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
