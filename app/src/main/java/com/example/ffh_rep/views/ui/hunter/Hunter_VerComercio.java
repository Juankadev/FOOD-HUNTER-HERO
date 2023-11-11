package com.example.ffh_rep.views.ui.hunter;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterVerComercioBinding;
import com.example.ffh_rep.viewmodels.hunter.CarritoViewModel;
import com.example.ffh_rep.viewmodels.hunter.HunterVerComercioViewModel;
import com.example.ffh_rep.views.adapters.ArticuloComercioListAdapter;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.viewmodels.factory.CarritoViewModelFactory;
import com.example.ffh_rep.viewmodels.factory.HunterVerComercioViewModelFactory;
import com.example.ffh_rep.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class Hunter_VerComercio extends Fragment {

    private HunterVerComercioViewModel mViewModel;
    private CarritoViewModel carrito;
    private Comercio commerce;
    private LinearLayout bottomBar;
    private TextView descripcion, cantArticulos, tvNoData;
    private ImageView favDispatch, favDispatch_filled;
    private GridView gv_articulos;
    private EditText etBuscador;
    private Button btnAbrirCarrito, btnVerResenias, btnVerBeneficios;
    private ProgressBar pBarMarkingAsFav;
    private FragmentHunterVerComercioBinding binding;
    private ArticuloComercioListAdapter aclAdapter;

    private SessionManager sessionManager;
    private Hunter userSession;

    public static Hunter_VerComercio newInstance() {
        return new Hunter_VerComercio();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterVerComercioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initializeComponents(view);
        Bundle bundle = getArguments();

        if(bundle != null){
            if(bundle.containsKey("comercioSelect")){
                commerce = (Comercio) bundle.getSerializable("comercioSelect");
            }
        }

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();

        mViewModel.getStockbyArticulos(commerce);
        carrito.setComercio(commerce);

        isThisFavorite(commerce.isFavorite());
        searcher();
        setUpListeners();
        setUpObservers();
        descripcion.setText(commerce.getRazonSocial());
        gv_articulos.setAdapter(aclAdapter);
        return view;
    }


    public void initializeComponents(View view){
        descripcion = view.findViewById(R.id.txt_shop_name_description);
        gv_articulos = view.findViewById(R.id.gv_articulos_comerciodetail);
        btnAbrirCarrito = view.findViewById(R.id.btnAbrirCarrito);
        cantArticulos = view.findViewById(R.id.tv_cantArticulos);
        bottomBar = view.findViewById(R.id.bottomNavCart);
        favDispatch = view.findViewById(R.id.favDispatcher);
        favDispatch_filled = view.findViewById(R.id.favDispatcher_filled);
        pBarMarkingAsFav  = view.findViewById(R.id.progressBarMarkingFav);
        btnVerResenias = view.findViewById(R.id.btn_HunterVerReseñasComercio);
        btnVerBeneficios = view.findViewById(R.id.btn_hunterVerBeneficios);
        etBuscador = view.findViewById(R.id.et_browser_articulos);
        tvNoData = view.findViewById(R.id.tvNoData);
    }

    public void initModelsAndAdapters(){

        mViewModel = new ViewModelProvider(requireActivity(), new HunterVerComercioViewModelFactory(getActivity())).get(HunterVerComercioViewModel.class);
        carrito = new ViewModelProvider(requireActivity(), new CarritoViewModelFactory(getActivity())).get(CarritoViewModel.class);
        aclAdapter = new ArticuloComercioListAdapter(new ArrayList<>(), getContext());
    }

    public void setUpListeners(){
        btnAbrirCarrito.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_hunter_VerComercio_to_hunter_MiCarrito));
        favDispatch.setOnClickListener(v -> markAsFav());
        favDispatch_filled.setOnClickListener(v -> dismarkAsFav());
        btnVerResenias.setOnClickListener(v -> redirectToResenias());
        btnVerBeneficios.setOnClickListener(v-> redirectToBeneficios());
    }

    public void pressBackValidation(){
        Log.d("pressback", "llamando");
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(carrito.getTotalIntegervalue() > 0 && carrito.getTotalIntegervalue() != null){
                    mensajeSalir();
                }
                else{
                    Navigation.findNavController(requireView()).popBackStack();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void setUpObservers(){

        mViewModel.getMldStockArticulos().observe(getViewLifecycleOwner(), stocks->{
            if(stocks.size() > 0){
                tvNoData.setVisibility(View.GONE);
                gv_articulos.setVisibility(View.VISIBLE);
                aclAdapter.setData(stocks);
            }
            else{
                tvNoData.setVisibility(View.VISIBLE);
                gv_articulos.setVisibility(View.GONE);
            }
        });

        carrito.getCarrito().observe(getViewLifecycleOwner(), new Observer<List<ItemCarrito>>() {
            @Override
            public void onChanged(List<ItemCarrito> articulos) {
                Log.d("carrito actual", articulos.toString());
            }
        });


        carrito.getTotArticulos().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer > 0){
                    cantArticulos.setText(integer + " articulo(s) en carrito");
                    bottomBar.setVisibility(View.VISIBLE);
                }
                else{
                    bottomBar.setVisibility(View.GONE);
                }
            }
        });

        mViewModel.getIsMarkingAsFav().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    favDispatch.setVisibility(View.GONE);
                    pBarMarkingAsFav.setVisibility(View.VISIBLE);
                }
            }
        });

        mViewModel.getErrorMarking().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    pBarMarkingAsFav.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Ha ocurrido un error al marcar como favorito al comercio.. Intentelo mas tarde", Toast.LENGTH_LONG).show();
                    favDispatch.setVisibility(View.VISIBLE);
                }
            }
        });

        mViewModel.getMarkSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    favDispatch_filled.setVisibility(View.VISIBLE);
                    favDispatch.setVisibility(View.GONE);
                    pBarMarkingAsFav.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Se ha marcado el comercio como favorito!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mViewModel.getDismarkSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    favDispatch_filled.setVisibility(View.GONE);
                    favDispatch.setVisibility(View.VISIBLE);
                    pBarMarkingAsFav.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Se ha desmarcado el comercio como favorito!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mViewModel.getIsDismarkingAsFav().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    favDispatch_filled.setVisibility(View.GONE);
                    pBarMarkingAsFav.setVisibility(View.VISIBLE);
                }
            }
        });

        mViewModel.getErrorDismarking().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    pBarMarkingAsFav.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Ha ocurrido un error al desmarcar como favorito al comercio.. Intentelo mas tarde", Toast.LENGTH_LONG).show();
                    favDispatch_filled.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void markAsFav(){
        mViewModel.markAsFav(this.commerce, this.userSession);
    }

    public void dismarkAsFav(){mViewModel.dismarkAsFav(this.commerce, this.userSession);}


    public void mensajeSalir() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmar salida");
        builder.setMessage("Hay artículos en el carrito. ¿Estás seguro de que quieres salir?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carrito.clearChart();
                dialog.dismiss();
                Navigation.findNavController(requireView()).popBackStack();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModelsAndAdapters();
        pressBackValidation();
    }



    public void redirectToResenias(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("comercioxresenia", commerce);
        Navigation.findNavController(requireView()).navigate(R.id.action_hunter_VerComercio_to_hunter_ReseniasComercio, bundle);
    }

    public void redirectToBeneficios(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("comerciobeneficios", commerce);
        Navigation.findNavController(requireView()).navigate(R.id.action_hunter_VerComercio_to_hunter_VerBeneficios, bundle);

    }

    public void isThisFavorite(boolean value){
        mViewModel.resetFavAtts();
        if(value){
            favDispatch.setVisibility(View.GONE);
            favDispatch_filled.setVisibility(View.VISIBLE);
        }
        else{
            favDispatch.setVisibility(View.VISIBLE);
            favDispatch_filled.setVisibility(View.GONE);
        }
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