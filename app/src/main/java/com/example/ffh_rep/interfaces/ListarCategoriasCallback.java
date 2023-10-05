package com.example.ffh_rep.interfaces;

import com.example.ffh_rep.entidades.Categoria;

import java.util.List;

public interface ListarCategoriasCallback {
    void onCategoriasListadas(List<Categoria> categorias);
    void onListarCategoriasError(String mensajeError);
}