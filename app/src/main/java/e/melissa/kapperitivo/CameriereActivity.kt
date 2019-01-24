package e.melissa.kapperitivo

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
    private lateinit var cameriere_attivita :CameriereActivity
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


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user)
        cameriere_attivita = this@CameriereActivity
        databaseHelper=DatabaseHelper(cameriere_attivita)
        usernameFromIntent=intent.getStringExtra("USERNAME")
        initViews()
        init_Listeners()
        vedi_username.text = "Benvenuto "+usernameFromIntent + ", seleziona il tavolo:"
        for(i in 0..12)
        {
            var t= Tavolo()

            t.setNumero(i)
            databaseHelper.addTavolo(t)
        }

    }

    private fun initViews(){
        vedi_username= findViewById<TextView>(R.id.vedi)
        tavolo1= findViewById<Button>(R.id.tavolo1)
        tavolo2= findViewById<Button>(R.id.tavolo2)
        tavolo3= findViewById<Button>(R.id.tavolo3)
        tavolo4= findViewById<Button>(R.id.tavolo4)
        tavolo5= findViewById<Button>(R.id.tavolo5)
        tavolo6= findViewById<Button>(R.id.tavolo6)
        tavolo7= findViewById<Button>(R.id.tavolo7)
        tavolo8= findViewById<Button>(R.id.tavolo8)
        tavolo9= findViewById<Button>(R.id.tavolo9)
        tavolo10= findViewById<Button>(R.id.tavolo10)
        tavolo11= findViewById<Button>(R.id.tavolo11)
        tavolo12= findViewById<Button>(R.id.tavolo12)
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
        //gestisco i click sui bottoni
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
                intent.putExtra("cameriere", usernameFromIntent)
                startActivity(intent)
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