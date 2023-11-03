package com.example.ffh_rep.ui.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ffh_rep.adapters.AprobarComerciosViewAdapter;
import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ArticuloComercioListAdapter;
import com.example.ffh_rep.databinding.FragmentAprobarComerciosItemBinding;
import com.example.ffh_rep.databinding.FragmentAprobarComerciosListBinding;
import com.example.ffh_rep.databinding.FragmentHunterVerComercioBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Resenia;
import com.example.ffh_rep.factory.AprobarComerciosViewModelFactory;
import com.example.ffh_rep.factory.CarritoViewModelFactory;
import com.example.ffh_rep.factory.ComercioViewModelFactory;
import com.example.ffh_rep.factory.HunterVerComercioViewModelFactory;
import com.example.ffh_rep.placeholder.PlaceholderContent;
import com.example.ffh_rep.ui.hunter.CarritoViewModel;
import com.example.ffh_rep.ui.hunter.HunterReseniasComercioViewModel;
import com.example.ffh_rep.ui.hunter.HunterVerComercioViewModel;
import com.example.ffh_rep.ui.hunter.Hunter_VerComercio;
import com.example.ffh_rep.utils.SessionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A fragment representing a list of Items.
 */
public class AprobarComercios extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;
    private AdminAprobarComerciosViewModel mViewModel;
    private Comercio commerce;
    private GridView gv_comercios;

    private FragmentAprobarComerciosListBinding binding;
    private AprobarComerciosViewAdapter listAdapter;

    public static AprobarComercios newInstance() {
        return new AprobarComercios();
    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AprobarComercios newInstance(int columnCount) {
        AprobarComercios fragment = new AprobarComercios();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mViewModel = new ViewModelProvider(requireActivity(), new AprobarComerciosViewModelFactory(getActivity())).get(AdminAprobarComerciosViewModel.class);
        listAdapter = new AprobarComerciosViewAdapter(new ArrayList<>(), getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAprobarComerciosListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        gv_comercios = view.findViewById(R.id.gv_comercios);

        mViewModel.cargarComerciosNoAprobados();

        
        mViewModel.getListComerciosNoAprobados().observe(getViewLifecycleOwner(), new Observer<List<Comercio>>() {
            @Override
            public void onChanged(List<Comercio> comercios) {
                listAdapter.setData(comercios);
            }
        });

        mViewModel.cargarComerciosNoAprobados();

        setUpObservers();
        gv_comercios.setAdapter(listAdapter);

        return view;
    }


    public void setUpObservers(){
        mViewModel.getListComerciosNoAprobados().observe(getViewLifecycleOwner(), new Observer<List<Comercio>>() {
            @Override
            public void onChanged(List<Comercio> comercios) {
                Log.d("comercios", comercios.toString());
                listAdapter.setData(comercios);
            }
        });
    }
}