package com.example.ffh_rep.views.ui.hunter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterReseniasComercioBinding;
import com.example.ffh_rep.viewmodels.hunter.HunterReseniasComercioViewModel;
import com.example.ffh_rep.views.adapters.ReseniasAdapter;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Resenia;
import com.example.ffh_rep.viewmodels.factory.HunterReseniasComercioViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Hunter_ReseniasComercio extends Fragment {

    private HunterReseniasComercioViewModel mViewModel;
    private ReseniasAdapter rAdapter;
    private FragmentHunterReseniasComercioBinding binding;
    private TextView nameShop;
    private GridView gvResenias;
    private Comercio commerce;

    public static Hunter_ReseniasComercio newInstance() {
        return new Hunter_ReseniasComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterReseniasComercioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();

        if(bundle != null){
            if(bundle.containsKey("comercioxresenia")){
                commerce = (Comercio) bundle.getSerializable("comercioxresenia");
            }
        }

        initComponents(view);

        mViewModel.cargarResenias(commerce);

        setUpObservers();

        gvResenias.setAdapter(rAdapter);
        return view;
    }

    public void initComponents(View view){
        gvResenias = view.findViewById(R.id.gv_resenias);

        mViewModel = new ViewModelProvider(requireActivity(),new HunterReseniasComercioViewModelFactory(getActivity())).get(HunterReseniasComercioViewModel.class);
        rAdapter = new ReseniasAdapter(new ArrayList<>(), getContext());


    }

    public void setUpObservers(){
            mViewModel.getReseniasList().observe(getViewLifecycleOwner(), new Observer<List<Resenia>>() {
                @Override
                public void onChanged(List<Resenia> resenias) {
                    Log.d("resenias", resenias.toString());
                    rAdapter.setReseniasList(resenias);
                }
            });
    }



}