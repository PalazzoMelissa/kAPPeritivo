package com.example.gian2.apperitivogmm.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import e.melissa.kapperitivo.ConfermaOrdineActivity
import e.melissa.kapperitivo.R
import model.CustomPietanzaAdapter
import model.EditPietanzaModel
import sql.DatabaseHelper
import java.util.*

/**
 * Created by gian2 on 02/08/2018.
 */

class OrdinaActivity : AppCompatActivity(), View.OnClickListener {

    private val ordinaActivity: AppCompatActivity

    private lateinit var databaseHelper: DatabaseHelper
    //tavolo scelto su cui fare l'ordine
    private var tavolo: Int = 0
    //cameriere che accede alla creazione dell'ordine
    private lateinit var utente: String

    //creo oggetto bottone per ordinare
    //private Button conferma;
    private lateinit var conferma: Button
    //creo arrayList pietanze

    private lateinit var customPietanzaAdapter: CustomPietanzaAdapter
    private lateinit var lvmenu: ListView
    private lateinit var pietanzaView: ArrayList<EditPietanzaModel>

    private//oggetto per vedere tutte le pietanze dal database
    //grafica scritte
    //creo tale Edittext come solo edit text numerica
    val _all_dishes: ArrayList<EditPietanzaModel>
        get() {
            val categoria = arrayOf("bevanda", "antipasto", "primo", "secondo", "dolce")
            val lvmenu = ArrayList<EditPietanzaModel>()
            for (j in categoria.indices) {
                val cursor = databaseHelper!!.vediPietanze(categoria[j])
                if (cursor.count > 0) {
                    cursor.moveToFirst()

                    val categoria_attuale = ""
                    val categoria_vedi = arrayOfNulls<TextView>(6)
                    for (i in 0 until cursor.count) {
                        if (cursor.getString(3) != categoria_attuale) {

                        }
                        val editPietanzaModel = EditPietanzaModel()
                        editPietanzaModel.setPrezzo(cursor.getString(1) as Double)
                        editPietanzaModel.setDescrizione(cursor.getString(2))
                        editPietanzaModel.setNomePietanza(cursor.getString(0))
                        editPietanzaModel.setQuantita("")
                        lvmenu.add(editPietanzaModel)
                        cursor.moveToNext()
                    }
                }
            }
            return lvmenu
        }

    init {
        ordinaActivity = this@OrdinaActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_ordina)

        //inizializzo views
        initViews()
        //inizializzo listeners
        initListeners()
        //inizializzo oggetti
        initObjects()

        //creo il menu
        databaseHelper!!.createMenu()
        pietanzaView = _all_dishes
        customPietanzaAdapter = CustomPietanzaAdapter(this)
        lvmenu!!.adapter = customPietanzaAdapter


    }

    override fun onClick(view: View) {
        val intent = Intent(this@OrdinaActivity, ConfermaOrdineActivity::class.java)
        //passo username cameriere alla prossima activity
        intent.putExtra("cameriere", utente)
        //passo tavolo alla prossima activity
        intent.putExtra("tavolo", tavolo)
        startActivity(intent)

    }

    //inizializzo viste
    private fun initViews() {
        lvmenu = findViewById(R.id.menu)
        conferma = findViewById(R.id.conferma)

    }

    //inizializo listeners
    private fun initListeners() {
        conferma!!.setOnClickListener(this)
    }

    //inizializzo oggetti
    private fun initObjects() {
        tavolo = intent.getIntExtra("Tavolo", 0)
        utente = intent.getStringExtra("Cameriere_usrnm").toString().trim { it <= ' ' }
        databaseHelper = DatabaseHelper(ordinaActivity)
    }


}
