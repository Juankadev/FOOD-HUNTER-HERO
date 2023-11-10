package com.example.ffh_rep.views.ui.hunter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.se.omapi.Session;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterGenerarQrBinding;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.factory.CarritoViewModelFactory;
import com.example.ffh_rep.viewmodels.factory.GenerarQrViewModelFactory;
import com.example.ffh_rep.viewmodels.factory.HunterMiCuentaViewModelFactory;
import com.example.ffh_rep.viewmodels.hunter.CarritoViewModel;
import com.example.ffh_rep.viewmodels.hunter.GenerarQrViewModel;
import com.example.ffh_rep.viewmodels.hunter.HunterMiCuentaViewModel;
import com.example.ffh_rep.views.activitys.NavigationController;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

public class Hunter_GenerarQr extends Fragment {

    private CarritoViewModel carrito;
    private FragmentHunterGenerarQrBinding binding;
    private HunterMiCuentaViewModel cuentaController;
    private GenerarQrViewModel qrController;
    private ImageView qrContain;
    private SessionManager sessionManager;
    private Button btnVolver;
    private Hunter userSession;
    private JSONQRRequest jsonQRRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHunterGenerarQrBinding.inflate(inflater, container, false);
        View view =  binding.getRoot();
        Bundle args = getArguments();
        initComponents(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();

        qrController.setPollingEnabled(true);

        String data = args.getString("json_request");
        Log.d("Log Data in json generated", data);
        generateQr(data, qrContain);
        Gson gson = new Gson();
        jsonQRRequest = gson.fromJson(data, JSONQRRequest.class);
        qrController.checkQRStatus(jsonQRRequest);
        setUpObservers();
        setUpListeners();
        return view;
    }


    public void initComponents(View view){
        qrController = new ViewModelProvider(requireActivity(), new GenerarQrViewModelFactory(getActivity())).get(GenerarQrViewModel.class);
        qrContain = view.findViewById(R.id.hunter__qr_container);
        btnVolver = view.findViewById(R.id.btnVolver_generarQR);
        cuentaController = new ViewModelProvider(requireActivity(), new HunterMiCuentaViewModelFactory(getActivity())).get(HunterMiCuentaViewModel.class);
        carrito = new ViewModelProvider(requireActivity(), new CarritoViewModelFactory(getActivity())).get(CarritoViewModel.class);
    }

    public void setUpListeners(){
        btnVolver.setOnClickListener(v-> backDialog());
    }

    public void setUpObservers(){
        qrController.getCazaAprobada().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                cuentaController.refreshAccount(userSession, sessionManager);
            }
        });

        cuentaController.getSuccessRangoUpdate().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                showSuccessDialog();
            }
        });

        qrController.getCazaRechazada().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                showRejectDialog();
            }
        });
    }
    public void generateQr(String data, ImageView container){
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bit = writer.encode(data, BarcodeFormat.QR_CODE, 800, 800);

            int width = bit.getWidth();
            int height = bit.getHeight();

            int[] pixels = new int[width * height];

            for (int y =0 ;y<height; y++){
                for (int x = 0; x<width; x++){
                    pixels[y * width + x] = bit.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                }
            }

            Bitmap map = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            map.setPixels(pixels, 0, width, 0, 0, width, height);
            container.setImageBitmap(map);
        }
        catch (WriterException e){
            e.printStackTrace();
        }
    }

    public void backDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("QR GENERADO");
        builder.setMessage("¿Usted esta seguro que desea volver?");
        builder.setMessage("El qr sera eliminado y debera generar otro al aceptar");

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qrController.setPollingEnabled(false);
                qrController.deleteQr(jsonQRRequest);
                qrContain = null;
                qrController.resetAttributes();
                cuentaController.resetSuccessRango();
                qrController = null;
                Navigation.findNavController(requireView()).popBackStack();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("CAZA APROBADA");
        builder.setMessage("Tu caza ha sido aprobada con exito");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qrContain = null;
                carrito.clearChart();
                qrController.resetAttributes();
                cuentaController.resetSuccessRango();
                qrController = null;
                Bundle args = new Bundle();
                args.putSerializable("data",jsonQRRequest);
                Navigation.findNavController(requireView()).navigate(R.id.action_hunter_GenerarQr_to_hunter_ReseniarComercio, args);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showRejectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("CAZA RECHAZADA");
        builder.setMessage("Tu caza ha rechazada por el comercio");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qrContain = null;
                qrController.resetAttributes();
                cuentaController.resetSuccessRango();
                qrController = null;
                Navigation.findNavController(requireView()).popBackStack();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}