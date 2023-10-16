package com.example.ffh_rep.ui.hunter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffh_rep.R;

public class Hunter_ReseniarComercio extends Fragment {

    private HunterReseniarComercioViewModel mViewModel;

    public static Hunter_ReseniarComercio newInstance() {
        return new Hunter_ReseniarComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hunter__reseniar_comercio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HunterReseniarComercioViewModel.class);
        // TODO: Use the ViewModel
    }

}