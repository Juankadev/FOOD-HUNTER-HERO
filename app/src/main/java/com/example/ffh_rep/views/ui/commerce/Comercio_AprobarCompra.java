package com.example.ffh_rep.views.ui.commerce;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.viewmodels.commerce.ComercioAprobarCompraViewModel;
import com.example.ffh_rep.viewmodels.factory.ComercioAprobarCompraViewModelFactory;
import com.example.ffh_rep.views.adapters.ArticulosCarritoListAdapter;

import java.util.ArrayList;

public class Comercio_AprobarCompra extends Fragment {

    private ComercioAprobarCompraViewModel mViewModel;
    private ArticulosCarritoListAdapter alAdapter;

    private ListView listaProductos;

    private JSONQRRequest response;

    public static Comercio_AprobarCompra newInstance() {
        return new Comercio_AprobarCompra();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercio__aprobar_compra, container, false);

        initComponents(view);
        Bundle args = getArguments();
        if(args != null){
            response = (JSONQRRequest) args.getSerializable("responseQr");
        }
        mViewModel.setIdComercio(response.getIdComercio());
        mViewModel.setIdHunter(response.getIdHunter());
        mViewModel.setMlArticulos(response.getlCarrito());

        setUpObservers();

        listaProductos.setAdapter(alAdapter);

        return view;
    }



    public void initComponents(View view){
        alAdapter = new ArticulosCarritoListAdapter(new ArrayList<>(), getContext());
        mViewModel = new ViewModelProvider(requireActivity(), new ComercioAprobarCompraViewModelFactory(getActivity())).get(ComercioAprobarCompraViewModel.class);
        listaProductos = view.findViewById(R.id.comercio_lista_productos);
    }

    public void setUpObservers(){
        mViewModel.getMlArticulos().observe(getViewLifecycleOwner(), articulos -> alAdapter.setlArticulos(articulos));
    }
}