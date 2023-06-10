package ufc.smd.esqueleto_placar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import ufc.smd.esqueleto_placar.data.Placar
import ufc.smd.esqueleto_placar.data.game.Cup
import ufc.smd.esqueleto_placar.data.game.Game
import ufc.smd.esqueleto_placar.data.game.Normal
import ufc.smd.esqueleto_placar.data.game.Racha


class ConfigActivity : AppCompatActivity() {
    var placar: Placar = Placar("Jogo sem Config","0x0", "20/05/20 10h",false)
    lateinit var spinner: Spinner;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        spinner=findViewById(R.id.modos);
        val spinnerOptions=listOf("Normal","Racha","Cup","Custom Game");
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, spinnerOptions
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = adapter;

        openConfig()
        initInterface()

    }
    fun saveConfig(){
        val sharedFilename = "configPlacar"
        val sp:SharedPreferences = getSharedPreferences(sharedFilename,Context.MODE_PRIVATE)
        val edShared = sp.edit()


        edShared.putString("matchname",placar.nome_partida)
        edShared.putBoolean("has_timer",placar.has_timer)
        edShared.apply()
    }
    fun openConfig()
    {
        val sharedFilename = "configPlacar"
        val sp:SharedPreferences = getSharedPreferences(sharedFilename,Context.MODE_PRIVATE)
        placar.nome_partida=sp.getString("matchname","Jogo Padrão").toString()
        placar.has_timer=sp.getBoolean("has_timer",false)

    }
    fun initInterface(){
        val tv= findViewById<EditText>(R.id.editTextGameName)
        tv.setText(placar.nome_partida)
        val sw= findViewById<Switch>(R.id.swTimer)
        sw.isChecked=placar.has_timer
    }

    fun updatePlacarConfig(){
        val tv= findViewById<EditText>(R.id.editTextGameName)
        val sw= findViewById<Switch>(R.id.swTimer)
        placar.nome_partida= tv.text.toString()
        placar.has_timer=sw.isChecked
    }

    fun openPlacar(v: View){ //Executa ao click do Iniciar Jogo
        updatePlacarConfig() //Pega da Interface e joga no placar
        saveConfig() //Salva no Shared preferences
        val gamename=findViewById<EditText>(R.id.editTextGameName).text.toString();
        val eqp1=findViewById<EditText>(R.id.equipe1).text.toString();
        val eqp2=findViewById<EditText>(R.id.equipe2).text.toString()
        val sw= findViewById<Switch>(R.id.swTimer);
        val text = spinner.selectedItem.toString()

        val game: Game =when(text){
            "Normal"-> Normal(gamename,eqp1,eqp2,sw.isChecked)
            "Racha"-> Racha(gamename,eqp1,eqp2,sw.isChecked)
            "Cup" -> Cup(gamename,eqp1,eqp2,sw.isChecked)
            else -> {
                val intent = Intent(this, GameConfigActivity::class.java).apply{

                }
                startActivity(intent)
                return
            }
        }

        val intent = Intent(this, PlacarActivity::class.java).apply{
            putExtra("game",game)
        }
        startActivity(intent)
    }
}