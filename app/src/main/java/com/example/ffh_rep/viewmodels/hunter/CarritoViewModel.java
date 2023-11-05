package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.ItemCarrito;

import java.util.ArrayList;
import java.util.List;

public class CarritoViewModel extends ViewModel{

    private MutableLiveData<List<ItemCarrito>> carrito;
    private MutableLiveData<Integer> totArticulos;
    private MutableLiveData<Integer> puntos;
    private Comercio comercio;
    private Context ctx;

    public CarritoViewModel(){
    }
    public CarritoViewModel(Context ctx){
        this.ctx = ctx;
        this.carrito = new MutableLiveData<>();
        this.totArticulos = new MutableLiveData<>(0);
        this.puntos = new MutableLiveData<>(0);
    }


    public void setComercio(Comercio comercio){
        this.comercio = comercio;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public MutableLiveData<List<ItemCarrito>> getCarrito() {
        return carrito;
    }

    public void addArticleToCart(ItemCarrito article){
        List<ItemCarrito> _currCart = this.carrito.getValue();
        Integer _currTot = this.totArticulos.getValue();
        if(_currCart == null){
            _currCart = new ArrayList<>();
        }
        _currCart.add(article);
        _currTot += article.getCantidad();
        this.carrito.setValue(_currCart);
        this.totArticulos.setValue(_currTot);
        recountPoints();
    }
    public void setCarrito(MutableLiveData<List<ItemCarrito>> carrito) {
        this.carrito = carrito;
    }

    public MutableLiveData<Integer> getTotArticulos() {
        return totArticulos;
    }

    public Integer getTotalIntegervalue(){
        return this.totArticulos.getValue();
    }

    public void setTotArticulos(MutableLiveData<Integer> totArticulos) {
        this.totArticulos = totArticulos;
    }

    public MutableLiveData<Integer> getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos.postValue(puntos);
    }

    public void clearChart(){
        List<ItemCarrito> curr = carrito.getValue();
        if(curr != null){
            curr.clear();
        }
        carrito.postValue(curr);
        this.totArticulos.postValue(0);
        this.puntos.postValue(0);
    }

    public boolean isArticleInCart(ItemCarrito article) {
        List<ItemCarrito> _currCart = this.carrito.getValue();
        if (_currCart != null) {
            for (ItemCarrito item : _currCart) {
                if (item.getArtc().getIdArticulo() == article.getArtc().getIdArticulo()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addMoreUnitsToCart(ItemCarrito itemToModify) {
        List<ItemCarrito> _currCart = this.carrito.getValue();
        Integer _currTot = this.totArticulos.getValue();

        if (_currCart != null) {
            for (ItemCarrito item : _currCart) {
                if (item.getArtc().getIdArticulo() == itemToModify.getArtc().getIdArticulo()) {
                    int newQuantity = item.getCantidad() + itemToModify.getCantidad();
                    item.setCantidad(newQuantity);
                    _currTot += itemToModify.getCantidad();
                    break;
                }
            }
            this.carrito.setValue(_currCart);
            this.totArticulos.setValue(_currTot);
            recountPoints();
        }
    }

    public void recountPoints(){
        int quantity = this.totArticulos.getValue();
        if(quantity >= 1 && quantity < 5){
            setPuntos(5);
        }
        else if(quantity >= 5 && quantity < 10){
            setPuntos(10);
        }
        else if(quantity >= 10 && quantity < 20){
            setPuntos(15);
        }
        else if(quantity >=20){
            setPuntos(15);
        }
        else{
            setPuntos(0);
        }
    }
}
