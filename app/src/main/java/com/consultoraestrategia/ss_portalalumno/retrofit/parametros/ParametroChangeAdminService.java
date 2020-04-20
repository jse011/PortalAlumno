package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;

public class ParametroChangeAdminService extends ApiRetrofit.Parameters {

    private UsuarioAdminService vobj_Usuario;


    public ParametroChangeAdminService(UsuarioAdminService vobj_Usuario) {
        this.vobj_Usuario = vobj_Usuario;
    }

    public UsuarioAdminService getVobj_Usuario() {
        return vobj_Usuario;
    }

    public void setVobj_Usuario(UsuarioAdminService vobj_Usuario) {
        this.vobj_Usuario = vobj_Usuario;
    }
}
