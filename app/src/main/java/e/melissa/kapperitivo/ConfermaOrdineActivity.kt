package e.melissa.kapperitivo


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.gian2.apperitivogmm.activities.ContoActivity
import model.EditPietanzaModel
import model.EditPietanzaOrdinataModel
import model.Ordine
import sql.DatabaseHelper
import android.text.Editable
import android.text.TextWatcher



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
    private lateinit var listaPietanzeScelte: ArrayList<EditPietanzaModel>
    private lateinit var pietanzaView: ArrayList<EditPietanzaOrdinataModel>
    private lateinit var ordine: Ordine



    override fun onCreate(savedInstanceState: Bundle?)
    {
        //avvia l'activity e imposta il layout corrispondente
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_conferma_ordine)

        //recupera username del cameriere e il numero del tavolo
        cameriere = intent.getStringExtra("cameriere").toString().trim()
        tavolo = intent.getIntExtra("tavolo", 0)

        init__Views()
        init__Listeners()
        init__Objects()

        supportActionBar?.hide()

        //ottiene la lista di pietanze ordinate
        pietanzaView = getPietanzeOrdinate()
        //inizializzo Adapter per inserire tutte le diverse pietanze ordinate nella ListView
        customPietanzaOrdinataAdapter = CustomPietanzaOrdinataAdapter(this, pietanzaView)

        //inserisco la lista pietanze, se è vuota impedisco di confermare l'ordine
        // (evito di far chiudere l'applicazione a causa dell'eventuale errore)
        try{
            listaPietanzaOrdinate.adapter = customPietanzaOrdinataAdapter
        }
        catch (e:Throwable){
            Toast.makeText(this,"Ordine vuoto!!!",Toast.LENGTH_LONG).show()
            finish()
        }


    }


    //inizializza le listView e il bottone
    private fun init__Views()
    {
        invia_ordine= findViewById(R.id.conferma)
        listaPietanzaOrdinate= findViewById(R.id.ordine_completo)

    }


    //inizializza listener del bottone
    private fun init__Listeners()
    {
        invia_ordine.setOnClickListener(this@ConfermaOrdineActivity as View.OnClickListener)
    }



    private fun init__Objects()
    {
        //recupera l'ordine con le pietanze scelte dall'altra activity
        databaseHelper= DatabaseHelper(this)
        listaPietanzeScelte= intent.extras.getSerializable("pietanze_scelte") as ArrayList<EditPietanzaModel>
        ordine=Ordine()
    }



    override fun onClick(view: View)
    {

        //comincia l'activity del Conto
        // vengono passati il codice dell'ordine, la lista delle pietanze, l'username del cameriere e il numero del tavolo
        var intent= Intent(this, ContoActivity::class.java)
        //passo i due importi all'activity ContoActvity
        intent.putExtra("pietanze",pietanzaView)
        //passo anche codice, cameriere e tavolo
        intent.putExtra("ordine", ordine.getCodice())
        intent.putExtra("Cameriere_usrnm", cameriere)
        intent.putExtra("tavolo", tavolo)
        startActivity(intent)

    }


    //resituisce un arraylist con le pietanze scelte
    private fun getPietanzeOrdinate(): ArrayList<EditPietanzaOrdinataModel>
    {
        var editPietanzaOrdinataModelArrayList = ArrayList<EditPietanzaOrdinataModel> ()

        for (i in 0..(listaPietanzeScelte.size-1)) {

            //per ogni elemento recupera nome della pietanza, costo, quantità desiderata e lascia vuoto il campo modifica (riempito dall'utente)
            var editPietanzaOrdinataModel = EditPietanzaOrdinataModel()
                editPietanzaOrdinataModel.setCosto(listaPietanzeScelte[i].getPrezzo())
                editPietanzaOrdinataModel.setNomePietanza(listaPietanzeScelte[i].getNomePietanza())
                editPietanzaOrdinataModel.setQuantita(Integer.parseInt(listaPietanzeScelte[i].getQuantita()))
                editPietanzaOrdinataModel.setModifica("")
                editPietanzaOrdinataModelArrayList.add(editPietanzaOrdinataModel)

        }

        return editPietanzaOrdinataModelArrayList
    }


    //classe per gestire la visione grafica delle pietanze ordinate
    //attraverso delle view che verranno inserite in una ListView
    private class CustomPietanzaOrdinataAdapter(cont: Context, pietOrd: ArrayList<EditPietanzaOrdinataModel>): BaseAdapter()
    {
        //array delle pietanze ordinate
        private var pietanzeOrdinate: ArrayList<EditPietanzaOrdinataModel> = pietOrd
        private var context=cont

        //override metodi baseadpater
        override fun getViewTypeCount(): Int {return count}

        override fun getItemViewType(position: Int): Int {return position}

        override fun getCount(): Int {return pietanzeOrdinate.size}

        override fun getItem(position: Int): Any {return pietanzeOrdinate[position]}  //elemento corrispondente all'indice

        override fun getItemId(position: Int): Long {return 0}

        //ritorno pietanza in forma grafica
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

            var vi:View?
            var holder:View_Holder
            //creo view dove visualizzare tutti i componenti della grafica della pietanza ordinata
            if(convertView == null)
            {
                var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                vi=inflater.inflate(R.layout.layout_pietanza_ordinata, null, true)
                holder= View_Holder()
                holder.editTextModifica = vi.findViewById(R.id.modifica)
                holder.textViewNome = vi.findViewById(R.id.nome)
                holder.textViewQuantita = vi.findViewById(R.id.quantita)
                holder.textViewPrezzo = vi.findViewById(R.id.prezzo)
                holder.editTextModifica.id=(position)
                vi.tag = holder
            }else{
                vi=convertView
                //getTag ritorna l'object set come un tag per la view
                holder= vi.tag as View_Holder
            }

            //acquisisce i vari dati del riepilogo dell'ordine
            holder.editTextModifica.setText("" + pietanzeOrdinate[position].getModifica())
            holder.textViewPrezzo.text = "" + pietanzeOrdinate[position].getCosto()
            holder.textViewNome.text = ""+pietanzeOrdinate[position].getNomePietanza()
            holder.textViewQuantita.text = "" + pietanzeOrdinate[position].getQuantita()
            holder.editTextModifica.id=position

            //salvo modifiche in caso ne vengano scritte su un EditText
            holder.editTextModifica.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    pietanzeOrdinate[position].setModifica(holder.editTextModifica.text.toString())

                }

                override fun afterTextChanged(editable: Editable) {

                }
            })


            return vi
        }


        //classe con componenti grafici interni alla view
        private class View_Holder {

            lateinit var editTextModifica: EditText
            lateinit var textViewNome: TextView
            lateinit var textViewPrezzo: TextView
            lateinit var textViewQuantita: TextView

        }
    }


}

