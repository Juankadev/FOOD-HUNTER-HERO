package com.example.ffh_rep.views.ui.hunter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentMisComprasListBinding;
import com.example.ffh_rep.entidades.Caza;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.commerce.MisComprasViewModel;
import com.example.ffh_rep.viewmodels.factory.MisComprasViewModelFactory;
import com.example.ffh_rep.views.adapters.MisComprasViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class MisCompras extends Fragment {

    private FragmentMisComprasListBinding binding;
    private MisComprasViewAdapter listAdapter;
    private ListView lvMisCompras;
    private TextView tvPuntos, tvComercio, tvFecha;
    private EditText etBuscador;
    private Hunter userSession;
    private SessionManager sessionManager;
    private MisComprasViewModel viewModel;
    private TextView tvNoData;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MisCompras() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMisComprasListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initComponents(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();

        viewModel.cargarCazasPorIdHunter(userSession.getIdHunter());

        setUpObservers();
        searcher();
        lvMisCompras.setAdapter(listAdapter);
        return view;
    }

    public void initComponents(View view){
        lvMisCompras = view.findViewById(R.id.hunter_lista_miCarrito);
        tvPuntos = view.findViewById(R.id.tvPuntos);
        tvComercio = view.findViewById(R.id.razonSocial);
        tvFecha = view.findViewById(R.id.tvFecha);
        tvNoData = view.findViewById(R.id.tvNoData);
        etBuscador = view.findViewById(R.id.et_browserMisCompras);
        listAdapter = new MisComprasViewAdapter(new ArrayList<>(), viewModel, getContext());
        viewModel = new ViewModelProvider(requireActivity(), new MisComprasViewModelFactory(getActivity())).get(MisComprasViewModel.class);
    }

    public void setUpObservers(){
        viewModel.getCazasDelHunter().observe(getViewLifecycleOwner(), new Observer<List<Caza>>() {
            @Override
            public void onChanged(List<Caza> cazas) {
                if(cazas.size()>0)
                {
                    lvMisCompras.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                    listAdapter.setData(cazas);
                }
                else{
                    tvNoData.setVisibility(View.VISIBLE);
                    lvMisCompras.setVisibility(View.GONE);
                }

            }
        });
    }

    public void searcher(){
        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String _secuente = s.toString();
                viewModel.applyFilter(_secuente);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}