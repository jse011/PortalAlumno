package com.consultoraestrategia.ss_portalalumno.pregunta.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.util.KeyboardUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Dialogkeyboard extends BottomSheetDialogFragment implements DialogkeyBoardView {

    @BindView(R.id.txt_titulo)
    TextView txtTitulo;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.btn_guardar)
    Button btnGuardar;
    @BindView(R.id.btn_cancelar)
    Button btnCancelar;
    private Unbinder unbinder;
    private CallBack listener;


    @Override
    public void setupDialog(Dialog dialog, int style) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
        bottomSheetDialog.setContentView(R.layout.dialog_keyboard);
        unbinder = ButterKnife.bind(this, dialog.getWindow().getDecorView());
        editText.requestFocus(); //Asegurar que editText tiene focus
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

        try {
            Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
            behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        txtTitulo.setText("Ingresa una Respuesta");

        listener.onCreateDialogKeyBoard(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogKeyBoardStyle);
    }


    @Override
    public void onDismiss(DialogInterface dialog)
    {
        editText.clearFocus();
        Log.d(getClass().getSimpleName(), "hideKeyboard");
        KeyboardUtils.hideKeyboard(editText.getContext(), editText);
        listener.onDismissDialogkeyboard();
        super.onDismiss(dialog);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                FrameLayout bottomSheet = (FrameLayout)
                        dialog.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setPeekHeight(0);
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallBack) {
            listener = (CallBack) context;
        }else {
            throw new ClassCastException(
                    context.toString() + "must implement CallBack");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_guardar, R.id.btn_cancelar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_guardar:
                listener.onClickAceptarDialogkeyboard(editText.getText().toString());
                //dismiss();
                break;
            case R.id.btn_cancelar:
                dismiss();
                break;
        }
    }

    public interface CallBack{
        void onClickAceptarDialogkeyboard(String contenido);

        void onDismissDialogkeyboard();

        void onCreateDialogKeyBoard(DialogkeyBoardView view);

    }

    @Override
    public void dialogDissmit() {
        dismiss();
    }

    @Override
    public void errorText() {
        editText.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void setColorButons(String color1, String color2) {
        try {
            btnGuardar.setTextColor(Color.parseColor(color2));
            btnCancelar.setTextColor(Color.parseColor(color2));
            ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(color2));
            ViewCompat.setBackgroundTintList(editText, colorStateList);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void setRespuesta(String contenido) {
        editText.setText(contenido);
    }

}
