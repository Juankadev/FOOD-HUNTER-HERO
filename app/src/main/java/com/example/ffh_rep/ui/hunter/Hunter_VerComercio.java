package com.example.ffh_rep.ui.hunter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ArticuloComercioListAdapter;
import com.example.ffh_rep.databinding.FragmentHunterVerComercioBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.factory.HunterVerComercioViewModelFactory;
import com.example.ffh_rep.repositories.ArticuloRepository;

import java.util.ArrayList;
import java.util.List;

public class Hunter_VerComercio extends Fragment {

    private HunterVerComercioViewModel mViewModel;
    private Comercio commerce;
    private TextView descripcion;
    private GridView gv_articulos;

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

        descripcion = view.findViewById(R.id.txt_shop_name_description);
        gv_articulos = view.findViewById(R.id.gv_articulos_comerciodetail);
        Bundle bundle = getArguments();

        if(bundle != null){
            if(bundle.containsKey("comercioSelect")){
                commerce = (Comercio) bundle.getSerializable("comercioSelect");
            }
        }

        mViewModel = new ViewModelProvider(requireActivity(), new HunterVerComercioViewModelFactory(getActivity())).get(HunterVerComercioViewModel.class);
        aclAdapter = new ArticuloComercioListAdapter(new ArrayList<>(), getContext());

        mViewModel.cargarArticulos(commerce.getId());

        mViewModel.getMldArticulos().observe(getViewLifecycleOwner(), new Observer<List<Articulo>>() {
            @Override
            public void onChanged(List<Articulo> articulos) {
                Log.d("Articulo listener", articulos.toString());
                aclAdapter.setData(articulos);
            }
        });

        gv_articulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        descripcion.setText(commerce.getRazonSocial());
        gv_articulos.setAdapter(aclAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HunterVerComercioViewModel.class);
    }
}