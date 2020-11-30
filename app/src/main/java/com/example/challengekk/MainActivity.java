package com.example.challengekk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    public ArrayList<String> variablesAutocomplete;
    private ArrayAdapter<String> autocompleteAdapter;
    BroadcastReceiver mUpdateSearchBroadcastReceiver;
    BroadcastReceiver mSendEventBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                if (searchAutoCompleteText.getText().toString().length() >= 3){
                    Intent searchRequest = new Intent(MainActivity.this, FakeServer.class);
                    searchRequest.putExtra("request", searchAutoCompleteText.getText().toString());
                    startService(searchRequest);
                }
            }
        });

        //Приемник для варинатов поиска
        class UpdateSearchBroadcastReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                variablesAutocomplete = intent.getStringArrayListExtra("resultSearch");
            }
        }

        mUpdateSearchBroadcastReceiver = new UpdateSearchBroadcastReceiver();
        IntentFilter updateIntentFilter = new IntentFilter(FakeServer.ACTION_UPDATE_SEARCH);
        updateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mUpdateSearchBroadcastReceiver, updateIntentFilter);

        //Адаптер для связывания поля автодополнения и вариантов для него
        autocompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, variablesAutocomplete);
        searchAutoCompleteText.setAdapter(autocompleteAdapter);

        //Приемник для события записи формы
        class SendEventBroadcastReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(), intent.getStringExtra("sendEvent"), Toast.LENGTH_LONG).show();
            }
        }

        mSendEventBroadcastReceiver = new SendEventBroadcastReceiver();
        IntentFilter sendIntentFilter = new IntentFilter(FakeServer.ACTION_UPDATE_SEND);
        sendIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mSendEventBroadcastReceiver, sendIntentFilter);


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

                sendToServer(currentForm);
            }
        });

    }

    void sendToServer(Form currentForm){
        Intent intent = new Intent(MainActivity.this, FakeServer.class);
        intent.putExtra("sendForm", (Serializable) currentForm);
        startService(intent);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mUpdateSearchBroadcastReceiver);
        unregisterReceiver(mSendEventBroadcastReceiver);
    }
}
