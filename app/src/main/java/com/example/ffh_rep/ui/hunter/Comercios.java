package com.example.ffh_rep.ui.hunter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
    private NavController nav;
    private int mColumnCount = 2;

    public Comercios() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercios_list, container, false);
        viewModel = new ViewModelProvider(requireActivity(), new ComercioViewModelFactory(getActivity())).get(ComerciosViewModel.class);
        RecyclerView rview = (RecyclerView) view;
        nav = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_navigation_controller);

        cAdapter = new ComerciosAdapter(new ArrayList<>(), getContext(), nav);

        viewModel.cargarComercios();

        viewModel.getMldComercios().observe(getViewLifecycleOwner(), new Observer<List<Comercio>>() {
            @Override
            public void onChanged(List<Comercio> comercios) {
                cAdapter.setData(comercios);
            }
        });

        rview.setAdapter(cAdapter);
        return rview;
    }
}