package com.example.ffh_rep.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.ActivityNavigationControllerBinding;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.ui.hunter.Hunter_VerComercio;
import com.example.ffh_rep.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class NavigationController extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationControllerBinding binding;
    private Usuario userLogged;
    private Hunter hunterLogged;
    private Comercio commerceLogged;
    private int IdRole;
    private Intent intent;

    private SessionManager sessionManager;

    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationControllerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigationController.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        sessionManager = new SessionManager(this);


        this.intent = getIntent();

        if(intent.hasExtra("hunter")){
            hunterLogged = (Hunter) intent.getSerializableExtra("hunter");
            this.IdRole = userRol(hunterLogged.getUser());
            sessionManager.saveHunterSession(hunterLogged);
            initializeViews(hunterLogged.getUser());
        }
        else if(intent.hasExtra("comercio")){
            commerceLogged = (Comercio) intent.getSerializableExtra("comercio");
            this.IdRole = userRol(commerceLogged.getUser());
            sessionManager.saveCommerceSession(commerceLogged);
            initializeViews(commerceLogged.getUser());
        }
        else{
            userLogged = (Usuario) getIntent().getSerializableExtra("usuario");
            this.IdRole = userRol(userLogged);
            sessionManager.saveUserSession(userLogged);
            initializeViews(userLogged);
        }
        Log.d("ROL", String.valueOf(this.IdRole));
        int navGraphResId = getNavGraphResId(this.IdRole);
        int menuResId = getMenuResId(this.IdRole);

        setupNavigation(this.IdRole, navGraphResId, menuResId, navigationView, drawer);
    }
    private void initializeViews(Usuario u) {
        username = findViewById(R.id.username);
        username.setText(u.getUsername());
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
        Log.d("Graphs", String.valueOf(navController.getGraph()));

        NavigationUI.setupWithNavController(navigationView, navController);
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout){
            sessionManager.clearSession();
            redirectToLoginScreen();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_controller);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


    public void redirectToLoginScreen(){
        Toast.makeText(this, "Nos vemos luego!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(NavigationController.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private int userRol(Usuario user) {
        return user.getRol().getIdRol();
    }
}