package com.example.ffh_rep.ui.commerce;

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

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentCommerceMisArticulosBinding;
import com.example.ffh_rep.entidades.Comercio;

public class Commerce_MisArticulos extends Fragment {

    private CommerceMisArticulosViewModel mViewModel;
    private FragmentCommerceMisArticulosBinding binding;
    private Button btnAniadirArticulo;


    public static Commerce_MisArticulos newInstance() {
        return new Commerce_MisArticulos();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCommerceMisArticulosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        initViews(view);
        setUpListeners();
        return view;
    }


    private void initViews(View view){
        btnAniadirArticulo = view.findViewById(R.id.btn_commerce_addArticle);
    }

    private void setUpListeners(){
        btnAniadirArticulo.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_commerce_MisArticulos_to_agregarArticulo));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CommerceMisArticulosViewModel.class);
        // TODO: Use the ViewModel
    }

}