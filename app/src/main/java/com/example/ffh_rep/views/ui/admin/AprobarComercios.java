package com.example.ffh_rep.views.ui.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ffh_rep.databinding.FragmentAprobarComerciosListBinding;
import com.example.ffh_rep.viewmodels.admin.AdminAprobarComerciosViewModel;
import com.example.ffh_rep.views.adapters.AprobarComerciosViewAdapter;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.viewmodels.factory.AprobarComerciosViewModelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class AprobarComercios extends Fragment {
    private AdminAprobarComerciosViewModel mViewModel;
    private GridView gv_comercios;
    private FragmentAprobarComerciosListBinding binding;
    private AprobarComerciosViewAdapter listAdapter;
    private TextView tvNoData;

    public static AprobarComercios newInstance() {
        return new AprobarComercios();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAprobarComerciosListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initComponents(view);
        mViewModel.cargarComerciosNoAprobados();
        setUpObservers();
        gv_comercios.setAdapter(listAdapter);
        return view;
    }
    public void initComponents(View view){
        gv_comercios = view.findViewById(R.id.gv_comercios);
        tvNoData = view.findViewById(R.id.tvNoData);
        mViewModel = new ViewModelProvider(requireActivity(), new AprobarComerciosViewModelFactory(getActivity())).get(AdminAprobarComerciosViewModel.class);
        listAdapter = new AprobarComerciosViewAdapter(new ArrayList<>(), mViewModel, getContext());
    }
    public void setUpObservers(){
        mViewModel.getListComerciosNoAprobados().observe(getViewLifecycleOwner(), new Observer<List<Comercio>>() {
            @Override
            public void onChanged(List<Comercio> comercios) {
                if(comercios.size()>0)
                {
                    tvNoData.setVisibility(View.GONE);
                    listAdapter.setData(comercios);
                }
                else{
                    tvNoData.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}