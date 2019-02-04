package com.example.ricardo.huaroapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity
{

    EditText cum, contra;
    Button i_sesion, i_invitado, reg;
    SharedPreferences loggin, usu;
    Activity Contexto=this;
    int validado;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cum = (EditText) findViewById(R.id.cum);
        contra = (EditText) findViewById(R.id.contra);
        i_sesion = (Button) findViewById(R.id.IS);
        i_invitado = (Button) findViewById(R.id.II);
        reg = (Button) findViewById(R.id.R);

         /*
        *   Aqui se crean las shared Preferences y se confirma si el usuario
        *   ya ha iniciado sesion
        */

        usu=getApplicationContext().getSharedPreferences("usuarios",MODE_PRIVATE);
        loggin=getApplicationContext().getSharedPreferences("Archivo",MODE_PRIVATE);

        /*
        *    En caso de que ya se haya entrado a la app y no se haya cerrado sesion
        *    ingresará directamente al dashboard
        */

        switch (loggin.getInt("tipoUsuario",0))
        {
            case 0:
                break;
            case 1:
                Intent menu=new Intent(this,dash.class);
                startActivity(menu);
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }

    }


    //esta es la funcion de ingreso al inicio de sesion
    public void iniSesion(View view)
    {
        final String user, pass;

        user = cum.getText().toString();
        pass = contra.getText().toString();
        //cambiar a un switch case        tip = stat.isChecked();

        //aqui guardo la opcion para saber si el que ingresa es participante o staff
        /*
        if(tip)
        {
            tipo="Staff";
        }
        else
        {
            tipo="participante";
        }
        */

        /*
        * Funcion de Volley que envia los datos por metodo get y recibe un booleano que dice si se realizo de manera correcta
        * el inicio de sesion*/
        RequestQueue Objeto = Volley.newRequestQueue(Contexto);//creacion de request

        //Asignacion de URL que envia datos por get
        final String Direccion = "http://192.168.43.141:1234/Huaro/Movil/BaseDe.php?usuario=" + user + "&pass=" + pass;

        //metodo que envia la cadena de busqueda y recibe la respuesta del servicio web
        StringRequest Busca = new StringRequest(Request.Method.GET, Direccion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {         //obtengo los valores del response de volley
                        JSONObject obj = jsonarray.getJSONObject(i);  //convierto de json a string separadas
                        validado = obj.getInt("tipo");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Aqui confirmo que se han ingresado los datos correctamente y se guarda en shared preferences
                if (validado!=0)
                {
                    Toast.makeText(Contexto,"Bienvenido",Toast.LENGTH_LONG).show();//Toast al iniciar correctamente
                    SharedPreferences.Editor x, y;
                    x = loggin.edit();
                    y = usu.edit();

                    y.putString("usuario",user);
                    x.putInt("tipoUsuario", validado);

                    x.commit();
                    y.commit();

                    //entra a la actividad Dashboard
                    Intent nots = new Intent(Contexto, dash.class);

                    startActivity(nots);
                }
                else
                {
                    Toast.makeText(Contexto,"ERROR en usuario/contraseña",Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Contexto,"ERROR",Toast.LENGTH_LONG).show();//En caso de errror mostrara un Toast indicandolo
            }

        }
        );
        Objeto.add(Busca);
    }

    //Al presionar el boton de registro se envia a la ventana de registros
    public void registro(View view)
    {
        Intent registroo=new Intent(Contexto,registrer.class);
        startActivity(registroo);
        finish();
    }
    //esta es la funcion de entrada del modo invitado
    public void iniInvitado(View view)
    {
        Intent Dash = new Intent(Contexto, dash.class);
        startActivity(Dash);
    }
}
