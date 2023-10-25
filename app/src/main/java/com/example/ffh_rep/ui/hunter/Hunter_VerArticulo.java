package com.example.ffh_rep.ui.hunter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterVerArticuloBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.factory.CarritoViewModelFactory;

public class Hunter_VerArticulo extends Fragment {

    private HunterVerArticuloViewModel mViewModel;
    private CarritoViewModel carrito;
    private Articulo article;
    private FragmentHunterVerArticuloBinding binding;
    private TextView descripcion, precio, categoria, marca, cantidadArticulo;
    private Button btnAniadirCarrito, btnSumar, btnRestar;
    private ItemCarrito item;

    private int _cantidadArticulo;
    public static Hunter_VerArticulo newInstance() {
        return new Hunter_VerArticulo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding  = FragmentHunterVerArticuloBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        _cantidadArticulo = 0;

        initComponentes(view);


        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey("articuloSelected")){
                article = (Articulo) bundle.getSerializable("articuloSelected");
            }
        }

        settingComponents(article);
        setUpListeners(article);


       return view;
    }

    public void initComponentes(View view){
        carrito = new ViewModelProvider(requireActivity(), new CarritoViewModelFactory(getActivity())).get(CarritoViewModel.class);
        descripcion = view.findViewById(R.id.tvDescripcionArt);
        precio = view.findViewById(R.id.tvPrecioArt);
        marca = view.findViewById(R.id.tvMarcaArt);
        categoria = view.findViewById(R.id.tvCategoriaArt);
        cantidadArticulo = view.findViewById(R.id.tv_cantidadItem);
        btnAniadirCarrito = view.findViewById(R.id.btnAgregarCarrito);
        btnSumar = view.findViewById(R.id.btnSumarItem);
        btnRestar = view.findViewById(R.id.btnRestarItem);
    }

    public void settingComponents(Articulo article){
        precio.setText(String.valueOf(article.getPrecio()));
        descripcion.setText(article.getDescripcion());
        marca.setText(article.getMarca().getDescripcion());
        categoria.setText(article.getCategoria().getDescripcion());
    }

    public void setUpListeners(Articulo article){

        btnAniadirCarrito.setOnClickListener(v -> addArticle(article));
        btnSumar.setOnClickListener(v -> sumarItem());
        btnRestar.setOnClickListener(v -> restarItem());
    }


    public void addArticle(Articulo article){
        if(this._cantidadArticulo > 0){
            item = new ItemCarrito(article, this._cantidadArticulo);
            carrito.addArticleToCart(item);

        }
    }

    public void sumarItem(){
        this._cantidadArticulo+=1;
        cantidadArticulo.setText(String.valueOf(this._cantidadArticulo));

    }

    public void restarItem(){
        if(this._cantidadArticulo > 0){
            this._cantidadArticulo--;
            cantidadArticulo.setText(String.valueOf(this._cantidadArticulo));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HunterVerArticuloViewModel.class);
        // TODO: Use the ViewModel
    }

}