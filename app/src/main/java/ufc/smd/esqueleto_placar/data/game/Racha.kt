package ufc.smd.esqueleto_placar.data.game

import ufc.smd.esqueleto_placar.data.game.events.EventType

class Racha(nome_partida: String,nome_equipe1:String,
            nome_equipe2: String ) : Game(nome_partida,nome_equipe1,nome_equipe2,
    seconds=15*60, halfs=2) {
    companion object {
        @JvmStatic private val serialVersionUID: Long = 6529685098267757690L
    }

    override fun getNome():String {
        return "Racha"
    }

}