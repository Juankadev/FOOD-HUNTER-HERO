package com.example.ffh_rep.interfaces;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;

public interface RegistrarUsuarioCallback {
    void onUsuarioInsertado(Integer userId);
    void onUsuarioError();
    void onCompleteInsert(String username, String password);

    void onCompleteInsertAndRedirectToApp(Hunter hunter);

}
