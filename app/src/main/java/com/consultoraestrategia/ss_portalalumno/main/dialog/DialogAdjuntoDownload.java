package com.consultoraestrategia.ss_portalalumno.main.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.MainPresenter;
import com.consultoraestrategia.ss_portalalumno.main.MainView;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.util.AdjuntoEventoBuild;
import com.consultoraestrategia.ss_portalalumno.util.blurdialogfragment.BlurDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DialogAdjuntoDownload extends BlurDialogFragment implements MainView.AdjuntoEventoDownload, AdjuntoEventoBuild.AdjuntoEventoAdapter.Listener {

    private MainPresenter presenter;
    private AdjuntoEventoBuild.AdjuntoEventoAdapter adjuntoEventoAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            this.getDialog().requestWindowFeature(STYLE_NO_TITLE);
            //this.getDialog().setCancelable(false);
        }
        //setCancelable(false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_clean_adjunto_download, null);
        Button btnAceptar = ((Button) view.findViewById(R.id.btn_aceptar));
        RecyclerView rc_recursos = ((RecyclerView) view.findViewById(R.id.rc_recursos));
        adjuntoEventoAdapter = new AdjuntoEventoBuild.AdjuntoEventoAdapter(new ArrayList<>(),this);
        rc_recursos.setAdapter(adjuntoEventoAdapter);
        rc_recursos.setLayoutManager(new LinearLayoutManager(rc_recursos.getContext()));

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });

        //label.setMovementMethod(LinkMovementMethod.getInstance());
        //Linkify.addLinks(label, Linkify.WEB_URLS);
        builder.setView(view);
        return builder.create();
    }

    @Override
    protected boolean isDebugEnable() {
        return false;
    }

    @Override
    protected boolean isDimmingEnable() {
        return false;
    }

    @Override
    protected boolean isActionBarBlurred() {
        return true;
    }

    @Override
    protected float getDownScaleFactor() {
        return 2;
    }

    @Override
    protected int getBlurRadius() {
        return 4;
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setList(List<EventoUi.AdjuntoUi> adjuntoUiList) {
        adjuntoEventoAdapter.setList(adjuntoUiList);
    }


    @Override
    public void onClickAdjunto(EventoUi.AdjuntoUi adjuntoUi) {
        presenter.onClickDialogAdjunto(adjuntoUi);
    }
}
