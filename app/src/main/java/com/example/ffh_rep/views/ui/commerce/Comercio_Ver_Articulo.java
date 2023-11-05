package com.example.ffh_rep.views.ui.commerce;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioVerArticuloBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.viewmodels.factory.ArticuloViewModelFactory;
import com.example.ffh_rep.views.ui.hunter.Hunter_VerArticulo;
import com.example.ffh_rep.viewmodels.hunter.ArticulosViewModel;
import com.example.ffh_rep.viewmodels.hunter.CarritoViewModel;

public class Comercio_Ver_Articulo extends Fragment {

    private ArticulosViewModel controller_articulos;
    private CarritoViewModel carrito;
    private Articulo article;
    private FragmentComercioVerArticuloBinding binding;
    private TextView descripcion, precio, categoria, marca;
    private ImageView ivArticulo;
    private Button btnStock;

    public static Comercio_Ver_Articulo newInstance() {
        return new Comercio_Ver_Articulo();
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
        setUpListeners();


        return view;
    }


    public void initComponentes(View view){
        controller_articulos = new ViewModelProvider(this, new ArticuloViewModelFactory(getActivity())).get(ArticulosViewModel.class);
        btnStock = view.findViewById(R.id.btnStock);
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

    public void setUpListeners(){
        btnStock.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_comercio_Ver_Articulo_to_comercio_Ver_Stock_x_Articulo));
    }











}