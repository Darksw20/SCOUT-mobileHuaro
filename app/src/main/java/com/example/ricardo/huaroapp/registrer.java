package com.example.ricardo.huaroapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.util.HashMap;
import java.util.Map;

public class registrer extends Activity
{
    EditText cum, nombre, a_pat, a_mat, contra, confirmContra,grupo, fecha;
    int sexo=3;
    Button registrar;
    Activity Contexto=this;
    SharedPreferences loggin, usu;
    String Dfecha;
    int validado;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer);

        cum=(EditText)findViewById(R.id.ingcum);
        nombre=(EditText)findViewById(R.id.ingnomb);
        a_pat=(EditText)findViewById(R.id.ingap);
        a_mat=(EditText)findViewById(R.id.ingam);
        contra=(EditText)findViewById(R.id.contraseña);
        confirmContra=(EditText)findViewById(R.id.confi);
        grupo=(EditText)findViewById(R.id.gscot);
        registrar=(Button)findViewById(R.id.regis);
        fecha=(EditText)findViewById(R.id.fecha_na);

        usu=getApplicationContext().getSharedPreferences("usuarios",MODE_PRIVATE);
        loggin=getApplicationContext().getSharedPreferences("Archivo",MODE_PRIVATE);
    }

    public void entrar(View v)
    {
        switch (v.getId())
        {
            case R.id.fecha_na:
                showDatePickerDialog();
                break;
        }
    }

    //Revisa que los datos esten correctos dentro de la app
    public void registrar(View view)
    {
        if(sexo==3||grupo.getText().toString().equals("")||confirmContra.getText().toString().equals("")||cum.getText().toString().equals("")||nombre.getText().toString().equals("")||a_pat.getText().toString().equals("")||a_mat.getText().toString().equals("")||contra.getText().toString().equals(""))
        {
            Toast.makeText(Contexto,"Ingrese todos los datos",Toast.LENGTH_LONG).show();
        }
        else
        {
            if (contra.getText().toString().equals(confirmContra.getText().toString())) {
                guardar();
            } else {
                Toast.makeText(Contexto, "Error de coincidencia en las contraseñas", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void guardar()
    {
        final String Dcum=cum.getText().toString(),Dnombre=nombre.getText().toString(),Dapat=a_pat.getText().toString(),Damat=a_mat.getText().toString();
        final String Dcont=contra.getText().toString(),gg=grupo.getText().toString(),Dsex=Integer.toString(sexo),Dfechas=Dfecha;

        RequestQueue Objeto = Volley.newRequestQueue(Contexto);
        final String Direccion = "http://192.168.43.141:1234/Huaro/Movil/guard.php";


        StringRequest postRequest = new StringRequest(Request.Method.POST, Direccion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++)
                    {         //obtengo los valores del response de volley
                        JSONObject obj = jsonarray.getJSONObject(i);  //convierto de json a string separadas
                        validado = obj.getInt("tipo");
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                //Aqui confirmo que se han ingresado los datos correctamente y se guarda en shared preferences
                if (validado!=0)
                {
                    Toast.makeText(Contexto,"Bienvenido",Toast.LENGTH_LONG).show();//Toast al iniciar correctamente
                    SharedPreferences.Editor x, y;
                    x = loggin.edit();
                    y = usu.edit();

                    y.putString("usuario",Dcum);
                    x.putInt("tipoUsuario", validado);

                    x.commit();
                    y.commit();

                    //entra a la actividad Dashboard
                    Intent dash = new Intent(Contexto, dash.class);

                    startActivity(dash);
                    finish();

                }
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("Error.Response","valio madre");
            }

        })
        {
           @Override
            protected Map<String, String> getParams()
           {
                Map<String, String> params= new HashMap<String, String>();
                params.put("cum",Dcum);
                params.put("nombre",Dnombre);
                params.put("apat",Dapat);
                params.put("amat",Damat);
                params.put("contra",Dcont);
                params.put("gg",gg);
                params.put("sexo",Dsex);
                params.put("fechaN",Dfechas);

               return params;
           }

        };

        Objeto.add(postRequest);

    }

    public void onRadioButtonClicked(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.masc:
                if (checked) {
                    sexo = 1;
                }
                break;
            case R.id.fem:
                if (checked) {
                    sexo = 0;
                }
                break;
        }
    }

    private void showDatePickerDialog()
    {
        DatePickerFragment  newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String selectDate= year + "-"+ (month+1) +"-"+ dayOfMonth;
                fecha.setText(selectDate);
                Dfecha=selectDate;
            }
        });
        newFragment.show(getFragmentManager(),"datePicker");
    }

}
