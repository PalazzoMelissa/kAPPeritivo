package e.melissa.kapperitivo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import model.CustomPietanzaAdapter
import model.CustomPietanzaOrdinataAdapter
import model.EditPietanzaOrdinataModel
import model.Ordine
import sql.DatabaseHelper

/**
 * Created by melissa on 01/01/19.
 */
class ConfermaOrdineActivity: AppCompatActivity(), View.OnClickListener {

    private var tavolo: Int=0
    private lateinit var cameriere: String
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var invia_ordine: Button

    private lateinit var customPietanzaOrdinataAdapter: CustomPietanzaOrdinataAdapter
    private lateinit var listaPietanzaOrdinate: ListView
    private lateinit var pietanzaView: ArrayList<EditPietanzaOrdinataModel>
    private lateinit var ordine: Ordine
    //ordine da creare



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_conferma_ordine)
        cameriere = intent.getStringExtra("cameriere").toString().trim { it <= ' ' }
        tavolo = intent.getIntExtra("tavolo", 0)
        init__Views()
        init__Listeners()
        init__Objects()

        //ottengo lista componenti grafici relativi a pietanze ordinate
        pietanzaView = getPietanzeOrdinate()
        //inizializzo Adapter per inserire tutte le diverse pietanze ordinate nella ListView
        customPietanzaOrdinataAdapter = CustomPietanzaOrdinataAdapter(this, pietanzaView)
        //inserisco la lista di pietanze

        //inserisco lista pietanze
        try {
          //  listaPietanzaOrdinate.adapter = customPietanzaOrdinataAdapter
        }
        catch (e: Exception)
        {
            Toast.makeText(applicationContext, "Ordine vuoto!", Toast.LENGTH_SHORT).show()
            finish()
        }

    }


    private fun init__Views()
    {
        invia_ordine= findViewById(R.id.conferma)
        listaPietanzaOrdinate= findViewById(R.id.ordine_completo)

    }



    private fun init__Listeners()
    {
        invia_ordine.setOnClickListener(this@ConfermaOrdineActivity as View.OnClickListener)
    }



    private fun init__Objects()
    {

    }


    //
    override fun onClick(view: View)
    {
        var conto_senza_modifiche= 0.0
        var conto_modifiche= 0.0

       /* for (i in 0..pietanzaView.size)
        {
            conto_senza_modifiche += pietanzaView[i].getCosto()* pietanzaView[i].getQuantita()
            if(pietanzaView[i].getModifica() != (""))
                conto_modifiche++
        }*/

        var intent= Intent(this, ContoActivity::class.java)

        //passo i due importi all'activity ContoActvity
        intent.putExtra("conto_modifiche", conto_modifiche)
        intent.putExtra("conto_senza-modifiche", conto_senza_modifiche)

        //passo ancge codice, cameriere e tavolo
        intent.putExtra("ordine", ordine.getCodice())
        intent.putExtra("Cameriere_usrnm", cameriere)
        intent.putExtra("tavolo", tavolo)
        startActivity(intent)

    }


    /*private fun getPietanzeOrdinate(): ArrayList<EditPietanzaOrdinataModel>
    {
        var editPietanzaOrdinataModelArrayList = ArrayList<EditPietanzaOrdinataModel> ()

        for (i in 0..CustomPietanzaAdapter::getCount()) {

            if (CustomPietanzaAdapter::pietanze:: != 0) {
                val editPietanzaOrdinataModel = EditPietanzaOrdinataModel()
                editPietanzaOrdinataModel.setCosto(CustomPietanzaAdapter::pietanze.get(i))
                editPietanzaOrdinataModel.setNomePietanza(CustomPietanzaAdapter::pietanze.get(i).getNomePietanza())
                editPietanzaOrdinataModel.setQuantita(Integer.parseInt(CustomPietanzaAdapter::pietanze.get(i).getQuantita()))
                editPietanzaOrdinataModel.setModifica("")
                editPietanzaOrdinataModelArrayList.add(editPietanzaOrdinataModel)
            }
        }

        return editPietanzaOrdinataModelArrayList
    }*/


}

