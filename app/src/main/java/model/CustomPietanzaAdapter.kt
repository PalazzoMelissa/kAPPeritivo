package model

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView

import e.melissa.kapperitivo.R
import e.melissa.kapperitivo.R.id.pietanze

//import java.util.ArrayList

class CustomPietanzaAdapter (context: Context, pietanze: ArrayList<EditPietanzaModel>): BaseAdapter() {

    private val  context: Context
    var pietanze: ArrayList<EditPietanzaModel>
    private lateinit var holder: ViewHolder

    //costruttore
    init {
        this.pietanze= pietanze
        this.context= context
    }


    override fun getViewTypeCount(): Int {return count}

    override fun getItemViewType(position: Int): Int {return position}

    override fun getCount(): Int {return pietanze.size}

    override fun getItem(position: Int): Any {return pietanze.get(position)}

    override fun getItemId(position: Int): Long {return 0}

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View
    {
        if(convertView == null)
        {
            var inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            convertView= inflater.inflate(R.layout.layout_pietanza, null, true)

            holder.editTextQuantita = convertView.findViewById(R.id.quantita) as EditText
            holder.textViewNome= convertView.findViewById(R.id.nome) as TextView
            holder.textViewPrezzo= convertView.findViewById(R.id.prezzo) as TextView
            holder.textViewDescrizione= convertView.findViewById(R.id.descrizione) as TextView

            convertView.setTag(holder)
        }else
        //getTag ritorna l'object set come un tag per la view
            holder= convertView.getTag() as ViewHolder

        holder.editTextQuantita.setText("" + pietanze[position].getQuantita())
        holder.textViewPrezzo.text = "" + pietanze[position].getPrezzo()
        holder.textViewNome.setText(pietanze[position].getNomePietanza())
        holder.textViewDescrizione.setText(pietanze[position].getDescrizione())

        holder.editTextQuantita.addTextChangedListener(watcher)

        return convertView
    }

}


private class ViewHolder
{
    lateinit var editTextQuantita: EditText
    lateinit var textViewNome: TextView
    lateinit var textViewPrezzo: TextView
    lateinit var textViewDescrizione: TextView

}


private lateinit var watcher: Watcher


class Watcher: TextWatcher {override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}


    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        pietanze.[position].setQuantita(holder.setTextQuantita.getText.toString())
    }

    override fun afterTextChanged(editable: Editable) {}
}