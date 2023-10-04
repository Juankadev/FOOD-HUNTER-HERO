package com.example.ffh_rep.interfaces;

public interface RegistrarUsuarioCallback {
    void onUsuarioInsertado(Integer userId);
    void onUsuarioError();
    void onCompleteInsert(String username, String password);

}
