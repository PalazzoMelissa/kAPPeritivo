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

class Login : AppCompatActivity(), View.OnClickListener {

    private var activity : AppCompatActivity? = this@Login
    private lateinit var aggiungi_profilo: Button


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



    //inizializzo parti del form
    private fun initViews()
    {
        var preferences : SharedPreferences  = getPreferences(MODE_PRIVATE)
        aggiungi_profilo = findViewById(R.id.crea)
        accesso= findViewById(R.id.accedi)
        username= findViewById(R.id.username)
    }

    //inizializzo bottoni
    private fun initListeners(){
        accesso.setOnClickListener(this)
        aggiungi_profilo.setOnClickListener(this)
    }

    //inizializzo oggetti
    private fun initObjects()
    {
         databaseHelper= DatabaseHelper(this)
         inputValidation= InputValidation(this)

    }



    @SuppressLint("CommitPrefEdits")


        override fun onPause()
        {
            super.onPause()
            var preferences : SharedPreferences = getPreferences(MODE_PRIVATE)
            var editor : SharedPreferences.Editor = preferences.edit()

            var usn : EditText = findViewById(R.id.username)
            var string_usrnm = usn.text.toString()

            editor.putString("username", string_usrnm)
            editor.commit()

        }


        override fun onClick(view : View) {
            //gestione casi click in uno dei due bottoni
            when(view.id){
                R.id.accedi->{
                    //click sul bottone di accesso all'app
                    verifyFromSQLite()
                }
                R.id.crea->{
                    val intent = Intent(applicationContext, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        //verifica del login
        fun verifyFromSQLite(){
            //se ho l'username vuoto, allora non eseguo
            if(!inputValidation.isInputEditTextFilled(username)){
                Toast.makeText(applicationContext,"Inserisci un valore valido!", Toast.LENGTH_LONG).show()
                return
            }

            if(databaseHelper.checkCameriere(username.text.toString().trim())){
                //se l'username inserito è esatto accedo alla pagina di gestione, passando il valore dell'username
                var accedi_area = Intent(activity, CameriereActivity::class.java)
                accedi_area.putExtra("USERNAME", username.text.toString().trim())

                username.text = null
                startActivity(accedi_area)
            }else
            {
                //altrimenti avviso l'utente di aver inserito un username non valido
                Toast.makeText(activity, "Errore, cameriere"+username.text.toString().trim()+" non iscritto", Toast.LENGTH_SHORT).show()
            }
        }


    }
