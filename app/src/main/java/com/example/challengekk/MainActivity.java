package com.example.challengekk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FakeServer fakeServer;
    private boolean bound = false;
    public ArrayList<String> variablesAutocomplete;
    private ArrayAdapter<String> autocompleteAdapter;
    private static final String TAG = "myLogs";

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FakeServer.FakeServerBinder fakeServerBinder = (FakeServer.FakeServerBinder) service;
            fakeServer = fakeServerBinder.getFakeServer();
            fakeServer.setOnSaveListener(new FakeServer.OnSaveListener(){
                @Override
                public void onSave(String massage){
                    Toast.makeText(getApplicationContext(), massage,
                            Toast.LENGTH_LONG).show();
                }
            });

            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Подключаемся к сервису
        Intent intent = new Intent(this, FakeServer.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        final ArrayList<String> test = new ArrayList<String>();
        test.add("Вариант1");
        test.add("Вариант2");
        test.add("Вариант3");

        final AutoCompleteTextView searchAutoCompleteText = (AutoCompleteTextView) findViewById(R.id.search_autoCompleteTextView);
        searchAutoCompleteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String request = searchAutoCompleteText.getText().toString();
                fakeServer.getVariantsSearch(request);

                fakeServer.setOnGetVariantsListener(new FakeServer.OnGetVariantsListener() {
                    @Override
                    public void onGetVariants(ArrayList<String> variants) {
                        variablesAutocomplete = variants;

                        //Адаптер для связывания поля автодополнения и вариантов для него
                        autocompleteAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, variablesAutocomplete);
                        searchAutoCompleteText.setAdapter(autocompleteAdapter);

                    }
                });

            }
        });






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

                fakeServer.saveForm(currentForm);
            }
        });

    }



    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (bound){
            unbindService(connection);
            bound = false;
        }

    }
}
