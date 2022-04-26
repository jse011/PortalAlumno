package com.consultoraestrategia.ss_portalalumno.main.tab;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.MainPresenter;
import com.consultoraestrategia.ss_portalalumno.main.MainView;
import com.consultoraestrategia.ss_portalalumno.main.adapter.FamiliaAdapter;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.util.QRGEncoderRemoveMargin;
import com.consultoraestrategia.ss_portalalumno.util.RoundedView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TabQRFragment extends Fragment implements MainView.TabQR, FamiliaAdapter.Callback {
    private Unbinder unbinder;

    @BindView(R.id.frameText)
    ProgressBar qrCodeTextView;
    @BindView(R.id.QRCodeImg)
    ImageView qrCodeImageView;
    @BindView(R.id.img_entidad)
    ImageView imgEntidad;
    @BindView(R.id.txt_entidad)
    TextView txtEntidad;
    @BindView(R.id.text_alumno)
    TextView textAlumno;
    @BindView(R.id.foto_alumno)
    ImageView fotoAlumno;
    @BindView(R.id.imageView20)
    ImageView imageView20;
    @BindView(R.id.programa)
    TextView programa;
    @BindView(R.id.card_entidad_imagen)
    CardView cardEntidadImagen;
    @BindView(R.id.card_entidad_texto)
    CardView cardEntidadTexto;
    @BindView(R.id.card_linea1)
    CardView cardLinea1;
    @BindView(R.id.card_linea2)
    CardView cardLinea2;
    @BindView(R.id.card_linea3)
    CardView cardLinea3;
    @BindView(R.id.constraintLayout7)
    ConstraintLayout constraintLayout7;

    private MainPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_qr, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter=null;
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showQR(String data) {
        // Initialize multi format writer
       /* MultiFormatWriter writer = new MultiFormatWriter();

        // Initialize bit matrix
        try {
            BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, 250, 250);

            // Initialize barcode encoder
            BarcodeEncoder encoder = new BarcodeEncoder();

            // Initialize Bitmap
            Bitmap bitmap = encoder.createBitmap(matrix);

            //set bitmap on image view
            qrCodeImageView.setImageBitmap(bitmap);

            qrCodeTextView.setVisibility(View.GONE);

        } catch (WriterException e) {
            e.printStackTrace();
        }*/
        imageView20.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(imageView20.getContext())
                .load(R.drawable.gorro)
                .into(imageView20);

       if(!TextUtils.isEmpty(data)){
           QRGEncoderRemoveMargin qrgEncoder = new QRGEncoderRemoveMargin(data, null, QRGContents.Type.TEXT, 200);
           qrgEncoder.setColorBlack(Color.BLACK);
           qrgEncoder.setColorWhite(Color.TRANSPARENT);

           try {
               Bitmap original = qrgEncoder.getBitmap();
               ByteArrayOutputStream out = new ByteArrayOutputStream();
               original.compress(Bitmap.CompressFormat.PNG, 100, out);
               Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

               // Getting QR-Code as Bitmap

               qrCodeImageView.setScaleType(ImageView.ScaleType.FIT_XY);
               Glide.with(qrCodeImageView.getContext())
                       .load(decoded)
                       .into(qrCodeImageView);
               // Setting Bitmap to ImageView
               qrCodeTextView.setVisibility(View.GONE);
           } catch (Exception e) {
               e.printStackTrace();
           }

       }else {
           qrCodeImageView.setImageDrawable(null);
       }

    }

    @Override
    public void tarjetaUsuario(AlumnoUi alumnoUi, ProgramaEduactivoUI programaEducativo) {

    }

    @Override
    public void logoEntidad(AnioAcademicoUi anioAcademicoUi) {
        imgEntidad.post(new Runnable() {
            @Override
            public void run() {

                if(anioAcademicoUi!=null){
                    String fotoEntidad = anioAcademicoUi.getFotoEntidad();
                    if(!TextUtils.isEmpty(fotoEntidad)){
                        fotoEntidad = fotoEntidad.replace(".png", "-wmark.png");

                    }

                    Glide.with(imgEntidad.getContext())
                            .load(fotoEntidad)
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(imgEntidad);
                    txtEntidad.setText(anioAcademicoUi.getNombreGeoref());
                }else {
                    imgEntidad.setImageDrawable(null);
                    txtEntidad.setText("");
                }


            }
        });

    }

    @Override
    public void fotoAlumno(AlumnoUi alumnoUi) {
        if(alumnoUi!=null){
            Glide.with(fotoAlumno.getContext())
                    .load(alumnoUi.getFoto())
                    .apply(new RequestOptions()
                            .centerInside()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(fotoAlumno);
            textAlumno.setText(alumnoUi.getNombre2());
        }else {
            fotoAlumno.setImageDrawable(null);
            textAlumno.setText("");
        }
    }

    @Override
    public void programaEval(ProgramaEduactivoUI programaEduactivoUI) {
        programa.setText(programaEduactivoUI.getPeriodoyseccion());
    }

    @Override
    public void colorTarjetaQr(String colorTarjetaQr) {
        int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        try {
            if(!TextUtils.isEmpty(colorTarjetaQr)){
                color = Color.parseColor(colorTarjetaQr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Drawable background = constraintLayout7.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(color);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(color);
        }

        cardEntidadImagen.setCardBackgroundColor(color);
        cardEntidadTexto.setCardBackgroundColor(color);
        cardLinea1.setCardBackgroundColor(color);
        cardLinea2.setCardBackgroundColor(color);
        cardLinea3.setCardBackgroundColor(color);
        textAlumno.setTextColor(color);
        programa.setTextColor(color);
    }

    @Override
    public void onClickTooglePersona(Object personUi) {
        presenter.onClickTooglePersona(personUi);
    }

}

