package com.example.challengekk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText nameEditText = (EditText) findViewById(R.id.name_editText);
        final EditText divisionEditText = (EditText) findViewById(R.id.division_editText);
        final EditText themeEditText = (EditText) findViewById(R.id.theme_editText);
        final EditText contentEditText = (EditText) findViewById(R.id.content_editText);
        final Spinner eventSpinner = (Spinner) findViewById(R.id.events_spinner);

        Button clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEditText.setText("");
                divisionEditText.setText("");
                themeEditText.setText("");
                contentEditText.setText("");
                eventSpinner.setSelection(0);
            }
        });

        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Form currentForm = new Form();
                currentForm.setName(nameEditText.getText().toString());
                currentForm.setDivision(divisionEditText.getText().toString());
                currentForm.setTheme(themeEditText.getText().toString());
                currentForm.setContent(contentEditText.getText().toString());
                currentForm.setEvent(eventSpinner.getSelectedItem().toString());

                Toast.makeText(getApplicationContext(),
                        currentForm.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
