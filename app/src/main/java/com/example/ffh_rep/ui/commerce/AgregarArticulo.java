package com.example.ffh_rep.ui.commerce;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.repositories.ArticuloRepository;
import com.example.ffh_rep.repositories.CategoriaRepository;
import com.example.ffh_rep.repositories.MarcaRepository;
import com.example.ffh_rep.utils.SessionManager;

import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

public class AgregarArticulo extends Fragment {

    private EditText txtIDArticulo;
    private EditText txtDescripcionArticulo;
    private EditText txtPrecioArticulo;
    private EditText txtURLImagenArticulo;
    private Spinner spinnerIDCategoriaArticulo;
    private Spinner spinnerIDMarcaArticulo;
    private Button btnAgregarArticulo;

    private List<String> categorias;
    private List<String> marcas;

    /*VARIABLES PARA IMAGEN*/
    private static int IMAGE_REQ = 1;
    private ImageView imageViewArticulo;
    private Button btnCargarImagen;
    private Uri imagePath;

    public static AgregarArticulo newInstance() {
        return new AgregarArticulo();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_articulo, container, false);


        txtIDArticulo = view.findViewById(R.id.txtIDArticulo);
        txtDescripcionArticulo = view.findViewById(R.id.txtDescripcionArticulo);
        txtPrecioArticulo = view.findViewById(R.id.txtPrecioArticulo);
        spinnerIDCategoriaArticulo = view.findViewById(R.id.spinnerIDCategoriaArticulo);
        spinnerIDMarcaArticulo = view.findViewById(R.id.spinnerIDMarcaArticulo);
        btnAgregarArticulo = view.findViewById(R.id.btnAgregarArticulo);

        imageViewArticulo = view.findViewById(R.id.imageViewArticulo);
        btnCargarImagen = view.findViewById(R.id.btnCargarImagen);

        /*1.USUARIO QUIERE HACER CLICK EN IMAGEVIEW*/
        /*2.USUARIO QUIERE SELECCIONAR LA IMAGEN*/
        /*3.USUARIO QUIERE LA PREVIEW DE LA IMAGEN*/
        /*3.USUARIO QUIERE PRESIONAR EL BOTON DE CARGA DE LA IMAGEN PARA GUARDARLA*/

        imageViewArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*1.PREGUNTARLE AL USUARIO SI LE DA PERMISO PARA EL MEDIA GALLERY*/
                requestPermission();
                /*2.IR A LA GALERIA*/
            }
        });

        obtenerCategoriasDesdeRepositorio();
        obtenerMarcasDesdeRepositorio();

        btnAgregarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idArticulo = Integer.parseInt(txtIDArticulo.getText().toString());
                String descripcion = txtDescripcionArticulo.getText().toString();
                double precio = Double.parseDouble(txtPrecioArticulo.getText().toString());
                String urlImagen = txtURLImagenArticulo.getText().toString();

                int idCategoria = spinnerIDCategoriaArticulo.getSelectedItemPosition();
                int idMarca = spinnerIDMarcaArticulo.getSelectedItemPosition();

                Articulo articulo = new Articulo();
                articulo.setIdArticulo(idArticulo);
                articulo.setDescripcion(descripcion);

                Comercio comercio;
                SessionManager session = new SessionManager(getContext());
                comercio = session.getCommerceSession();
                articulo.setComercio(comercio);

                Log.d("LOG", "---------------------------");
                Log.d("LOG", "---------------------------");
                Log.d("LOG", comercio.toString());
                Log.d("LOG", "---------------------------");
                Log.d("LOG", "---------------------------");


                articulo.setPrecio(precio);
                articulo.setImagen(urlImagen);

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(idCategoria);
                articulo.setCategoria(categoria);

                Marca marca = new Marca();
                marca.setIdMarca(idMarca);
                articulo.setMarca(marca);

                articulo.setEstado("1");

                Log.d("LOG", "---------------------------");
                Log.d("LOG", "---------------------------");
                Log.d("LOG", articulo.toString());
                Log.d("LOG", "---------------------------");
                Log.d("LOG", "---------------------------");

                insertarArticulo(articulo);
            }
        });

        return view;
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            selectImage();
        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1 );
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*"); //SE PUEDE USAR PDF O VIDEO
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQ && resultCode== Activity.RESULT_OK && data != null && data.getData()!=null){
           imagePath = data.getData();
           Picasso.get().load(imagePath).into(imageViewArticulo);

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

            spinnerIDCategoriaArticulo.setAdapter(categoriaAdapter);
            spinnerIDMarcaArticulo.setAdapter(marcaAdapter);
        }
    }

    private void insertarArticulo(Articulo articulo) {
        ArticuloRepository articuloRepository = new ArticuloRepository();
        Context context = requireContext();
        articuloRepository.insertArticulo(context, articulo);
    }
}
