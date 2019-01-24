package model
/*
import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import e.melissa.kapperitivo.R
import kotlin.collections.ArrayList

class CustomPietanzaAdapter (cont: Context,editPietanzeModel: ArrayList<EditPietanzaModel>): BaseAdapter() {
    private val context: Context
    private val
    init {
        context=cont
    }
    private var holder=ViewHolder()

    init {
        pietanze=editPietanzeModel
    }




    override fun getCount(): Int {return pietanze.size}

    override fun getItem(position: Int): Any {return pietanze[position]
    }

    override fun getItemId(position: Int): Long {return 0}

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?
    {

        var vi:View?
        vi=convertView
        if(convertView == null)
        {
            var inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            vi= inflater.inflate(R.layout.layout_pietanza, null, true)

            holder.editTextQuantita = vi.findViewById<EditText>(R.id.quantita)
            holder.textViewNome= vi.findViewById(R.id.nome)
            holder.textViewPrezzo= vi.findViewById(R.id.prezzo)
            holder.textViewDescrizione= vi.findViewById(R.id.descrizione)

            vi.tag = holder
        }else
        //getTag ritorna l'object set come un tag per la view
            holder= vi?.tag as ViewHolder

        holder.editTextQuantita.setText(pietanze[position].quantita)
        holder.textViewPrezzo.text = "" + pietanze[position].prezzo
        holder.textViewNome.text = "" + pietanze[position].nomePietanza
        holder.textViewDescrizione.text = "" + pietanze[position].descrizione

        holder.editTextQuantita.addTextChangedListener(object: android.text.TextWatcher{

                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int)
                {
                    pietanze[position].quantita=(holder.editTextQuantita.text.toString())
                }

                override fun afterTextChanged(editable: Editable) {}
                })

        return vi
    }


private class ViewHolder()
{
    lateinit var editTextQuantita: EditText
    lateinit var textViewNome: TextView
    lateinit var textViewPrezzo: TextView
    lateinit var textViewDescrizione: TextView


}


    fun getPietanza( i: Int): EditPietanzaModel{
        return pietanze[i]
    }


}*/