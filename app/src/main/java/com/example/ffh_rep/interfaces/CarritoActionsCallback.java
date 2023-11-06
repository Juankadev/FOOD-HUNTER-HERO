package com.example.ffh_rep.interfaces;

import com.example.ffh_rep.entidades.ItemCarrito;

public interface CarritoActionsCallback {
    void onSumArticuloCallback(ItemCarrito item);
    void onAbsArticuloCallback(ItemCarrito item);
    void onDeleteArticuloCallback(ItemCarrito item);
}
