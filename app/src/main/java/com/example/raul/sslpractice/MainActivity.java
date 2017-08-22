package com.example.raul.sslpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.chilkatsoft.CkSocket;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Chilkat";

    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CkSocket socket = new CkSocket();

        boolean success;
        success = socket.UnlockComponent("Anything for 30-day trial");
        if (success != true) {
            Log.i(TAG, socket.lastErrorText());
            return;
        }

        boolean ssl = true;
        int maxWaitMillisec = 20000;

        //  The SSL server hostname may be an IP address, a domain name,
        //  or "localhost".  You'll need to change this:
        String sslServerHost;
        sslServerHost = "123.123.88.88";
        int sslServerPort = 8123;

        //  Connect to the SSL server:
        success = socket.Connect(sslServerHost,sslServerPort,ssl,maxWaitMillisec);
        if (success != true) {
            Log.i(TAG, socket.lastErrorText());
            return;
        }

        //  Set maximum timeouts for reading an writing (in millisec)
        socket.put_MaxReadIdleMs(20000);
        socket.put_MaxSendIdleMs(20000);

        //  Send a "Hello Server! -EOM-" message:
        success = socket.SendString("Hello Server! -EOM-");
        if (success != true) {
            Log.i(TAG, socket.lastErrorText());
            return;
        }

        //  The server (in this example) is going to send a "Hello Client! -EOM-"
        //  message.  Read it:
        String receivedMsg = socket.receiveUntilMatch("-EOM-");
        if (socket.get_LastMethodSuccess() != true) {
            Log.i(TAG, socket.lastErrorText());
            return;
        }

        //  Close the connection with the server
        //  Wait a max of 20 seconds (20000 millsec)
        success = socket.Close(20000);

        Log.i(TAG, receivedMsg);

    }

    static {
        // Important: Make sure the name passed to loadLibrary matches the shared library
        // found in your project's libs/armeabi directory.
        //  for "libchilkat.so", pass "chilkat" to loadLibrary
        //  for "libchilkatemail.so", pass "chilkatemail" to loadLibrary
        //  etc.
        //
        System.loadLibrary("chilkat");

        // Note: If the incorrect library name is passed to System.loadLibrary,
        // then you will see the following error message at application startup:
        //"The application <your-application-name> has stopped unexpectedly. Please try again."
    }
}
