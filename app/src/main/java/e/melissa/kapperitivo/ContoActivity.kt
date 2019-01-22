package com.example.gian2.apperitivogmm.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

import model.CustomPietanzaOrdinataAdapter
import e.melissa.kapperitivo.R
import model.Ordine
import sql.DatabaseHelper

/**
 * Created by gian2 on 14/01/2019.
 */

class ContoActivity : AppCompatActivity(), View.OnClickListener {

    private var databaseHelper: DatabaseHelper? = null
    //textView per vedere il conto senza modifiche
    private var conto_senza_modificheTextView: TextView? = null
    //textView per vedere il conto totale
    private var conto_totaleTextView: TextView? = null
    //textView per vedere il conto delle modifiche
    private var conto_modificheTextView: TextView? = null
    //conto totale
    private var info_ordine: TextView? = null
    //conto senza modifiche
    private var conto_senza_modifiche: Float = 0.toFloat()
    //conto delle modifiche
    private var conto_modifiche: Float = 0.toFloat()
    //conto totale
    private var conto_totale: Float = 0.toFloat()
    //tavolo dell' ordine
    private var tavolo: Int = 0
    //cameriere responsabile
    private var cameriere: String? = null
    //buton per tornare alla scelta dei tavoli post completamento ordine
    private var torna_tavolo: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conto)
        //ottengo dati passati via Intent
        //ottengo conti tavolo,cameriere dalla precedente activity ConfermaOrdineActivity
        conto_senza_modifiche = intent.getFloatExtra("conto_senza_modifiche", 0f)
        conto_modifiche = intent.getFloatExtra("conto_modifiche", 0f)
        tavolo = intent.getIntExtra("tavolo", 0)
        cameriere = intent.getStringExtra("Cameriere_usrnm")
        conto_totale = conto_modifiche + conto_senza_modifiche
        //inizializzo views
        initViews()
        //inizializzo listeners
        initListeners()
        //inizializzo oggetti
        initObjects()
        //inserisco stringa per info dell'ordine
        info_ordine!!.text = "Ordine  del cameriere $cameriere \nal tavolo $tavolo"
        conto_modificheTextView!!.text = "Importo modifiche: € $conto_modifiche"
        conto_senza_modificheTextView!!.text = "Importo parziale: € $conto_senza_modifiche"
        conto_totaleTextView!!.text = "Importo totale : € $conto_totale"


    }


    private fun initViews() {
        //inizializzo tutti i componenti del Layout
        conto_totaleTextView = findViewById(R.id.conto_totale)
        conto_senza_modificheTextView = findViewById(R.id.conto_senza_modifiche)
        conto_modificheTextView = findViewById(R.id.conto_modifiche)
        info_ordine = findViewById(R.id.info)
        torna_tavolo = findViewById(R.id.torna)
    }

    private fun initListeners() {
        //assoccio listener al Button
        torna_tavolo!!.setOnClickListener(this)

    }


    private fun initObjects() {
        databaseHelper = DatabaseHelper(applicationContext)
    }

    override fun onClick(view: View) {
        //creo ordine e lo inserisco nel database
        val ordine = Ordine()
        ordine.setTavolo(tavolo)
        ordine.setCameriere(cameriere)
        ordine.setConto(conto_totale)
        /*
        **inserisco l'ordine nel database e tale operazione mi restituisce il codice dell'ordine
        **essendo che l'attributo codice dell'ordine è un auto_increment
        */
        ordine.setCodice(databaseHelper!!.addOrdine(ordine))
        //inserisco ogni pietanza ordinata nella tabella del database composto
        for (i in 0 until CustomPietanzaOrdinataAdapter.pietanzeOrdinate.size()) {
            //ottengo i diversi attributi della pietanza ordinata di indice i
            val pietanza = CustomPietanzaOrdinataAdapter.pietanzeOrdinate.get(i).getNomePietanza()
            val quantita = CustomPietanzaOrdinataAdapter.pietanzeOrdinate.get(i).getQuantita()
            val modifica = CustomPietanzaOrdinataAdapter.pietanzeOrdinate.get(i).getModifica()
            //aggiungo tale pietanza ordinata, la quantita, e le possibili modifiche al database nella tabella composto
            databaseHelper!!.addComposto(ordine, pietanza, quantita, modifica)
        }
        val intent = Intent(this@ContoActivity, CameriereActivity::class.java)
        intent.putExtra("USERNAME", cameriere)
        startActivity(intent)
    }


}
