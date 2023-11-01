package com.example.ffh_rep.ui.hunter;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.adapters.ArticulosCarritoListAdapter;
import com.example.ffh_rep.adapters.ArticulosListAdapter;
import com.example.ffh_rep.databinding.FragmentHunterMiCarritoBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.factory.CarritoViewModelFactory;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Hunter_MiCarrito extends Fragment {

    private CarritoViewModel carrito;
    private FragmentHunterMiCarritoBinding binding;
    private ArticulosCarritoListAdapter alAdapter;
    private ListView lvArticulos;
    private TextView tvPuntos;
    private Button btnEndHunting;
    private List<ItemCarrito> _currChart;

    public static Hunter_MiCarrito newInstance() {
        return new Hunter_MiCarrito();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterMiCarritoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initComponents(view);
        setUpObservers();
        setUpListeners();

        lvArticulos.setAdapter(alAdapter);
        return view;
    }

    public void setUpObservers(){
        carrito.getCarrito().observe(getViewLifecycleOwner(), new Observer<List<ItemCarrito>>() {
            @Override
            public void onChanged(List<ItemCarrito> articulos) {
                alAdapter.setlArticulos(articulos);
                _currChart = articulos;
            }
        });

        carrito.getPuntos().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tvPuntos.setText(String.valueOf(integer));
            }
        });
    }

    public void setUpListeners(){
        btnEndHunting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle args = new Bundle();
                    Gson gson = new Gson();
                    String jsonDataArr = gson.toJson(_currChart);
                    JSONObject jsonData = new JSONObject();
                    JSONArray jArray = new JSONArray(jsonDataArr);
                    jsonData.put("articulos", jArray);

                    String data = jsonData.toString();
                    args.putString("articulos", data);

                    Navigation.findNavController(v).navigate(R.id.action_hunter_MiCarrito_to_hunter_GenerarQr, args);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void initComponents(View view){
        lvArticulos = view.findViewById(R.id.hunter_lista_miCarrito);
        tvPuntos = view.findViewById(R.id.tv_puntos_reemplazar);
        btnEndHunting = view.findViewById(R.id.btnEndHunting);
        alAdapter = new ArticulosCarritoListAdapter(new ArrayList<>(), getContext());

        carrito = new ViewModelProvider(requireActivity(), new CarritoViewModelFactory(getActivity())).get(CarritoViewModel.class);

    }

}