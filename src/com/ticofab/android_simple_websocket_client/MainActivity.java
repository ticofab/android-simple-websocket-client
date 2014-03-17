package com.ticofab.android_simple_websocket_client;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.codebutler.android_websockets.WebSocketClient;
import com.codebutler.android_websockets.WebSocketClient.Listener;

public class MainActivity extends Activity {
    private static final String TAG = "AndroidSimpleWebsocketClient";

    private final List<BasicNameValuePair> mExtraHeaders = null;
    private WebSocketClient mWsClient;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWsClient = new WebSocketClient(URI.create("ws://echo.websocket.org"),
                new Listener() {

                    @Override
                    public void onMessage(final String message) {
                        Log.d(TAG, "onMessage:  " + message);
                    }

                    @Override
                    public void onError(final Exception error) {
                        Log.e(TAG, "Error!", error);
                    }

                    @Override
                    public void onDisconnect(final int code, final String reason) {
                        Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
                    }

                    @Override
                    public void onConnect() {
                        Log.d(TAG, "onConnect");
                        mWsClient.send("Hello from Android!");
                    }

                    @Override
                    public void onMessage(final byte[] data) {
                        String message = "couldn't read message";
                        try {
                            message = new String(data, "UTF-8");
                        } catch (final UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Log.d(TAG, "Got binary message! " + message);

                    }
                }, mExtraHeaders);

        mWsClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
