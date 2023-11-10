package com.example.ffh_rep.views.ui.commerce;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.models.repositories.ArticuloRepository;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.factory.ArticuloViewModelFactory;
import com.example.ffh_rep.views.ui.hunter.Hunter_VerArticulo;
import com.example.ffh_rep.viewmodels.hunter.ArticulosViewModel;
import com.example.ffh_rep.viewmodels.hunter.CarritoViewModel;

import java.sql.Date;

public class Comercio_Ver_Articulo extends Fragment {

    private ArticulosViewModel controller_articulos;
    private CarritoViewModel carrito;
    private Articulo article;
    private FragmentComercioVerArticuloBinding binding;
    private TextView descripcion, precio, categoria, marca, tvCantidadArticulo;
    private ImageView ivArticulo;
    private Button btnStock, btnEliminar, btnModificar;

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
                this.article = (Articulo) bundle.getSerializable("articuloSelected");
            }
        }

        settingComponents(article, view);
        setUpListeners();

        return view;
    }


    public void initComponentes(View view){
        controller_articulos = new ViewModelProvider(this, new ArticuloViewModelFactory(getActivity())).get(ArticulosViewModel.class);
        btnStock = view.findViewById(R.id.btnStock);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        btnModificar = view.findViewById(R.id.btnModificar);
        descripcion = view.findViewById(R.id.tvDescripcionArt);
        precio = view.findViewById(R.id.tvPrecioArt);
        marca = view.findViewById(R.id.tvMarcaArt);
        categoria = view.findViewById(R.id.tvCategoriaArt);
        ivArticulo = view.findViewById(R.id.ivArticulo);
    }

    public void settingComponents(Articulo article, View view){
        precio.setText("$"+String.valueOf(article.getPrecio()));
        descripcion.setText(article.getDescripcion());
        marca.setText(article.getMarca().getDescripcion());
        categoria.setText(article.getCategoria().getDescripcion());
        Glide.with(view).load(article.getImagen()).into(ivArticulo);
    }

    public void setUpListeners(){
        btnStock.setOnClickListener(v-> redirectToStockxArticulos());

        btnModificar.setOnClickListener(v-> redirectToModificarArticulo());

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Eliminar articulo");
                builder.setMessage("¿Estás seguro de que deseas eliminar este articulo? Esta acción no se puede deshacer.");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarArticulo(article);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void redirectToStockxArticulos(){
        Bundle args = new Bundle();
        args.putSerializable("articuloAseleccionar", this.article);
        Navigation.findNavController(requireView()).navigate(R.id.action_comercio_Ver_Articulo_to_comercio_Ver_Stock_x_Articulo, args);
    }

    public void eliminarArticulo(Articulo articulo){
        ArticuloRepository articuloRepository = new ArticuloRepository();
        articuloRepository.eliminarArticulo(getContext(), articulo);
        Navigation.findNavController(requireView()).navigate(R.id.action_comercio_Ver_Articulo_to_commerce_MisArticulos2);
    }

    public void redirectToModificarArticulo(){
        Bundle args = new Bundle();
        args.putSerializable("articuloAseleccionar", this.article);
        Navigation.findNavController(requireView()).navigate(R.id.action_comercio_Ver_Articulo_to_comercio_ModificarArticulo, args);
    }


}