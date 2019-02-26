package com.arvoron.scherpel.arvoron;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class NewMainActivity extends AppCompatActivity {
    private Toolbar mainToolBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        FirebaseApp.initializeApp(this);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = getInstance().getCurrentUser();
        mainToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mainToolBar);
        getSupportActionBar().setTitle("Arvoron");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = getInstance().getCurrentUser();

        if (currentUser == null) {
            sendToLogin();
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
}
