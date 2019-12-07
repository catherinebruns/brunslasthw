package com.example.classdemo1112;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class Search extends AppCompatActivity implements View.OnClickListener {

    Button buttonSubmit, buttonImportance;
    TextView textViewShowBirdName, textViewShowEmail;
    EditText editTextSearchZipCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonImportance = findViewById(R.id.buttonImportance);
        textViewShowBirdName = findViewById(R.id.textViewShowBirdName);
        textViewShowEmail = findViewById(R.id.textViewShowEmail);
        editTextSearchZipCode = findViewById(R.id.editTextSearchZipCode);

        buttonSubmit.setOnClickListener(this);
        buttonImportance.setOnClickListener(this);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemReport) {
            Intent HomeIntent = new Intent(this, MainActivity.class);
            startActivity(HomeIntent);
        }
        else if (item.getItemId() == R.id.itemHighestImportance){
            Intent HighestActivityIntent = new Intent(this, ImportanceActivity.class);
            startActivity(HighestActivityIntent);}
        else if (item.getItemId() == R.id.itemSearch) {
            // Intent SettingsIntent = new Intent(this, Search.class);
            //startActivity(SettingsIntent);
        } else if (item.getItemId() == R.id.itemLogOut) {
        //need to actually log out
        FirebaseAuth.getInstance().signOut();
        Intent LogOutIntent = new Intent(this, LogInActivity.class);
        startActivity(LogOutIntent);
    }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Birds");

        if (view == buttonSubmit) {
            //inserting toast if user hits buttonsubmit without entering zip code.
            if (editTextSearchZipCode == null) {
                Toast.makeText(Search.this, "Zip Code is Empty", Toast.LENGTH_SHORT).show();
            }
            String findZipCode = editTextSearchZipCode.getText().toString();
            myRef.orderByChild("ZipCode").equalTo(findZipCode).limitToLast(1).addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //Toast.makeText(Search.this,  String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();

                    BirdSightings findBird = dataSnapshot.getValue(BirdSightings.class);
                    String findBirdName = findBird.BirdName;
                    textViewShowBirdName.setText(findBirdName);

                    String findPersonEmail = findBird.PersonEmail;
                    textViewShowEmail.setText(findPersonEmail);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else if (view == buttonImportance){
            //inserting toast if user hits buttonsubmit without entering zip code.
            if (editTextSearchZipCode == null) {
                Toast.makeText(Search.this, "Zip Code is Empty", Toast.LENGTH_SHORT).show();
            }
            String findZipCode = editTextSearchZipCode.getText().toString();
            myRef.orderByChild("ZipCode").equalTo(findZipCode).limitToLast(1).addChildEventListener(new ChildEventListener() {


                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String key = dataSnapshot.getKey();
                    BirdSightings found = dataSnapshot.getValue(BirdSightings.class);
                    found.Importance++;
                    myRef.child(key).child("Importance").setValue(found.Importance);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}