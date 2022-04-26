package com.consultoraestrategia.ss_portalalumno.login2.principal.usuario;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;
import com.consultoraestrategia.ss_portalalumno.util.CustomHint;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UsuarioFragment extends Fragment implements UsuarioView, TextView.OnEditorActionListener {

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.edittext_username)
    TextInputEditText edittextUsername;
    @BindView(R.id.edittext_password_user)
    TextInputEditText edittextPasswordUser;
    @BindView(R.id.btn_siguiente_user)
    Button btnSiguienteUser;
    @BindView(R.id.imageView17)
    ImageView imageView17;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.textView89)
    TextView textView89;
    @BindView(R.id.textView90)
    TextView textView90;
    @BindView(R.id.til_passname)
    TextInputLayout tilPassname;
    @BindView(R.id.til_username)
    TextInputLayout tilUsername;
    @BindView(R.id.btn_atras_lst_usu)
    ImageView btnAtrasLstUsu;
    private Unbinder unbinder;
    private Login2Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_usuario, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ColorStateList colorStateList;
        if(getResources().getString(R.string.app_name).equals("Educar Student")){
            colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorEducarStudent));
            Glide.with(imageView17)
                    .load(R.drawable.educar_d_login)
                    .into(imageView17);
            edittextPasswordUser.setOnEditorActionListener(this);
            textView89.setVisibility(View.GONE);
            imageView7.setVisibility(View.VISIBLE);
            textView90.setText("Centro de Aprendizaje Virtual");
            textView90.setTextColor(ContextCompat.getColor(getContext(), R.color.colorEducarStudent));
            //edittextUsername.setTextColor(ContextCompat.getColor(getContext(), R.color.colorEvaStudent));
            btnSiguienteUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_login_educar));
        }else{
            colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorEvaStudent));
            Glide.with(imageView17)
                    .load(R.drawable.icrm_d_login)
                    .into(imageView17);
            textView89.setVisibility(View.GONE);
            edittextPasswordUser.setOnEditorActionListener(this);
            imageView7.setVisibility(View.INVISIBLE);
            textView90.setText("Social iCRM Educativo Móvil");
            textView90.setTextColor(ContextCompat.getColor(getContext(), R.color.colorEvaStudent));
            btnSiguienteUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_login));
            //edittextUsername.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDocenteMentor));
        }
        //ViewCompat.setBackgroundTintList(tilPassname, colorStateList);
        //ViewCompat.setBackgroundTintList(tilUsername, colorStateList);
        /* Here you get int representation of an HTML color resources */

        Glide.with(imageView17)
                .load(R.drawable.docente_mentor)
                .into(imageView7);
        textView89.setVisibility(View.GONE);
        imageView7.setVisibility(View.VISIBLE);

        //   initAdapter();
    }

    @Override
    public void onAttach(Login2Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onErrorPassword(String error) {
        edittextPasswordUser.requestFocus();
        edittextPasswordUser.setSelected(true);
        edittextPasswordUser.setError(error);
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void hideAtras() {
        btnAtrasLstUsu.setVisibility(View.GONE);
    }

    @Override
    public void showAtras() {
        btnAtrasLstUsu.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCredencialesIncorrectos(String error) {
        edittextUsername.requestFocus();
        edittextUsername.setSelected(true);
        edittextUsername.setError(error);
    }

    @Override
    public void setUsuario(String usuario) {
        edittextUsername.setText(usuario);
    }

    @Override
    public void setPassword(String password) {
        edittextPasswordUser.setText(password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            int i = textView.getId();
            if (i == R.id.edittext_password_user) {
                String usuario = edittextUsername.getText()==null?"":edittextUsername.getText().toString();
                String password = edittextPasswordUser.getText()==null?"":edittextPasswordUser.getText().toString();
                presenter.onClickUsuarioSiguiente(usuario,password);
            }
            handled = true;
        }
        return handled;

    }

    @OnClick({R.id.btn_siguiente_user, R.id.btn_atras_lst_usu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_siguiente_user:
                String usuario = edittextUsername.getText()==null?"":edittextUsername.getText().toString();
                String password = edittextPasswordUser.getText()==null?"":edittextPasswordUser.getText().toString();
                presenter.onClickUsuarioSiguiente(usuario,password);
                break;
            case R.id.btn_atras_lst_usu:
                presenter.onClickUsuarioAtras();
                break;
        }
    }
}
