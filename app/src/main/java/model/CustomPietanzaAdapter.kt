package model

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import e.melissa.kapperitivo.R
import sql.DatabaseHelper
import java.util.*
import kotlin.collections.ArrayList


class CustomPietanzaAdapter (cont: Context): BaseAdapter() {

    companion object Pietanze{
        fun inserisci_pietanze(databaseHelper: DatabaseHelper): ArrayList<EditPietanzaModel>{
            var categoria= arrayOf("bevanda", "antipasto", "primo", "secondo", "dolce")
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

    val pietanze=CustomPietanzaAdapter.Pietanze

    private val  context= cont
    private val databaseHelper=DatabaseHelper(context)
    private var holder= ViewHolder()


    override fun getViewTypeCount(): Int {return count}

    override fun getItemViewType(position: Int): Int {return position}

    override fun getCount(): Int {return Pietanze.inserisci_pietanze(databaseHelper).size}

    override fun getItem(position: Int): Any {return Pietanze.inserisci_pietanze(databaseHelper)[position]
    }

    override fun getItemId(position: Int): Long {return 0}

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View
    {
        var vi= convertView

        if(vi == null)
        {
            var inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            vi= inflater.inflate(R.layout.layout_pietanza, null, true)

            holder.editTextQuantita = convertView.findViewById(R.id.quantita)
            holder.textViewNome= convertView.findViewById(R.id.nome)
            holder.textViewPrezzo= convertView.findViewById(R.id.prezzo)
            holder.textViewDescrizione= convertView.findViewById(R.id.descrizione)

            convertView.tag = holder
        }else
        //getTag ritorna l'object set come un tag per la view
            holder= vi.tag as ViewHolder

        holder.editTextQuantita.setText("" + Pietanze.inserisci_pietanze(databaseHelper)[position].getQuantita())
        holder.textViewPrezzo.text = "" + Pietanze.inserisci_pietanze(databaseHelper)[position].getPrezzo()
        holder.textViewNome.text = "" + Pietanze.inserisci_pietanze(databaseHelper)[position].getNomePietanza()
        holder.textViewDescrizione.text = "" + Pietanze.inserisci_pietanze(databaseHelper)[position].getDescrizione()

        holder.editTextQuantita.addTextChangedListener(object: android.text.TextWatcher{

                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int)
                {
                    Pietanze.inserisci_pietanze(databaseHelper)[position].setQuantita(holder.editTextQuantita.text.toString())
                }

                override fun afterTextChanged(editable: Editable) {}
                })

        return vi
    }


private class ViewHolder
{
    lateinit var editTextQuantita: EditText
    lateinit var textViewNome: TextView
    lateinit var textViewPrezzo: TextView
    lateinit var textViewDescrizione: TextView

}


}