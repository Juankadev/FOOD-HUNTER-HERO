package com.example.ffh_rep.views.ui.commerce;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.models.repositories.ArticuloRepository;
import com.example.ffh_rep.models.repositories.CategoriaRepository;
import com.example.ffh_rep.models.repositories.ImageRepository;
import com.example.ffh_rep.models.repositories.MarcaRepository;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.R;
import com.example.ffh_rep.viewmodels.commerce.ComercioVerStockXArticuloViewModel;
import com.example.ffh_rep.viewmodels.factory.ComercioVerStocksArticuloViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Comercio_ModificarArticulo extends Fragment {

    private ComercioVerStockXArticuloViewModel mViewModel;

    private TextView descripcion, precio;
    private Spinner categoria, marca;
    private Button editar, btnVolver;
    private List<String> categorias;
    private List<String> marcas;
    private int idArticulo;


    private static int IMAGE_REQ = 1;
    private ImageView ivArticulo;
    private Uri imagePath;
    private String imageUrlArticulo = "";
    private Articulo article;

    public static Comercio_ModificarArticulo newInstance() {
        return new Comercio_ModificarArticulo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercio__modificar_articulo, container, false);

        initComponents(view);
        Bundle args = getArguments();
        if(args != null){
            this.article = (Articulo) args.getSerializable("articuloAseleccionar");
        }
        ivArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*PREGUNTARLE AL USUARIO SI LE DA PERMISO PARA EL MEDIA GALLERY*/
                requestPermission();

            }
        });

        obtenerCategoriasDesdeRepositorio();
        obtenerMarcasDesdeRepositorio();

        fillInputs();
        setUpListeners();


        return view;
    }

    public void initComponents(View view){
        descripcion = view.findViewById(R.id.txtDescripcionArticulo_Modif);
        precio = view.findViewById(R.id.txtPrecioArticulo_Modif);
        categoria = view.findViewById(R.id.spinnerIDCategoriaArticulo_Modif);
        marca = view.findViewById(R.id.spinnerIDMarcaArticulo_Modif);
        editar = view.findViewById(R.id.btnEditarArticulo);
        ivArticulo = view.findViewById(R.id.imageViewArticuloModif);
        btnVolver = view.findViewById(R.id.btnVolver);

        mViewModel = new ViewModelProvider(requireActivity(), new ComercioVerStocksArticuloViewModelFactory(getActivity())).get(ComercioVerStockXArticuloViewModel.class);

    }

    public void setUpListeners(){

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descripcionText = descripcion.getText().toString().trim();
                String precioText = precio.getText().toString().trim();


                if (descripcionText.isEmpty() || precioText.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, complete todos los campos e inserte imagen del producto", Toast.LENGTH_SHORT).show();
                    return;
                }

                Articulo articulo = new Articulo();

                String descripcionMod = descripcion.getText().toString();
                double precioMod = Double.parseDouble(precio.getText().toString());
                //SE AGREGA +1 PORQUE EL LISTADO SPINNER ARRANCA EN 0
                int idCategoria = categoria.getSelectedItemPosition()+1;
                int idMarca = marca.getSelectedItemPosition()+1;

                articulo.setIdArticulo(idArticulo);
                articulo.setDescripcion(descripcionMod);
                Comercio comercio;
                SessionManager session = new SessionManager(getContext());
                comercio = session.getCommerceSession();
                articulo.setComercio(comercio);
                articulo.setPrecio(precioMod);
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(idCategoria);
                articulo.setCategoria(categoria);
                Marca marca = new Marca();
                marca.setIdMarca(idMarca);
                articulo.setMarca(marca);
                articulo.setImagen(imageUrlArticulo);
                articulo.setEstado("1");




                updateArticulo(articulo);

            }
        });
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            selectImage();
        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1 );
        }
    }

    public void fillInputs(){
        idArticulo = article.getIdArticulo();
        descripcion.setText(this.article.getDescripcion());
        precio.setText(String.valueOf(this.article.getPrecio()));
        categoria.setSelection(this.article.getCategoria().getIdCategoria());
        marca.setSelection(this.article.getMarca().getIdMarca());
        imageUrlArticulo = this.article.getImagen();



        Glide.with(ivArticulo).load(this.article.getImagen()).into(ivArticulo);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*"); //SE PUEDE USAR PDF
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQ && resultCode== Activity.RESULT_OK && data != null && data.getData()!=null){
            imagePath = data.getData();
            Picasso.get().load(imagePath).into(ivArticulo);

            //GUARDO LA IMAGEN EN CLOUDINARY
            ImageRepository imagen = new ImageRepository(getContext());
            imagen.uploadImage(imagePath, new ImageRepository.ImageUploadCallback() {
                @Override
                public void onSuccess(String imageUrl) {
                    imageUrlArticulo = imageUrl;
                }

            });

        }
    }


    private void obtenerCategoriasDesdeRepositorio() {
        CategoriaRepository categoriaRepository = new CategoriaRepository();
        MutableLiveData<List<Categoria>> categoriaLiveData = new MutableLiveData<>();
        categoriaRepository.getCategorias(categoriaLiveData);
        categoriaLiveData.observe(getViewLifecycleOwner(), categorias -> {
            this.categorias = new ArrayList<>();
            for (Categoria categoria : categorias) {
                this.categorias.add(categoria.getDescripcion());
            }
            cargarDatosEnSpinners();
        });
    }

    private void obtenerMarcasDesdeRepositorio() {
        MarcaRepository marcaRepository = new MarcaRepository();
        MutableLiveData<List<Marca>> marcaLiveData = new MutableLiveData<>();
        marcaRepository.getMarcas(marcaLiveData);
        marcaLiveData.observe(getViewLifecycleOwner(), marcas -> {
            this.marcas = new ArrayList<>();
            for (Marca marca : marcas) {
                this.marcas.add(marca.getDescripcion());
            }
            cargarDatosEnSpinners();
        });
    }

    private void cargarDatosEnSpinners() {
        if (categorias != null && marcas != null) {
            ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorias);
            ArrayAdapter<String> marcaAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, marcas);

            categoria.setAdapter(categoriaAdapter);
            marca.setAdapter(marcaAdapter);
        }
    }

    private void updateArticulo(Articulo articulo) {
        ArticuloRepository articuloRepository = new ArticuloRepository();
        Context context = requireContext();
        CompletableFuture<Void> updateArticuloFuture = CompletableFuture.runAsync(() -> {
            articuloRepository.updateArticulo(context, articulo);
        });
        redirectToHome();
    }
    public void redirectToHome(){
        Navigation.findNavController(requireView()).navigate(R.id.action_comercio_ModificarArticulo_to_commerce_MisArticulos);
    }

    public void goBack() {
        requireActivity().onBackPressed();
    }



}