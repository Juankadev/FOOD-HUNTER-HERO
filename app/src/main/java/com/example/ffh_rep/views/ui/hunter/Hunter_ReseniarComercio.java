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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterReseniarComercioBinding;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.entidades.Resenia;
import com.example.ffh_rep.viewmodels.factory.HunterReseniasComercioViewModelFactory;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.hunter.HunterReseniasComercioViewModel;

public class Hunter_ReseniarComercio extends Fragment {

    private HunterReseniasComercioViewModel mViewModel;
    private FragmentHunterReseniarComercioBinding binding;
    private RatingBar rbPuntaje;
    private EditText etComentario;
    private TextView tvTexto;
    private CardView btnEnviarResenia;

    private Hunter userSession;
    private Comercio commerce;
    private JSONQRRequest json;
    private ProgressBar pbBar;
    private SessionManager sessionManager;

    public static Hunter_ReseniarComercio newInstance() {
        return new Hunter_ReseniarComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterReseniarComercioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initComponents(view);

        Bundle bundle = getArguments();
        if(bundle != null){
            json = (JSONQRRequest) bundle.getSerializable("data");
        }

        setUpListeners();
        setUpObservers();

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();


        return view;
    }

    public void initComponents(View view){
        mViewModel = new ViewModelProvider(requireActivity(),new HunterReseniasComercioViewModelFactory(getActivity())).get(HunterReseniasComercioViewModel.class);
        rbPuntaje = view.findViewById(R.id.rb_estrellas);
        etComentario = view.findViewById(R.id.etComentario);
        tvTexto = view.findViewById(R.id.txtEnviandoResenias);
        btnEnviarResenia = view.findViewById(R.id.cv_enviarreseniaactioner);
        pbBar = view.findViewById(R.id.pbEnviarResenia);
    }

    public void setUpListeners(){
        btnEnviarResenia.setOnClickListener(v -> enviarResenia());
    }

    public void setUpObservers(){
        mViewModel.getLoadingSendResenia().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    pbBar.setVisibility(View.VISIBLE);
                    tvTexto.setText("Enviando...");
                }
                else{
                    pbBar.setVisibility(View.GONE);
                    tvTexto.setText("Enviar");
                }
            }
        });

        mViewModel.getSuccessSendResenia().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    pbBar.setVisibility(View.GONE);
                    showSuccessDialog();
                    tvTexto.setText("Enviar");
                }
            }
        });

        mViewModel.getErrorSendResenia().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    pbBar.setVisibility(View.GONE);
                    tvTexto.setText("Enviar");
                }
            }
        });
    }


    public void enviarResenia(){

        if(validateInputs()){
            Resenia res = new Resenia();
            res.setComercio(new Comercio());
            res.getComercio().setId(json.getIdComercio());
            res.setId_usuario(userSession.getUser());
            res.setComentario(etComentario.getText().toString());
            res.setCalificacion((int) rbPuntaje.getRating());
            if (rbPuntaje.getRating() == 0) {
                showRatingConfirmationDialog(res);
            } else {
                res.setCalificacion((int) rbPuntaje.getRating());

            }
            mViewModel.generarResenia(res);
        }
        else{
            Toast.makeText(getContext(), "Por favor, complete los campos indicados para continuar", Toast.LENGTH_LONG).show();
        }

    }

    public boolean validateInputs(){
        boolean flag = true;

        if(etComentario.getText().toString().isEmpty()){
            flag = false;
            etComentario.setError("El comentario es requerido");
        }

        return flag;
    }

    public void showRatingConfirmationDialog(Resenia res) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmar puntaje");
        builder.setMessage("Al no puntuar el comercio se establecerá el puntaje en 1 si procedes. ¿Deseas continuar?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                res.setCalificacion(1);
                dialog.dismiss();
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

    public void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Reseñado con exito!");
        builder.setMessage("Has reseñado al comercio");

        builder.setPositiveButton("Volver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Navigation.findNavController(requireView()).navigate(R.id.action_hunter_ReseniarComercio_to_nav_hunter_Home);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}