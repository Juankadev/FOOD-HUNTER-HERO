package com.example.ffh_rep.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

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
    private Usuario userLogged;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationControllerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigationController.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        userLogged = (Usuario) getIntent().getSerializableExtra("usuario");
        int userRole = userRol(userLogged);

        int navGraphResId = getNavGraphResId(userRole);
        int menuResId = getMenuResId(userRole);
        initializeViews();
        setupNavigation(userRole, navGraphResId, menuResId, navigationView, drawer);
    }
    private void initializeViews() {
        username = findViewById(R.id.username);
        username.setText(userLogged.getUsername());
    }
    private int getNavGraphResId(int userRole) {
        switch (userRole) {
            case 1:
                return R.navigation.nav_graph_commerce;
            case 2:
                return R.navigation.nav_graph_hunter;
            default:
                return R.navigation.mobile_navigation;
        }
    }

    private int getMenuResId(int userRole) {
        switch (userRole) {
            case 1:
                return R.menu.menu_commerce;
            case 2:
                return R.menu.menu_hunter;
            default:
                return R.menu.menu_admin;
        }
    }

    private void setupNavigation(int userRole, int navGraphResId, int menuResId, NavigationView navigationView, DrawerLayout drawer) {
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
        int userRole = userRol((Usuario) getIntent().getSerializableExtra("usuario"));
        int menuResId = getMenuResId(userRole);
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