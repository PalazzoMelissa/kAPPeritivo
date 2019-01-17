package e.melissa.kapperitivo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ListView
import model.CustomPietanzaAdapter
import model.CustomPietanzaOrdinataAdapter
import model.EditPietanzaOrdinataModel
import model.Ordine
import sql.DatabaseHelper

/**
 * Created by melissa on 01/01/19.
 */
class ConfermaOrdineActivity: AppCompatActivity(), View.OnClickListener {
    private var databaseHelper= DatabaseHelper(applicationContext)
    private var tavolo= intent.getIntExtra("tavolo", 0)
    private var cameriere= intent.getStringExtra("cameriere").toString().trim()
    private var invia_ordine= findViewById<Button>(R.id.conferma)

    private var pietanzaView= getPietanzeOrdinate()
    private var customPietanzaOrdinataAdapter= CustomPietanzaOrdinataAdapter(this, pietanzaView)
    private var listaPietanzaOrdinate= findViewById<ListView>(R.id.ordine_completo)
    private var ordine= Ordine()


    override fun onCreate(savedInstanceState: Bundle)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_conferma_ordine)
        init__Listeners()

        //creo il menù
        databaseHelper.createMenu()
        listaPietanzaOrdinate.adapter = customPietanzaOrdinataAdapter

    }


    private fun init__Listeners()
    {
        invia_ordine.setOnClickListener(this@ConfermaOrdineActivity as View.OnClickListener)
    }



    override fun onClick(view: View)
    {
        var conto_senza_modifiche= 0.0
        var conto_modifiche= 0.0

        for (i in 0..pietanzaView.size)
        {
            conto_senza_modifiche += pietanzaView[i].getCosto()* pietanzaView[i].getQuantita()
            if(pietanzaView[i].getModifica() != (""))
            {
                conto_modifiche++
            }
        }

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


    private fun getPietanzeOrdinate(): ArrayList<EditPietanzaOrdinataModel>
    {
        var editPietanzaOrdinataModelArrayList = ArrayList<EditPietanzaOrdinataModel> ()

        for (i in 0..CustomPietanzaAdapter::pietanze.getCount) {
            if (Integer.parseInt(CustomPietanzaAdapter::pietanze[i].getQuantita()) != 0) {
                val editPietanzaOrdinataModel = EditPietanzaOrdinataModel()
                editPietanzaOrdinataModel.setCosto(CustomPietanzaAdapter::pietanze.getItem().getPrezzo())
                editPietanzaOrdinataModel.setNomePietanza(CustomPietanzaAdapter.pietanze.get(i).getNomePietanza())
                editPietanzaOrdinataModel.setQuantita(Integer.parseInt(CustomPietanzaAdapter.pietanze.get(i).getQuantita()))
                editPietanzaOrdinataModel.setModifica("")
                editPietanzaOrdinataModelArrayList.add(editPietanzaOrdinataModel)
            }
        }

        return editPietanzaOrdinataModelArrayList
    }


}

