package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ufc.smd.esqueleto_placar.R
import ufc.smd.esqueleto_placar.data.game.events.Card
import ufc.smd.esqueleto_placar.data.game.events.CardColor
import ufc.smd.esqueleto_placar.data.game.events.EventType

class EventsAdapter(private val mList: List<EventType>) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {


    // Criação de Novos ViewHolders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // infla o card_previous_games
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_event, parent, false)

        return ViewHolder(view)
    }

    // Ligando o Recycler view a um View Holder
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvEventName: TextView = ItemView.findViewById(R.id.tvEventName)
        val tvTempoEvento: TextView = ItemView.findViewById(R.id.tvTempoEvento)
        val lnCell: LinearLayout = ItemView.findViewById(R.id.lnCell)
    }

    // faz o bind de uma ViewHolder a um Objeto da Lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val evento = mList[position]

        //alimentando os elementos a partir do objeto placar
        var adicional=""
        if (evento is Card){
            val card=evento as Card;
            adicional=" "+when(card.cardColor){
                CardColor.YELLOW ->"Amarelo"
                CardColor.RED -> "Vermelho"
                CardColor.BLUE -> "Azul"
            }
        }
        holder.tvEventName.text = evento.getName() + adicional + " Para a equipe " + evento.teamNumber
        holder.tvTempoEvento.text = "Tempo:${evento.half} Minuto:" + evento.second/60+ "m" + evento.second+"s";

        holder.lnCell.setOnClickListener{
            val duration= Snackbar.LENGTH_LONG
            val text= evento.getName()

            val snack= Snackbar.make(holder.lnCell,text,duration)
            snack.show()

        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


}
