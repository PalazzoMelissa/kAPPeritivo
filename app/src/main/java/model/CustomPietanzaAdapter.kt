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

class CustomPietanzaAdapter (cont: Context, piet: ArrayList<EditPietanzaModel>): BaseAdapter() {

    var pietanze= piet
    private val  context= cont
    private var holder= ViewHolder()


    override fun getViewTypeCount(): Int {return count}

    override fun getItemViewType(position: Int): Int {return position}

    override fun getCount(): Int {return pietanze.size}

    override fun getItem(position: Int): Any {return pietanze[position]}

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

            convertView.tag = holder
        }else
        //getTag ritorna l'object set come un tag per la view
            holder= convertView.tag as ViewHolder

        holder.editTextQuantita.setText("" + pietanze[position].getQuantita())
        holder.textViewPrezzo.text = "" + pietanze[position].getPrezzo()
        holder.textViewNome.text = pietanze[position].getNomePietanza()
        holder.textViewDescrizione.text = pietanze[position].getDescrizione()

        holder.editTextQuantita.addTextChangedListener(watcher as TextWatcher)

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


class Watcher: TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int)
    {
        CustomPietanzaAdapter::pietanze.get(position).setQuantita(holder.setTextQuantita.getText.toString())
    }

    override fun afterTextChanged(editable: Editable) {}
}