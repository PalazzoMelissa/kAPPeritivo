package e.melissa.kapperitivo

import Helper.InputValidation
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import e.melissa.kapperitivo.R.id.numtel

/**
 * Created by melissa on 01/01/19.
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var Registrati_button : Button
    private lateinit var username : EditText
    private lateinit var nome : EditText
    private lateinit var cognome : EditText
    private lateinit var numeroTel : EditText
    private lateinit var databaseHelper : DatabaseHelper
    //private var cameriere : Cameriere

    override fun onCreate (savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_registration)

        initViews()
        initListeners()
        initObjects()

    }

    private fun initViews()
    {
        var preferences : SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        var String_username = preferences.getString("username",null)
        var String_cognome = preferences.getString("cognome",null)
        var String_nome = preferences.getString("nome",null)
        var String_numtel = preferences.getString("numtel",null)
        var registrati_button = findViewById(R.id.registrati)
        username = findViewById(R.id.usrnm)
        nome = findViewById(R.id.nome)
        cognome = findViewById(R.id.cognome)
        numeroTel = findViewById(R.id.numtel)
        numeroTel.setInputType(InputType.TYPE_CLASS_NUMBER)

    }


    private fun initListeners()
    {
        registrati_button.setOnClickListener(this@RegisterActivity)
    }


    private fun initObjects()
    {
        var databaseHelper : DataBaseHelper(register)
        var inputValidation : InputValidation(register)
    }


    override fun onClick(v: View)
    {
        if(v == registrati_button)
            //mostra se la registrazione è andata a buon fine
            postDataToSQLite()
    }


    private fun postDataToSQLite(){
        if(!inputValidation.isInputEditTextFilled(username)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per l'username!",Toast.LENGTH_LONG).show()
            return
        }
        if(!inputValidation.isInputEditTextFilled(nome)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per il nome!",Toast.LENGTH_LONG).show()
            return
        }
        if(!inputValidation.isInputEditTextFilled(cognome)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per il cognome!",Toast.LENGTH_LONG).show()
            return
        }
        if(!inputValidation.isInputTextNumTelFilled(numtel) || numtel.length()!= 10){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per il numero di telefono!",Toast.LENGTH_LONG).show()
            return
        }

        if(!databaseHelper.checkCameriere(username.getText().toString().trim()))
        {
            cameriere.setUsername(username.getText().toString().trim())
            cameriere.setNome(nome.getText().toString().trim())
            cameriere.setCognome(cognome.getText().toString().trim())
            cameriere.setNum_telefono(numeroTel.getText().toString().trim())

            databaseHelper.addCameriere(cameriere)
            Toast.makeText(register,"Sei stato registrato con successo",Toast.LENGTH_LONG).show()
            emptyInputEditText()


            var intent = Intent(getApplicationContext(), Login.class)
            startActivity(intent)

        }else

            Toast.makeText(register,"Errore nella registrazione",Toast.LENGTH_LONG).show()

    }

    fun emptyInputEditText()
    {
        username.setText(null)
        nome.setText(null)
        cognome.setText(null)
        numeroTel.setText(null)
    }
}