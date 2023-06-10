package ufc.smd.esqueleto_placar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.core.content.getSystemService
import data.Placar
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets

class PlacarActivity : AppCompatActivity() {
    lateinit var placar:Placar
    lateinit var tvResultadoJogo: TextView
    lateinit var chronometer: Chronometer
    var game =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placar)
        placar= getIntent().getExtras()?.getSerializable("placar") as Placar
        tvResultadoJogo= findViewById(R.id.tvPlacar)
        chronometer = findViewById(R.id.c_meter)
        chronometer.setOnChronometerTickListener( {
            val (h, m, s) = getTime(chronometer.getContentDescription())
            /*
            * Racha -> 2 tempos de 15 minutos sem prorrogação e sem penaltis
            * Copa -> 2 tempos de 45 minutos, com prorrogação e/ou penalti
            * Custom -> 2 tempos de x minutos,sem/com prorrogação e sem/e/ou penaltis
            * */
            if (h == 0 && m == 1 && s == 15) {
                chronometer.stop()
            }
        })

        chronometer.start()
        //Mudar o nome da partida
        val tvNomePartida=findViewById(R.id.tvNomePartida2) as TextView
        tvNomePartida.text=placar.nome_partida
        ultimoJogos()
    }

    fun timeIsOver(h: Int, m: Int, s: Int): Boolean {
        return true
    }

    fun getTime(chronContent: CharSequence): Triple<Int, Int, Int> {
        val time: List<String> = chronContent.split(" ")
        var h: Int = 0
        var m: Int = 0

        if (time.contains("hour,") || time.contains("hours,")) {
            h = time[0].toInt()
        }
        if (time.contains("minute,") || time.contains("minutes,")) {
            m = time[time.size-4].toInt()
        }
        val s: Int = time[time.size-2].toInt()

        Log.v("SMD26", time.toString())
        Log.v("SMD26 h", h.toString())
        Log.v("SMD26 m", m.toString())
        Log.v("SMD26 s", s.toString())

        return Triple(h, m, s)
    }

    fun alteraPlacar (v:View){
//        game++
//        if ((game % 2) != 0) {
//            placar.resultado = ""+game+" vs "+ (game-1)
//        }else{
//            placar.resultado = ""+(game-1)+" vs "+ (game-1)
//            vibrar(v)
//        }
//        tvResultadoJogo.text=placar.resultado
    }



    fun saveGame(v: View) {

        val sharedFilename = "PreviousGames"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        var edShared = sp.edit()
        //Salvar o número de jogos já armazenados
        var numMatches= sp.getInt("numberMatch",0) + 1
        edShared.putInt("numberMatch", numMatches)

        //Escrita em Bytes de Um objeto Serializável
        var dt= ByteArrayOutputStream()
        var oos = ObjectOutputStream(dt);
        oos.writeObject(placar);

        //Salvar como "match"
        edShared.putString("match"+numMatches, dt.toString(StandardCharsets.ISO_8859_1.name()))
        edShared.commit() //Não esqueçam de comitar!!!

    }

    fun lerUltimosJogos(v: View){
        val sharedFilename = "PreviousGames"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)

        var meuObjString:String= sp.getString("match1","").toString()
        if (meuObjString.length >=1) {
            var dis = ByteArrayInputStream(meuObjString.toByteArray(Charsets.ISO_8859_1))
            var oos = ObjectInputStream(dis)
            var placarAntigo:Placar=oos.readObject() as Placar
            Log.v("SMD26",placar.resultado)
        }
    }




    fun ultimoJogos () {
        val sharedFilename = "PreviousGames"
        val sp:SharedPreferences = getSharedPreferences(sharedFilename,Context.MODE_PRIVATE)
        var matchStr:String=sp.getString("match1","").toString()
       // Log.v("PDM22", matchStr)
        if (matchStr.length >=1){
            var dis = ByteArrayInputStream(matchStr.toByteArray(Charsets.ISO_8859_1))
            var oos = ObjectInputStream(dis)
            var prevPlacar:Placar = oos.readObject() as Placar
            Log.v("PDM22", "Jogo Salvo:"+ prevPlacar.resultado)
        }

    }

//    fun iniciarPartida () {
//        var isWorking = false
//
//        override fun onClick(v: View) {
//            if (!isWorking) {
//                meter.start()
//                isWorking = true
//            } else {
//                meter.stop()
//                isWorking = false
//            }
//
//            btn.setText(if (isWorking) R.string.start else R.string.stop)
//
//            Toast.makeText(this@MainActivity, getString(
//                if (isWorking)
//                    R.string.working
//                else
//                    R.string.stopped),
//                Toast.LENGTH_SHORT).show()
//        }
//    }
}