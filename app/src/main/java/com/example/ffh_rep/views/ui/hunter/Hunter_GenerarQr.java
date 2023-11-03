package com.example.ffh_rep.views.ui.hunter;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterGenerarQrBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

public class Hunter_GenerarQr extends Fragment {

    private FragmentHunterGenerarQrBinding binding;
    private ImageView qrContain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHunterGenerarQrBinding.inflate(inflater, container, false);
        View view =  binding.getRoot();
        Bundle args = getArguments();

        qrContain = view.findViewById(R.id.hunter__qr_container);
        String data = args.getString("articulos");

        generateQr(data, qrContain);
        return view;
    }

    public void generateQr(String data, ImageView container){
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bit = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512);

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
}