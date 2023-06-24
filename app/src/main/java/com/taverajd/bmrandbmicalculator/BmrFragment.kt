package com.taverajd.bmrandbmicalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class BmrFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_bmr, container, false)

        val sp_naf = view.findViewById<Spinner>(R.id.sp_naf)
        val options = arrayOf("Seleccione...", "Muy ligera", "Ligera", "Moderada", "Intensa", "Muy intensa")
        val adapter = ArrayAdapter(requireContext(), R.layout.layout_spinner, options)
        sp_naf.adapter = adapter

        val bt_calcular = view.findViewById<Button>(R.id.bt_calcular)

        bt_calcular.setOnClickListener(View.OnClickListener { validarCampos(view) })

        return view
    }

    // Función para comprobar la información
    private fun validarCampos(view : View) {
        val et_peso = view.findViewById<EditText>(R.id.et_peso)
        val et_altura = view.findViewById<EditText>(R.id.et_altura)
        val et_edad = view.findViewById<EditText>(R.id.et_edad)
        val sp_naf = view.findViewById<Spinner>(R.id.sp_naf)

        if (et_peso.text.isEmpty()) {
            et_peso.error = "El peso es necesario"
        } else if (et_altura.text.isEmpty()) {
            et_altura.error = "La altura es necesaria"
        } else if (et_edad.text.isEmpty()) {
            et_edad.error = "La edad es necesaria"
        } else if (sp_naf.selectedItem == "Seleccione...") {
            Toast.makeText(context, "El Nivel de Actividad Física es necesario", Toast.LENGTH_SHORT).show()
        } else {
            val peso = et_peso.text.toString().toDouble()
            val altura = et_altura.text.toString().toDouble()
            val edad = et_edad.text.toString().toInt()
            val naf = sp_naf.selectedItem.toString()
            calcularTMB(view, peso, altura, edad, naf)
        }
    }

    // Función para realizar el cálculo TMB
    private fun calcularTMB(view : View, peso: Double, altura: Double, edad: Int, naf: String) {
        val rb_sexoM = view.findViewById<RadioButton>(R.id.rb_sexoM)

        if (rb_sexoM.isChecked) {
            val res = (66.47 + (13.75 * peso) + (5 * altura) - (6.74 * edad))
            calcularGMT(view, res, naf)
        } else {
            val res = (655.1 + (9.56 * peso) + (1.85 * altura) - (4.68 * edad))
            calcularGMT(view, res, naf)
        }
    }

    // Función para realizar el cálculo GMT
    private fun calcularGMT(view : View, res: Double, naf: String) {
        val rb_sexoM = view.findViewById<RadioButton>(R.id.rb_sexoM)
        val tv_resultado = view.findViewById<TextView>(R.id.tv_resultado)

        var gmt = 0.0
        if (rb_sexoM.isChecked) {
            when (naf) {
                "Muy ligera" -> gmt = res * 1.3
                "Ligera" -> gmt = res * 1.6
                "Moderada" -> gmt = res * 1.7
                "Intensa" -> gmt = res * 2.1
                "Muy intensa" -> gmt = res * 2.4
            }
        } else {
            when (naf) {
                "Muy ligera" -> gmt = res * 1.3
                "Ligera" -> gmt = res * 1.5
                "Moderada" -> gmt = res * 1.6
                "Intensa" -> gmt = res * 1.9
                "Muy intensa" -> gmt = res * 2.2
            }
        }

        val i_gmt = gmt.toInt()
        tv_resultado.text = "Debe consumir $i_gmt KCals diarias"
    }
}