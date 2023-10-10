package com.example.ffh_rep.ui.hunter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ComercioListAdapter;
import com.example.ffh_rep.databinding.FragmentHomeBinding;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.factory.HunterHomeViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class Hunter_Home extends Fragment {

    private HunterHomeViewModel mViewModel;
    private FragmentHomeBinding binding;
    private GridView gv_comercios_list;
    private ComercioListAdapter cla;

    public static Hunter_Home newInstance() {
        return new Hunter_Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        gv_comercios_list = view.findViewById(R.id.gv_comercios_hunter_home);

        mViewModel = new ViewModelProvider(requireActivity(), new HunterHomeViewModelFactory(getActivity())).get(HunterHomeViewModel.class);
        cla = new ComercioListAdapter(getContext(), new ArrayList<>());
        mViewModel.cargarComercios();

        mViewModel.getMlDataComercio().observe(getViewLifecycleOwner(), new Observer<List<Comercio>>() {
            @Override
            public void onChanged(List<Comercio> comercios) {
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