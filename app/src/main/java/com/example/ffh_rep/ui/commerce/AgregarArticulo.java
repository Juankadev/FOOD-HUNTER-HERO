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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.activitys.RegistroBasic;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.repositories.ArticuloRepository;
import com.example.ffh_rep.repositories.CategoriaRepository;
import com.example.ffh_rep.repositories.ImageRepository;
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
    private Spinner spinnerIDCategoriaArticulo;
    private Spinner spinnerIDMarcaArticulo;
    private Button btnAgregarArticulo;
    private Button btnVolver;

    private List<String> categorias;
    private List<String> marcas;

    /*VARIABLES PARA IMAGEN*/
    private static int IMAGE_REQ = 1;
    private ImageView imageViewArticulo;
    private Uri imagePath;
    private String imageUrlArticulo = "";

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

        btnVolver = view.findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


        /*1.USUARIO QUIERE HACER CLICK EN IMAGEVIEW*/
        /*2.USUARIO QUIERE SELECCIONAR LA IMAGEN*/
        /*3.USUARIO QUIERE LA PREVIEW DE LA IMAGEN*/
        /*3.USUARIO QUIERE PRESIONAR EL BOTON DE CARGA DE LA IMAGEN PARA GUARDARLA*/

        imageViewArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*PREGUNTARLE AL USUARIO SI LE DA PERMISO PARA EL MEDIA GALLERY*/
                requestPermission();

            }
        });


        obtenerCategoriasDesdeRepositorio();
        obtenerMarcasDesdeRepositorio();

        btnAgregarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idArticuloText = txtIDArticulo.getText().toString().trim();
                String descripcionText = txtDescripcionArticulo.getText().toString().trim();
                String precioText = txtPrecioArticulo.getText().toString().trim();

                if (idArticuloText.isEmpty() || descripcionText.isEmpty() || precioText.isEmpty() || imageUrlArticulo=="") {
                    Toast.makeText(getContext(), "Por favor, complete todos los campos e inserte imagen del producto", Toast.LENGTH_SHORT).show();
                    return;
                }

                Articulo articulo = new Articulo();

                int idArticulo = Integer.parseInt(txtIDArticulo.getText().toString());
                String descripcion = txtDescripcionArticulo.getText().toString();
                double precio = Double.parseDouble(txtPrecioArticulo.getText().toString());
                //SE AGREGA +1 PORQUE EL LISTADO SPINNER ARRANCA EN 0
                int idCategoria = spinnerIDCategoriaArticulo.getSelectedItemPosition()+1;
                int idMarca = spinnerIDMarcaArticulo.getSelectedItemPosition()+1;


                articulo.setIdArticulo(idArticulo);
                articulo.setDescripcion(descripcion);
                Comercio comercio;
                SessionManager session = new SessionManager(getContext());
                comercio = session.getCommerceSession();
                articulo.setComercio(comercio);
                articulo.setPrecio(precio);
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(idCategoria);
                articulo.setCategoria(categoria);
                Marca marca = new Marca();
                marca.setIdMarca(idMarca);
                articulo.setMarca(marca);
                articulo.setImagen(imageUrlArticulo);
                articulo.setEstado("1");

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

            spinnerIDCategoriaArticulo.setAdapter(categoriaAdapter);
            spinnerIDMarcaArticulo.setAdapter(marcaAdapter);
        }
    }

    private void insertarArticulo(Articulo articulo) {
        ArticuloRepository articuloRepository = new ArticuloRepository();
        Context context = requireContext();
        articuloRepository.insertArticulo(context, articulo);
        clearFields();
    }

    public void goBack() {
        requireActivity().onBackPressed();
    }

    private void clearFields() {
        txtIDArticulo.setText("");
        txtDescripcionArticulo.setText("");
        txtPrecioArticulo.setText("");
        spinnerIDCategoriaArticulo.setSelection(0);
        spinnerIDMarcaArticulo.setSelection(0);
        imageViewArticulo.setImageResource(R.mipmap.ic_launcher); 
        imagePath = null;
        imageUrlArticulo = "";
    }

}
