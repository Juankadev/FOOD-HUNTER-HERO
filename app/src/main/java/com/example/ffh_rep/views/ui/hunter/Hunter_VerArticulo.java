package com.example.ffh_rep.views.ui.hunter;

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
import com.example.ffh_rep.databinding.FragmentHunterVerArticuloBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.viewmodels.factory.ArticuloViewModelFactory;
import com.example.ffh_rep.viewmodels.factory.CarritoViewModelFactory;
import com.example.ffh_rep.viewmodels.hunter.ArticulosViewModel;
import com.example.ffh_rep.viewmodels.hunter.CarritoViewModel;

import java.util.List;

public class Hunter_VerArticulo extends Fragment {

    private ArticulosViewModel controller_articulos;
    private CarritoViewModel carrito;
    private Stock stock;
    private FragmentHunterVerArticuloBinding binding;
    private TextView descripcion, precio, categoria, marca, cantidadArticulo, stockDisponible;
    private ImageView ivArticulo;
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
            if(bundle.containsKey("stockSelected")){
                stock = (Stock) bundle.getSerializable("stockSelected");
            }
        }

        settingComponents(stock, view);
        setUpListeners(stock);


        carrito.getCarrito().observe(getViewLifecycleOwner(), new Observer<List<ItemCarrito>>() {
            @Override
            public void onChanged(List<ItemCarrito> itemCarritos) {
                Log.d("CARRITO EN DEATLLE ARTICULO", itemCarritos.toString());
            }
        });

       return view;
    }

    public void initComponentes(View view){
        carrito = new ViewModelProvider(requireActivity(), new CarritoViewModelFactory(getActivity())).get(CarritoViewModel.class);
        controller_articulos = new ViewModelProvider(this, new ArticuloViewModelFactory(getActivity())).get(ArticulosViewModel.class);

        descripcion = view.findViewById(R.id.tvDescripcionArt);
        precio = view.findViewById(R.id.tvPrecioArt);
        marca = view.findViewById(R.id.tvMarcaArt);
        categoria = view.findViewById(R.id.tvCategoriaArt);
        cantidadArticulo = view.findViewById(R.id.tv_cantidadItem);
        btnAniadirCarrito = view.findViewById(R.id.btnAgregarCarrito);
        ivArticulo = view.findViewById(R.id.ivArticulo);
        btnSumar = view.findViewById(R.id.btnSumarItem);
        stockDisponible = view.findViewById(R.id.tvStockDisponible);
        btnRestar = view.findViewById(R.id.btnRestarItem);
    }

    public void settingComponents(Stock stock, View view){
        precio.setText("$" + String.valueOf(stock.getId_articulo().getPrecio()));
        descripcion.setText(stock.getId_articulo().getDescripcion());
        marca.setText(stock.getId_articulo().getMarca().getDescripcion());
        categoria.setText(stock.getId_articulo().getCategoria().getDescripcion());
        stockDisponible.setText(String.valueOf(stock.getCantidad()));
        Glide.with(view).load(stock.getId_articulo().getImagen()).into(ivArticulo);
    }

    public void setUpListeners(Stock stock){
        btnAniadirCarrito.setOnClickListener(v -> addArticle(stock));
        btnSumar.setOnClickListener(v -> sumarItem());
        btnRestar.setOnClickListener(v -> restarItem());
    }


    public void addArticle(Stock stock){
        if(this._cantidadArticulo > 0 && this._cantidadArticulo <= stock.getCantidad()){
            item = new ItemCarrito(stock, this._cantidadArticulo);
            if(!carrito.isArticleInCart(item)){
                controller_articulos.reducirStock(item); //descuento el stock en la db, para que otro usuario no pueda reservarlo
                carrito.addArticleToCart(item);
                cantidadArticulo.setText(String.valueOf(0));
                Toast.makeText(getContext(), "Articulo Agregado", Toast.LENGTH_LONG).show();
                this._cantidadArticulo=0;
            }
            else{
                if(this._cantidadArticulo > 0 && this._cantidadArticulo <= stock.getCantidad()){
                    showDuplicateItemDialog();
                }
                else{
                    Toast.makeText(getContext(), "No se pudo agregar el articulo. Stock insuficiente", Toast.LENGTH_LONG).show();
                }
            }
        }
        else{
            Toast.makeText(getContext(), "No se pudo agregar el articulo. Stock insuficiente", Toast.LENGTH_LONG).show();
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

    public void showDuplicateItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Artículo duplicado");
        builder.setMessage("Este artículo ya está en el carrito. ¿Deseas agregar más unidades?");
        builder.setPositiveButton("Sí", (dialog, which) -> {
            addQuantityToChart();
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }

    public void addQuantityToChart(){
        controller_articulos.reducirStock(this.item); //descuento el stock en la db, para que otro usuario no pueda reservarlo
        carrito.addMoreUnitsToCart(this.item);
        this._cantidadArticulo=0;
        cantidadArticulo.setText(String.valueOf(0));
        Toast.makeText(getContext(), "Se han añadido mas unidades al carrito", Toast.LENGTH_LONG).show();
    }

}