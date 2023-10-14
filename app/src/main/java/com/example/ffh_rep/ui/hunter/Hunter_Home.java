package com.example.ffh_rep.ui.hunter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ArticulosListAdapter;
import com.example.ffh_rep.adapters.ComercioListAdapter;
import com.example.ffh_rep.databinding.FragmentHomeBinding;
import com.example.ffh_rep.databinding.FragmentHunterHomeBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.factory.HunterHomeViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class Hunter_Home extends Fragment {

    private HunterHomeViewModel mViewModel;
    private FragmentHunterHomeBinding binding;
    private GridView gv_comercios_list, gv_productos_hunter_home;
    private ComercioListAdapter cla;
    private ArticulosListAdapter ala;
    private Button btnMasComercios;

    public static Hunter_Home newInstance() {
        return new Hunter_Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        gv_comercios_list = view.findViewById(R.id.gv_comercios_hunter_home);
        gv_productos_hunter_home = view.findViewById(R.id.gv_productos_hunter_home);
        btnMasComercios = view.findViewById(R.id.btn_mas_comercios_hunter);

        btnMasComercios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Hunter_Home.this).navigate(R.id.list);
            }
        });


        mViewModel = new ViewModelProvider(requireActivity(), new HunterHomeViewModelFactory(getActivity())).get(HunterHomeViewModel.class);
        cla = new ComercioListAdapter(getContext(), new ArrayList<>());
        mViewModel.cargarComercios();

        mViewModel.getMlDataComercio().observe(getViewLifecycleOwner(), new Observer<List<Comercio>>() {
            @Override
            public void onChanged(List<Comercio> comercios) {

                Log.d("Comercios en obverser", comercios.toString());
                cla.setComercioList(comercios);
            }
        });


        gv_comercios_list.setAdapter(cla);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HunterHomeViewModel.class);
    }

}