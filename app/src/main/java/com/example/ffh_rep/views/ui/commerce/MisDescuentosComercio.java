package com.example.ffh_rep.views.ui.commerce;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioMisDescuentosBinding;
import com.example.ffh_rep.viewmodels.commerce.MisDescuentosComercioViewModel;
import com.example.ffh_rep.views.adapters.MisDescuentosComercioListAdapter;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.viewmodels.factory.DescuentosViewModelFactory;
import com.example.ffh_rep.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MisDescuentosComercio extends Fragment {
    private MisDescuentosComercioViewModel mViewModel;
    private FragmentComercioMisDescuentosBinding binding;
    private Button btnAddDescuento;
    private GridView gv_descuentos;
    private SessionManager sessionManager;
    private EditText etBuscador;
    private Comercio userSession;
    private MisDescuentosComercioListAdapter mdcListAdapter;
    private TextView tvNoData;

    public static MisDescuentosComercio newInstance() {return new MisDescuentosComercio();}

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
        searcher();
        gv_descuentos.setAdapter(mdcListAdapter);

        return view;
    }

    /**
     * Inicializa las vistas necesarias para la interfaz de usuario.
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    public void initializeViews(View view){
        this.btnAddDescuento = view.findViewById(R.id.btnAddDescuento);
        this.gv_descuentos = view.findViewById(R.id.gv_descuentos_comerciodetail);
        this.tvNoData = view.findViewById(R.id.tvNoData);
        this.etBuscador = view.findViewById(R.id.et_browsermisDescuentos);
    }

    /**
     * Esta función se encarga de configurar un listener para el evento de clic en un botón (btnAddDescuento).
     * Cuando se hace clic en ese botón, se navega a otro destino utilizando el componente de navegación (Navigation).
     * La navegación se realiza mediante el ID de acción action_fragmentAgregarDescuentoComercio_to_agregarDescuento, que está definido
     * en el archivo de recursos de navegación. Esta acción está destinada a llevar al usuario desde el fragmento actual
     * (fragmentAgregarDescuentoComercio) al fragmento de agregar descuento (agregarDescuento).
     */
    public void setUpListeners() {
        btnAddDescuento.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_fragmentAgregarDescuentoComercio_to_agregarDescuento));
    }

    /**
     * Esta función se encarga de inicializar modelos y adaptadores relacionados con la gestión de descuentos comerciales
     */
    public void initModelsAndAdapters(){
        mViewModel = new ViewModelProvider(requireActivity(), new DescuentosViewModelFactory(getActivity())).get(MisDescuentosComercioViewModel.class);
        mdcListAdapter = new MisDescuentosComercioListAdapter(new ArrayList<>(), getContext(), mViewModel);
    }

    /**
     * Este observador responde a cambios en la lista de beneficios y actualiza la interfaz de usuario en consecuencia.
     * Si la lista de beneficios no está vacía, se hace visible el GridView que muestra la lista de beneficios. Además, se oculta el mensaje de "No hay datos".
     * Si la lista de beneficios está vacía, se hace visible el mensaje de "No hay datos" y se oculta el GridView
     */
    public void setUpObserver() {
        mViewModel.getMldListaBeneficios().observe(getViewLifecycleOwner(), new Observer<List<Beneficio>>() {
            @Override
            public void onChanged(List<Beneficio> beneficios) {
                if(beneficios.size()>0)
                {
                    gv_descuentos.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                    mdcListAdapter.setData(beneficios);
                }
                else{
                    tvNoData.setVisibility(View.VISIBLE);
                    gv_descuentos.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * En el método onCreate se está realizando la inicialización inicial necesaria para preparar el fragmento para su uso,
     * específicamente configurando modelos y adaptadores que pueden ser utilizados en la lógica y la interfaz de usuario del fragmento.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModelsAndAdapters();
    }

    /**
     * Esta función searcher() está diseñada para proporcionar una funcionalidad de búsqueda en tiempo real al observar y reaccionar a los
     * cambios en el texto de etBuscador y filtrar la lista de beneficios en consecuencia a través del ViewModel.
     */
    public void searcher(){
        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String _secuente = s.toString();
                mViewModel.applyFilter(_secuente);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}