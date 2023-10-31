package com.example.ffh_rep.ui.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.factory.CarritoViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class CarritoViewModel extends ViewModel{

    private MutableLiveData<List<ItemCarrito>> carrito;
    private MutableLiveData<Integer> totArticulos;
    private Context ctx;

    public CarritoViewModel(){
    }
    public CarritoViewModel(Context ctx){
        this.ctx = ctx;
        this.carrito = new MutableLiveData<>();
        this.totArticulos = new MutableLiveData<>(0);
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
        _currTot++;
        this.carrito.setValue(_currCart);
        this.totArticulos.setValue(_currTot);
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

    public void clearChart(){
        List<ItemCarrito> curr = carrito.getValue();
        if(curr != null){
            curr.clear();
        }
        carrito.postValue(curr);
        this.totArticulos.postValue(0);
    }

    public boolean isArticleInCart(ItemCarrito article) {
        List<ItemCarrito> _currCart = this.carrito.getValue();
        if (_currCart != null) {
            for (ItemCarrito item : _currCart) {
                if (item.getArtc().equals(article.getArtc())) {
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
                if (item.equals(itemToModify)) {
                    int newQuantity = item.getCantidad() + itemToModify.getCantidad();
                    item.setCantidad(newQuantity);
                    _currTot += itemToModify.getCantidad();
                    break;
                }
            }
            this.carrito.setValue(_currCart);
            this.totArticulos.setValue(_currTot);
        }
    }
}
