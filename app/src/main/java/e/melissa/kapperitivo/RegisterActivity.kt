package e.melissa.kapperitivo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import helper.InputValidation
import model.Cameriere
import sql.DatabaseHelper

/**
 * Created by melissa on 01/01/19.
 */
class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val register= this
    private lateinit var registrati_button: Button
    private lateinit var username:EditText
    private lateinit var nome:EditText
    private lateinit var cognome :EditText
    private lateinit var numeroTel:EditText
    private var cameriere= Cameriere()

    private var databaseHelper= DatabaseHelper(register)
    private var inputValidation= InputValidation(register)


    override fun onCreate (savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_registration)

        initViews()
        initListeners()

    }


    private fun initViews()
    {
        registrati_button = findViewById<Button>(R.id.registrati)
        username= findViewById<EditText>(R.id.usrnm)
        nome= findViewById<EditText>(R.id.nome)
        cognome = findViewById<EditText>(R.id.cognome)
        numeroTel = findViewById<EditText>(R.id.numtel)
        numeroTel.inputType = InputType.TYPE_CLASS_NUMBER
    }


    private fun initListeners()
    {
        registrati_button.setOnClickListener(this@RegisterActivity as View.OnClickListener)
    }


    override fun onClick(v: View)
    {
        //mostra se la registrazione è andata a buon fine
        if(v == registrati_button)
            postDataToSQLite()
    }


    private fun postDataToSQLite(){
        if(!inputValidation.isInputEditTextFilled(username)){
            Toast.makeText(applicationContext,"Inserisci un valore valido per l'username!",Toast.LENGTH_LONG).show()
            return
        }
        if(!inputValidation.isInputEditTextFilled(nome)){
            Toast.makeText(applicationContext,"Inserisci un valore valido per il nome!",Toast.LENGTH_LONG).show()
            return
        }
        if(!inputValidation.isInputEditTextFilled(cognome)){
            Toast.makeText(applicationContext,"Inserisci un valore valido per il cognome!",Toast.LENGTH_LONG).show()
            return
        }
        if(!inputValidation.isInputTextNumTelFilled(numeroTel) || numeroTel.length() != 10){
            Toast.makeText(applicationContext,"Inserisci un valore valido per il numero di telefono!",Toast.LENGTH_LONG).show()
            return
        }

        if(!databaseHelper.checkCameriere(username.text.toString().trim()))
        {
            cameriere.setUsername(username.text.toString().trim())
            cameriere.setNome(nome.text.toString().trim())
            cameriere.setCognome(cognome.text.toString().trim())
            cameriere.setNumTel(numeroTel.text.toString().trim())

            databaseHelper.addCameriere(cameriere)
            Toast.makeText(register,"Sei stato registrato con successo", Toast.LENGTH_LONG).show()
            emptyInputEditText()

            startActivity(Intent(applicationContext, Login::class.java))

        }else

            Toast.makeText(register,"Errore nella registrazione",Toast.LENGTH_LONG).show()

    }

    fun emptyInputEditText()
    {
        username.text = null
        nome.text = null
        cognome.text = null
        numeroTel.text = null
    }
}

