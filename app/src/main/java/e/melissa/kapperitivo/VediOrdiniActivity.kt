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
import android.content.Intent
import android.widget.Button


class VediOrdiniActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var databaseHelper: DatabaseHelper
    //cameriere di cui visualizzerò gli ordini
    private lateinit var cameriere: Cameriere
    private lateinit var linearLayoutVediordini: LinearLayout
    private lateinit var buttonTornaTavoli : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        //avvia l'activity e imposta il layout corrispondente
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_vedi_ordini)
        InitViews()
        InitListeners()
        InitObjects()
        visualizzaOrdini()

        supportActionBar?.hide()

    }




    //inizializza gli elementi della view
    private fun InitViews() {
        linearLayoutVediordini = findViewById<View>(R.id.vedi_ordini) as LinearLayout
        buttonTornaTavoli=findViewById<View>(R.id.torna_tavoli) as Button

    }

    private fun InitListeners() {
        buttonTornaTavoli.setOnClickListener(this)
    }



    //inizializza gli oggetti
    private fun InitObjects() {
        databaseHelper = DatabaseHelper(this)
        cameriere=Cameriere()
        cameriere.setUsername(intent.getStringExtra("cameriere"))
    }

    override fun onClick(view: View) {
        val intent = Intent(applicationContext, CameriereActivity::class.java)
        intent.putExtra("USERNAME", cameriere.getUsername())
        startActivity(intent)
    }


    private fun visualizzaOrdini() {
        //ottengo il cursor con tutte le tuple degli ordini
        val ordiniCameriere = databaseHelper.ordini_cameriere(cameriere)

        ordiniCameriere.moveToFirst()

        do {
            //crea un linear layout per ogni ordine fatto
            val linearLayout = LinearLayout(this)
            linearLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.setPadding(18, 0, 18, 18)
            linearLayout.setBackgroundColor(getColor(R.color.colorPrimary))

            //visualizza le  informazioni dell 'ordine in una textView
            val info_ordine = TextView(this)
            info_ordine.setTextColor(Color.WHITE)
            info_ordine.gravity = Gravity.CENTER
            info_ordine.setBackgroundColor(getColor(R.color.colorSecondary))
            info_ordine.textSize = 20f
            info_ordine.width = 300
            info_ordine.height = 100
            info_ordine.text = "Ordine " + ordiniCameriere.getInt(0) + " con conto €" + ordiniCameriere.getFloat(1)
            linearLayoutVediordini.addView(linearLayout)
            linearLayout.addView(info_ordine)

            //vedo tuple con codice dell'ordine
            val pietanza_ordine = databaseHelper.vedi_pietanze_ordine(ordiniCameriere.getInt(0))

            pietanza_ordine.moveToFirst()

            do {
                //visualizzo pietanze relative all'ordine attraverso un edit text
                //formato di visualizzazione: quantità x nomePietanza
                //                            eventuale modifica
                val pietanzaordinataTextView = TextView(this)
                pietanzaordinataTextView.gravity = Gravity.CENTER
                pietanzaordinataTextView.setBackgroundColor(getColor(R.color.colorAccent))
                pietanzaordinataTextView.text = ""+pietanza_ordine.getInt(1) + " x " + pietanza_ordine.getString(0)  + "\n" + pietanza_ordine.getString(2)
                pietanzaordinataTextView.gravity = Gravity.CENTER
                pietanzaordinataTextView.textSize = 15f
                pietanzaordinataTextView.setTextColor(Color.WHITE)
                pietanzaordinataTextView.setPadding(0, 0, 0, 1)
                linearLayout.addView(pietanzaordinataTextView)

            }while(pietanza_ordine.moveToNext())
            pietanza_ordine.close()

        }while (ordiniCameriere.moveToNext())
        ordiniCameriere.close()

    }

}
