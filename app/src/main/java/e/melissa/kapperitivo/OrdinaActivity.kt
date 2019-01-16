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
class OrdinaActivity: AppCompatActivity() {
    private val ordinaActivity= this@OrdinaActivity

    private var databaseHelper= DatabaseHelper(ordinaActivity)
    private var inputValidation= InputValidation(ordinaActivity)
    private var tavolo= intent.getIntExtra("Tavolo", 0)
    private var cameriere= ""
    private var utente= intent.getStringExtra("Cameriere_usrnm").trim()

    private var conferma= findViewById<Button>(R.id.conferma)
    private var customPietanzaAdapter= CustomPietanzaAdapter(this, pietanzaView)
    private var lvmenu= findViewById<ListView>(R.id.menu)
    private var pietanzaView=get_all_dishes() as ArrayList<EditPietanzaModel>


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
        var intent: Intent= Intent(this@OrdinaActivity, ConfermaOrdineActivity)
        intent.putExtra("tavolo", tavolo)
        startActivity(intent)
    }


    private fun initListeners()
    {
        conferma.setOnClickListener(this@OrdinaActivity as View.OnClickListener)
    }


    private fun get_all_dishes()
    {
        //mostra tutte le pietanze del database
        var categoria= arrayOf("bevanda", "antopasto", "primo", "secondo", "dolce")
        var lvmenu= ArrayList<EditPietanzaModel> ()
    }






}














