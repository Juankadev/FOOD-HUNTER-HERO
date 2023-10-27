package com.example.ffh_rep.ui.commerce;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioMisDescuentosBinding;
import com.example.ffh_rep.databinding.FragmentMisDescuentosItemBinding;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.factory.CarritoViewModelFactory;
import com.example.ffh_rep.factory.DescuentosViewModelFactory;
import com.example.ffh_rep.repositories.DescuentoRepository;
import com.example.ffh_rep.ui.hunter.CarritoViewModel;

import java.util.List;

public class MisDescuentosComercio extends Fragment {

    private FragmentComercioMisDescuentosBinding binding;
    private DescuentosViewModel mViewModel;
    private TextView shopName, descDescuento, puntosDescuento, idDescuento;
    private Button btnMisArticulos, btnEliminarDescuento, btnModificarDescuento;

    public static MisDescuentosComercio newInstance() {
        return new MisDescuentosComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentComercioMisDescuentosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initComponentes(view);
        this.mViewModel.listarDescuentos();
        setUpListeners();
        setUpObserver();

        return view;
    }

    public void initComponentes(View view){
        this.shopName = view.findViewById(R.id.et_ShopName);
        this.idDescuento = view.findViewById(R.id.IdDescuento);
        this.descDescuento = view.findViewById(R.id.etDescDescuento);
        this.puntosDescuento = view.findViewById(R.id.etPuntosReq);

        this.btnMisArticulos = view.findViewById(R.id.btnMisArticulos);
        this.btnEliminarDescuento = view.findViewById(R.id.btnEliminarDescuento);
        //btnModificarDescuento = view.findViewById(R.id.btnModificarDescuento);

        this.mViewModel = new ViewModelProvider(requireActivity(), new DescuentosViewModelFactory(getActivity())).get(DescuentosViewModel.class);
    }

    public void setUpListeners(){
        btnEliminarDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = Integer.parseInt(idDescuento.getText().toString());

                Beneficio beneficio = new Beneficio();
                beneficio.setId_beneficio(id);

            }
        });

        /*.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MisDescuentosComercio.this).navigate(R.id.modificarDescuento);
                // ENVIARLE EL ID DEL DESCUENTO A MODIFICAR
            }
        });*/

      /*  btnMisArticulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MisDescuentosComercio.this).navigate(R.id.fragment_comercio_articulos);
            }
        });*/
    }

    private void setUpObserver(){
        mViewModel.getMldBeneficio().observe(getViewLifecycleOwner(), new Observer<Beneficio>() {
            @Override
            public void onChanged(Beneficio beneficio) {
                Log.d("beneficios", beneficio.toString());
            }
        });

        mViewModel.getMldListaBeneficios().observe(getViewLifecycleOwner(), new Observer<List<Beneficio>>() {
            @Override
            public void onChanged(List<Beneficio> beneficios) {
                Log.d("beneficios", beneficios.toString());
            }
        });
    }

}