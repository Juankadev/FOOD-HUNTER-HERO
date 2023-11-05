package com.example.ffh_rep.views.ui.admin;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentAdminHomeBinding;
import com.example.ffh_rep.databinding.FragmentComercioHomeBinding;
import com.example.ffh_rep.viewmodels.admin.AdminHomeViewModel;
import com.example.ffh_rep.viewmodels.commerce.ComercioHomeViewModel;
import com.example.ffh_rep.viewmodels.factory.AdminHomeViewModelFactory;
import com.example.ffh_rep.viewmodels.factory.ComercioHomeViewModelFactory;
import com.example.ffh_rep.views.adapters.ArticuloUsuarioComercioListAdapter;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Admin_Home extends Fragment {

    private AdminHomeViewModel mViewModel;
    private FragmentAdminHomeBinding binding;
    private TextView resComercios, resHunters;

    public static Admin_Home newInstance() {
        return new Admin_Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initViews(view);

        CompletableFuture<Integer> futureCantidadComercios = mViewModel.getCantidadComercios();
        futureCantidadComercios.thenAccept(cantidadComercios -> {
            requireActivity().runOnUiThread(() -> {
                resComercios.setText(String.valueOf(cantidadComercios));
            });
        });

        CompletableFuture<Integer> futureCantidadHunters = mViewModel.getCantidadHunters();
        futureCantidadHunters.thenAccept(cantidadHunters -> {
            requireActivity().runOnUiThread(() -> {
                resHunters.setText(String.valueOf(cantidadHunters));
            });
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminHomeViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initViews(View view){
        resComercios = view.findViewById(R.id.resComercios);
        resHunters = view.findViewById(R.id.resHunters);
    }

    public void initModelsAndAdapters(){
        mViewModel = new ViewModelProvider(requireActivity(), new AdminHomeViewModelFactory(getActivity())).get(AdminHomeViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModelsAndAdapters();
    }

}