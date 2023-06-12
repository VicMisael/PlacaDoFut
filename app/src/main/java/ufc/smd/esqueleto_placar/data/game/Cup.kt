package ufc.smd.esqueleto_placar.data.game

class Cup(nome_partida: String,nome_equipe1:String,
          nome_equipe2: String):
    Game(nome_partida,nome_equipe1,nome_equipe2,
    seconds=45*60, halfs=2)  {
    override fun getNome():String {
        return "Cup"
    }
          }