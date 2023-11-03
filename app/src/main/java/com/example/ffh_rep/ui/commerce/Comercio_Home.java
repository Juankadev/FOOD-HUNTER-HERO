package com.example.ffh_rep.ui.commerce;

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

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ArticuloUsuarioComercioListAdapter;
import com.example.ffh_rep.databinding.FragmentComercioHomeBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.factory.ComercioHomeViewModelFactory;
import com.example.ffh_rep.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class Comercio_Home extends Fragment {

    private ComercioHomeViewModel mViewModel;
    private FragmentComercioHomeBinding binding;
    private Button btnAniadirArticulo, btnIrBeneficios;
    private GridView gv_articulos;
    private SessionManager sessionManager;
    private Comercio userSession;

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
        btnIrBeneficios = view.findViewById(R.id.btn_MisDescuentos);
        gv_articulos = view.findViewById(R.id.gv_articulos_comerciodetail2);
        Log.d("Debug Pablo", "-----------");
        Log.d("Debug Pablo", gv_articulos.toString());
        Log.d("Debug Pablo", "-----------");

    }

    public void initModelsAndAdapters(){
        mViewModel = new ViewModelProvider(requireActivity(), new ComercioHomeViewModelFactory(getActivity())).get(ComercioHomeViewModel.class);
        aclAdapter = new ArticuloUsuarioComercioListAdapter(new ArrayList<>(), getContext());
    }

    public void setUpObservers() {
        mViewModel.getMldArticulos().observe(getViewLifecycleOwner(), new Observer<List<Articulo>>() {
            @Override
            public void onChanged(List<Articulo> articulos) {
                aclAdapter.setData(articulos);
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
        btnIrBeneficios.setOnClickListener(v->Navigation.findNavController(v).navigate(R.id.action_commerce_MisArticulos_to_fragmentAgregarDescuentoComercio));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ComercioHomeViewModel.class);
        // TODO: Use the ViewModel
    }

}