package com.consultoraestrategia.ss_portalalumno.evidencia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragment;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentListener;
import com.consultoraestrategia.ss_portalalumno.evidencia.adapter.EvidenciaSesAdapter;
import com.consultoraestrategia.ss_portalalumno.evidencia.data.source.EvidenciaRepository;
import com.consultoraestrategia.ss_portalalumno.evidencia.data.source.EvidenciaRepositoryImpl;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;
import com.consultoraestrategia.ss_portalalumno.evidencia.filterChooser.TipoEvidenciaDialog;
import com.consultoraestrategia.ss_portalalumno.evidencia.filterChooser.TipoEvidenciaDialogCallback;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.DeleteArchivoStorageFB;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.EntregarSesEvidenciaFB;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.GetEvidenciaSesArchivosUi;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.IsEntregado;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.UpdateEvidenciaSesion;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.UploadArchivoStorageFB;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.UploadLinkFB;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.previewDrive.PreviewArchivoActivity;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionEvidenciaView;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.filterChooser.FilterChooserBottomSheetDialog;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EvidenciaFragment extends BaseFragment<EvidenciaView, EvidenciaPresenter, BaseFragmentListener> implements EvidenciaView, EvidenciaSesAdapter.Listener, TabSesionEvidenciaView, TipoEvidenciaDialogCallback {
    private final static int REQUEST_CODE_DOC_Q = 2312;
    private static final int REQUEST_CODE_IMAGE_Q = 2314;
    @BindView(R.id.txt_titulo_tu_trabajo)
    TextView txtTituloTuTrabajo;
    @BindView(R.id.txt_sin_entregar)
    TextView txtSinEntregar;
    @BindView(R.id.txt_entrgado)
    TextView txtEntrgado;
    @BindView(R.id.cont_entrgado)
    CardView contEntrgado;
    @BindView(R.id.rc_adjunto)
    RecyclerView rcAdjunto;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.btn_adjuntar)
    ConstraintLayout btnAdjuntar;
    @BindView(R.id.txt_anular_entrega)
    TextView txtAnularEntrega;
    @BindView(R.id.tipo_anular_entrega)
    CardView tipoAnularEntrega;
    @BindView(R.id.tipo_entrega)
    TextView tipoEntrega;
    @BindView(R.id.btn_entregar)
    CardView btnEntregar;
    @BindView(R.id.conten_entregar_archivo)
    NestedScrollView contenEntregarArchivo;
    @BindView(R.id.progressBar9)
    ProgressBar progressBar9;
    private EvidenciaSesAdapter adapter;

    @Override
    protected String getLogTag() {
        return "EvidenciaTag";
    }

    @Override
    protected EvidenciaPresenter getPresenter() {
        EvidenciaRepository evidenciaRepository = new EvidenciaRepositoryImpl();

        return new EvidenciaPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetEvidenciaSesArchivosUi(evidenciaRepository),
                new UpdateEvidenciaSesion(evidenciaRepository),
                new IsEntregado(evidenciaRepository),
                new EntregarSesEvidenciaFB(evidenciaRepository),
                new UploadArchivoStorageFB(evidenciaRepository),
                new UploadLinkFB(evidenciaRepository),
                new DeleteArchivoStorageFB(evidenciaRepository),
                new AndroidOnlineImpl(getContext()));
    }

    @Override
    protected EvidenciaView getBaseView() {
        return this;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_evidencia_sesion, container, false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ;
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        adapter = new EvidenciaSesAdapter(this);
        rcAdjunto.setAdapter(adapter);
        rcAdjunto.setLayoutManager(new LinearLayoutManager(getContext()));
        rcAdjunto.setHasFixedSize(false);
        rcAdjunto.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator) rcAdjunto.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    protected ViewGroup getRootLayout() {
        return contenEntregarArchivo;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progressBar9;
    }

    @Override
    public void showImportantMessage(CharSequence message) {

    }

    @Override
    public void showFinalMessage(CharSequence message) {

    }

    @Override
    public void showListSingleChooser(String title, List<Object> items, int positionSelected) {

    }

    @Override
    public void showFinalMessageAceptCancel(CharSequence message, CharSequence messageTitle) {

    }

    @Override
    public void onClickActionArchivo(ArchivoSesEvidenciaUi evidenciaUi) {
        presenter.onClickActionArchivo(evidenciaUi);
    }

    @Override
    public void onClickRemoverchivo(ArchivoSesEvidenciaUi evidenciaUi) {
        presenter.onClickRemoverchivo(evidenciaUi);
    }

    @Override
    public void onClickOpenArchivo(ArchivoSesEvidenciaUi evidenciaUi) {
        presenter.onClickOpenArchivo(evidenciaUi);
    }

    @Override
    public void setListEvidencia(List<ArchivoSesEvidenciaUi> archivoSesEvidenciaUiList) {
        adapter.setList(archivoSesEvidenciaUiList);
    }

    @Override
    public void changeFechaEntrega(boolean entregaAlumno, boolean retrasoEntrega) {
        if (entregaAlumno) {
            contEntrgado.setVisibility(View.VISIBLE);
            txtSinEntregar.setVisibility(View.GONE);
            tipoAnularEntrega.setVisibility(View.VISIBLE);
            tipoEntrega.setVisibility(View.GONE);
            btnAdjuntar.setVisibility(View.GONE);
            if (retrasoEntrega) {
                txtEntrgado.setText("Entregado con retraso");
            } else {
                txtEntrgado.setText("Entregado");
            }
        } else {
            contEntrgado.setVisibility(View.GONE);
            txtSinEntregar.setVisibility(View.VISIBLE);
            tipoAnularEntrega.setVisibility(View.GONE);
            tipoEntrega.setVisibility(View.VISIBLE);
            btnAdjuntar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void update(ArchivoSesEvidenciaUi evidenciaSesionUi) {
        adapter.update(evidenciaSesionUi);
    }

    @Override
    public void diabledButtons() {
        btnAdjuntar.setVisibility(View.GONE);
    }

    @Override
    public void openCamera(ArrayList<String> photoSelected) {
        Options options = Options.init()
                .setCount(6)                                                   //Number of images to restict selection count
                .setFrontfacing(false);                                      //Front Facing camera on start
        if (photoSelected != null && !photoSelected.isEmpty()) {
            options
                    .setPreSelectedUrls(photoSelected);                             //Pre selected Image Urls
        }
        options
                .setExcludeVideos(true)                                         //Option to exclude videos
                //.setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath(getResources().getString(R.string.app_name) + "/images")//Custom Path For media Storage
                .setRequestCode(REQUEST_CODE_IMAGE_Q);                            //Request code for activity results

        Pix.start(this, options);
    }

    @Override
    public void addTareaArchivo(ArchivoSesEvidenciaUi archivoSesEvidenciaUi) {
        adapter.add(archivoSesEvidenciaUi);
    }

    @Override
    public void remove(ArchivoSesEvidenciaUi tareaArchivoUi) {
        adapter.remove(tareaArchivoUi);
    }

    @Override
    public void onShowPickDoc() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent();
        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_DOC_Q);
    }

    @Override
    public void showPreviewArchivo() {
        startActivity(new Intent(getContext(), PreviewArchivoActivity.class));
    }

    @Override
    public void showVinculo(String path) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(path)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al abrir el vínculo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTema(String color1, String color2, String color3) {
        //txtLinea.setBackgroundColor(Color.parseColor(color1));
        txtTituloTuTrabajo.setTextColor(Color.parseColor(color2));
        //comentarioClase.mutate().setColorFilter(Color.parseColor(color2), PorterDuff.Mode.SRC_ATOP);
        //imgComentarioClase.setImageDrawable(comentarioClase);
        //txtComentarioClase.setTextColor(Color.parseColor(color2));
        //txtTituloTarea.setTextColor(Color.parseColor(color2));
        //cardView.setCardBackgroundColor(Color.parseColor(color1));
        //txtSinEntregar.setTextColor(Color.parseColor(color3));
        btnEntregar.setCardBackgroundColor(Color.parseColor(color1));
        contEntrgado.setCardBackgroundColor(Color.parseColor(color1));
        txtAnularEntrega.setTextColor(Color.parseColor(color1));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void changeList() {
        if (presenter != null) presenter.notifyChangeFragment();
    }

    @OnClick({R.id.btn_adjuntar, R.id.btn_entregar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_adjuntar:
                TipoEvidenciaDialog filterChooserBottomSheetDialog = new TipoEvidenciaDialog();
                filterChooserBottomSheetDialog.setTargetFragment(this, 101);
                filterChooserBottomSheetDialog.show(getParentFragmentManager(), "TipoEvidenciaDialog");
                break;
            case R.id.btn_entregar:
                presenter.onBtnEntregarClicked();
                break;
        }
    }

    @Override
    public void onClickAddLink() {
        showDialogAddLink();
    }

    private void showDialogAddLink() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View view = li.inflate(R.layout.alert_dialog_evidencia_add_link, null);
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(getContext());
        aBuilder.setView(view);

        EditText editText = (EditText)view.findViewById(R.id.edi_link);
        EditText editTextDescrip = (EditText)view.findViewById(R.id.edi_nombre);

        aBuilder.setCancelable(false)
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText!=null){
                    String desripcion = editTextDescrip.getText().toString();
                    String vinculo = editText.getText().toString();
                    if(TextUtils.isEmpty(desripcion)){
                        Toast.makeText(editText.getContext(), "Ingrese un título valido", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(vinculo)||!isURL(vinculo)){
                        Toast.makeText(editText.getContext(), "Ingrese un vínculo valido", Toast.LENGTH_SHORT).show();
                    }else {
                        presenter.onClickAddLink(desripcion, vinculo);
                        if(alertDialog!=null)alertDialog.dismiss();
                    }
                }else {
                    if(alertDialog!=null)alertDialog.dismiss();
                }
            }
        });
    }

    public boolean isURL(String text) {
        if (!text.startsWith("http")) {
            return false;
        }
        return true;
    }

    @Override
    public void onClickAddFile() {
        presenter.onClickAddFile();
    }

    @Override
    public void onClickCamera() {
        presenter.onClickCamera();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_Q) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            presenter.onResultCamara(returnValue);
        }else if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_DOC_Q){
            Uri uri = data.getData();
            presenter.onResultDoc(uri, getNombre(uri, getContext()));
        }
    }

    public static String getNombre(Uri uri, Context context) {
        String displayName = null;
        try {
            Cursor cursor = context.getContentResolver()
                    .query(uri, null, null, null, null, null);
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null) {
                if(cursor.moveToFirst()){
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
                cursor.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return displayName;
    }
}
