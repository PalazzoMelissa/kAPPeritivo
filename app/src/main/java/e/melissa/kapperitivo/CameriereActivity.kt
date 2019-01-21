package e.melissa.kapperitivo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import model.Tavolo
import sql.DatabaseHelper

/**
 * Created by melissa on 01/01/19.
 */
class CameriereActivity: AppCompatActivity(), View.OnClickListener  {
    private var cameriere_attivita = this@CameriereActivity
    private var vedi_username= findViewById<TextView>(R.id.vedi)
    private var tavolo1= findViewById<Button>(R.id.tavolo1)
    private var tavolo2= findViewById<Button>(R.id.tavolo2)
    private var tavolo3= findViewById<Button>(R.id.tavolo3)
    private var tavolo4= findViewById<Button>(R.id.tavolo4)
    private var tavolo5= findViewById<Button>(R.id.tavolo5)
    private var tavolo6= findViewById<Button>(R.id.tavolo6)
    private var tavolo7= findViewById<Button>(R.id.tavolo7)
    private var tavolo8= findViewById<Button>(R.id.tavolo8)
    private var tavolo9= findViewById<Button>(R.id.tavolo9)
    private var tavolo10= findViewById<Button>(R.id.tavolo10)
    private var tavolo11= findViewById<Button>(R.id.tavolo11)
    private var tavolo12= findViewById<Button>(R.id.tavolo12)
    private var databaseHelper= DatabaseHelper(cameriere_attivita)
    private lateinit var usernameFromIntent: String


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user)
        vedi_username= intent.getStringExtra("USERNAME") as TextView
        usernameFromIntent=intent.getStringExtra("USERNAME")
        vedi_username.setText("Benvenuto "+usernameFromIntent + ", seleziona il tavolo:")

        init_Listeners()

        for(i in 0..12)
        {
            var t= Tavolo()

            t.setNumero(i)
            databaseHelper.addTavolo(t)
        }

    }

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
    }



    override fun onClick(view: View)
    {
        //gestisco i click sugli TextEdit
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

            R.id.vedi_ordini -> {
                var intent= Intent(applicationContext, VediOrdiniActivity::class.java)

            }
        }
    }


    private fun ordina(tavolo: Tavolo)
    {
        val intent = Intent(applicationContext, OrdinaActivity::class.java)
        //passo all'activity successiva il dato relativo al tavolo da cui ho ricevuto un ordine
        intent.putExtra("Tavolo", tavolo.getNumero())
        intent.putExtra("Cameriere_usrnm", usernameFromIntent)
        //faccio partire intent
        startActivity(intent)

    }




}