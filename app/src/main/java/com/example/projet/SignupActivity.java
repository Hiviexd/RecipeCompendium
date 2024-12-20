package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText usernameInput = findViewById(R.id.registerUsername);
        EditText emailInput = findViewById(R.id.registerEmail);
        EditText passwordInput = findViewById(R.id.registerPassword);
        EditText confirmPasswordInput = findViewById(R.id.confirmPassword);
        Button registerButton = findViewById(R.id.registerButton);
        TextView loginLink = findViewById(R.id.switchToLogin);

        registerButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String confirmPwd = confirmPasswordInput.getText().toString();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPwd.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPwd)) {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(SignupActivity.this, RecipeListActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        });

        loginLink.setOnClickListener(v -> finish());
    }
}
