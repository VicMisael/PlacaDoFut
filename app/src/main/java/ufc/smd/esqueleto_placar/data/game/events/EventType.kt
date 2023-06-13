package ufc.smd.esqueleto_placar.data.game.events

import java.io.Serializable

abstract class EventType(val teamNumber:Int, val half:Int, val second:Int): Serializable
{
    abstract fun getName():String;
}