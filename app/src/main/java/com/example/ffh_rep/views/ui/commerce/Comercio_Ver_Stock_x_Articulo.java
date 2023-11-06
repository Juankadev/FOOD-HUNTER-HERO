package com.example.ffh_rep.views.ui.commerce;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.GridView;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioHomeBinding;
import com.example.ffh_rep.databinding.FragmentComercioVerStockXArticuloBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.commerce.ComercioHomeViewModel;
import com.example.ffh_rep.viewmodels.commerce.ComercioVerStockXArticuloViewModel;
import com.example.ffh_rep.viewmodels.factory.ArticuloViewModelFactory;
import com.example.ffh_rep.viewmodels.factory.ComercioHomeViewModelFactory;
import com.example.ffh_rep.viewmodels.factory.ComercioVerStocksArticuloViewModelFactory;
import com.example.ffh_rep.viewmodels.hunter.ArticulosViewModel;
import com.example.ffh_rep.views.adapters.ArticuloUsuarioComercioListAdapter;
import com.example.ffh_rep.views.adapters.StocksArticuloComercioListAdapter;

import java.util.ArrayList;
import java.util.List;

public class Comercio_Ver_Stock_x_Articulo extends Fragment {

    private ComercioVerStockXArticuloViewModel mViewModel;
    private FragmentComercioVerStockXArticuloBinding binding;
    private Button btnAniadirStock;
    private GridView gv_stocks;
    private SessionManager sessionManager;
    private Comercio userSession;
    private StocksArticuloComercioListAdapter sacAdapter;
    private Stock stock;
    private Articulo article;

    public static Comercio_Ver_Stock_x_Articulo newInstance() {
        return new Comercio_Ver_Stock_x_Articulo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentComercioVerStockXArticuloBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        initViews(view);
        initModelsAndAdapters();

        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey("stockSelected")){
                stock = (Stock) bundle.getSerializable("stockSelected");
            }
        }

        if(bundle != null){
            if(bundle.containsKey("articuloAseleccionar")){
                article = (Articulo) bundle.getSerializable("articuloAseleccionar");
            }
        }

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getCommerceSession();




        mViewModel.listarStocksArticuloxComercio(article, userSession);

        setUpListeners();
        setUpObservers();
        gv_stocks.setAdapter(sacAdapter);
        return view;
    }


    private void initViews(View view){
        btnAniadirStock = view.findViewById(R.id.btn_commerce_addStockArticulo);
        gv_stocks = view.findViewById(R.id.gv_stocks_detail_articulo);
    }


    public void initModelsAndAdapters(){
        mViewModel = new ViewModelProvider(requireActivity(), new ComercioVerStocksArticuloViewModelFactory(getActivity())).get(ComercioVerStockXArticuloViewModel.class);
        sacAdapter = new StocksArticuloComercioListAdapter(new ArrayList<>(), getContext());


    }

    private void setUpListeners(){
        btnAniadirStock.setOnClickListener(v-> redirectToAgregarStockArticulo());
    }

    public void setUpObservers() {
        mViewModel.getMldListaStocks().observe(getViewLifecycleOwner(), stocks -> {
            sacAdapter.setData(stocks);
        });
    }

    //PASO EL ARTICULO POR BUNDLE
    public void redirectToAgregarStockArticulo(){
        Bundle args = new Bundle();
        args.putSerializable("articuloSeleccionadoStock", this.article);
        Navigation.findNavController(requireView()).navigate(R.id.action_comercio_Ver_Stock_x_Articulo_to_comercio_AgregarStockArticulo, args);
    }




}