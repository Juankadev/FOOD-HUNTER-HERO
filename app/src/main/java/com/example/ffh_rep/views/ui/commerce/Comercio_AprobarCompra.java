package com.example.ffh_rep.views.ui.commerce;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.viewmodels.commerce.ComercioAprobarCompraViewModel;
import com.example.ffh_rep.viewmodels.factory.ComercioAprobarCompraViewModelFactory;
import com.example.ffh_rep.viewmodels.factory.GenerarQrViewModelFactory;
import com.example.ffh_rep.viewmodels.hunter.GenerarQrViewModel;
import com.example.ffh_rep.views.adapters.ArticulosCarritoListAdapter;

import java.util.ArrayList;

public class Comercio_AprobarCompra extends Fragment {

    private ComercioAprobarCompraViewModel mViewModel;
    private GenerarQrViewModel qrController;
    private ArticulosCarritoListAdapter alAdapter;
    private CardView aprobar;
    private TextView txtTileDispatch;
    private ProgressBar pbAprobando;

    private ListView listaProductos;

    private JSONQRRequest response;

    public static Comercio_AprobarCompra newInstance() {
        return new Comercio_AprobarCompra();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercio__aprobar_compra, container, false);

        initComponents(view);
        Bundle args = getArguments();
        if(args != null){
            response = (JSONQRRequest) args.getSerializable("responseQr");
        }
        mViewModel.setIdComercio(response.getIdComercio());
        mViewModel.setIdHunter(response.getIdHunter());
        mViewModel.setMlArticulos(response.getlCarrito());

        setUpObservers();
        setUpListeners();
        listaProductos.setAdapter(alAdapter);

        return view;
    }



    public void initComponents(View view){
        alAdapter = new ArticulosCarritoListAdapter(new ArrayList<>(), getContext());
        mViewModel = new ViewModelProvider(requireActivity(), new ComercioAprobarCompraViewModelFactory(getActivity())).get(ComercioAprobarCompraViewModel.class);
        aprobar = view.findViewById(R.id.btnAprobarCaza);
        listaProductos = view.findViewById(R.id.comercio_lista_productos);
        txtTileDispatch = view.findViewById(R.id.txtTileDispatch);
        pbAprobando = view.findViewById(R.id.pbAprobando);
        qrController = new ViewModelProvider(requireActivity(), new GenerarQrViewModelFactory(getActivity())).get(GenerarQrViewModel.class);
    }

    public void setUpObservers(){
        mViewModel.getMlArticulos().observe(getViewLifecycleOwner(), articulos -> alAdapter.setlArticulos(articulos));

        mViewModel.getLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                txtTileDispatch.setVisibility(View.GONE);
                pbAprobando.setVisibility(View.VISIBLE);
            }
        });


        mViewModel.getSuccess().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                txtTileDispatch.setVisibility(View.VISIBLE);
                pbAprobando.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "APROBADO!", Toast.LENGTH_SHORT);
            }
        });

        mViewModel.getError().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                showErrorDialog();
            }
        });
    }

    public void setUpListeners(){
        aprobar.setOnClickListener(v -> generateAprobe());
    }

    public void generateAprobe(){
        mViewModel.aprobeHunt(response);
    }

    public void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Beneficios");
        builder.setMessage("Ocurrió un error al canjear el beneficio. Intentelo mas tarde");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}