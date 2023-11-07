package com.example.ffh_rep.views.ui.hunter;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterMiCarritoBinding;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.entidades.QrObject;
import com.example.ffh_rep.interfaces.CarritoActionsCallback;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.factory.GenerarQrViewModelFactory;
import com.example.ffh_rep.viewmodels.hunter.CarritoViewModel;
import com.example.ffh_rep.viewmodels.hunter.GenerarQrViewModel;
import com.example.ffh_rep.views.adapters.ArticulosCarritoListAdapter;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.viewmodels.factory.CarritoViewModelFactory;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Hunter_MiCarrito extends Fragment implements CarritoActionsCallback {

    private CarritoViewModel carrito;
    private GenerarQrViewModel qrController;
    private FragmentHunterMiCarritoBinding binding;
    private ArticulosCarritoListAdapter alAdapter;
    private ProgressBar pbFinalizando;
    private ListView lvArticulos;
    private TextView tvPuntos;
    private CardView btnEndHunting;
    private List<ItemCarrito> _currChart;
    private Comercio comercio;
    private Hunter userSession;
    private SessionManager sessionManager;

    public static Hunter_MiCarrito newInstance() {
        return new Hunter_MiCarrito();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterMiCarritoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initComponents(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();
        comercio = carrito.getComercio();

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

        qrController.getLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                pbFinalizando.setVisibility(View.VISIBLE);
            }
        });

        qrController.getSuccess().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                pbFinalizando.setVisibility(View.GONE);
                generateJsonAndRedirect();
            }
        });


        qrController.getError().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                pbFinalizando.setVisibility(View.GONE);
                Toast.makeText(requireActivity(), "No se ha podido Finalizar la caza, intente mas tarde", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setUpListeners(){
        btnEndHunting.setOnClickListener( v -> generateQr());
    }

    public void initComponents(View view){
        lvArticulos = view.findViewById(R.id.hunter_lista_miCarrito);
        tvPuntos = view.findViewById(R.id.tv_puntos_reemplazar);
        btnEndHunting = view.findViewById(R.id.btnEndHunting);
        pbFinalizando = view.findViewById(R.id.pbFinalizando);
        alAdapter = new ArticulosCarritoListAdapter(new ArrayList<>(), getContext(), this);
        qrController = new ViewModelProvider(requireActivity(), new GenerarQrViewModelFactory(getActivity())).get(GenerarQrViewModel.class);
        carrito = new ViewModelProvider(requireActivity(), new CarritoViewModelFactory(getActivity())).get(CarritoViewModel.class);
    }

    @Override
    public void onSumArticuloCallback(ItemCarrito item) {
        carrito.addOneUnitToCart(item, alAdapter);
    }

    @Override
    public void onAbsArticuloCallback(ItemCarrito item) {
        if(item.getCantidad() - 1 >= 1){
            carrito.subtractUnitsFromCart(item, alAdapter);
        }
        else{
            Toast.makeText(requireActivity(),"Si quieres dejar en 0 el articulo, por favor eliminalo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteArticuloCallback(ItemCarrito item) {
        showConfirmDialog(item);
    }

    public void showConfirmDialog(ItemCarrito item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Eliminar Articulo");
        builder.setMessage("¿Estás seguro que quieres eliminar el artículo?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carrito.removeItemFromCart(item, alAdapter);
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

    public void generateQr(){
        QrObject qr = new QrObject();
        qr.setHunter(userSession);
        qr.setCommerce(comercio);
        qrController.generarQr(qr);
    }

    public void generateJsonAndRedirect(){
        Bundle args = new Bundle();
        Gson gson = new Gson();
        JSONQRRequest req = new JSONQRRequest(userSession.getIdHunter(), _currChart, comercio.getId(), carrito.getPuntos().getValue());
        String json = gson.toJson(req);
        args.putString("json_request", json);
        Navigation.findNavController(requireView()).navigate(R.id.action_hunter_MiCarrito_to_hunter_GenerarQr, args);
    }
}