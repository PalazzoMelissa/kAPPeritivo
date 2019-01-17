package model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import e.melissa.kapperitivo.R

class CustomPietanzaOrdinataAdapter(cont: Context, pietanzeOrdinate: ArrayList<EditPietanzaOrdinataModel>): BaseAdapter(){
    var pietanzeOrdinate: ArrayList<EditPietanzaOrdinataModel> = pietanzeOrdinate
    private var context= cont


    override fun getViewTypeCount(): Int {return count
    }


    override fun getItemViewType(position: Int): Int {return position}


    override fun getCount(): Int {return pietanzeOrdinate.size}


    override fun getItem(position: Int): Any {return pietanzeOrdinate[position]}


    override fun getItemId(position: Int): Long {return 0}


    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        //final CustomPietanzaOrdinataAdapter.ViewHolder holder;
        val holder: View_Holder

        if(convertView == null)
        {
            holder= View_Holder()
            val inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView= inflater.inflate(R.layout.layout_pietanza_ordinata, null, true)

            holder.editTextModifica = convertView.findViewById<View>(R.id.modifica) as EditText
            holder.textViewNome = convertView.findViewById<View>(R.id.nome) as TextView
            holder.textViewPrezzo = convertView.findViewById<View>(R.id.prezzo) as TextView
            holder.textViewQuantita = convertView.findViewById<View>(R.id.quantita) as TextView

            convertView.tag = holder
        }else
            //getTag ritorna l'object set come un tag per la view
            holder= convertView.tag as View_Holder

        holder.editTextModifica.setText("" + pietanzeOrdinate[position].getQuantita())
        holder.textViewPrezzo.text = "" + pietanzeOrdinate[position].getCosto()
        holder.textViewNome.text = ""+pietanzeOrdinate[position].getNomePietanza()
        holder.textViewQuantita.text = "" + pietanzeOrdinate[position].getQuantita()

        holder.editTextModifica.addTextChangedListener(watcher)

    }
}

private lateinit var watcher: Watcher

private class View_Holder {

    lateinit var editTextModifica: EditText
    lateinit var textViewNome: TextView
    lateinit var textViewPrezzo: TextView
    lateinit var textViewQuantita: TextView

}
