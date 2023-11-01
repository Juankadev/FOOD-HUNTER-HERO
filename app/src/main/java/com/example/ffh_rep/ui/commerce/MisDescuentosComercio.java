package com.example.ffh_rep.ui.commerce;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.MisDescuentosComercioListAdapter;
import com.example.ffh_rep.databinding.FragmentComercioMisDescuentosBinding;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.factory.DescuentosViewModelFactory;
import com.example.ffh_rep.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MisDescuentosComercio extends Fragment {
    private MisDescuentosComercioViewModel mViewModel;
    private FragmentComercioMisDescuentosBinding binding;
    private Button btnEliminarDescuento, btnModificarDescuento, btnAddDescuento, btnMisArticulos;
    private GridView gv_descuentos;
    private TextView  idDescuento, descDescuento, puntosDescuento;
    private SessionManager sessionManager;
    private Comercio userSession;
    private MisDescuentosComercioListAdapter mdcListAdapter;


    public static MisDescuentosComercio newInstance() {
        return new MisDescuentosComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentComercioMisDescuentosBinding.inflate(inflater, container, false);

        View view = binding.getRoot();
        initializeViews(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getCommerceSession();
        mViewModel.listarDescuentos(userSession.getId());

        setUpListeners();
        setUpObserver();

        gv_descuentos.setAdapter(mdcListAdapter);

        return view;
    }
    /**
     * Inicializa las vistas necesarias para la interfaz de usuario.
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    public void initializeViews(View view){
        this.idDescuento = view.findViewById(R.id.tv_item_descuento_id);
        this.descDescuento = view.findViewById(R.id.tv_item_descuento_descripcion);
        this.puntosDescuento = view.findViewById(R.id.tv_item_descuento_puntos);

        this.btnMisArticulos = view.findViewById(R.id.btnMisArticulos);
        this.btnEliminarDescuento = view.findViewById(R.id.btnEliminarDescuento);
        this.btnModificarDescuento = view.findViewById(R.id.btnModificarDescuento);
        this.btnAddDescuento = view.findViewById(R.id.btnAddDescuento);

        this.gv_descuentos = view.findViewById(R.id.gv_descuentos_comerciodetail);
    }

    public void initModelsAndAdapters(){
        mViewModel = new ViewModelProvider(requireActivity(), new DescuentosViewModelFactory(getActivity())).get(MisDescuentosComercioViewModel.class);
        mdcListAdapter = new MisDescuentosComercioListAdapter(new ArrayList<>(), getContext());
    }

    public void setUpObserver() {
        mViewModel.getMldListaBeneficios().observe(getViewLifecycleOwner(), new Observer<List<Beneficio>>() {
            @Override
            public void onChanged(List<Beneficio> beneficios) {
                mdcListAdapter.setData(beneficios);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModelsAndAdapters();
    }

    /**
     * Configura los listeners de la interfaz.
     * Asigna los métodos correspondientes a los eventos.
     */
    public void setUpListeners() {
        btnEliminarDescuento.setOnClickListener(v-> eliminarBeneficio());
        btnAddDescuento.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.agregarDescuento));
        btnMisArticulos.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.commerce_MisArticulos));
        // ENVIARLE EL ID DEL DESCUENTO A MODIFICAR
        btnModificarDescuento.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.modificarDescuento));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MisDescuentosComercioViewModel.class);
        // TODO: Use the ViewModel
    }

    /**
     * Metodo utilizado para eliminar beneficios
     */
    private void eliminarBeneficio(){
        // OBTENGO EL ID DEL TXT
        Integer id = Integer.parseInt(idDescuento.getText().toString());

        /// INSTANCIO UN OBJETO BENEFICIO Y SETEO EL VALOR
        Beneficio beneficio = new Beneficio();
        beneficio.setId_beneficio(id);

        /// USO EL METODO PARA ELIMINAR EL BENEFICIO
        mViewModel.eliminarBeneficio(beneficio);
    }

}