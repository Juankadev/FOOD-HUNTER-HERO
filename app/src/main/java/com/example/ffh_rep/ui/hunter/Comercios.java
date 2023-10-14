package com.example.ffh_rep.ui.hunter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ComerciosAdapter;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.factory.ComercioViewModelFactory;
import com.example.ffh_rep.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

public class Comercios extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private ComerciosViewModel viewModel;
    private ComerciosAdapter cAdapter;
    private int mColumnCount = 2;

    public Comercios() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Comercios newInstance(int columnCount) {
        Comercios fragment = new Comercios();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercios_list, container, false);
        viewModel = new ViewModelProvider(requireActivity(), new ComercioViewModelFactory(getActivity())).get(ComerciosViewModel.class);
        RecyclerView rview = (RecyclerView) view;
        cAdapter = new ComerciosAdapter(new ArrayList<>());

        viewModel.cargarComercios();

        viewModel.getMldComercios().observe(getViewLifecycleOwner(), new Observer<List<Comercio>>() {
            @Override
            public void onChanged(List<Comercio> comercios) {
                cAdapter.setData(comercios);
            }
        });


        return view;
    }
}