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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ImportanceActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewHighestImportanceBird, textViewBirdZipCode,textViewImportanceNumber, textViewEmail;
    Button buttonUpdateHighestImportance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importance);

        textViewHighestImportanceBird = findViewById(R.id.textViewHighestImportanceBird);
        textViewBirdZipCode = findViewById(R.id.textViewBirdZipCode);
        textViewImportanceNumber = findViewById(R.id.textViewImportanceNumber);
        textViewEmail = findViewById(R.id.textViewEmail);

        buttonUpdateHighestImportance = findViewById(R.id.buttonHighestImportance);

        buttonUpdateHighestImportance.setOnClickListener(this);
        }

    //inserting menu on this page
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
        //else if (item.getItemId() == R.id.itemHighestImportance){
            //Intent HighestActivityIntent = new Intent(this, ImportanceActivity.class);
            //startActivity(HighestActivityIntent);}
        else if (item.getItemId() == R.id.itemSearch) {
            Intent SettingsIntent = new Intent(this, Search.class);
            startActivity(SettingsIntent);
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
        //ordering database by importance and limiting to the last/highest rating.
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Birds");

        myRef.orderByChild("Importance").limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //showing most important bird information in text views
                BirdSightings findBird = dataSnapshot.getValue(BirdSightings.class);
                Integer findImportance = findBird.Importance;
                //converting the value of the integer Importance to be able to view it in the Text view
                textViewImportanceNumber.setText(String.valueOf(findImportance));

                String findBirdName = findBird.BirdName;
                textViewHighestImportanceBird.setText(findBirdName);

                String findZipCode = findBird.ZipCode;
                textViewBirdZipCode.setText(findZipCode);

                String findEmail = findBird.PersonEmail;
                textViewEmail.setText(findEmail);

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
