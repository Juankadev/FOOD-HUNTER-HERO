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
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.hunter.ComerciosViewModel;
import com.example.ffh_rep.viewmodels.factory.ComercioViewModelFactory;
import com.example.ffh_rep.views.adapters.ComerciosAdapter;

import java.util.ArrayList;

public class Comercios extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private ComerciosViewModel viewModel;
    private ComerciosAdapter cAdapter;
    private NavController nav;
    private Hunter userSession;
    private SessionManager sessionManager;

    public Comercios() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercios_list, container, false);
        initializeViews(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();
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
        cAdapter = new ComerciosAdapter(new ArrayList<>(), getContext(), nav, 1);
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
            viewModel.cargarComercios(userSession.getUser().getId_usuario());
        }
    }
}

