package com.example.ffh_rep.views.ui.hunter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ffh_rep.databinding.FragmentHunterMisComerciosFavoritosBinding;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.factory.ComercioViewModelFactory;
import com.example.ffh_rep.viewmodels.hunter.ComerciosViewModel;
import com.example.ffh_rep.R;
import com.example.ffh_rep.views.adapters.ComerciosAdapter;

import java.util.ArrayList;
import java.util.List;

public class Hunter_MisComerciosFavoritos extends Fragment {
    private ComerciosViewModel comerciosController;
    private NavController nav;
    private ComerciosAdapter cAdapter;

    private FragmentHunterMisComerciosFavoritosBinding binding;

    private RecyclerView rvFavorites;
    private Hunter userSession;
    private SessionManager sessionManager;
    private TextView tvNoData;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHunterMisComerciosFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initComponents(view);
        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();
        comerciosController.cargarComercios(userSession.getUser().getId_usuario());

        setUpObservers();

        rvFavorites.setAdapter(cAdapter);

        return view;
    }

    public void initComponents(View view){
        nav = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_navigation_controller);
        rvFavorites = view.findViewById(R.id.hunter_list_favorites);
        tvNoData = view.findViewById(R.id.tvNoData);
        comerciosController = new ViewModelProvider(this, new ComercioViewModelFactory(getActivity())).get(ComerciosViewModel.class);
        cAdapter = new ComerciosAdapter(new ArrayList<>(), getContext(), nav, 2);
    }


    public void setUpObservers(){
        comerciosController.getMldComercios().observe(getViewLifecycleOwner(), new Observer<List<Comercio>>() {
            @Override
            public void onChanged(List<Comercio> comercios) {
                if(comercios.size()>0)
                {
                    tvNoData.setVisibility(View.GONE);

                    List<Comercio> comerciosFavoritos = new ArrayList<>();
                    for (Comercio comercio : comercios) {
                        if (comercio.isFavorite()) {
                            comerciosFavoritos.add(comercio);
                        }
                    }
                    cAdapter.setData(comerciosFavoritos);
                }
                else{
                    tvNoData.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}