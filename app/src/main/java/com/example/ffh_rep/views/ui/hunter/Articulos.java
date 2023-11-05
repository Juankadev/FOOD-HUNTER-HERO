package com.example.ffh_rep.views.ui.hunter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.viewmodels.hunter.ArticulosViewModel;
import com.example.ffh_rep.views.adapters.ArticulosAdapter;
import com.example.ffh_rep.viewmodels.factory.ArticuloViewModelFactory;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class Articulos extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private ArticulosViewModel viewModel;
    private ArticulosAdapter aAdapter;
    private NavController nav;
    private int mColumnCount = 2;

    public Articulos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articulos_list, container, false);
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
        aAdapter = new ArticulosAdapter(new ArrayList<>(), getContext(), nav);
        rview.setAdapter(aAdapter);
    }

    private void setupRecyclerView() {
    }
    /**
     * Observa los cambios en el ViewModel y realiza acciones correspondientes.
     * - Actualiza el adaptador con la lista de articulos cuando hay cambios.
     */
    private void observeViewModel() {
        viewModel = new ViewModelProvider(this, new ArticuloViewModelFactory(getActivity())).get(ArticulosViewModel.class);

        // Observa los cambios en la lista de articulos y actualiza el adaptador.
        viewModel.getMldArticulos().observe(getViewLifecycleOwner(), articulos -> {
            aAdapter.setData(articulos);

            // Verifica si la lista de articulos está vacía y muestra un Toast en consecuencia.
            if (articulos == null || articulos.isEmpty()) {
                Toast.makeText(getContext(), "No se encontraron comercios.", Toast.LENGTH_SHORT).show();
            }
        });

        cargarArticulos();
    }
    /**
     * Carga los articulos.
     */
    private void cargarArticulos() {
            viewModel.cargarArticulos();
    }
}