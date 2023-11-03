package com.example.ffh_rep.views.ui.hunter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterVerBeneficiosBinding;
import com.example.ffh_rep.viewmodels.hunter.HunterVerBeneficiosViewModel;
import com.example.ffh_rep.views.adapters.BeneficiosAdapterHunter;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.viewmodels.factory.HunterVerBeneficiosViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class Hunter_VerBeneficios extends Fragment {

    private HunterVerBeneficiosViewModel mViewModel;
    private FragmentHunterVerBeneficiosBinding binding;
    private GridView gvBeneficios;
    private BeneficiosAdapterHunter bAdapter;
    private Comercio commerce;

    public static Hunter_VerBeneficios newInstance() {
        return new Hunter_VerBeneficios();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterVerBeneficiosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Bundle bundle = getArguments();
        if(bundle != null){
            commerce = (Comercio) bundle.getSerializable("comerciobeneficios");
        }

        initComponents(view);


        mViewModel.cargarBeneficios(commerce);
        setUpObserver();

        gvBeneficios.setAdapter(bAdapter);
        return view;
    }

    public void initComponents(View view){
        gvBeneficios = view.findViewById(R.id.gv_beneficios);
        bAdapter = new BeneficiosAdapterHunter(requireContext(), new ArrayList<>());
        mViewModel = new ViewModelProvider(requireActivity(), new HunterVerBeneficiosViewModelFactory(getActivity())).get(HunterVerBeneficiosViewModel.class);
    }

    public void setUpObserver(){
            mViewModel.getListBeneficios().observe(getViewLifecycleOwner(), new Observer<List<Beneficio>>() {
                @Override
                public void onChanged(List<Beneficio> beneficios) {
                    bAdapter.setlBeneficios(beneficios);
                }
            });
    }

    public void setUpListeners(){

    }

}