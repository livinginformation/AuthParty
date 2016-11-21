package authparty.authparty_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mrd.bitlib.crypto.InMemoryPrivateKey;
import com.mrd.bitlib.crypto.Signatures;
import com.mrd.bitlib.crypto.SignedMessage;
import com.mrd.bitlib.model.NetworkParameters;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client;
    MediaType JSON;
    String urlPost, signature, publickey,message;
    private static InMemoryPrivateKey privKey;
    String privateKey="";
    String contents="";
    NetworkParameters network = NetworkParameters.productionNetwork;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        privateKey=i.getStringExtra("privateKey");
    }

    //product barcode mode
    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //product qr code mode
    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_SHORT);
                toast.show();
                String a[]=contents.split(" ");
                showDialog2(MainActivity.this, "CVParty", "Connect to url "+a[1]+"?", "Yes", "No",contents).show();
                // sendContents(contents);
//                Intent i = new Intent(getBaseContext(),ResultUseEndPt.class);
//                i.putExtra("contents", contents);
//
//                startActivity(i);




            }
        }
    }
    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }



    private  AlertDialog showDialog2(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo, final String contents1) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                sendContents(contents1);
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }


    public void sendContents(String contents) {
        //privKey = new InMemoryPrivateKey("Kyj7PtGZUiS9FcaLvzW47rb7oQCvFKuYm1fSHeWVk2KutGmtFCgY", network);
        Log.i("mansi","hjh");
        privKey = new InMemoryPrivateKey(privateKey, network);

        String abc[] = contents.split(" ");


        message = abc[0];
        byte[] msg = Signatures.formatMessageForSigning(message);
        SignedMessage signed2 = privKey.signMessage(message);

        String sigStr = signed2.getBase64Signature();
        //System.out.println(sigStr);



        urlPost = "http://" + abc[1];
        //urlPost="http://requestb.in/10x0iki1";
        signature = sigStr;
        publickey = privKey.getPublicKey().toAddress(network).toString();


        client = new OkHttpClient();
        JSON = MediaType.parse("application/json; charset=utf-8");
        try {
            makePostRequest();
        } catch (Exception e) {

        }


    }

    protected void onPostExecute(String getResponse) {
        System.out.println(getResponse);
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public void makePostRequest() throws IOException {
        PostTask task = new PostTask();
        task.execute();
    }

    public class PostTask extends AsyncTask<String, Void, String> {
        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                String getResponse = post(urlPost, makingJson(signature, publickey, message));
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(String getResponse) {
            CharSequence text = "";
            {
                if(getResponse == null || "".equals(getResponse)){
                    text = "Sorry a failure occured";
                }
                else if (getResponse.contains("Authenticated")) {
                    text = "Authenticated Successfully";
                } else {

                    text = "Sorry a failure occured";
                }
                Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG);
                toast.show();

                System.out.println(getResponse);
            }
        }

        private String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }

    public String makingJson(String signature, String publickey, String message) {
        return "{'signature':'"+ signature+"',"
                +"'address':'"+ publickey+"',"
                +"'challenge_string':'"+ message+"'}";
    }



}
