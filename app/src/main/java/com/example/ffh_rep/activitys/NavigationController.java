package com.example.ffh_rep.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.ActivityNavigationControllerBinding;
import com.example.ffh_rep.entidades.Usuario;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class NavigationController extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationControllerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationControllerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigationController.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        binding.appBarNavigationController.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Usuario userLogged = (Usuario) getIntent().getSerializableExtra("usuario");
        int userRole = userRol(userLogged);

        int navGraphResId = R.navigation.mobile_navigation;
        int menuResId = R.menu.activity_main_drawer;

        if (userRole == 1) {
            navGraphResId = R.navigation.nav_graph_commerce;
            menuResId = R.menu.menu_commerce;
        } else if (userRole == 2) {
            navGraphResId = R.navigation.nav_graph_hunter;
            menuResId = R.menu.menu_hunter;
        }
        else {
            navGraphResId = R.navigation.nav_graph_admin;
            menuResId = R.menu.menu_admin;
        }

        navigationView.getMenu().clear();
        navigationView.inflateMenu(menuResId);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_controller);
        navController.setGraph(navGraphResId);

        NavigationUI.setupWithNavController(navigationView, navController);
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuResId = R.menu.activity_main_drawer;

        Usuario userLogged = (Usuario) getIntent().getSerializableExtra("usuario");
        int userRole = userRol(userLogged);

        if (userRole == 1) {
            Log.d("Comercio MENU", "Entro al rol comercio");
            menuResId = R.menu.menu_commerce;
        } else if (userRole == 2) {
            Log.d("Hunter MENU", "Entro al rol hunter");
            menuResId = R.menu.menu_hunter;
        }
        else{
            Log.d("Admin MENU", "Entro al rol admin");
            menuResId = R.menu.menu_admin;
        }

        getMenuInflater().inflate(menuResId, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_controller);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private int userRol(Usuario user) {
        return user.getRol().getIdRol();
    }
}
