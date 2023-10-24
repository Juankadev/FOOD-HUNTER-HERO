package com.example.ffh_rep.interfaces;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Usuario;

public interface LoginUsuarioCallback {
    void onSuccessLogin(Usuario user);

    void onSuccessLoginHunter(Hunter hunter);
    void onSuccessLoginCommerce(Comercio commerce);

    void doGetUserDescriptionByRole(Usuario user);
}


