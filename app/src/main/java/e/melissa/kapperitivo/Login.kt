package e.melissa.kapperitivo

import helper.InputValidation
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import sql.DatabaseHelper

//import com.example.gian2.apperitivogmm.helper.InputValidation
//import com.example.gian2.apperitivogmm.sql.DatabaseHelper

open class Login : AppCompatActivity() {

    private lateinit var aggiungi_profilo: Button
    private var activity : AppCompatActivity? = this@Login


    private lateinit var accesso : Button
    private lateinit var username : EditText
    private lateinit var inputValidation : InputValidation
    private lateinit var databaseHelper : DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        supportActionBar?.hide()

        initViews()
        initListeners()
        initObjects()

    }


private infix fun Int.getSupportActionBar(hide: Any): Any {}


        //inizializzo parti del form
        private fun initViews()
    {
        var preferences : SharedPreferences  = getPreferences(MODE_PRIVATE)
        aggiungi_profilo = findViewById(R.id.crea) as Button
        accesso= findViewById(R.id.accedi)
        username= findViewById(R.id.username) as EditText
        var String_usrnm : String = preferences.getString("username",null)
    }
        //inizializzo bottoni
        private fun initListeners(){
            accesso.setOnClickListener(this@Login)
        aggiungi_profilo.setOnClickListener(this)
    }
        //inizializzo oggetti
        private fun initObjects()
        {
            var databaseHelper : DatabaseHelper(activity)
            var inputValidation : InputValidation(activity)

        }



        @SuppressLint("CommitPrefEdits")


        override fun onPause()
        {
            super.onPause()
            var preferences : DatabaseHelper = getPreferences(MODE_PRIVATE)
            var editor : SharedPreferences.Editor = preferences.edit()

            var usn : EditText = findViewById(R.id.username)
            var string_usrnm = usn.getText().toString()

            editor.putString("username", string_usrnm)
            editor.commit()

        }


        override fun onClick(view : View) {
            //gestione casi click in uno dei due bottoni
            if(view.getId().toString()=="accedi")

            //click sul bottone di accesso all'app
            verifyFromSQLite()

            else if(view.getId().toString()=="crea")
                //click sul bottone di registrazione per l'utilizzo dell'app
                var intent : Intent = Intent(getApplicationContext(),RegisterActivity.class)
                        startActivity(intent)

        }

        //verifica del login
        fun verifyFromSQLite(){
            //se ho l'username vuoto, allora non eseguo
            if(!inputValidation.isInputEditTextFilled(username)){
                Toast.makeText(getApplicationContext(),"Inserisci un valore valido !",Toast.LENGTH_LONG).show()
                return
            }
            //se l'username inserito è esatto
            if(databaseHelper.checkCameriere(username.getText().toString().trim())){
                //accedo alla pagina di gestione
                var accedi_area = Intent(activity, CameriereActivity.class)
                        //passo a tale pagina il valore dell'username
                accedi_area.putExtra("USERNAME", username.getText().toString().trim())

                username.setText(null)
                startActivity(accedi_area)
                //altrimenti
            }else
            {
                //avviso l'utente di non aver inserito un username non valido
                Toast.makeText(activity, "Errore, cameriere non iscritto", Toast.LENGTH_SHORT).show()
            }
        }


    }

}
