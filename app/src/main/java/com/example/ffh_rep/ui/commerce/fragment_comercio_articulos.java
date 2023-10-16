package com.example.ffh_rep.ui.commerce;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffh_rep.R;

public class fragment_comercio_articulos extends Fragment {

    private FragmentComercioArticulosViewModel mViewModel;

    public static fragment_comercio_articulos newInstance() {
        return new fragment_comercio_articulos();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comercio_articulos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentComercioArticulosViewModel.class);
        // TODO: Use the ViewModel
    }

}