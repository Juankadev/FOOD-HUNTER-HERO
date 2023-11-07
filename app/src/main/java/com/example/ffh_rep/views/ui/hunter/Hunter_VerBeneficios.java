package com.example.ffh_rep.views.ui.hunter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterVerBeneficiosBinding;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.interfaces.CanjearBeneficiosCallback;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.hunter.HunterVerBeneficiosViewModel;
import com.example.ffh_rep.views.adapters.BeneficiosAdapterHunter;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.viewmodels.factory.HunterVerBeneficiosViewModelFactory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Hunter_VerBeneficios extends Fragment implements CanjearBeneficiosCallback {

    private HunterVerBeneficiosViewModel mViewModel;
    private FragmentHunterVerBeneficiosBinding binding;
    private GridView gvBeneficios;
    private TextView error;
    private ProgressBar pbBeneficios;
    private BeneficiosAdapterHunter bAdapter;
    private Comercio commerce;
    private Hunter userSession;
    private SessionManager sessionManager;

    public static Hunter_VerBeneficios newInstance() {
        return new Hunter_VerBeneficios();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterVerBeneficiosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();

        Bundle bundle = getArguments();
        if(bundle != null){
            commerce = (Comercio) bundle.getSerializable("comerciobeneficios");
        }


        initComponents(view);


        mViewModel.cargarBeneficios(commerce);
        setUpObserver();

        gvBeneficios.setAdapter(bAdapter);
        return view;
    }

    public void initComponents(View view){
        gvBeneficios = view.findViewById(R.id.gv_beneficios);
        bAdapter = new BeneficiosAdapterHunter(requireContext(), new ArrayList<>(), this);
        mViewModel = new ViewModelProvider(requireActivity(), new HunterVerBeneficiosViewModelFactory(getActivity())).get(HunterVerBeneficiosViewModel.class);
        pbBeneficios = view.findViewById(R.id.pbBeneficiosCargados);
        error = view.findViewById(R.id.txtErrorBeneficios);
    }

    public void setUpObserver(){
            mViewModel.getListBeneficios().observe(getViewLifecycleOwner(), new Observer<List<Beneficio>>() {
                @Override
                public void onChanged(List<Beneficio> beneficios) {
                    Log.d("beneficios", beneficios.toString());
                    bAdapter.setlBeneficios(beneficios);
                }
            });


            mViewModel.getSuccessInsertBene().observe(getViewLifecycleOwner(), aBoolean -> {
                if(aBoolean){
                    showSuccessDialog();
                }
            });

            mViewModel.getErrorInsertBene().observe(getViewLifecycleOwner(), aBoolean -> {
                if (aBoolean){
                    showErrorDialog();
                }
            });

            mViewModel.getLoadingBeneficios().observe(getViewLifecycleOwner(), aBoolean -> {
                if(aBoolean){
                    pbBeneficios.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                    gvBeneficios.setVisibility(View.GONE);
                }
            });

            mViewModel.getSuccessBeneficios().observe(getViewLifecycleOwner(), aBoolean -> {
                if (aBoolean){
                    pbBeneficios.setVisibility(View.GONE);
                    gvBeneficios.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                }
            });

            mViewModel.getErrorBeneficios().observe(getViewLifecycleOwner(), aBoolean -> {
                if(aBoolean){
                    pbBeneficios.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);
                }
            });

    }

    public void setUpListeners(){

    }



    @Override
    public void onCanjearBeneficio(Beneficio bene) {
        if(userSession.getPuntaje() >= bene.getPuntos_requeridos()){
            mViewModel.canjearBeneficio(this.userSession, bene, this.sessionManager);
            bAdapter.setLoading(true);
            bAdapter.notifyDataSetChanged();
        }
        else{
            Toast.makeText(requireContext(), "No cuentas con los puntos suficientes para canjear el beneficio", Toast.LENGTH_SHORT).show();
        }
    }

    public void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Beneficios");
        builder.setMessage("Ocurrió un error al canjear el beneficio. Intentelo mas tarde");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mViewModel.resetInsertValues();
                bAdapter.setLoading(false);
                bAdapter.notifyDataSetChanged();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Beneficios");
        builder.setMessage("Haz canjeado el beneficio con exito");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mViewModel.resetInsertValues();
                bAdapter.setLoading(false);
                bAdapter.notifyDataSetChanged();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}