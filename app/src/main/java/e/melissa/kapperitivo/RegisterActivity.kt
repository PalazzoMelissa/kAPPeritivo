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

    private lateinit var registrati_button: Button
    private lateinit var username:EditText
    private lateinit var nome:EditText
    private lateinit var cognome :EditText
    private lateinit var numeroTel:EditText
    private var cameriere= Cameriere()  //memorizza i dati del cameriere che si vuole iscrivere

    private var databaseHelper= DatabaseHelper(this)
    private var inputValidation= InputValidation(this)


    override fun onCreate (savedInstanceState: Bundle?)
    {
        //avvia l'activity e imposta il layout corrispondente
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_registration)

        initViews()
        initListeners()

        //nasconde la toolbar con il nome dell'applicazione
        supportActionBar?.hide()

    }


    //inizializza i bottoni e gli editText
    private fun initViews()
    {
        registrati_button = findViewById(R.id.registrati)
        username= findViewById(R.id.usrnm)
        nome= findViewById(R.id.nome)
        cognome = findViewById(R.id.cognome)
        numeroTel = findViewById(R.id.numtel)
        numeroTel.inputType = InputType.TYPE_CLASS_NUMBER
    }


    private fun initListeners()
    {
        //inizializza il Listener per la pressione del bottone
        registrati_button.setOnClickListener(this@RegisterActivity as View.OnClickListener)
    }


    override fun onClick(v: View)
    {
        //premendo il bottone si verifica se la registrazione è andata a buon fine
        if(v == registrati_button)
            postDataToSQLite()
    }


    private fun postDataToSQLite(){

        //i campi dell'iscrizione devono essere tutti riempiti, altrimenti viene segnalato

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


        //ultimo campo da controllare: se è stato riempito si procede con la registrazione
        //inserendo nella variabile del cameriere e poi nel database il valore immesso nei campi
        if(!databaseHelper.checkCameriere(username.text.toString().trim()))
        {
            cameriere.setUsername(username.text.toString().trim())
            cameriere.setNome(nome.text.toString().trim())
            cameriere.setCognome(cognome.text.toString().trim())
            cameriere.setNumTel(numeroTel.text.toString().trim())

            databaseHelper.addCameriere(cameriere)
            Toast.makeText(this,"Sei stato registrato con successo", Toast.LENGTH_LONG).show()
            emptyInputEditText()

            startActivity(Intent(applicationContext, Login::class.java))

        }else

            //non può mancare proprio l'username...
            Toast.makeText(this,"Errore nella registrazione",Toast.LENGTH_LONG).show()

    }

    fun emptyInputEditText()
    {
        username.text = null
        nome.text = null
        cognome.text = null
        numeroTel.text = null
    }
}

