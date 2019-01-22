package e.melissa.kapperitivo

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView

import e.melissa.kapperitivo.R
import model.CustomPietanzaOrdinataAdapter
import model.EditPietanzaOrdinataModel
import model.Ordine
import sql.DatabaseHelper


class ContoActivity: AppCompatActivity(), View.OnClickListener {


    //inizializzo tutti i componenti del Layout
    private var  conto_totaleTextView = findViewById<View>(R.id.conto_totale) as TextView
    private var conto_senza_modificheTextView = findViewById<View>(R.id.conto_senza_modifiche) as TextView
    private var conto_modificheTextView = findViewById<View>(R.id.conto_modifiche) as TextView
    private var info_ordine = findViewById<View>(R.id.info) as TextView
    private var torna_tavolo = findViewById<View>(R.id.torna) as Button

    private var databaseHelper = DatabaseHelper(applicationContext)

    //recupero i dati passati dall'altra activity
    private var conto_senza_modifiche=intent.getDoubleExtra("conto_senza_modifiche", 0.0)
    private var conto_modifiche = intent.getDoubleExtra("conto_modifiche", 0.0)
    private var conto_totale = conto_modifiche + conto_senza_modifiche

    private var tavolo = intent.getIntExtra("tavolo", 0)
    private var cameriere = intent.getStringExtra("Cameriere_usrnm")



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_conto)

        //inizializzo listeners
        initListeners()

        //inserisco stringa per info dell'ordine
        info_ordine.text = "Ordine  dal cameriere $cameriere \nal tavolo $tavolo"
        conto_senza_modificheTextView.text = "Importo parziale: € $conto_senza_modifiche"
        conto_modificheTextView.text= "Importo aggiuntivo: € $conto_modifiche"
        conto_totaleTextView.text = "Importo totale: € $conto_totale"

    }


    private fun initListeners() {
        //assoccio listener al Button
        torna_tavolo.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        //creo ordine e lo inserisco nel database
        val ordine = Ordine()
        ordine.setTavolo(tavolo)
        ordine.setCameriere(cameriere)
        ordine.setConto(conto_totale)

        /*
        **inserisco l'ordine nel database e tale operazione mi restituisce il codice dell'ordine
        **sfruttando il fatto che questo attributo dell'ordine è un auto_increment
        */
        ordine.setCodice(databaseHelper.addOrdine(ordine))
        //inserisco ogni pietanza ordinata nella tabella del database composto
        for (i in 0..CustomPietanzaOrdinataAdapter::pietanzeOrdinate as Int) {
            //ottengo i diversi attributi della pietanza ordinata di indice i
            val pietanza = CustomPietanzaOrdinataAdapter::pietanzeOrdinate.get(i).getNomePietanza()
            val quantita = CustomPietanzaOrdinataAdapter::pietanzeOrdinate.get(i).getQuantita()
            val modifica = CustomPietanzaOrdinataAdapter::pietanzeOrdinate.getter("modifica")//get(i).getModifica()
            //aggiungo tale pietanza ordinata, la quantita, e le possibili modifiche al database nella tabella composto
            databaseHelper.addComposto(ordine, pietanza, quantita, modifica)
        }
        val intent = Intent(this@ContoActivity, CameriereActivity::class.java)
        intent.putExtra("USERNAME", cameriere)
        startActivity(intent)
    }


}
















