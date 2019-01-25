package com.example.gian2.apperitivogmm.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import e.melissa.kapperitivo.ConfermaOrdineActivity
import e.melissa.kapperitivo.R
import model.EditPietanzaModel
import sql.DatabaseHelper


/**
 * Created by gian2 on 02/08/2018.
 */

class OrdinaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var databaseHelper: DatabaseHelper
    private var tavolo: Int = 0  //tavolo scelto su cui fare l'ordine
    private lateinit var utente: String  //cameriere che accede pe creare dell'ordine

    private  lateinit var pietanze_menu:ArrayList<EditPietanzaModel>  //arrayList contenente tutte le pietanze
    private lateinit var lvmenu:ListView  //mostra tutte le voci del menù
    private lateinit var conferma: Button  //oggetto bottone per ordinare



    override fun onCreate(savedInstanceState: Bundle?) {

        //avvia l'activity e imposta il layout corrispondente
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_ordina)

        initViews()
        initListeners()
        initObjects()

        supportActionBar?.hide()

        //creo il menu
        databaseHelper.createMenu()
        pietanze_menu=inserisci_pietanze()
        var customPietanzaAdapter = CustomPietanzaAdapter(this, pietanze_menu)
        lvmenu.adapter = customPietanzaAdapter

    }


    //inizializza la listView e il bottone
    private fun initViews() {
        lvmenu = findViewById(R.id.menu)
        conferma = findViewById(R.id.conferma)

    }

    //inizializza il  listener per la pressione del bottone
    private fun initListeners() {
        conferma.setOnClickListener(this)

    }

    //inizializza gli oggetti
    private fun initObjects() {
        //recupera il numero del tavolo e l'username del cameriere relativi all'ordine passati dall'activity precedente
        tavolo = intent.getIntExtra("Tavolo", 0)
        utente = intent.getStringExtra("Cameriere_usrnm").toString().trim { it <= ' ' } //GIAN, COSSA XEA STA ROBA? ste parentesi graffe
        databaseHelper = DatabaseHelper(this)
    }

    override fun onClick(v: View?) {

        //alla conferma dell'ordine le pietanze a cui è stata inserita la quantità vengono inserite in un array
        var pietanze_scelte= ArrayList<EditPietanzaModel>()
        for(i in 0..pietanze_menu.size-1){

            if(pietanze_menu[i].getQuantita()!=""){
                pietanze_scelte.add(pietanze_menu[i])
            }
        }

        val intent = Intent(this@OrdinaActivity, ConfermaOrdineActivity::class.java)
        //e poi passate all'activity successiva insieme all'username del cameriere e il numero del tavolo
        intent.putExtra("cameriere", utente)
        intent.putExtra("pietanze_scelte",pietanze_scelte)
        intent.putExtra("tavolo", tavolo)
        startActivity(intent)
    }



    //motra tutte le pietanze presenti nel menù  usando una ListView
    private fun inserisci_pietanze(): ArrayList<EditPietanzaModel>{
            var categoria= arrayOf("bevanda", "antipasto", "primo", "secondo", "dolce") //array con i nomi delle categorie
            var lvmenu= ArrayList<EditPietanzaModel> ()  //listView in cui verranno inserite le pietanze con i loro dati

        //raggruppa le pietanze in base alla categoria
        for (i in categoria)
            {
                //ottenuto un cursor con i dati delle pietanze per la categoria dell'iterazione corrispondente
                var cursor= databaseHelper.vediPietanze(i)

                //se ci sono voci per questa categoria, partendo dalla prima vengono mostrati a video tutte le "proprietà"
                if(cursor.count > 0)
                {
                    cursor.moveToFirst()

                    do
                    {
                        var editPietanzaModel= EditPietanzaModel()
                        editPietanzaModel.setPrezzo(cursor.getDouble(1)  )
                        editPietanzaModel.setDescrizione(cursor.getString(2))
                        editPietanzaModel.setNomePietanza(cursor.getString(0))
                        editPietanzaModel.setQuantita("") //lascio vuoto l'editText della quantià che deve essere riempito dall'utente
                        lvmenu.add(editPietanzaModel)

                    }while(cursor.moveToNext())

                }
                cursor.close()
            }
            return lvmenu
        }


    //classe per rappresentare la lista delle pietanze
    private class CustomPietanzaAdapter(cont: Context, pietanzeLista: ArrayList<EditPietanzaModel>) : BaseAdapter() {
        private var pietanze=pietanzeLista
        private var context=cont


        override fun getCount(): Int {return pietanze.size}  //numero totale di pietanze

        override fun getItem(position: Int): Any {return pietanze[position]}  //restituisce la pietanza all'indice desiderato

        override fun getItemId(position: Int): Long {return 0}


        //funzione per inserire la pietanza attraverso un componente grafico
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?
        {

            var view:View?
            var vh:ViewHolder
            if(convertView == null)
            {
                var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view=inflater.inflate(R.layout.layout_pietanza,null,true)
                vh=ViewHolder()
                vh.editTextQuantita=view?.findViewById<TextView>(R.id.quantita) as EditText
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


            //inserisce nei vari campi i valori associati alla pietanza considerata
            vh.editTextQuantita.setText(pietanze[position].getQuantita())
            vh.textViewPrezzo.text = "" + pietanze[position].getPrezzo()
            vh.textViewNome.text = "" + pietanze[position].getNomePietanza()
            vh.textViewDescrizione.text = "" + pietanze[position].getDescrizione()
            vh.editTextQuantita.id=position


            //in caso cambi editText , allora salvo il valore della editTextQuantita precedente
            /*
            * v=View relativa ad un ediTextModifica di una pietanza ordinata
            * b= boolean che controlla se cambio editText
             */
            vh.editTextQuantita.onFocusChangeListener = View.OnFocusChangeListener{ v, b ->
                if(!b){
                    val position=v.id
                    val editText=v as EditText
                    pietanze[position].setQuantita(editText.text.toString())
                }
            }


            return view
        }


        //classe per inserire le parti grafiche relative alla pietanza nella view
         private class ViewHolder {
            lateinit var editTextQuantita:EditText
            lateinit var textViewNome: TextView
            lateinit var textViewPrezzo: TextView
            lateinit var textViewDescrizione: TextView

        }


    }


}
