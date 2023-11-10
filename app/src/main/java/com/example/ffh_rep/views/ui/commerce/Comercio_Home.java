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
import com.example.ffh_rep.viewmodels.commerce.ComercioHomeViewModel;
import com.example.ffh_rep.views.adapters.ArticuloUsuarioComercioListAdapter;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.viewmodels.factory.ComercioHomeViewModelFactory;
import com.example.ffh_rep.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class Comercio_Home extends Fragment {

    private ComercioHomeViewModel mViewModel;
    private FragmentComercioHomeBinding binding;
    private Button btnAniadirArticulo;
    private GridView gv_articulos;
    private SessionManager sessionManager;
    private Comercio userSession;
    private TextView tvNoData;

    private ArticuloUsuarioComercioListAdapter aclAdapter;


    public static Comercio_Home newInstance() {
        return new Comercio_Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentComercioHomeBinding.inflate(inflater, container, false);

        View view = binding.getRoot();
        initViews(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getCommerceSession();
        mViewModel.cargarArticulos(userSession.getId());
        setUpListeners();
        setUpObservers();
        gv_articulos.setAdapter(aclAdapter);


        return view;
    }


    private void initViews(View view){
        btnAniadirArticulo = view.findViewById(R.id.btn_commerce_addArticle);
        gv_articulos = view.findViewById(R.id.gv_articulos_comerciodetail2);
        tvNoData = view.findViewById(R.id.tvNoData);
    }

    public void initModelsAndAdapters(){
        mViewModel = new ViewModelProvider(requireActivity(), new ComercioHomeViewModelFactory(getActivity())).get(ComercioHomeViewModel.class);
        aclAdapter = new ArticuloUsuarioComercioListAdapter(new ArrayList<>(), getContext());
    }

    public void setUpObservers() {
        mViewModel.getMldArticulos().observe(getViewLifecycleOwner(), new Observer<List<Articulo>>() {
            @Override
            public void onChanged(List<Articulo> articulos) {
                if(articulos.size()>0)
                {
                    tvNoData.setVisibility(View.GONE);
                    aclAdapter.setData(articulos);
                }
                else{
                    tvNoData.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModelsAndAdapters();
    }

    private void setUpListeners(){
        btnAniadirArticulo.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_commerce_MisArticulos_to_agregarArticulo));
        //btnIrBeneficios.setOnClickListener(v->Navigation.findNavController(v).navigate(R.id.action_commerce_MisArticulos_to_fragmentAgregarDescuentoComercio));
    }


}