package helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
/**
 * Scopi :
 * >Controllo dei dati inseriti campi per la registrazione
 *
 *@authors Gianluca Giacometti, Melissa Palazzo, Marco Bonavoglia
 *@version : 1.0
 *
 */
class InputValidation(cont: Context)
{
    private var context: Context = cont

    //controlla se i dati inseriti sono nulli
    fun isInputEditTextFilled(testo : EditText) : Boolean
    {
        var value = testo.text.toString()

        if(value.isEmpty())
        {
            hideKeyboardFrom(testo)
            return false
        }

        return true
    }


    //verifica l'esattezza del formato del numero telefonico
    fun isInputTextNumTelFilled(num_tel : EditText) : Boolean
    {
        var value = num_tel.text.toString()

        if(value.isEmpty() && value.length!=10)
        {
            hideKeyboardFrom(num_tel)
            return false
        }

        return true
    }

    //nasconde la tastiera da un EditText
    private fun hideKeyboardFrom(v : View)
    {
        var imm : InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromInputMethod(v.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

}