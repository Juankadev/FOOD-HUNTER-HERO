package com.example.ffh_rep.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ffh_rep.databinding.ActivityNavigationControllerBinding;

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

        // SETEO DE RUTAS POR ROL
        Usuario userLogged = (Usuario) getIntent().getSerializableExtra("usuario");

        int navHostFragmentId = -1;
        NavController navController = null;
        switch(userRol(userLogged)){
            case 1:
                navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_commerce);
                mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_slideshow)
                        .setOpenableLayout(drawer)
                        .build();
            break;
            case 2:
                navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_hunter);
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_hunter_Home, R.id.nav_hunter_MiCuenta)
                        .setOpenableLayout(drawer)
                        .build();
            break;
            case 3:
                navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_controller);
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home, R.id.nav_gallery)
                        .setOpenableLayout(drawer)
                        .build();
                break;
            default:
                Intent intent = new Intent(NavigationController.this, MainActivity.class);
                startActivity(intent);
                break;
        }

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_controller, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_controller);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private Integer userRol(Usuario user){
        return user.getRol().getIdRol();
    }
}