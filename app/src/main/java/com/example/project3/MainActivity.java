package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigInteger;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText input, output;
    private String pubKey = "";
    private String privateKey = "";
    private byte[] encode = null;
    private Button VisualButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.inputEdit);
        output = findViewById(R.id.outputEdit);
        VisualButton = findViewById(R.id.VisualButton);

        VisualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MainActivity.this, Visual.class);
                startActivity(startIntent);
            }
        });

        try {
            Map<String, Object> keyMap = RSA.initKey();
            pubKey = RSA.getPublicKey(keyMap);
            privateKey = RSA.getPrivateKey(keyMap);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void encrypt(View v){
        byte[] Data = input.getText().toString().getBytes();

        try {
            encode = RSA.encryptByPublicKey(Data, getPubKey());
            String encodeString = new BigInteger(1, encode).toString();
            output.setText(encodeString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt(View v){
        String privateKey = getPrivateKey();

        try {
            byte[] decode = RSA.decryptByPrivateKey(encode, getPrivateKey());
            String decodeString = new String(decode);
            output.setText(decodeString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPubKey() {
        return pubKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}