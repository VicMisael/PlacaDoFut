package data.game

class Cup(nome_partida: String,nome_equipe1:String,
          nome_equipe2: String, has_timer: Boolean ):Game(nome_partida,nome_equipe1,nome_equipe2, has_timer,
    seconds=45*60, halfs=2, scoresTeamOne = 0, scoreTeamTwo = 0)  {
    override fun getNome():String {
        return "Cup"
    }
          }