package com.example.ffh_rep.ui.commerce;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.repositories.ArticuloRepository;

import java.util.ArrayList;
import java.util.List;

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
        txtURLImagenArticulo = view.findViewById(R.id.txtURLImagenArticulo);
        spinnerIDCategoriaArticulo = view.findViewById(R.id.spinnerIDCategoriaArticulo);
        spinnerIDMarcaArticulo = view.findViewById(R.id.spinnerIDMarcaArticulo);
        btnAgregarArticulo = view.findViewById(R.id.btnAgregarArticulo);

        // SPINNERS ADAPTADORES DE EJEMPLO, FALTA CARGAR LOS LISTADOS DE C/U
        categorias = obtenerCategoriasEjemplo();
        marcas = obtenerMarcasEjemplo();

        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorias);
        ArrayAdapter<String> marcaAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, marcas);

        spinnerIDCategoriaArticulo.setAdapter(categoriaAdapter);
        spinnerIDMarcaArticulo.setAdapter(marcaAdapter);

        btnAgregarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idArticulo = Integer.parseInt(txtIDArticulo.getText().toString());
                String descripcion = txtDescripcionArticulo.getText().toString();
                double precio = Double.parseDouble(txtPrecioArticulo.getText().toString());
                String urlImagen = txtURLImagenArticulo.getText().toString();

                //NO SE UTILIZA, HAY QUE CARGAR LOS SPINNERS
                int idCategoria = spinnerIDCategoriaArticulo.getSelectedItemPosition();
                int idMarca = spinnerIDMarcaArticulo.getSelectedItemPosition();

                Articulo articulo = new Articulo();
                articulo.setIdArticulo(idArticulo);
                articulo.setDescripcion(descripcion);
                articulo.setPrecio(precio);
                articulo.setImagen(urlImagen);
                //SON SOLO PRUEBAS DE LOS SPINNERS
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(1);
                articulo.setCategoria(categoria);

                Marca marca = new Marca();
                marca.setIdMarca(1);
                articulo.setMarca(marca);

                articulo.setEstado("1");


                Log.d("LOG","---------------------------");
                Log.d("LOG","---------------------------");
                Log.d("LOG",articulo.toString());
                Log.d("LOG","---------------------------");
                Log.d("LOG","---------------------------");

                simularInsercion(articulo);
            }
        });

        return view;
    }

    //SPINNERS DE EJEMPLO
    private List<String> obtenerCategoriasEjemplo() {
        List<String> categorias = new ArrayList<>();
        categorias.add("Categoria 1");
        categorias.add("Categoria 2");
        categorias.add("Categoria 3");
        return categorias;
    }

    private List<String> obtenerMarcasEjemplo() {
        List<String> marcas = new ArrayList<>();
        marcas.add("Marca 1");
        marcas.add("Marca 2");
        marcas.add("Marca 3");
        return marcas;
    }

    private void simularInsercion(Articulo articulo) {
            ArticuloRepository articuloRepository = new ArticuloRepository();

            Context context = requireContext();



            articuloRepository.insertArticulo(context, articulo);

    }
}

