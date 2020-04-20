package com.consultoraestrategia.ss_portalalumno.login2.domain.useCase;

import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepository;

public class SaveUrlSevidorLocal {
    private LoginDataRepository loginDataRepository;

    public SaveUrlSevidorLocal(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }
    public void execute(String url){
        loginDataRepository.saveUrlServidorLocal(url);
    }
}
