package de.fivoroe.challenges.bowling.game.output;

import de.fivoroe.challenges.bowling.game.Player;

import java.util.List;

/**
 * Interface für die Entkopplung möglicher Ausgabe-Systeme von deren integration in die Programm-Logik.
 * Es werden einfach feste Handler-Methoden zur Implementierung vorgegeben, die entsprechende Spielevents behandeln.
 * Alle Methoden sind für den Spielablauf "optional" und somit als default deklariert mit leerem Body.
 */
public interface OutputSystem {
    /**
     * Wird gerufen wenn ein Spieler einen Strike geworfen hat
     * @param name der Name des Spielers
     */
    default void onPlayerStrike(String name){}

    /**
     * Wird gerufen wenn ein Spieler einen Spare geworfen hat
     * @param name der Name des Spielers
     */
    default void onPlayerSpare(String name){}

    /**
     * Wird zu Beginn eines neuen Frames im laufenden Spiel gerufen
     * @param frame die Nummer des Frames
     */
    default void onNewFrame(int frame){}

    /**
     * Wird unmittelbar nach einem Roll eines Spielers gerufen
     * @param name der Name des Spielers
     * @param roll das Ergebnis des Rolls
     */
    default void onPlayerRoll(String name, int roll){}

    /**
     * Wird zu Beginn eines neuen Spiels gerufen
     * @param players die Namen der Mitspieler
     */
    default void onGameStart(String...players){}

    /**
     * Wird am Ende eines Spiels gerufen. Über die Player-Instanzen der Mitspieler können deren Namen und Score
     * abgerufen werden.
     * @param players eine modifizierbare Liste der Player-Instanzen der Mitspieler
     */
    default void onGameEnd(List<Player> players) {}
}
