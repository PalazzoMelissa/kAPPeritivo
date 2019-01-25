package com.example.gian2.apperitivogmm.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import e.melissa.kapperitivo.CameriereActivity

import e.melissa.kapperitivo.R
import model.EditPietanzaOrdinataModel
import model.Ordine
import sql.DatabaseHelper

/**
 * Created by gian2 on 14/01/2019.
 */

class ContoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var conto_senza_modificheTextView: TextView  //textView per vedere il conto senza modifiche
    private lateinit var conto_totaleTextView: TextView//textView per vedere il conto totale
    private lateinit var conto_modificheTextView: TextView//textView per vedere il conto delle modifiche
    private lateinit var info_ordine: TextView    //conto totale
    private var conto_senza_modifiche: Double=0.0 //conto senza modifiche
    private var conto_modifiche=0.0 //conto delle modifiche
    private var conto_totale: Double=0.0 //conto totale
    private var tavolo: Int = 0 //tavolo dell' ordine
    private lateinit var cameriere: String //cameriere responsabile
    private lateinit var torna_tavolo: Button //button per tornare alla scelta dei tavoli post completamento ordine
    private lateinit var pietanze:ArrayList<EditPietanzaOrdinataModel>


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_conto)
        //ottengo dati passati via Intent
        //conti tavolo, cameriere dalla precedente activity (ConfermaOrdineActivity)
        tavolo = intent.getIntExtra("tavolo", 0)
        cameriere = intent.getStringExtra("Cameriere_usrnm")

        //inizializzo views
        initViews()
        //inizializzo listeners
        initListeners()
        //inizializzo oggetti
        initObjects()

        supportActionBar?.hide()

        //inserisco stringa per info dell'ordine
        info_ordine!!.text = "Ordine  del cameriere $cameriere \nal tavolo $tavolo"
        conto_modificheTextView!!.text = "Importo modifiche: € $conto_modifiche"
        conto_senza_modificheTextView!!.text = "Importo parziale: € $conto_senza_modifiche"
        conto_totaleTextView!!.text = "Importo totale: € $conto_totale"


    }


    private fun initViews() {
        //inizializza tutti i componenti del Layout
        conto_totaleTextView = findViewById(R.id.conto_totale)
        conto_senza_modificheTextView = findViewById(R.id.conto_senza_modifiche)
        conto_modificheTextView = findViewById(R.id.conto_modifiche)
        info_ordine = findViewById(R.id.info)
        torna_tavolo = findViewById(R.id.torna)
    }

    private fun initListeners() {
        //associo listener al Button
        torna_tavolo!!.setOnClickListener(this)

    }


    private fun initObjects() {
        databaseHelper = DatabaseHelper(applicationContext)
        pietanze= intent.extras.getSerializable("pietanze") as ArrayList<EditPietanzaOrdinataModel>
        calcolaContoModifiche(pietanze)
        calcolaContoSenzaModifiche(pietanze)
        conto_totale=conto_senza_modifiche+conto_modifiche
    }

    override fun onClick(view: View) {
        //creo ordine e lo inserisco nel database
        val ordine = Ordine()
        ordine.setTavolo(tavolo)
        ordine.setCameriere(cameriere as String)
        ordine.setConto(conto_totale )
        /*
        **inserisco l'ordine nel database e tale operazione mi restituisce il codice dell'ordine
        **sfruttando il fatto che l'attributo codice dell'ordine è un auto_increment
        */
        ordine.setCodice(databaseHelper!!.addOrdine(ordine))
        //inserisco ogni pietanza ordinata nella tabella del database composto
        for (i in 0 .. pietanze.size-1) {
            //ottengo i diversi attributi della pietanza ordinata di indice i
            val pietanza = pietanze.get(i).getNomePietanza() as String
            val quantita = pietanze.get(i).getQuantita()
            val modifica = pietanze.get(i).getModifica() as String
            //aggiungo tale pietanza ordinata, la quantita, e le possibili modifiche al database nella tabella composto
            databaseHelper!!.addComposto(ordine, pietanza, quantita, modifica)
        }
        val intent = Intent(this@ContoActivity, CameriereActivity::class.java)
        intent.putExtra("USERNAME", cameriere)
        startActivity(intent)
    }


    //calcolo conto senza modifiche
    private fun calcolaContoSenzaModifiche(arrayEditPietanzaOrdinataModel: ArrayList<EditPietanzaOrdinataModel>){
        for (i in 0..arrayEditPietanzaOrdinataModel.size-1)
        {
            conto_senza_modifiche += (arrayEditPietanzaOrdinataModel[i].getCosto()* arrayEditPietanzaOrdinataModel[i].getQuantita())

        }
    }

    private fun calcolaContoModifiche(arrayEditPietanzaOrdinataModel: ArrayList<EditPietanzaOrdinataModel>){
        for(i in 0..arrayEditPietanzaOrdinataModel.size-1){
            if(arrayEditPietanzaOrdinataModel[i].getModifica() !="")
            conto_modifiche++
        }
    }


}
