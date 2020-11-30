package com.example.challengekk;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FakeServer extends Service {
    private Looper mLooper;
    private Handler mHandler;

    private final IBinder binder = new FakeServerBinder();

    public class FakeServerBinder extends Binder {
        FakeServer getFakeServer() {
            return FakeServer.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public static final String ACTION_UPDATE_SEARCH = "updateSearchResult";
    public static final String ACTION_UPDATE_SEND = "messageSend";
    String message;
    Map<String, Form> allForms = new HashMap<>();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    //@Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            Form form = (Form) intent.getSerializableExtra("sendForm");
            if (form != null) {
                String themeForm = form.
                        getTheme();
                if (allForms.containsKey(themeForm)) {
                    //Отрпавить в активность сообщение о том, то событие с такой темой уже существует
                    message = "Событие с такой темой уже существует";
                } else {
                    allForms.put(themeForm, form);
                    //Отправить сообщение о том, что событие добавлено в список
                    message = "Событие добавлено в список";
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(ACTION_UPDATE_SEND);
                sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
                sendIntent.putExtra("sendEvent", message);
                sendBroadcast(sendIntent);
            }

            String searchTheme = intent.getStringExtra("request");
            if (searchTheme != null) {
                ArrayList<String> resultSearch = new ArrayList<String>();
                for (String key : allForms.keySet()) {
                    if (searchTheme.contains(key)) {
                        resultSearch.add(key);
                    }
                }
                Intent updateSearchResult = new Intent();
                updateSearchResult.setAction(ACTION_UPDATE_SEARCH);
                updateSearchResult.addCategory(Intent.CATEGORY_DEFAULT);
                updateSearchResult.putStringArrayListExtra("resultSearch", resultSearch);
                sendBroadcast(updateSearchResult);
            }
        }
    }


}
