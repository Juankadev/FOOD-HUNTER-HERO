package com.example.ffh_rep.ui.commerce;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ComercioListAdapter;
import com.example.ffh_rep.adapters.MisDescuentosComercioListAdapter;
import com.example.ffh_rep.databinding.FragmentComercioMisDescuentosBinding;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.factory.DescuentosViewModelFactory;
import com.example.ffh_rep.factory.HunterHomeViewModelFactory;
import com.example.ffh_rep.ui.hunter.HunterHomeViewModel;
import com.example.ffh_rep.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MisDescuentosComercio extends Fragment {
    private FragmentComercioMisDescuentosBinding binding;
    private GridView gv_descuentos_container;
    private MisDescuentosComercioListAdapter descuentosComercioListAdapterListAdapter;
    private Button btnMisArticulos, btnEliminarDescuento, btnModificarDescuento, btnAddDescuento;
    private MisDescuentosComercioViewModel mViewModel;
    private TextView shopName, descDescuento, puntosDescuento, idDescuento;

    public static MisDescuentosComercio newInstance() {
        return new MisDescuentosComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentComercioMisDescuentosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initializeViews(view);
        setUpListeners();
        setupBeneficiosGridView();
        //setUpObserver();
        return view;
    }
    /**
     * Inicializa las vistas necesarias para la interfaz de usuario.
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    public void initializeViews(View view){
        this.shopName = view.findViewById(R.id.et_ShopName);
        this.idDescuento = view.findViewById(R.id.IdDescuento);
        this.descDescuento = view.findViewById(R.id.etDescDescuento);
        this.puntosDescuento = view.findViewById(R.id.etPuntosReq);

        this.btnMisArticulos = view.findViewById(R.id.btnMisArticulos);
        this.btnEliminarDescuento = view.findViewById(R.id.btnEliminarDescuento);
        this.btnModificarDescuento = view.findViewById(R.id.btnModificarDescuento);
        this.btnAddDescuento = view.findViewById(R.id.btnAddDescuento);

        this.gv_descuentos_container = view.findViewById(R.id.gv_descuentos_container);

        this.mViewModel = new ViewModelProvider(requireActivity(), new DescuentosViewModelFactory(getActivity())).get(MisDescuentosComercioViewModel.class);
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
    /**
     * Configura el GridView de beneficios con un adaptador y observa los cambios en la lista de beneficios desde el ViewModel.
     * Carga inicialmente los beneficios y actualiza la interfaz gráfica cuando hay cambios en la lista.
     */
    private void setupBeneficiosGridView() {
        descuentosComercioListAdapterListAdapter = new MisDescuentosComercioListAdapter(getContext(), new ArrayList<>());
        gv_descuentos_container.setAdapter(descuentosComercioListAdapterListAdapter);

        mViewModel.listarDescuentos();
        mViewModel.getMldListaBeneficios().observe(getViewLifecycleOwner(), beneficios -> descuentosComercioListAdapterListAdapter.setBeneficiosList(beneficios));
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

    /*private void setUpObserver(){
        mViewModel.getMldBeneficio().observe(getViewLifecycleOwner(), new Observer<Beneficio>() {
            @Override
            public void onChanged(Beneficio beneficio) {
                Log.d("beneficios", beneficio.toString());
            }
        });

        mViewModel.getMldListaBeneficios().observe(getViewLifecycleOwner(), new Observer<List<Beneficio>>() {
            @Override
            public void onChanged(List<Beneficio> beneficios) {
                Log.d("beneficios", beneficios.toString());
            }
        });
    }*/

}