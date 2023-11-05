package com.example.ffh_rep.views.ui.commerce;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.models.repositories.ImageRepository;
import com.example.ffh_rep.viewmodels.commerce.ComercioModificarArticuloViewModel;
import com.example.ffh_rep.R;
import com.example.ffh_rep.viewmodels.commerce.ComercioVerStockXArticuloViewModel;
import com.example.ffh_rep.viewmodels.factory.ArticuloViewModelFactory;
import com.example.ffh_rep.viewmodels.factory.ComercioVerStocksArticuloViewModelFactory;
import com.example.ffh_rep.viewmodels.hunter.ArticulosViewModel;
import com.example.ffh_rep.views.ui.hunter.Articulos;
import com.squareup.picasso.Picasso;

public class Comercio_ModificarArticulo extends Fragment {

    private ComercioVerStockXArticuloViewModel mViewModel;

    private TextView descripcion, precio, tvCantidadArticulo;
    private Spinner categoria, marca;
    private Button editar;

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
            this.article = (Articulo) args.getSerializable("modifArticulo");
        }


        fillInputs();
        return view;
    }

    public void initComponents(View view){
        descripcion = view.findViewById(R.id.txtDescripcionArticulo_Modif);
        precio = view.findViewById(R.id.txtPrecioArticulo_Modif);
        categoria = view.findViewById(R.id.spinnerIDCategoriaArticulo_Modif);
        marca = view.findViewById(R.id.spinnerIDMarcaArticulo_Modif);
        editar = view.findViewById(R.id.btnEditarArticulo);
        ivArticulo = view.findViewById(R.id.imageViewArticulo);

        Glide.with(view).load(this.article.getImagen()).into(ivArticulo);
        mViewModel = new ViewModelProvider(requireActivity(), new ComercioVerStocksArticuloViewModelFactory(getActivity())).get(ComercioVerStockXArticuloViewModel.class);

    }


    public void setUpListeners(){
        //Funcion editar;
        editar.setOnClickListener(v-> {});
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            selectImage();
        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1 );
        }
    }

    public void fillInputs(){
        descripcion.setText(this.article.getDescripcion());
        precio.setText(String.valueOf(this.article.getPrecio()));
        categoria.setSelection(this.article.getCategoria().getIdCategoria());
        marca.setSelection(this.article.getMarca().getIdMarca());
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
}