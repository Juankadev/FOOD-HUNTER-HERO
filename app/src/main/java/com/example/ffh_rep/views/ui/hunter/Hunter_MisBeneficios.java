package com.example.ffh_rep.views.ui.hunter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.factory.HunterMisBeneficiosViewModelFactory;
import com.example.ffh_rep.viewmodels.hunter.HunterMisBeneficiosViewModel;
import com.example.ffh_rep.views.adapters.BeneficioHunterAdapter;

import java.util.ArrayList;

public class Hunter_MisBeneficios extends Fragment {

    private HunterMisBeneficiosViewModel mViewModel;
    private GridView gvBeneficios;

    private Hunter userSession;
    private ProgressBar pbBeneficios;
    private EditText etBuscador;

    private BeneficioHunterAdapter beneAdapter;
    private SessionManager sessionManager;
    private TextView txtError;
    private TextView tvNoData;

    public static Hunter_MisBeneficios newInstance() {
        return new Hunter_MisBeneficios();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hunter__mis_beneficios, container, false);
        initComponents(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();
        mViewModel.cargarMisBeneficios(userSession);

        setUpObservers();
        searcher();
        gvBeneficios.setAdapter(beneAdapter);

        return view;
    }

    public void initComponents(View view){
        gvBeneficios = view.findViewById(R.id.gv_descuentos_comerciodetail);
        mViewModel = new ViewModelProvider(requireActivity(), new HunterMisBeneficiosViewModelFactory(getActivity())).get(HunterMisBeneficiosViewModel.class);
        beneAdapter = new BeneficioHunterAdapter(requireActivity(), new ArrayList<>());
        pbBeneficios = view.findViewById(R.id.pbLoadingBeneficios);
        txtError = view.findViewById(R.id.errorBeneficiosLoad);
        tvNoData = view.findViewById(R.id.tvNoData);
        etBuscador = view.findViewById(R.id.et_browserBeneficios);
    }

    public void setUpObservers(){
        mViewModel.getMlBeneficios().observe(getViewLifecycleOwner(), beneficios -> {
            if(beneficios.size()>0)
            {
                tvNoData.setVisibility(View.GONE);
                beneAdapter.setLista(beneficios);
            }
            else{
                tvNoData.setVisibility(View.VISIBLE);
            }

        });

        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                gvBeneficios.setVisibility(View.GONE);
                pbBeneficios.setVisibility(View.VISIBLE);
            }
        });

        mViewModel.getSuccess().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                gvBeneficios.setVisibility(View.VISIBLE);
                pbBeneficios.setVisibility(View.GONE);
            }
        });

        mViewModel.getError().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                gvBeneficios.setVisibility(View.GONE);
                pbBeneficios.setVisibility(View.GONE);
                txtError.setVisibility(View.VISIBLE);
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
                mViewModel.applyFilter(_secuente);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}