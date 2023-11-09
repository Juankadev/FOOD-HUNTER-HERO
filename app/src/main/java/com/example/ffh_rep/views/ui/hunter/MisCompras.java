package com.example.ffh_rep.views.ui.hunter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private Hunter userSession;
    private SessionManager sessionManager;
    private MisComprasViewModel viewModel;

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

        lvMisCompras.setAdapter(listAdapter);
        return view;
    }

    public void initComponents(View view){
        lvMisCompras = view.findViewById(R.id.hunter_lista_miCarrito);
        tvPuntos = view.findViewById(R.id.tvPuntos);
        tvComercio = view.findViewById(R.id.razonSocial);
        tvFecha = view.findViewById(R.id.tvFecha);
        listAdapter = new MisComprasViewAdapter(new ArrayList<>(), viewModel, getContext());
        viewModel = new ViewModelProvider(requireActivity(), new MisComprasViewModelFactory(getActivity())).get(MisComprasViewModel.class);
    }

    public void setUpObservers(){
        viewModel.getCazasDelHunter().observe(getViewLifecycleOwner(), new Observer<List<Caza>>() {
            @Override
            public void onChanged(List<Caza> cazas) {
                listAdapter.setData(cazas);
            }
        });
    }
}