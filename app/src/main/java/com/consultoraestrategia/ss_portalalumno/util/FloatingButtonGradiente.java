package com.consultoraestrategia.ss_portalalumno.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.consultoraestrategia.ss_portalalumno.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FloatingButtonGradiente extends FloatingActionButton {
    Paint paint = new Paint();
    private LinearGradient shader;
    private Bitmap myLogo;
    private Bitmap bmp;
    private Tipo tipo;
    public enum Tipo{
        EDUCAR, PADREMENTOR
    }


    public FloatingButtonGradiente(@NonNull Context context) {
        super(context);
        init();
    }

    public FloatingButtonGradiente(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatingButtonGradiente(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        myLogo = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_add_white);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int xPos = (getWidth() / 2);
        int yPos = (int) ((getHeight() / 2)) ;
        int radius = getHeight()/2;

        if(shader==null)
            shader = new LinearGradient(0, 0, getWidth(), getHeight() , Color.parseColor("#173766"), Color.parseColor("#0277BD"), Shader.TileMode.CLAMP);
        paint.setShader(shader);

        canvas.drawCircle(xPos, yPos, radius , paint);
        /*
        if(tipo!=null){
            int idDrawable;
            float dimension = 0;
            float reajustar = 0;
            switch (tipo){
                case EDUCAR:
                    idDrawable = R.drawable.ic_logo_educar;
                    dimension = 2;
                    break;
                case PADREMENTOR:
                    idDrawable = R.drawable.ic_logo_padre_mentor;
                    dimension = 1.5f;
                    reajustar = ((paint.descent() + paint.ascent()) / 2);
                    break;
                default:
                    idDrawable = R.drawable.ic_logo_padre_mentor;
                    dimension = 1.5f;
                    reajustar = ((paint.descent() + paint.ascent()) / 2);
                    break;

            }
            if(bmp==null){
                Bitmap bitmap = getBitmap(getContext(), idDrawable);
                if(bitmap!=null){
                    bmp =getResizedBitmap(bitmap
                            , (int)((float)getWidth()/dimension), (int)((float)getHeight()/dimension));
                }
            }

            if(bmp!=null){
                //canvas.drawBitmap(bmp, xPos, yPos, null);
                int left = getWidth() /2-bmp.getWidth()/2;
                int top = (int) (getHeight()/2-bmp.getHeight()/2 - reajustar);
                canvas.drawBitmap(bmp, left, top, null);
            }
        }
        */

    }

    public void setTipoIcono(Tipo tipo){
        this.tipo = tipo;
        requestLayout();
    }

    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
