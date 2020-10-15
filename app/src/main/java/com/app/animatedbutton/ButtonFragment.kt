package com.app.animatedbutton

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class ButtonFragment : Fragment() {
    // para el metodo getColor que requiere Api level: 23 (minimo actual de este proyecto es 16)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_button, container, false)

        val btn: CircularProgressButton = view.findViewById(R.id.btn_id)
        btn.setOnClickListener {
            CoroutineScope(Main).launch {
                // Muestra la animacion de loading
                btn.startAnimation()

                // Realiza una tarea asincrona
                fakeApiRequest()

                // Convierte la imagen del icono a bitmap porque el metodo doneLoadingAnimation recibe un bitmap
                // el cual mostrara cuando termine de cargar el proceso que se esta realizando.
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.done_ic)

                // Obtiene un color de la carpeta values el cual sirve para cambiar el color de fondo cuando se carga
                // la imagen de success
                val color = resources.getColor(R.color.fillColor, resources.newTheme())

                // Muestra el estado de success al finalizar el proceso
                btn.doneLoadingAnimation(color, bitmap)

                // Espera 2 segundos antes de volver al estado normal
                delay(2000)

                // Retorna el boton a su estado normal
                btn.revertAnimation()
            }
        }

        return view
    }


    // Simula una tarea asincrona
    private suspend fun fakeApiRequest(): String {
        delay(2000)

        return "response"
    }

}