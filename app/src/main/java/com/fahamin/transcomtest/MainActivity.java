package com.fahamin.transcomtest;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.fahamin.transcomtest.ui.product_add.productAddFragment;
import com.fahamin.transcomtest.ui.product_view.ProductViewFragment;
import com.fahamin.transcomtest.ui.product_eidt.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    DrawerLayout drawer;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               fragmentManager.beginTransaction().replace(R.id.mainContainerId,new productAddFragment()).commit();
               setTitle("Add Your Product");
            }
        });


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();

        fragmentManager.beginTransaction().replace(R.id.mainContainerId,new ProductViewFragment()).commit();
        setTitle("All Product");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:

                        fragmentManager.beginTransaction().replace(R.id.mainContainerId, new ProductViewFragment()).commit();
                        setTitle("All Product");
                        break;
                    case R.id.nav_gallery:

                        fragmentManager.beginTransaction().replace(R.id.mainContainerId, new productAddFragment()).commit();
                        setTitle("Add Your Product");

                        break;

                 /*   case R.id.nav_liveTv:
                            fragmentManager.beginTransaction().replace(R.id.mainContainerId, new LiveTv()).commit();
                            setTitle("Live Stream Build");
                        break;*/

                    case R.id.nav_slideshow:

                        fragmentManager.beginTransaction().replace(R.id.mainContainerId, new SlideshowFragment()).commit();
                        setTitle("Favorite");

                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

   /* @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.mainContainerId);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}