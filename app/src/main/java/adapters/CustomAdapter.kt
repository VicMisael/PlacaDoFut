package adapters

import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ufc.smd.esqueleto_placar.Detalhes
import ufc.smd.esqueleto_placar.PlacarActivity
import ufc.smd.esqueleto_placar.data.Placar
import ufc.smd.esqueleto_placar.R
import ufc.smd.esqueleto_placar.data.game.Game

class CustomAdapter(private val mList: List<Game>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    // Criação de Novos ViewHolders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // infla o card_previous_games
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_previous_game, parent, false)

        return ViewHolder(view)
    }

    // Ligando o Recycler view a um View Holder
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = ItemView.findViewById(R.id.imageview)
        val tvNomePartida: TextView = ItemView.findViewById(R.id.tvNomePartida)
        val tvResultadoJogo: TextView = ItemView.findViewById(R.id.tvResultadoJogo)
        val lnCell: LinearLayout = ItemView.findViewById(R.id.lnCell)
    }

    // faz o bind de uma ViewHolder a um Objeto da Lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val placarAnterior = mList[position]

        //alimentando os elementos a partir do objeto placar
        holder.tvNomePartida.text = placarAnterior.nome_partida
        holder.tvResultadoJogo.text = placarAnterior.description

        holder.lnCell.setOnClickListener {
            val duration = Snackbar.LENGTH_LONG
            val text = placarAnterior.description

            val snack = Snackbar.make(holder.lnCell, text, duration)
            snack.show()
            val intent = Intent(it.context, Detalhes::class.java).apply {
                putExtra("game", placarAnterior)
            }
            it.context.startActivity(intent);
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


}
