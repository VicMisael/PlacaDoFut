package ufc.smd.esqueleto_placar.data.game

import java.io.Serializable

data class Team(val nomeEquipe:String,var gols:Int=0,var yellowCards:Int=0,var redCards:Int=0,var blueCards:Int=0):Serializable{
}