package e.melissa.kapperitivo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import e.melissa.kapperitivo.R
import model.Cameriere
import model.Tavolo
import sql.DatabaseHelper
/**
 * Created by melissa on 01/01/19.
 */
class CameriereActivity: AppCompatActivity()  {
    private var cameriere_attivita = this@CameriereActivity
    private lateinit var vedi_username: TextView
    private lateinit var tavolo1: Button
    private lateinit var tavolo2: Button
    private lateinit var tavolo3: Button
    private lateinit var tavolo4: Button
    private lateinit var tavolo5: Button
    private lateinit var tavolo6: Button
    private lateinit var tavolo7: Button
    private lateinit var tavolo8: Button
    private lateinit var tavolo9: Button
    private lateinit var tavolo10: Button
    private lateinit var tavolo11: Button
    private lateinit var tavolo12: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var usernameFromIntent: String


    override fun onCreate(savedInstanceState: Bundle)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user)
        vedi_username= intent.getStringExtra("USERNAME") as TextView

        init_Views()
        init_Listeners()
        init_Objects()

        for(i in 0..12)
        {
            var t: Tavolo= Tavolo()

            t.setNumero(i)
            databaseHelper.addTavolo(t)
        }

    }


    private fun init_Views()
    {
        vedi_username= findViewById(R.id.vedi)

        //bottoni
        tavolo1 = findViewById(R.id.tavolo1)
        tavolo2 = findViewById(R.id.tavolo2)
        tavolo3 = findViewById(R.id.tavolo3)
        tavolo4 = findViewById(R.id.tavolo4)
        tavolo5 = findViewById(R.id.tavolo5)
        tavolo6 = findViewById(R.id.tavolo6)
        tavolo7 = findViewById(R.id.tavolo7)
        tavolo8 = findViewById(R.id.tavolo8)
        tavolo9 = findViewById(R.id.tavolo9)
        tavolo10 = findViewById(R.id.tavolo10)
        tavolo11 = findViewById(R.id.tavolo11)
        tavolo12 = findViewById(R.id.tavolo12)
    }


    private fun init_Listeners()
    {
        tavolo1.setOnClickListener(this as View.OnClickListener)
        tavolo2.setOnClickListener(this as View.OnClickListener)
        tavolo3.setOnClickListener(this as View.OnClickListener)
        tavolo4.setOnClickListener(this as View.OnClickListener)
        tavolo5.setOnClickListener(this as View.OnClickListener)
        tavolo6.setOnClickListener(this as View.OnClickListener)
        tavolo7.setOnClickListener(this as View.OnClickListener)
        tavolo8.setOnClickListener(this as View.OnClickListener)
        tavolo9.setOnClickListener(this as View.OnClickListener)
        tavolo10.setOnClickListener(this as View.OnClickListener)
        tavolo11.setOnClickListener(this as View.OnClickListener)
        tavolo12.setOnClickListener(this as View.OnClickListener)
    }


    private fun init_Objects() {var databaseHelper= DatabaseHelper(cameriere_attivita) }


    override fun onClick(view: View)
    {
        //gestisco i click sugli TextEdit
        var tavolo= Tavolo()

        when (view.id) {
            R.id.tavolo1 -> {
                tavolo.setNumero(1)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo2 -> {
                tavolo.setNumero(2)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo3 -> {
                tavolo.setNumero(3)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()

                ordina(tavolo)
            }
            R.id.tavolo4 -> {
                tavolo.setNumero(4)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo5 -> {
                tavolo.setNumero(5)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo6 -> {
                tavolo.setNumero(6)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo7 -> {
                tavolo.setNumero(7)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo8 -> {
                tavolo.setNumero(8)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo9 -> {
                tavolo.setNumero(9)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo10 -> {
                tavolo.setNumero(10)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo11 -> {
                tavolo.setNumero(11)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
            }
            R.id.tavolo12 -> {
                tavolo.setNumero(12)
                Toast.makeText(cameriere_attivita, "Tavolo " + tavolo.getNumero(), Toast.LENGTH_SHORT).show()
                ordina(tavolo)
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