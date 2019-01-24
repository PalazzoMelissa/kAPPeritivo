package e.melissa.kapperitivo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import model.Cameriere
import sql.DatabaseHelper

class VediOrdiniActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var databaseHelper: DatabaseHelper
    //cameriere di cui visualizzerò l'ordine
    private lateinit var cameriere: Cameriere
    private lateinit var linearLayoutVediordini: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_vedi_ordini)

        InitObjects()
        visualizzaOrdini()

    }


    override fun onClick(v: View?) {

    }



    private fun InitObjects() {

        cameriere.setUsername(intent.getStringExtra("cameriere"))
    }


    private fun visualizzaOrdini() {
        //ottengo il cursore con tutte le tuple degli ordini
        val ordiniCameriere = databaseHelper.ordini_cameriere(cameriere)
        ordiniCameriere.moveToFirst()

        do {
            //creo layout relativo ad un'ordine
            val linearLayout = LinearLayout(this)
            linearLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.setPadding(18, 0, 18, 18)
            linearLayout.setBackgroundColor(Color.BLACK)

            //visualizzo info dell 'ordine in una textView
            val info_ordine = TextView(this)
            info_ordine.setTextColor(Color.WHITE)
            info_ordine.gravity = Gravity.CENTER
            info_ordine.textSize = 20f
            info_ordine.width = 300
            info_ordine.height = 100
            info_ordine.text = "Ordine " + ordiniCameriere.getInt(0) + " con conto €" + ordiniCameriere.getFloat(1)
            linearLayoutVediordini.addView(linearLayout)
            linearLayout.addView(info_ordine)

            //vedo tuple con codice dell'ordine
            val pietanza_ordine = databaseHelper.vedi_pietanze_ordine(ordiniCameriere.getInt(0))

            while (pietanza_ordine.moveToNext()) {
                //visualizzo pietanze realtive all'ordine atraverso un edit text
                val pietanzaordinataTextView = TextView(this)
                pietanzaordinataTextView.text = pietanza_ordine.getString(0) + " x " + pietanza_ordine.getInt(1) + "\n" + pietanza_ordine.getString(2)
                pietanzaordinataTextView.gravity = Gravity.CENTER
                pietanzaordinataTextView.textSize = 15f
                pietanzaordinataTextView.setTextColor(Color.WHITE)
                pietanzaordinataTextView.setPadding(0, 0, 0, 5)
                linearLayout.addView(pietanzaordinataTextView)

            }
            pietanza_ordine.close()
        }while (ordiniCameriere.moveToNext())
        ordiniCameriere.close()

    }

}
