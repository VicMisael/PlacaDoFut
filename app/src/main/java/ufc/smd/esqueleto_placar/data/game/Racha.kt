package ufc.smd.esqueleto_placar.data.game

import ufc.smd.esqueleto_placar.data.game.events.EventType

class Racha(nome_partida: String,nome_equipe1:String,
            nome_equipe2: String, has_timer: Boolean ) : Game(nome_partida,nome_equipe1,nome_equipe2, has_timer,
    seconds=15*60, halfs=2, scoresTeamOne = 0, scoreTeamTwo = 0) {

    override fun getNome():String {
        return "Racha"
    }

}