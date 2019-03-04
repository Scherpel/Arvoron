package com.arvoron.scherpel.arvoron;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class NewMainActivity extends AppCompatActivity {
    private Toolbar mainToolBar;
    private FirebaseAuth mAuth;
    private FloatingActionButton addPostBtn;
    private FirebaseFirestore firebaseFirestore;
    private String user_id;
    private BottomNavigationView mainbottomNav;
    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private MapFragment mapFragment;
    private AccountFragment accountFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        FirebaseApp.initializeApp(this);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);
        getSupportActionBar().setTitle("Arvoron");
        mainbottomNav = findViewById(R.id.mainBottomNav);
        addPostBtn = findViewById(R.id.add_tree);
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newTree = new Intent(NewMainActivity.this, NewTreeActivity.class);
                startActivity(newTree);
                finish();
            }
        });

        if(mAuth.getCurrentUser() != null) {
            mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

                    switch (item.getItemId()) {

                        case R.id.item_bottom_home:

                            replaceFragment(homeFragment);
                            return true;

                        case R.id.item_bottom_notification:

                            replaceFragment(notificationFragment);
                            return true;

                        case R.id.item_bottom_maps:

                            replaceFragment(mapFragment);
                            return true;

                        case R.id.item_bottom_account:

                            replaceFragment(accountFragment);
                            return true;

                        default:
                            return false;


                    }

                }
            });
        }


        // FRAGMENTS
        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        accountFragment = new AccountFragment();
        mapFragment = new MapFragment();

        replaceFragment(homeFragment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = getInstance().getCurrentUser();

        if (currentUser == null) {
            sendToLogin();
        } else{
            user_id = mAuth.getCurrentUser().getUid();
            firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){

                        if(!task.getResult().exists()){
                            Intent setupIntent = new Intent(NewMainActivity.this, SetupActivity.class);
                            startActivity(setupIntent);
                            finish();
                        }

                    } else{
                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(NewMainActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_logout:
                logout();

                return true;
            case R.id.menu_item_account:
                Intent goToAccount = new Intent(this, SetupActivity.class);
                startActivity(goToAccount);
                finish();
                default:
                    return false;
        }
    }

    private void sendToLogin() {
        Intent ok = new Intent(this, LoginActivity.class);
        startActivity(ok);
        finish();
        // User is not signed in
    }
    private void logout() {
        mAuth.signOut();
        sendToLogin();
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }
}
