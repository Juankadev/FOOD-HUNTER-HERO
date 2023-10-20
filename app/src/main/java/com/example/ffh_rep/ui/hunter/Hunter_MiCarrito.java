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
import android.widget.ListView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ArticulosListAdapter;
import com.example.ffh_rep.databinding.FragmentHunterMiCarritoBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.factory.CarritoViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class Hunter_MiCarrito extends Fragment {

    private CarritoViewModel carrito;
    private FragmentHunterMiCarritoBinding binding;
    private ArticulosListAdapter alAdapter;
    private ListView lvArticulos;

    public static Hunter_MiCarrito newInstance() {
        return new Hunter_MiCarrito();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterMiCarritoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        lvArticulos = view.findViewById(R.id.hunter_lista_miCarrito);
        alAdapter = new ArticulosListAdapter(new ArrayList<>(), getContext());

        carrito = new ViewModelProvider(requireActivity(), new CarritoViewModelFactory(getActivity())).get(CarritoViewModel.class);

        carrito.getCarrito().observe(getViewLifecycleOwner(), new Observer<List<Articulo>>() {
            @Override
            public void onChanged(List<Articulo> articulos) {
                alAdapter.setlArticulos(articulos);
            }
        });

        lvArticulos.setAdapter(alAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        carrito = new ViewModelProvider(this).get(CarritoViewModel.class);
        // TODO: Use the ViewModel
    }

}