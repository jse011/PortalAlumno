package com.consultoraestrategia.ss_portalalumno.evidencia.filterChooser;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.consultoraestrategia.ss_portalalumno.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by SCIEV on 16/05/2018.
 */

public class TipoEvidenciaDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private TipoEvidenciaDialogCallback callbackFilter;
    private CoordinatorLayout.Behavior behavior;
    private Unbinder unbinder;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {

        View contentView = View.inflate(getContext(), R.layout.dialog_seleccionar_archivo, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        behavior = params.getBehavior();
        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                double SLIDEOFFSETHIDEN = -0.9f;

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    switch (newState) {

                        case BottomSheetBehavior.STATE_COLLAPSED: {

                            Log.d("BSB", "collapsed");
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {

                            Log.d("BSB", "settling");
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {

                            Log.d("BSB", "expanded");
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {

                            Log.d("BSB", "hidden");
                        }
                        case BottomSheetBehavior.STATE_DRAGGING: {

                            Log.d("BSB", "dragging");
                        }
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    try {
                        if (SLIDEOFFSETHIDEN >= slideOffset) dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
        unbinder = ButterKnife.bind(this, contentView);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), getTheme());
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbackFilter = (TipoEvidenciaDialogCallback)getTargetFragment();
    }

    public void onStart() {
        super.onStart();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_add_link, R.id.btn_add_file, R.id.btn_camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_link:
                callbackFilter.onClickAddLink();
                break;
            case R.id.btn_add_file:
                callbackFilter.onClickAddFile();
                break;
            case R.id.btn_camera:
                callbackFilter.onClickCamera();
                break;
        }
        dismiss();
    }
}
