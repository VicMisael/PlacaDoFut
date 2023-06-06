package data.game

import java.io.Serializable

data class Game(var nome_partida:String, var resultado:String, var resultadoLongo:String, var has_timer:Boolean):Serializable {
}