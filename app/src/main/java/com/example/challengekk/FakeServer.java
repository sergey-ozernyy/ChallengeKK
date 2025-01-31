package com.example.challengekk;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FakeServer extends Service {
    private static final String TAG = "myLogs";
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

    Map<String, Form> allForms = new HashMap<>();

    public void setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
    }

    String massage = "Test!";

    interface OnSaveListener{
        void onSave(String massage);
    }

    private OnSaveListener onSaveListener;

    public void saveForm(final Form newForm) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String themeForm = newForm.getTheme();
                if (allForms.containsKey(themeForm)) {
                    //Отрпавить в активность сообщение о том, то событие с такой темой уже существует
                    massage = "Событие с темой " + themeForm + " уже существует";
                } else {
                    allForms.put(themeForm, newForm);
                    //Отправить сообщение о том, что событие добавлено в список
                    massage = "Добавлено событие с темой " + themeForm;
                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(onSaveListener != null) {
                        onSaveListener.onSave(massage);
                        }
                    }
                });

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void setOnGetVariantsListener(OnGetVariantsListener onGetVariantsListener){
        this.onGetVariantsListener = onGetVariantsListener;
    }

    ArrayList<String> resultSearch;

    interface OnGetVariantsListener{
        void onGetVariants(ArrayList<String> variants);
    }

    private OnGetVariantsListener onGetVariantsListener;

    public void getVariantsSearch(final String searchTheme) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                resultSearch = new ArrayList<String>();
                for (String key : allForms.keySet()) {
                    if (key.contains(searchTheme)) {
                        resultSearch.add(key);
                    }
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(onGetVariantsListener != null){
                            onGetVariantsListener.onGetVariants(resultSearch);
                        }
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void setOnGetExistingFormListener(OnGetExistingFormListener onGetExistingFormListener){
        this.onGetExistingFormListener = onGetExistingFormListener;
    }

    interface OnGetExistingFormListener{
        void onGetForm(Form form);
    }

    private OnGetExistingFormListener onGetExistingFormListener;

    public void getExistingForm(final String theme) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Form existingForm = allForms.get(theme);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(onGetExistingFormListener != null){
                            onGetExistingFormListener.onGetForm(existingForm);
                        }
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


}


