package com.aldo.telefono;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String num = "";
    ListView listaContactos;
    EditText editMensaje;
    String hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaContactos = (ListView) findViewById(R.id.listaContactos);
        editMensaje = (EditText) findViewById(R.id.editMensaje);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);

        listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String contactoSeleccionado = (String) adapterView.getItemAtPosition(i);
                if(contactoSeleccionado.equals("Paloma")){
                    num = "+526682516091";
                    hint = "Cuenta chisme Paloma";
                }else if(contactoSeleccionado.equals("Yaritza")){
                    num = "+526683958054";
                    hint = "Cuenta chisme Yari";
                }else if(contactoSeleccionado.equals("Liz")){
                    num = "+526682044900";
                    hint = "Cuenta chisme esquizo";
                }else if(contactoSeleccionado.equals("Carlos")){
                    num = "+526681997834";
                    hint = "Cuenta chisme mi amor";
                }
                editMensaje.setHint(hint);
            }
        });

    }
    public void enviarMensaje(View v){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            String mensaje = editMensaje.getText().toString();
            if(!mensaje.equals("") && !num.equals("")){
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(num, null, mensaje, null, null);
                Toast.makeText(this, "Enviando mensaje...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "El mensaje se ha enviado!", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Y el mensaje pa'? Escribe algo primero, pongase pilas.", Toast.LENGTH_SHORT).show();
            }
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            enviarMensaje(v);
        }

    }
    public void teclado(View v){
        if(!num.equals("")){
            Intent iMarcar = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
            iMarcar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(iMarcar);
        }
    }
    public void llamar(View v){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            if(!num.equals("")){
                Intent iMarcar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
                iMarcar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iMarcar);
            }else{
                Toast.makeText(this, "No soy adivino pa', selecciona el contacto.", Toast.LENGTH_SHORT).show();
            }
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            llamar(v);
        }

    }
}