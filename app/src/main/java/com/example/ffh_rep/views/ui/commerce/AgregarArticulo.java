package com.example.ffh_rep.views.ui.commerce;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.models.repositories.ArticuloRepository;
import com.example.ffh_rep.models.repositories.CategoriaRepository;
import com.example.ffh_rep.models.repositories.ImageRepository;
import com.example.ffh_rep.models.repositories.MarcaRepository;
import com.example.ffh_rep.models.repositories.StockRepository;
import com.example.ffh_rep.utils.SessionManager;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.squareup.picasso.Picasso;

public class AgregarArticulo extends Fragment {


    private EditText txtDescripcionArticulo, txtCantidadStockInicial, txtFechaVencimientoStockInicial;
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



        txtDescripcionArticulo = view.findViewById(R.id.txtDescripcionArticulo);
        txtPrecioArticulo = view.findViewById(R.id.txtPrecioArticulo);
        txtCantidadStockInicial = view.findViewById(R.id.txtCantidadStockInicial);
        txtFechaVencimientoStockInicial = view.findViewById(R.id.txtFechaVencimientoStockInicial);
        spinnerIDCategoriaArticulo = view.findViewById(R.id.spinnerIDCategoriaArticulo);
        spinnerIDMarcaArticulo = view.findViewById(R.id.spinnerIDMarcaArticulo);
        btnAgregarArticulo = view.findViewById(R.id.btnAgregarArticulo);

        setupListeners();

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
                requestPermission();
            }
        });

        obtenerCategoriasDesdeRepositorio();
        obtenerMarcasDesdeRepositorio();
        btnAgregarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descripcionText = txtDescripcionArticulo.getText().toString().trim();
                String precioText = txtPrecioArticulo.getText().toString().trim();
                String cantidadInicial = txtCantidadStockInicial.getText().toString().trim();
                String fechaInicial = txtFechaVencimientoStockInicial.getText().toString().trim();

                if (descripcionText.isEmpty() || precioText.isEmpty() || imageUrlArticulo=="" || fechaInicial.isEmpty() || cantidadInicial.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, complete todos los campos e inserte imagen del producto", Toast.LENGTH_SHORT).show();
                    return;
                }

                Articulo articulo = new Articulo();

                String descripcion = txtDescripcionArticulo.getText().toString();
                double precio = Double.parseDouble(txtPrecioArticulo.getText().toString());
                //SE AGREGA +1 PORQUE EL LISTADO SPINNER ARRANCA EN 0
                int idCategoria = spinnerIDCategoriaArticulo.getSelectedItemPosition()+1;
                int idMarca = spinnerIDMarcaArticulo.getSelectedItemPosition()+1;


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

                Stock stock = new Stock();
                stock.setCantidad(Integer.valueOf(txtCantidadStockInicial.getText().toString()));
                stock.setFecha_vencimiento(Date.valueOf(txtFechaVencimientoStockInicial.getText().toString()));
                stock.setId_comercio(comercio);

                Log.d("Fecha antes de mandar", stock.getFecha_vencimiento().toString());

                insertarArticulo(articulo, stock);



            }
        });

        return view;
    }

    private void setupListeners() {

        txtFechaVencimientoStockInicial.setOnClickListener(v-> showDatePicker());
        txtFechaVencimientoStockInicial.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                showDatePicker();
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

    private void insertarArticulo(Articulo articulo, Stock stock) {
        ArticuloRepository articuloRepository = new ArticuloRepository();
        Context context = requireContext();

        CompletableFuture<Void> insertArticuloFuture = CompletableFuture.runAsync(() -> {
            articuloRepository.insertArticulo(context, articulo, stock);
        });


        clearFields();
        //redirectToHome();
    }


    public void goBack() {
        requireActivity().onBackPressed();
    }

    private void clearFields() {
        txtDescripcionArticulo.setText("");
        txtPrecioArticulo.setText("");
        txtCantidadStockInicial.setText("");
        txtFechaVencimientoStockInicial.setText("");
        spinnerIDCategoriaArticulo.setSelection(0);
        spinnerIDMarcaArticulo.setSelection(0);
        imageViewArticulo.setImageResource(R.mipmap.ic_launcher); 
        imagePath = null;
        imageUrlArticulo = "";
    }

    public void showDatePicker(){
        DatePickerDialog dpdialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                txtFechaVencimientoStockInicial.setText(date);
            }
        }, 2023, 11, 3);

        dpdialog.show();
    }

    public void redirectToHome(){
        Navigation.findNavController(requireView()).navigate(R.id.action_agregarArticulo_to_commerce_MisArticulos);
    }

}
