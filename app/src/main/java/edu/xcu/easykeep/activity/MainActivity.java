package edu.xcu.easykeep.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.xcu.easykeep.R;
import edu.xcu.easykeep.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.navView;

        //先找到 navHostfragment
        NavHostFragment navHostfragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        //再根据 navHostfragment 找到 navController
        if (navHostfragment != null) {
            NavController navController = navHostfragment.getNavController();
            // 将底部导航栏与 navController 关联，实现点击导航
            NavigationUI.setupWithNavController(navView, navController);
        }

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
    }

}