package com.example.gian2.apperitivogmm.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.*
import e.melissa.kapperitivo.ConfermaOrdineActivity

import e.melissa.kapperitivo.R
import model.EditPietanzaModel
import model.EditPietanzaOrdinataModel
import sql.DatabaseHelper
import kotlin.collections.ArrayList
import android.view.LayoutInflater
import android.widget.EditText
import android.text.TextWatcher


/**
 * Created by gian2 on 02/08/2018.
 */

class OrdinaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var databaseHelper: DatabaseHelper
    //tavolo scelto su cui fare l'ordine
    private var tavolo: Int = 0
    //cameriere che accede alla creazione dell'ordine
    private lateinit var utente: String

    //creo oggetto bottone per ordinare
    //private Button conferma;
    private lateinit var conferma: Button
    //creo arrayList pietanze
    private  lateinit var pietanze_menu:ArrayList<EditPietanzaModel>
    private lateinit var lvmenu:ListView



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_ordina)
        //inizializzo views
        initViews()
        //inizializzo Listeners
        initListeners()
        //inizializzo oggetti
        initObjects()

        //creo il menu
        databaseHelper.createMenu()
        pietanze_menu=inserisci_pietanze()
        var customPietanzaAdapter = CustomPietanzaAdapter(this,pietanze_menu)
        lvmenu.adapter = customPietanzaAdapter

    }


    //inizializzo viste
    private fun initViews() {
        lvmenu = findViewById(R.id.menu)
        conferma = findViewById(R.id.conferma)

    }

    //inizializo listeners
    private fun initListeners() {
        conferma.setOnClickListener(this)

    }

    //inizializzo oggetti
    private fun initObjects() {
        tavolo = intent.getIntExtra("Tavolo", 0)
        utente = intent.getStringExtra("Cameriere_usrnm").toString().trim { it <= ' ' }
        databaseHelper = DatabaseHelper(this)
    }

    override fun onClick(p0: View?) {
        var pietanze_scelte=ArrayList<EditPietanzaModel>()
        for(i in 0..pietanze_menu.size-1){

            if(pietanze_menu[i].getQuantita()!=""){
                pietanze_scelte.add(pietanze_menu[i])
            }
        }
        val intent = Intent(this@OrdinaActivity, ConfermaOrdineActivity::class.java)
        //passo username cameriere alla prossima activity
        intent.putExtra("cameriere", utente)
        intent.putExtra("pietanze_scelte",pietanze_scelte)
        //passo tavolo alla prossima activity
        intent.putExtra("tavolo", tavolo)
        startActivity(intent)
    }



     private    fun inserisci_pietanze(): ArrayList<EditPietanzaModel>{
            var categoria= arrayOf("bevanda", "antipasto", "primo", "secondo", "dolce")
            var lvmenu= ArrayList<EditPietanzaModel> ()

            for (i in categoria)
            {
                var cursor= databaseHelper.vediPietanze(i)

                if(cursor.count > 0)
                {
                    cursor.moveToFirst()

                    //grafica con le scritte
                    do
                    {

                        var editPietanzaModel= EditPietanzaModel()
                        editPietanzaModel.setPrezzo(cursor.getDouble(1)  )
                        editPietanzaModel.setDescrizione(cursor.getString(2))
                        editPietanzaModel.setNomePietanza(cursor.getString(0))
                        editPietanzaModel.setQuantita("")
                        lvmenu.add(editPietanzaModel)

                        //creo tale EditText solo come numerica
                    }while(cursor.moveToNext())

                }
                cursor.close()
            }
            return lvmenu
        }

    private class CustomPietanzaAdapter(cont: Context, pietanzeLista: ArrayList<EditPietanzaModel>) : BaseAdapter() {
        private var pietanze=pietanzeLista
        private var context=cont
        override fun getCount(): Int {return pietanze.size}

        override fun getItem(position: Int): Any {return pietanze[position]
        }

        override fun getItemId(position: Int): Long {return 0}

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?
        {

            var view:View?
            val VH: ViewHolder
            if(convertView == null)
            {
                var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view=inflater.inflate(R.layout.layout_pietanza,null,true)
                vh=ViewHolder()
                vh.editTextQuantita=view?.findViewById<EditText>(R.id.quantita) as EditText
                vh.textViewDescrizione= view.findViewById<TextView>(R.id.descrizione) as TextView
                vh.textViewPrezzo= view.findViewById<TextView>(R.id.prezzo) as TextView
                vh.textViewNome= view.findViewById<TextView>(R.id.nome) as TextView
                vh.editTextQuantita.id=(position)
                view.tag =vh

            }else{
                view=convertView
                //tag
                vh= view.tag as ViewHolder
            }


            var editPietanzaModel=pietanze[position]
            vh.editTextQuantita.setText(pietanze[position].getQuantita())
            vh.textViewPrezzo.text = "" + pietanze[position].getPrezzo()
            vh.textViewNome.text = "" + pietanze[position].getNomePietanza()
            vh.textViewDescrizione.text = "" + pietanze[position].getDescrizione()
            vh.editTextQuantita.id=(position)
            //vh.editTextQuantita.addTextChangedListener(GenericTextWatcher(vh.editTextQuantita))

            vh.editTextQuantita.onFocusChangeListener = View.OnFocusChangeListener{ v, b ->
                if(!b){
                    val position=v.id
                    val editText=v as EditText
                    pietanze.get(position).setQuantita(editText.text.toString())
                }
            }



            return view
        }

        companion object VH{
            var vh=ViewHolder()
        }
         class ViewHolder()
        {
            lateinit var editTextQuantita:EditText
            lateinit var textViewNome: TextView
            lateinit var textViewPrezzo: TextView
            lateinit var textViewDescrizione: TextView


        }

         inner class GenericTextWatcher  constructor( var view: View) : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val position = view.id
                val editText = view as EditText
                pietanze[position].setQuantita(editText.text.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                val position = view.id
                val editText = view as EditText
                pietanze[position].setQuantita(editText.text.toString())
            }
        }



    }






}
