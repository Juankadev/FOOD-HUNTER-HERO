package com.example.ffh_rep.interfaces;

import com.example.ffh_rep.entidades.Marca;

import java.util.List;

public interface ListarMarcasCallback {
    void onMarcasListadas(List<Marca> marcas);
    void onListarMarcasError(String mensajeError);
}