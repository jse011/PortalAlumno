package com.consultoraestrategia.ss_portalalumno.base;

/**
 * Created by @stevecampos on 10/11/2017.
 */

public interface BaseFragmentPresenter<T extends BaseView> extends BasePresenter<T> {
    void onAttach();

    void onCreateView();

    void onViewCreated();

    void onActivityCreated();

    void onDestroyView();

    void onDetach();
}
