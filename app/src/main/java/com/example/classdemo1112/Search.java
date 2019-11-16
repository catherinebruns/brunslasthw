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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class Search extends AppCompatActivity implements View.OnClickListener{

    Button buttonSubmit;
    TextView textViewShowBirdName;
    EditText editTextSearchZipCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewShowBirdName = findViewById(R.id.textViewShowBirdName);
        editTextSearchZipCode = findViewById(R.id.editTextSearchZipCode);


        buttonSubmit.setOnClickListener(this);

    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemReport) {
            Intent HomeIntent = new Intent(this, MainActivity.class);
            startActivity(HomeIntent);
        } else if (item.getItemId() == R.id.itemSearch){
           // Intent SettingsIntent = new Intent(this, Search.class);
            //startActivity(SettingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Birds");

        if (view == buttonSubmit){
            String findZipCode = editTextSearchZipCode.getText().toString();

            myRef.orderByChild("ZipCode").equalTo(findZipCode).addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    BirdSightings  findBird= dataSnapshot.getValue(BirdSightings.class);
                    String findBirdName = findBird.BirdName;

                    textViewShowBirdName.setText(findBirdName);

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
