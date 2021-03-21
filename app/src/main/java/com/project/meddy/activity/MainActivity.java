package com.project.meddy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.project.meddy.R;
import com.project.meddy.helper.SQLiteHandler;
import com.project.meddy.helper.SessionManager;

import java.util.HashMap;

public class MainActivity extends Activity {
    private TextView txtName, txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // SQLite DB handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIN()) {
            logoutUser();
        }

        // Fetch user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Display user details on screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout btn click event
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Logging out user and set isLoggedIn flag to false in shared preferences
     * Clear the user data from SQLite user table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launch login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
