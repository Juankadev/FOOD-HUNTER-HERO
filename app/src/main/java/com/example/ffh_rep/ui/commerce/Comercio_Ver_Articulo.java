package com.example.ffh_rep.ui.commerce;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioVerArticuloBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.factory.ArticuloViewModelFactory;
import com.example.ffh_rep.factory.CarritoViewModelFactory;
import com.example.ffh_rep.factory.HunterVerComercioViewModelFactory;
import com.example.ffh_rep.ui.hunter.ArticulosViewModel;
import com.example.ffh_rep.ui.hunter.CarritoViewModel;

import java.util.List;

public class Comercio_Ver_Articulo extends Fragment {

    private ArticulosViewModel controller_articulos;
    private CarritoViewModel carrito;
    private Articulo article;
    private FragmentComercioVerArticuloBinding binding;
    private TextView descripcion, precio, categoria, marca;
    private ImageView ivArticulo;

    public static com.example.ffh_rep.ui.hunter.Hunter_VerArticulo newInstance() {
        return new com.example.ffh_rep.ui.hunter.Hunter_VerArticulo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding  = FragmentComercioVerArticuloBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        initComponentes(view);

        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey("articuloSelected")){
                article = (Articulo) bundle.getSerializable("articuloSelected");
            }
        }

        settingComponents(article, view);
        setUpListeners(article);


        return view;
    }


    public void initComponentes(View view){
        controller_articulos = new ViewModelProvider(this, new ArticuloViewModelFactory(getActivity())).get(ArticulosViewModel.class);

        descripcion = view.findViewById(R.id.tvDescripcionArt);
        precio = view.findViewById(R.id.tvPrecioArt);
        marca = view.findViewById(R.id.tvMarcaArt);
        categoria = view.findViewById(R.id.tvCategoriaArt);
        ivArticulo = view.findViewById(R.id.ivArticulo);

    }

    public void settingComponents(Articulo article, View view){
        precio.setText(String.valueOf(article.getPrecio()));
        descripcion.setText(article.getDescripcion());
        marca.setText(article.getMarca().getDescripcion());
        categoria.setText(article.getCategoria().getDescripcion());
        Glide.with(view).load(article.getImagen()).into(ivArticulo);
    }

    public void setUpListeners(Articulo article){

    }











}