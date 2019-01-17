package com.example.gian2.apperitivogmm.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import e.melissa.kapperitivo.R
import sql.DatabaseHelper

class VediOrdiniActivity : AppCompatActivity(), View.OnClickListener {

    private var databaseHelper= DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_vedi_ordini)
    }

    override fun onClick(view: View) {

    }


    private fun initObjects() {
        databaseHelper = DatabaseHelper(this)
    }

}
