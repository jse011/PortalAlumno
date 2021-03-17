package com.consultoraestrategia.ss_portalalumno.main.nuevaVersion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NuevaVersionDisponible extends DialogFragment {


    Unbinder unbinder;
    @BindView(R.id.textView164)
    TextView textView164;
    @BindView(R.id.tvVersionCode)
    TextView tvVersionCode;
    @BindView(R.id.tvChanges)
    TextView tvChanges;
    @BindView(R.id.lnChangesInfo)
    LinearLayout lnChangesInfo;
    @BindView(R.id.linearLayout12)
    LinearLayout linearLayout12;
    @BindView(R.id.btn_actualizar)
    Button btnActualizar;
    @BindView(R.id.btn_cancelar)
    Button btnCancelar;
    @BindView(R.id.img_icon)
    ImageView imgIcon;

    public NuevaVersionDisponible() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            this.getDialog().requestWindowFeature(STYLE_NO_TITLE);
            this.getDialog().setCancelable(false);
        }
        setCancelable(false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSimpleDialog();
    }

    /**
     * Crea un diálogo de alerta sencillo
     *
     * @return Nuevo diálogo
     */
    public AlertDialog createSimpleDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.gpvch_layout_dialog_app, null);
        unbinder = ButterKnife.bind(this, view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        String newVersionCode = getArguments().getString("NewVersionCode", "");
        String lastChanges = getArguments().getString("Changes", "");
        tvVersionCode.setText(getString(R.string.app_name) + ":" + newVersionCode);
        if (!TextUtils.isEmpty(lastChanges)) {
            tvChanges.setText(lastChanges);
        } else {
            lnChangesInfo.setVisibility(View.GONE);
        }

        if (getResources().getString(R.string.app_name).equals("Educar Student")) {
            Glide.with(imgIcon)
                    .load(R.drawable.students)
                    .into(imgIcon);
        } else {
           // Glide.with(imgIcon)
             //       .load(R.drawable.icon_padre_mentor)
               //     .into(imgIcon);
        }
        return builder.create();
    }

    public static NuevaVersionDisponible newInstance(String NewVersionCode, String Changes) {
        NuevaVersionDisponible frag = new NuevaVersionDisponible();
        Bundle args = new Bundle();
        args.putString("Changes", Changes);
        args.putString("NewVersionCode", NewVersionCode);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_actualizar, R.id.btn_cancelar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_actualizar:
                openGooglePlay();
                break;
            case R.id.btn_cancelar:
                dismiss();
                break;
        }
    }

    private void openGooglePlay() {
        String packageName = getContext().getApplicationContext().getPackageName();
        String url = getString(R.string.gpvch_google_play_url) + packageName;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
