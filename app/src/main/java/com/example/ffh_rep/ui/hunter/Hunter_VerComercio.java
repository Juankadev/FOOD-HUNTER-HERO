package com.example.ffh_rep.ui.hunter;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ArticuloComercioListAdapter;
import com.example.ffh_rep.databinding.FragmentHunterVerComercioBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.factory.CarritoViewModelFactory;
import com.example.ffh_rep.factory.HunterVerComercioViewModelFactory;
import com.example.ffh_rep.repositories.ArticuloRepository;

import java.util.ArrayList;
import java.util.List;

public class Hunter_VerComercio extends Fragment {

    private HunterVerComercioViewModel mViewModel;
    private CarritoViewModel carrito;
    private Comercio commerce;
    private LinearLayout bottomBar;
    private TextView descripcion, cantArticulos;
    private GridView gv_articulos;
    private Button btnAbrirCarrito;

    private FragmentHunterVerComercioBinding binding;
    private ArticuloComercioListAdapter aclAdapter;

    public static Hunter_VerComercio newInstance() {
        return new Hunter_VerComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterVerComercioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initializeComponents(view);
        Bundle bundle = getArguments();

        if(bundle != null){
            if(bundle.containsKey("comercioSelect")){
                commerce = (Comercio) bundle.getSerializable("comercioSelect");
            }
        }

        mViewModel.cargarArticulos(commerce.getId());

        setUpListeners();
        setUpObservers();

        descripcion.setText(commerce.getRazonSocial());
        gv_articulos.setAdapter(aclAdapter);
        return view;
    }


    public void initializeComponents(View view){
        descripcion = view.findViewById(R.id.txt_shop_name_description);
        gv_articulos = view.findViewById(R.id.gv_articulos_comerciodetail);
        btnAbrirCarrito = view.findViewById(R.id.btnAbrirCarrito);
        cantArticulos = view.findViewById(R.id.tv_cantArticulos);
        bottomBar = view.findViewById(R.id.bottomNavCart);

        mViewModel = new ViewModelProvider(requireActivity(), new HunterVerComercioViewModelFactory(getActivity())).get(HunterVerComercioViewModel.class);
        carrito = new ViewModelProvider(requireActivity(), new CarritoViewModelFactory(getActivity())).get(CarritoViewModel.class);
        aclAdapter = new ArticuloComercioListAdapter(new ArrayList<>(), getContext());
    }

    public void setUpListeners(){
        btnAbrirCarrito.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_hunter_VerComercio_to_hunter_MiCarrito));
    }

    public void setUpObservers(){

        mViewModel.getMldArticulos().observe(getViewLifecycleOwner(), new Observer<List<Articulo>>() {
            @Override
            public void onChanged(List<Articulo> articulos) {
                aclAdapter.setData(articulos);
            }
        });

        carrito.getCarrito().observe(getViewLifecycleOwner(), new Observer<List<ItemCarrito>>() {
            @Override
            public void onChanged(List<ItemCarrito> articulos) {
                Log.d("carrito actual", articulos.toString());
            }
        });


        carrito.getTotArticulos().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer > 0){
                    cantArticulos.setText(integer + " articulo(s) en carrito");
                    bottomBar.setVisibility(View.VISIBLE);
                }
                else{
                    bottomBar.setVisibility(View.GONE);
                }
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HunterVerComercioViewModel.class);
        carrito = new ViewModelProvider(this).get(CarritoViewModel.class);
    }
}