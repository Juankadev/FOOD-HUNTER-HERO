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
import android.widget.Toast;

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
        initializeViews(view);
        setupRecyclerView();
        observeViewModel();

        return view;
    }
    /**
     * Inicializa las vistas necesarias para la interfaz de comercios.
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    private void initializeViews(View view) {
        RecyclerView rview = (RecyclerView) view;
        nav = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_navigation_controller);
        cAdapter = new ComerciosAdapter(new ArrayList<>(), getContext(), nav);
        rview.setAdapter(cAdapter);
    }

    private void setupRecyclerView() {
    }
    /**
     * Observa los cambios en el ViewModel y realiza acciones correspondientes.
     * - Actualiza el adaptador con la lista de comercios cuando hay cambios.
     * - Verifica si hay argumentos proporcionados y carga los comercios correspondientes, aplicando el filtro por razón social.
     * - Si no hay argumentos, carga todos los comercios.
     */
    private void observeViewModel() {
        viewModel = new ViewModelProvider(this, new ComercioViewModelFactory(getActivity())).get(ComerciosViewModel.class);

        // Observa los cambios en la lista de comercios y actualiza el adaptador.
        viewModel.getMldComercios().observe(getViewLifecycleOwner(), comercios -> {
            cAdapter.setData(comercios);

            // Verifica si la lista de comercios está vacía y muestra un Toast en consecuencia.
            if (comercios == null || comercios.isEmpty()) {
                Toast.makeText(getContext(), "No se encontraron comercios.", Toast.LENGTH_SHORT).show();
            }
        });

        // Verifica si hay argumentos y carga los comercios correspondientes.
        Bundle args = getArguments();
        if (args != null) {
            String filtro = args.getString("razonSocial");
            cargarComercios(filtro);
        }
        else
        {
            cargarComercios(null);
        }
    }
    /**
     * Carga los comercios según el filtro de razón social proporcionado.
     * @param razonSocial Filtro de razón social para buscar comercios específicos. Puede ser nulo para cargar todos los comercios.
     */
    private void cargarComercios(String razonSocial) {
        if(razonSocial != null)
        {
            viewModel.cargarComerciosByName(razonSocial);
        }
        else
        {
            viewModel.cargarComercios();
        }
    }
}

