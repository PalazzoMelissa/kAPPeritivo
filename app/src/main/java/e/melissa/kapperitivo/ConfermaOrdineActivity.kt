package e.melissa.kapperitivo

import android.support.v7.app.AppCompatActivity
import sql.DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import e.melissa.kapperitivo.R
import model.CustomPietanzaAdapter
import model.CustomPietanzaOrdinataAdapter
import model.EditPietanzaModel
import model.EditPietanzaOrdinataModel
import model.Ordine
import model.Pietanza

/**
 * Created by melissa on 01/01/19.
 */
class ConfermaOrdineActivity: AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null
    private var tavolo: Int= 0
    private var cameriere= ""
    private var invia_ordine: Button?= null

    private var customPietanzaOrdinataAdapter= CustomPietanzaOrdinataAdapter(this, pietanzaView)
    private var listaPietanzaOrdinate: ListView? = null
    private lateinit var pietanzaView: ArrayList<EditPietanzaOrdinataModel>
    private var ordine= Ordine()
    //ordine da creare

    override fun onCreate(savedInstanceState: Bundle)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_conferma_ordine)
        init__Views()
        init__Listeners()
        init__Objects()

        //creo il menù
        databaseHelper.createMenu()
        pietanzaView= getPietanzeOrdinate()
        listaPietanzaOrdinate.setAdapter(customPietanzaOrdinataAdapter)

    }


    private fun init__Views()
    {
        invia_ordine= findViewById(R.id.conferma) as Button
        listaPietanzaOrdinate= findViewById(R.id.ordine_completo) as ListView

        cameriere= getIntent().getStringExtra("cameriere").toString().trim()
        tavolo= getIntent().getIntExtra("tavolo", 0)
    }



    private fun init__Listeners()
    {
        invia_ordine.setOnClickListener(this@ConfermaOrdineActivity as View.OnClickListener)
    }



    private fun init__Objects()
    {
        databaseHelper= DatabaseHelper(applicationContext)
    }


    override fun onClick(view: View)
    {
        var conto_senza_modifiche= 0.0
        var conto_modifiche= 0.0

        for (i in 0..pietanzaView!!.size)
        {
            conto_senza_modifiche += pietanzaView[i].getCosto()* pietanzaView[i].getQuantita()
            if(pietanzaView[i].getModifica() != (""))
            {
                conto_modifiche++
            }
        }

        var intent= Intent(this, ContoActivity)

        //passo i due importi all'activity ContoActvity
        intent.putExtra("conto_modifiche", conto_modifiche)
        intent.putExtra("conto_senza-modifiche", conto_senza_modifiche)

        //passo ancge codice, cameriere e tavolo
        intent.putExtra("ordine", ordine.getCodice())
        intent.putExtra("Cameriere_usrnm", cameriere)
        intent.putExtra("tavolo", tavolo)
        startActivity(intent)

    }


    private fun getPietanzeOrdinate(): ArrayList<EditPietanzaOrdinataModel>
    {
        var editPietanzaOrdinataModelArrayList = ArrayList<EditPietanzaOrdinataModel> ()

        for (i in 0..CustomPietanzaAdapter.pietanze.size) {
            if (Integer.parseInt(CustomPietanzaAdapter.pietanze[i].getQuantita()) != 0) {
                val editPietanzaOrdinataModel = EditPietanzaOrdinataModel()
                editPietanzaOrdinataModel.setCosto(CustomPietanzaAdapter.pietanze.get(i).getPrezzo())
                editPietanzaOrdinataModel.setNomePietanza(CustomPietanzaAdapter.pietanze.get(i).getNomePietanza())
                editPietanzaOrdinataModel.setQuantita(Integer.parseInt(CustomPietanzaAdapter.pietanze.get(i).getQuantita()))
                editPietanzaOrdinataModel.setModifica("")
                editPietanzaOrdinataModelArrayList.add(editPietanzaOrdinataModel)
            }
        }

        return editPietanzaOrdinataModelArrayList
    }


}

