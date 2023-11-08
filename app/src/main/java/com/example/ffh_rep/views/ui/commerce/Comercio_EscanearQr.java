package com.example.ffh_rep.views.ui.commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioEscanearQrBinding;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.utils.CustomDateAdapter;
import com.example.ffh_rep.viewmodels.commerce.ComercioEscanearQrViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//FALTA DESARROLLAR
public class Comercio_EscanearQr extends Fragment {

    private ComercioEscanearQrViewModel mViewModel;
    private FragmentComercioEscanearQrBinding binding;


    public static Comercio_EscanearQr newInstance() {
        return new Comercio_EscanearQr();
    }
    private ActivityResultLauncher<Intent> qrScanLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String contenido = data.getStringExtra("SCAN_RESULT");
                        if (contenido != null) {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.registerTypeAdapter(Date.class, new CustomDateAdapter());
                            Gson gson = gsonBuilder.create();
                            JSONQRRequest res = gson.fromJson(contenido, JSONQRRequest.class);
                            Bundle args = new Bundle();
                            args.putSerializable("responseQr", res);
                            Navigation.findNavController(requireView()).navigate(R.id.action_comercio_EscanearQr_to_comercio_AprobarCompra, args);
                        }
                    }
                }
            }
    );
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentComercioEscanearQrBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initiateQRScan();
        return view;
    }


    private void initiateQRScan() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setPrompt("Escanea un código QR"); // Mensaje que se muestra al usuario
        Intent intent = integrator.createScanIntent();
        qrScanLauncher.launch(intent);
    }
}