package examples.aaronhoskins.com.firebaseapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataEntryActivity extends AppCompatActivity {
    //Declare Views
    EditText etEngine;
    EditText etTransmission;
    EditText etWheels;
    EditText etMake;
    EditText etModel;

    //Firebase database objects
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        //Initialize Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Cars");
        //retrieve updates upon changes
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG", "onDataChange: " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "onCancelled: ", databaseError.toException());
            }
        });
        bindViews();
    }

    public void bindViews() {
        etEngine = findViewById(R.id.etEngine);
        etTransmission = findViewById(R.id.etTransmission);
        etWheels = findViewById(R.id.etWheels);
        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
    }

    public Car createCarFromInput() {
        String engine = etEngine.getText().toString();
        String transmission = etTransmission.getText().toString();
        String wheels = etWheels.getText().toString();
        String make = etMake.getText().toString();
        String model = etModel.getText().toString();
        return new Car(wheels, engine, transmission, make, model);
    }

    public void onSaveClick(View view) {
        Car inputedCar = createCarFromInput();
        databaseReference.child(inputedCar.getMake()).setValue(inputedCar);
    }
}
