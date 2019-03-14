package examples.aaronhoskins.com.firebaseapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String WELCOME_MESSAGE = "Welcome! Your user email is %s";
    //Views
    TextView tvDisplay;
    EditText etUserEmail;
    EditText etUserPassword;

    //Firebase Authentication
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Firebase Authentication
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        //Bind the views
        tvDisplay = findViewById(R.id.tvDisplay);
        etUserEmail = findViewById(R.id.etUserEmail);
        etUserPassword = findViewById(R.id.etUserPassword);

        //New to Android O for push notifications
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel(
                    "0",
                    "message",
                    NotificationManager.IMPORTANCE_HIGH);
            //Send notifaction to user using this channel
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void onClick(View view) {
        String email = etUserEmail.getText().toString();
        String password = etUserPassword.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()) {
            switch (view.getId()) {
                case R.id.btnSignUpUser:
                    signUpFirebaseUser(email, password);
                    break;
                case R.id.btnSignOnUser:
                    signOnFirebaseUser(email, password);
                    break;
            }
        }
    }

    private void signUpFirebaseUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            firebaseUser = firebaseAuth.getCurrentUser();
                            displayWelcomeMessage();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void signOnFirebaseUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            firebaseUser = firebaseAuth.getCurrentUser();
                            displayWelcomeMessage();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    private void displayWelcomeMessage() {
        tvDisplay.setText(String.format(Locale.US, WELCOME_MESSAGE, firebaseUser.getEmail()));
    }
}
