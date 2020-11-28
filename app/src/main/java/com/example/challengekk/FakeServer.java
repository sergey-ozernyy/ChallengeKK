package com.example.challengekk;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FakeServer extends IntentService {
    public String message;
    Map<String, Form> allForms = new HashMap<>();

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.challengekk.action.FOO";
    private static final String ACTION_BAZ = "com.example.challengekk.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.challengekk.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.challengekk.extra.PARAM2";

    public FakeServer() {
        super("FakeServer");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FakeServer.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FakeServer.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    private Handler mHandler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            Form form = (Form) intent.getSerializableExtra("sendForm");
            if(form != null){
                String themeForm = form.
                        getTheme();
                if(allForms.containsKey(themeForm)){
                    //Отрпавить в активность сообщение о том, то событие с такой темой уже существует
                    message ="Событие с такой темой уже существует";
                } else {
                    allForms.put(themeForm, form);
                    //Отправить сообщение о том, что событие добавлено в список
                    message ="Событие добавлено в список";
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), message,
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            String searchTheme = intent.getStringExtra("request");
            if (searchTheme != null){
                ArrayList<String> resultSearch = new ArrayList<String>();
                for (String key : allForms.keySet()){
                    if (searchTheme.contains(key)){
                        resultSearch.add(key);
                    }
                }
                Intent updateSearchResult = new Intent();
                updateSearchResult.setAction("updateSearchResult");
                updateSearchResult.addCategory(Intent.CATEGORY_DEFAULT);
                updateSearchResult.putStringArrayListExtra("resultSearch", resultSearch);
                sendBroadcast(updateSearchResult);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
