package com.consultoraestrategia.ss_portalalumno.base.dialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.base.fragment.*;
import com.consultoraestrategia.ss_portalalumno.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by @stevecampos on 28/02/2018.
 */

public abstract class BaseDialogFragment<V extends BaseView<P>, P extends BaseFragmentPresenter<V>, L> extends DialogFragment implements BaseView<P> {


    protected abstract String getLogTag();

    protected abstract P getPresenter();

    protected abstract V getBaseView();

    //protected abstract Bundle getExtrasInf();

    protected P presenter;

    protected abstract View inflateView(LayoutInflater inflater, ViewGroup container);

    protected abstract ViewGroup getRootLayout();

    protected abstract ProgressBar getProgressBar();

    public BaseDialogFragment() {
    }

    /*public static <T extends AppCompatActivity> Intent getStartIntent(Context context, Class<T> tClass) {
        return new Intent(context, tClass);
    }*/

    private ProgressBar progressBar;
    protected L listener;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        Log.d(getLogTag(), "onAttach");
        super.onAttach(context);
        listener = (L) getTargetFragment();
        if (listener != null) return;
        if (context instanceof BaseFragmentListener) {
            listener = (L) context;
        } else {
            throw new ClassCastException(
                    context.toString() + "must implement BaseFragmentListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(getLogTag(), "onCreate");
        super.onCreate(savedInstanceState);
        setupPresenter();
        if (presenter != null) presenter.onCreate();
        if (getArguments() != null) {
        }
    }
    private void setupPresenter() {
        if (presenter == null) {
            presenter = getPresenter();
        }
        setPresenter(presenter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getDialog().requestWindowFeature(STYLE_NO_TITLE);
        Log.d(getLogTag(), "onCreateView");
        if (presenter != null) presenter.onCreateView();
        View view =  inflateView(inflater, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(getLogTag(), "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        setupProgressBar();

        //setRetainInstance(true);
        if (presenter != null) presenter.setExtras(getArguments());
        if (presenter != null) presenter.onViewCreated();
    }


    private void setupProgressBar() {
        progressBar = getProgressBar();
    }

    @Override
    public void onStart() {
        Log.d(getLogTag(), "onResume");
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        super.onStart();
        if (presenter != null) presenter.onStart();
    }

    @Override
    public void onResume() {
        Log.d(getLogTag(), "onResume");
        super.onResume();
        try {
            if (presenter != null) presenter.onResume();
        }catch (Exception e){}
    }

    @Override
    public void onPause() {
        Log.d(getLogTag(), "onPause");
        super.onPause();
        if (presenter != null) presenter.onPause();
    }

    @Override
    public void onStop() {
        Log.d(getLogTag(), "onStop");
        super.onStop();
        if (presenter != null) presenter.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(getLogTag(), "onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
        if (presenter != null) presenter.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(getLogTag(), "onDestroy");
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
    }


    @Override
    public void onDetach() {
        Log.d(getLogTag(), "onDetach");
        listener = null;
        super.onDetach();
        if (presenter != null) presenter.onDetach();
    }


    @Override
    public void setPresenter(P presenter) {
        if (presenter != null) {
            presenter.attachView(getBaseView());
        }
    }

    public void showProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }


    public void showMessage(CharSequence error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void showImportantMessage(CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(R.string.dialog_title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void showFinalMessage(CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.global_btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        getActivity().finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Crea un Diálogo con una lista de selección simple
     */
    public void showListSingleChooser(String dialogTitle, final List<Object> items, int positionSelected) {
        if (items.isEmpty()) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int size = items.size();
        final CharSequence[] singleItems = new CharSequence[size];

        for (int i = 0; i < size; i++) {
            singleItems[i] = items.get(i).toString();
        }

        if (positionSelected >= items.size()) {
            positionSelected = -1;
        }

        builder.setTitle(dialogTitle)
                .setSingleChoiceItems(singleItems, positionSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(getTag(), "setSingleChoiceItems onClick i: " + which);
                    }
                })
                .setPositiveButton(R.string.global_btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(getTag(), "setPositiveButton onClick i: " + which);
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        if (selectedPosition != -1) {
                            Object objectSelected = items.get(selectedPosition);
                            if (presenter != null) {
                                presenter.onSingleItemSelected(objectSelected, selectedPosition);
                            }
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.global_btn_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(getTag(), "setNegativeButton onClick i: " + which);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}