package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.component;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import com.consultoraestrategia.ss_portalalumno.R;


/**
 * Created by Huo Chhunleng on 8/20/2016.
 */
public class PEditText extends AppCompatEditText {

    private Drawable btn_clear = ResourcesCompat.getDrawable(getResources(), R.drawable.button_delete, null);

    public PEditText(Context context) {
        super(context);
        init();
    }

    public PEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {

        btn_clear.setBounds(0, 0, btn_clear.getIntrinsicWidth(), btn_clear.getIntrinsicHeight());
        final PEditText editText = PEditText.this;

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editText.getCompoundDrawables()[2] == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > editText.getWidth() - editText.getPaddingRight() - btn_clear.getIntrinsicWidth()) {
                    editText.setText("");
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], null, editText.getCompoundDrawables()[3]);
                }
                return false;
            }
        });

        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!"".equals(editText.getText().toString())){
                        editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], btn_clear, editText.getCompoundDrawables()[3]);
                    }
                }else {
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], null, editText.getCompoundDrawables()[3]);
                }
            }
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().equals("")) {
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], null, editText.getCompoundDrawables()[3]);
                }
                else {
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], btn_clear, editText.getCompoundDrawables()[3]);
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
    }

    //intercept Typeface change and set it with our custom font
    /*public void setTypeface(Typeface tf, int style) {
        if (style == Typeface.BOLD) {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Vegur-B 0.602.otf"));
        } else {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Vegur-R 0.602.otf"));
        }
    }*/
}