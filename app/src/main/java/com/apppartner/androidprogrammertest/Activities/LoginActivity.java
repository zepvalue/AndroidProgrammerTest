package com.apppartner.androidprogrammertest.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apppartner.androidprogrammertest.MyApplication;
import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_URL = "http://dev3.apppartner.com/AppPartnerDeveloperTest/scripts/login.php";
    public EditText usernameEditText;
    public EditText passwordEditText;
    private TextView titleTextView;
    boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        Utils.setFont(usernameEditText, "fonts/Jelloween - Machinato.ttf", this);
        Utils.setFont(passwordEditText, "fonts/Jelloween - Machinato.ttf", this);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        titleTextView = (TextView) toolbarTop.findViewById(R.id.textViewTitle);
        titleTextView.setText("Login");

        Utils.setFont(titleTextView, "fonts/Jelloween - Machinato ExtraLight.ttf", this);

    }

    public void onLogin(View v) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (password.equals("") || username.equals("")) {
            Toast.makeText(getApplicationContext(), "Error Empty Fields", Toast.LENGTH_SHORT).show();
            success = false;
        } else {
            success = true;
            sendPostRequest(username,password);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void sendPostRequest(String givenUsername, String givenPassword) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            Context context;
            long elapsedTime = 0;

            public void setContext(Context context) {
                this.context = context;
            }

            @Override
            protected String doInBackground(String... params) {

                String paramUsername = params[0];
                String paramPassword = params[1];

                if(params == null) return null;

                try {
                    URL url = new URL(LOGIN_URL);
                    String method = "POST";
                    long startTime = System.currentTimeMillis();
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod(method);
                    conn.setUseCaches(false); // Don't use a Cached Copy

                    if(method=="POST" && params.length>0)
                    {
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());


                        addFormPart(dos,"username", paramUsername);
                        addFormPart(dos,"password", paramPassword);

                        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                        dos.flush();
                        dos.close();
                    }

                    InputStream is = conn.getInputStream();
                    // Responses from the server (code and message)

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(is));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    is.close();

                    elapsedTime = System.currentTimeMillis() - startTime;

                    return response.toString();
                }
                catch(IOException e){
                    String msg = "Cannot load data: " + e.getMessage();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                        JSONObject json = new JSONObject(result);
                        String code = json.getString("code");
                        String message = json.getString("message");
                        MyApplication myApplication = (MyApplication)getApplicationContext();
                        if(code.equals("Success"))
                        {
                            myApplication.setLogged(true);
                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setTitle("Status");
                            alert.setMessage(code + " " + message + "\n" + "Elapsed Time: " + String.valueOf(elapsedTime) + " milliseconds");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(mainIntent);
                                }
                            });
                            alert.show();
                        }
                        else
                        {
                            myApplication.setLogged(false);
                        }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),"INVALID REQUEST", Toast.LENGTH_LONG).show();
                }
            }

            private void addFormPart(DataOutputStream dos, String paramName, String value) throws Exception {
                writeParamData(dos, paramName, value);
            }

            private void writeParamData(DataOutputStream dos, String paramName, String value) throws Exception {
                dos.write( (twoHyphens + boundary + lineEnd).getBytes());
                dos.write( "Content-Type: text/plain\r\n".getBytes());
                dos.write( ("Content-Disposition: form-data; name=\"" + paramName + "\""+lineEnd).getBytes());
                dos.write( (lineEnd + value +lineEnd).getBytes());

            }

        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.setContext(this);
        sendPostReqAsyncTask.execute(givenUsername, givenPassword);
    }
}

