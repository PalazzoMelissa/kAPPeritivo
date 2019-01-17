package e.melissa.kapperitivo

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

import e.melissa.kapperitivo.R
import helper.InputValidation
import model.CustomPietanzaAdapter
import model.EditPietanzaModel
import sql.DatabaseHelper
/**
 * Created by melissa on 01/01/19.
 */
class OrdinaActivity: AppCompatActivity(), View.OnClickListener {
    private val ordinaActivity= this

    private var databaseHelper= DatabaseHelper(ordinaActivity)
    private var inputValidation= InputValidation(ordinaActivity)
    private var tavolo= intent.getIntExtra("Tavolo", 0)
    private var cameriere= ""
    private var utente= intent.getStringExtra("Cameriere_usrnm").trim()

    private var conferma= findViewById<Button>(R.id.conferma)
    private var pietanzaView= get_all_dishes()
    private var customPietanzaAdapter= CustomPietanzaAdapter(this, pietanzaView)
    private var lvmenu= findViewById<ListView>(R.id.menu)


    override fun onCreate(savedInstanceState: Bundle)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_ordina)

        initListeners()

        databaseHelper.createMenu()
        lvmenu.adapter = customPietanzaAdapter

    }


    override fun onClick(view: View)
    {
        var intent= Intent(this@OrdinaActivity, ConfermaOrdineActivity::class.java)
        intent.putExtra("tavolo", tavolo)
        startActivity(intent)
    }


    private fun initListeners()
    {
        conferma.setOnClickListener(this@OrdinaActivity as View.OnClickListener)
    }


    private fun get_all_dishes(): ArrayList<EditPietanzaModel>
    {
        //mostra tutte le pietanze del database
        var categoria= arrayOf("bevanda", "antopasto", "primo", "secondo", "dolce")
        var lvmenu= ArrayList<EditPietanzaModel> ()

        for (i in 0..categoria.size)
        {
            var cursor= databaseHelper.vediPietanze(categoria[i])

            if(cursor.count > 0)
            {
                cursor.moveToFirst()

                //grafica con le scritte
                for(i in 0..cursor.count)
                {

                    var editPietanzaModel= EditPietanzaModel()

                    editPietanzaModel.setNomePietanza(cursor.getString(0))
                    editPietanzaModel.setPrezzo(cursor.getString(1) as Double)
                    editPietanzaModel.setDescrizione(cursor.getString(2))
                    editPietanzaModel.setQuantita("0")

                    lvmenu.add(editPietanzaModel)

                    //creo tale EditText solo come numerica
                    cursor.moveToNext()

                }

            }
        }

        return lvmenu
    }






}














