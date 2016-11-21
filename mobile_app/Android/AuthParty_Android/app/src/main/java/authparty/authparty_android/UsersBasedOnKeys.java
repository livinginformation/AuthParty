package authparty.authparty_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UsersBasedOnKeys extends AppCompatActivity {
    String privateKeyString="";
    Intent i=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


    }
    //alicepk function
    public void alicePv(View v) {
        privateKeyString="L287MDNEoeLNbgpFB9gAmVr5eaoNhgZfGv7QZA6c7FGUQ8Qetvfa";
        i = new Intent(getBaseContext(),MainActivity.class);
        i.putExtra("privateKey",privateKeyString);
        startActivity(i);

    }

    //bobpk function
    public void bobPv(View v) {
        privateKeyString="L2zs5fKkomZRcq6u2gaEwxv4XNywgV4nmdbFZvuueHGegW1LjKn2";
        i = new Intent(getBaseContext(),MainActivity.class);
        i.putExtra("privateKey",privateKeyString);
        startActivity(i);

    }



    //donpk function
    public void donPv(View v) {
        privateKeyString="Kx5o84poMa4SZp8WxWMJP7nT7CKPc5tXgmVNhXXg8fKnhb5juJgX";
        i = new Intent(getBaseContext(),MainActivity.class);
        i.putExtra("privateKey",privateKeyString);
        startActivity(i);

    }


    //johnpk function

    public void johnPv(View v) {
        privateKeyString="Kx7aKtNgsnbuugY4vBrFhZDcafmQfHLWJY6L5tGpb6nyWumz9Geh";
        i = new Intent(getBaseContext(),MainActivity.class);
        i.putExtra("privateKey",privateKeyString);
        startActivity(i);

    }



}
