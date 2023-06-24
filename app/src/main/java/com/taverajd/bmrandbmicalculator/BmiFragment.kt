package com.taverajd.bmrandbmicalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class BmiFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bmi, container, false)

        val bt_calcular = view.findViewById<Button>(R.id.bt_calcular)

        bt_calcular.setOnClickListener(View.OnClickListener { validarCampos(view) })

        return view
    }

    // Función para comprobar la información
    private fun validarCampos(view : View) {
        val et_peso = view.findViewById<EditText>(R.id.et_peso)
        val et_altura = view.findViewById<EditText>(R.id.et_altura)

        if (et_peso.text.isEmpty()) {
            et_peso.error = "El peso es necesario"
        } else if (et_altura.text.isEmpty()) {
            et_altura.error = "La altura es necesaria"
        } else {
            val peso = et_peso.text.toString().toDouble()
            val altura = et_altura.text.toString().toDouble()
            calcularIMC(view, peso, altura)
        }
    }

    // Función para realizar el cálculo IMC
    private fun calcularIMC(view : View, peso: Double, altura: Double) {
        val res = peso / (altura * altura)
        compararIMC(view, res)
    }

    // Función para realizar la comparación
    private fun compararIMC(view : View, res: Double) {
        val tv_resultado = view.findViewById<TextView>(R.id.tv_resultado)

        var imc = ""
        when {
            res<18.5 -> imc = "Insuficiencia ponderal"
            res>=18.5 && res<25 -> imc = "Normal / Saludable"
            res>=25 && res<30 -> imc = "Sobrepeso / Preobesidad"
            res>=30 && res<35 -> imc = "Obesidad Tipo I"
            res>=35 && res<40 -> imc = "Obesidad Tipo II"
            res>=40 -> imc = "Obesidad Tipo III"
        }

        tv_resultado.text = "Actualmente se clasifica como: $imc"
    }
}