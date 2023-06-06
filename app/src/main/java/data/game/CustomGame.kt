package data.game

class CustomGame(
    nome_partida: String,
    nome_equipe1: String,
    nome_equipe2: String,
    has_timer: Boolean,
    seconds: Int,
    halfs: Int
) : Game(
    nome_partida,
    nome_equipe1,
    nome_equipe2,
    has_timer,
    seconds,
    halfs,
    scoresTeamOne = 0,
    scoreTeamTwo = 0
) {}