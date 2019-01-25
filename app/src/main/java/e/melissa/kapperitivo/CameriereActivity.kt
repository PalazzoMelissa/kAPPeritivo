package e.melissa.kapperitivo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.gian2.apperitivogmm.activities.OrdinaActivity
import model.Tavolo
import sql.DatabaseHelper

/**
 * Created by melissa on 01/01/19.
 */
class CameriereActivity: AppCompatActivity(), View.OnClickListener  {
    private lateinit var vedi_username:TextView
    private lateinit var tavolo1:Button
    private lateinit var tavolo2:Button
    private lateinit var tavolo3:Button
    private lateinit var tavolo4:Button
    private lateinit var tavolo5:Button
    private lateinit var tavolo6:Button
    private lateinit var tavolo7:Button
    private lateinit var tavolo8:Button
    private lateinit var tavolo9:Button
    private lateinit var tavolo10:Button
    private lateinit var tavolo11:Button
    private lateinit var tavolo12:Button
    private lateinit var databaseHelper:DatabaseHelper
    private lateinit var usernameFromIntent: String
    private lateinit var buttonVediOrdini: Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        //avvia l'activity e imposta il layout corrispondente
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user)

        databaseHelper=DatabaseHelper(this)
        usernameFromIntent=intent.getStringExtra("USERNAME")

        initViews()
        init_Listeners()
        supportActionBar?.hide()

        //testo di benvenuto al cameriere he ha eseguito il login
        vedi_username.text = "Benvenuto "+usernameFromIntent + ", seleziona il tavolo:"

        //insetisce nel database i 12 tavoli presenti in sala
        for(i in 0..12)
        {
            var t= Tavolo()

            t.setNumero(i)
            databaseHelper.addTavolo(t)
        }

    }

    //inizializza i bottoni e la textView
    private fun initViews(){
        vedi_username= findViewById(R.id.vedi)
        tavolo1= findViewById(R.id.tavolo1)
        tavolo2= findViewById(R.id.tavolo2)
        tavolo3= findViewById(R.id.tavolo3)
        tavolo4= findViewById(R.id.tavolo4)
        tavolo5= findViewById(R.id.tavolo5)
        tavolo6= findViewById(R.id.tavolo6)
        tavolo7= findViewById(R.id.tavolo7)
        tavolo8= findViewById(R.id.tavolo8)
        tavolo9= findViewById(R.id.tavolo9)
        tavolo10= findViewById(R.id.tavolo10)
        tavolo11= findViewById(R.id.tavolo11)
        tavolo12= findViewById(R.id.tavolo12)
        buttonVediOrdini=findViewById(R.id.vedi_ordini)

    }

    //inizializza i listener dei bottoni
    private fun init_Listeners()
    {
        tavolo1.setOnClickListener(this)
        tavolo2.setOnClickListener(this)
        tavolo3.setOnClickListener(this)
        tavolo4.setOnClickListener(this)
        tavolo5.setOnClickListener(this)
        tavolo6.setOnClickListener(this)
        tavolo7.setOnClickListener(this)
        tavolo8.setOnClickListener(this)
        tavolo9.setOnClickListener(this)
        tavolo10.setOnClickListener(this)
        tavolo11.setOnClickListener(this)
        tavolo12.setOnClickListener(this)
        buttonVediOrdini.setOnClickListener(this)
    }



    override fun onClick(view: View)
    {
        //gestisco i click sui bottoni: inserisco il numero del tavolo corretto nella variabile
        var tavolo= Tavolo()

        when (view.id) {
            R.id.tavolo1 -> {
                tavolo.setNumero(1)
                ordina(tavolo)
            }

            R.id.tavolo2 -> {
                tavolo.setNumero(2)
                ordina(tavolo)
            }

            R.id.tavolo3 -> {
                tavolo.setNumero(3)
                ordina(tavolo)
            }

            R.id.tavolo4 -> {
                tavolo.setNumero(4)
                ordina(tavolo)
            }

            R.id.tavolo5 -> {
                tavolo.setNumero(5)
                ordina(tavolo)
            }

            R.id.tavolo6 -> {
                tavolo.setNumero(6)
                ordina(tavolo)
            }

            R.id.tavolo7 -> {
                tavolo.setNumero(7)
                ordina(tavolo)
            }

            R.id.tavolo8 -> {
                tavolo.setNumero(8)
                ordina(tavolo)
            }

            R.id.tavolo9 -> {
                tavolo.setNumero(9)
                ordina(tavolo)
            }

            R.id.tavolo10 -> {
                tavolo.setNumero(10)
                ordina(tavolo)
            }

            R.id.tavolo11 -> {
                tavolo.setNumero(11)
                ordina(tavolo)
            }

            R.id.tavolo12 -> {
                tavolo.setNumero(12)
                ordina(tavolo)
            }

            //passa all'activity che mostra lo storico degli ordini fatti dal cameriere corrente
            R.id.vedi_ordini -> {
                var intent= Intent(applicationContext, VediOrdiniActivity::class.java)
                intent.putExtra("cameriere", usernameFromIntent)
                startActivity(intent)
            }
        }
    }


    private fun ordina(tavolo: Tavolo)
    {
        //passa all'activity successiva il dato relativo al tavolo da cui ho ricevuto un ordine e il cameriere che l'ha fatto
        val intent = Intent(applicationContext, OrdinaActivity::class.java)
        intent.putExtra("Tavolo", tavolo.getNumero())
        intent.putExtra("Cameriere_usrnm", usernameFromIntent)
        //faccio partire intent
        startActivity(intent)

    }




}