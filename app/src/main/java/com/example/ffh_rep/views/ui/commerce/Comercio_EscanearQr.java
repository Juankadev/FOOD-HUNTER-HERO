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
import com.example.ffh_rep.databinding.FragmentComercioEscanearQrBinding;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.viewmodels.commerce.ComercioEscanearQrViewModel;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(contenido);
                                JSONArray articulosJSON = jsonObject.getJSONArray("articulos");
                                List<ItemCarrito> listaDeItems = new ArrayList<>();

                                for (int i = 0; i < articulosJSON.length(); i++) {

                                    ItemCarrito item = new ItemCarrito();
                                    item.setArtc(new Articulo());

                                    JSONObject articuloJSON = articulosJSON.getJSONObject(i);

                                    //Se deconstruye el objeto obtenido del array
                                    JSONObject artcJSON = articuloJSON.getJSONObject("artc");
                                    String descripcion = artcJSON.getString("descripcion");
                                    boolean estado = artcJSON.getBoolean("estado");
                                    int idarticulo = artcJSON.getInt("idArticulo");
                                    String img = artcJSON.getString("imagen");
                                    int precio = artcJSON.getInt("precio");
                                    int cantidad = articuloJSON.getInt("cantidad");

                                    // Configura los atributos de la clase Artc según la desestructuración
                                    listaDeItems.add(item);
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            //Gson gson = new Gson();
                            //ItemCarrito[] miObjetos = gson.fromJson(contenido, ItemCarrito[].class);
                            //List<ItemCarrito> listaDeObjetos = Arrays.asList(miObjetos);
                            Log.d("DATA", contenido);
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