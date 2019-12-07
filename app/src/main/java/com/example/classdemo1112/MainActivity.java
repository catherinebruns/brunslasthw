package com.example.classdemo1112;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextBirdName, editTextZipCode;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBirdName = findViewById(R.id.editTextBirdName);
        editTextZipCode = findViewById(R.id.editTextSearchZipCode);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemReport) {
            //Intent HomeIntent = new Intent(this, MainActivity.class);
            //startActivity(HomeIntent);
            }
        else if (item.getItemId() == R.id.itemHighestImportance){
        Intent HighestActivityIntent = new Intent(this, ImportanceActivity.class);
        startActivity(HighestActivityIntent);}
        else if (item.getItemId() == R.id.itemSearch){
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

    //adding actions when buttons are clicked
    @Override
    public void onClick(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Birds");

        //pulling current user email address
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (view == buttonSubmit){
            String PersonEmail = currentUser.getEmail();
            String ZipCode = editTextZipCode.getText().toString();
            String BirdName = editTextBirdName.getText().toString();
            Integer Importance = 0;

            BirdSightings newSighting = new BirdSightings (PersonEmail, ZipCode, BirdName, Importance);
            myRef.push().setValue(newSighting);
        }


    }

}
