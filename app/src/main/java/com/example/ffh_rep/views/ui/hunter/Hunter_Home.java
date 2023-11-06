package com.example.ffh_rep.views.ui.hunter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.se.omapi.Session;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterHomeBinding;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.hunter.HunterHomeViewModel;
import com.example.ffh_rep.views.adapters.ArticulosListAdapter;
import com.example.ffh_rep.views.adapters.ComercioListAdapter;
import com.example.ffh_rep.viewmodels.factory.HunterHomeViewModelFactory;

import java.util.ArrayList;

public class Hunter_Home extends Fragment {
    private FragmentHunterHomeBinding binding;
    private GridView gvComerciosList, gvProductosHunterHome;
    private ProgressBar pbComercios, pbArticulos;
    private ComercioListAdapter comercioListAdapter;
    private ArticulosListAdapter articulosListAdapter;
    private Button btnMasComercios, btnMasArticulos;
    private HunterHomeViewModel viewModel;
    private EditText etBrowse;

    private Hunter userSession;
    private SessionManager sessionManager;

    public static Hunter_Home newInstance() {
        return new Hunter_Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initializeViews(view);
        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();

        setupListeners();
        setupViewModel();
        setupComerciosGridView();
        setupArticulosGridView();
        return view;
    }
    /**
     * Inicializa las vistas necesarias para la interfaz de registro.
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    private void initializeViews(View view) {
        gvComerciosList = view.findViewById(R.id.gv_comercios_hunter_home);
        gvProductosHunterHome = view.findViewById(R.id.gv_productos_hunter_home);
        btnMasComercios = view.findViewById(R.id.btn_mas_comercios_hunter);
        btnMasArticulos = view.findViewById(R.id.btn_mas_articulos_hunter);
        etBrowse = view.findViewById(R.id.et_browser);
        pbArticulos = view.findViewById(R.id.pbComercios);
        pbComercios = view.findViewById(R.id.pbArticulos);
    }
    /**
     * Configura los listeners de la interfaz.
     * Asigna los métodos correspondientes a los eventos.
     */
    private void setupListeners() {
        btnMasComercios.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.comercios));
        btnMasArticulos.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.articulos));
        etBrowse.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    onSearch();
                    return true;
                }
                return false;
            }
        });

    }
    /**
     * Realiza una búsqueda utilizando el texto ingresado en el campo de búsqueda.
     * Navega hacia el fragmento de comercios y pasa el texto de búsqueda como argumento.
     */
    private void onSearch() {
        Bundle args = new Bundle();
        args.putString("razonSocial", etBrowse.getText().toString());
        NavHostFragment.findNavController(this).navigate(R.id.comercios, args);
    }
    /**
     * Configura y obtiene la instancia del ViewModel asociado a la pantalla de inicio del cazador (Hunter Home).
     * Utiliza el HunterHomeViewModelFactory para proporcionar el contexto de la actividad.
     */
    private void setupViewModel() {
        viewModel = new ViewModelProvider(requireActivity(), new HunterHomeViewModelFactory(getActivity())).get(HunterHomeViewModel.class);
    }
    /**
     * Configura el GridView de comercios con un adaptador y observa los cambios en la lista de comercios desde el ViewModel.
     * Carga inicialmente los comercios y actualiza la interfaz gráfica cuando hay cambios en la lista.
     */
    private void setupComerciosGridView() {
        comercioListAdapter = new ComercioListAdapter(getContext(), new ArrayList<>());
        gvComerciosList.setAdapter(comercioListAdapter);

        viewModel.cargarComercios(userSession.getUser().getId_usuario());
        viewModel.getMlDataComercio().observe(getViewLifecycleOwner(), comercios -> comercioListAdapter.setComercioList(comercios));
        viewModel.getSuccessComercios().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                gvComerciosList.setVisibility(View.VISIBLE);
                pbComercios.setVisibility(View.GONE);
            }
        });
    }
    /**
     * Configura el GridView de articulos con un adaptador y observa los cambios en la lista de articulos desde el ViewModel.
     * Carga inicialmente los articulos y actualiza la interfaz gráfica cuando hay cambios en la lista.
     */
    private void setupArticulosGridView() {
        articulosListAdapter = new ArticulosListAdapter(this.getContext(),  new ArrayList<>());
        gvProductosHunterHome.setAdapter(articulosListAdapter);

        viewModel.cargarListArticulo();
        viewModel.getMlDataArticulo().observe(getViewLifecycleOwner(), articulos -> articulosListAdapter.setlArticulos(articulos));
        viewModel.getSucessArticulos().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean) {
                gvProductosHunterHome.setVisibility(View.VISIBLE);
                pbArticulos.setVisibility(View.GONE);
            }
        });
    }
}
