package com.example.ffh_rep.views.ui.hunter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterGenerarQrBinding;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.viewmodels.factory.GenerarQrViewModelFactory;
import com.example.ffh_rep.viewmodels.hunter.GenerarQrViewModel;
import com.example.ffh_rep.views.activitys.NavigationController;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

public class Hunter_GenerarQr extends Fragment {

    private FragmentHunterGenerarQrBinding binding;
    private GenerarQrViewModel qrController;
    private ImageView qrContain;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHunterGenerarQrBinding.inflate(inflater, container, false);
        View view =  binding.getRoot();
        Bundle args = getArguments();
        initComponents(view);
        qrController.setPollingEnabled(true);
        String data = args.getString("json_request");
        Log.d("Log Data in json generated", data);
        generateQr(data, qrContain);
        Gson gson = new Gson();
        JSONQRRequest jsonQRRequest = gson.fromJson(data, JSONQRRequest.class);
        qrController.checkQRStatus(jsonQRRequest);
        setUpObservers();
        return view;
    }


    public void initComponents(View view){
        qrController = new ViewModelProvider(requireActivity(), new GenerarQrViewModelFactory(getActivity())).get(GenerarQrViewModel.class);
        qrContain = view.findViewById(R.id.hunter__qr_container);
    }

    public void setUpObservers(){
        qrController.getCazaAprobada().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                showSuccessDialog();
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

    public void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("CAZA APROBADA");
        builder.setMessage("Tu caza ha sido aprobada con exito");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qrContain = null;
                qrController.resetAttributes();
                qrController = null;
                Navigation.findNavController(requireView()).navigate(R.id.action_hunter_GenerarQr_to_nav_hunter_Home);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        qrController = null;
        qrContain = null;
    }
}