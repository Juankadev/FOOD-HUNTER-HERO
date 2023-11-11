package com.example.ffh_rep.viewmodels.hunter;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.interfaces.CarritoActionsCallback;
import com.example.ffh_rep.views.adapters.ArticulosCarritoListAdapter;
import com.example.ffh_rep.views.ui.hunter.Hunter_MiCarrito;

import java.util.ArrayList;
import java.util.List;

public class CarritoViewModel extends ViewModel{

    private MutableLiveData<List<ItemCarrito>> carrito;
    private MutableLiveData<Integer> totArticulos;
    private MutableLiveData<Integer> puntos;
    private Comercio comercio;

    private List<ItemCarrito> temporalCarrito;
    private Context ctx;


    public CarritoViewModel(){
    }

    public CarritoViewModel(Context ctx){
        this.ctx = ctx;
        this.carrito = new MutableLiveData<>();
        this.totArticulos = new MutableLiveData<>(0);
        this.puntos = new MutableLiveData<>(0);
        this.temporalCarrito = new ArrayList<>();
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
        Integer _currTot = this.totArticulos.getValue(); //es 0

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
                if (item.getArtc().getId_stock() == article.getArtc().getId_stock()) {
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
                if (item.getArtc().getId_stock() == itemToModify.getArtc().getId_stock()) {
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

    public void addOneUnitToCart(ItemCarrito itemToModify, ArticulosCarritoListAdapter adapter) {
        List<ItemCarrito> _currCart = this.carrito.getValue();
        Integer _currTot = this.totArticulos.getValue();

        if (_currCart != null) {
            for (ItemCarrito item : _currCart) {
                if (item.getArtc().getId_stock() == itemToModify.getArtc().getId_stock()) {
                    int newQuantity = item.getCantidad() + 1;
                    item.setCantidad(newQuantity);
                    _currTot += 1;
                    break;
                }
            }
            this.carrito.setValue(_currCart);
            this.totArticulos.setValue(_currTot);
            recountPoints();
        }

        adapter.notifyDataSetChanged();
    }


    public void subtractUnitsFromCart(ItemCarrito itemToModify, ArticulosCarritoListAdapter adapter) {
        List<ItemCarrito> _currCart = this.carrito.getValue();
        Integer _currTot = this.totArticulos.getValue();

        if (_currCart != null) {
            for (ItemCarrito item : _currCart) {
                if (item.getArtc().getId_stock() == itemToModify.getArtc().getId_stock()) {
                    int newQuantity = item.getCantidad() - 1;
                    item.setCantidad(newQuantity);
                    _currTot -= 1;
                    break;
                }
            }
            this.carrito.setValue(_currCart);
            this.totArticulos.setValue(_currTot);
            recountPoints();
        }

        adapter.notifyDataSetChanged();
    }

    public void removeItemFromCart(ItemCarrito itemToRemove, ArticulosCarritoListAdapter adapter) {
        List<ItemCarrito> _currCart = this.carrito.getValue();
        Integer _currTot = this.totArticulos.getValue();
        if (_currCart != null) {
            for (ItemCarrito item : _currCart) {
                if (item.getArtc().getId_stock() == itemToRemove.getArtc().getId_stock()) {
                    _currTot -= item.getCantidad();
                    _currCart.remove(item);
                    break;
                }
            }
            this.carrito.setValue(_currCart);
            this.totArticulos.setValue(_currTot);
            recountPoints();
            adapter.notifyDataSetChanged();
        }
    }


    public void recountPoints(){
        int quantity = this.totArticulos.getValue();
        Log.d("QT", String.valueOf(quantity));
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
            setPuntos(100);
        }
        else{
            setPuntos(0);
        }
    }

    public void resetAttributes(){
        this.carrito.setValue(new ArrayList<>());
        this.totArticulos.setValue(0);
        this.puntos.setValue(0);
    }


    public void applyFilter(String _secuence) {
        if(temporalCarrito.isEmpty()){
            temporalCarrito  = carrito.getValue();
        }
        List<ItemCarrito> originalList = carrito.getValue();
        if (originalList != null) {
            if (_secuence.equals("") || _secuence.isEmpty() ) {
                carrito.postValue(temporalCarrito);
            } else {
                List<ItemCarrito> filtered = new ArrayList<>();
                for (ItemCarrito item : originalList) {
                    if (item.getArtc().getId_articulo().getDescripcion().toLowerCase().contains(_secuence.toLowerCase())) {
                        filtered.add(item);
                    }
                }
                carrito.postValue(filtered);
            }
        }
    }
}
