package de.fivoroe.challenges.bowling.game;

import de.fivoroe.challenges.bowling.game.output.OutputSystem;

import java.util.*;

/**
 * Die Game-Klasse modelliert eine Bowling-Partie und verwaltet die Spieler, die "Roll-Engine" und das System zur
 * Ausgabe von Informationen.
 * Das Spiel wird hier gestartet dadurch einmal mit allen Spielern komplett simuliert. Die Ausgabe ermöglicht das
 * Senden von Spielinformationen an den Benutzer und zeigt entsprechend zum Spielende das Endergebnis.
 */
public class Game {

    public static final int NUMBER_OF_FRAMES = 10;
    public static final int NUMBER_OF_PINS = 10;

    private final List<Player> players = new ArrayList<>();
    private final OutputSystem output;
    private final RollEngine roller;

    /**
     * Initialisiert ein Game mit dem zu verwendenden Output-System und der zu verwendenden Roll-Engine
     * @param output das zu verwendende Output-System
     * @param roller die zu verwendende Roll-Engine
     */
    public Game(OutputSystem output, RollEngine roller) {
        this.output = output;
        this.roller = roller;
    }

    /**
     * Fügt dem Game einen Player hinzu. Die Reihenfolge der Aufrufe bestimmt gleichzeitig die Reihenfolge der
     * der Player im laufenden Game.
     * @param name der Name des hinzuzufügenden Players
     * @return die Game-Instanz, damit "fluent" Konfiguration des Games möglich wird
     */
    public Game addPlayer(String name) {
        players.add(
                Player.builder()
                        .name(name)
                        .output(this.output)
                        .roller(this.roller)
                        .build()
        );
        return this;
    }

    // Simuliert einen einzelnen Frame für alle Player
    private void playFrame(int i) {
        for ( Player player : players ) {
            player.playFrame(i);
        }
    }

    /**
     * Simuliert ein komplettes Game mit der zuvor eingegebenen Konfiguration aus Output-System, Roll-Engine und Playern
     */
    public void run() throws InvalidGameConfigException {
        if (players.isEmpty()) {
            throw new InvalidGameConfigException("Fehler: Es wurde kein Player dem Game hinzugefügt!");
        }

        output.onGameStart(players.stream().map(Player::getName).toArray(String[]::new));

        // sequentielle Simulation der einzelnen Game-Frames
        for (int i = 1; i <= Game.NUMBER_OF_FRAMES; i++) {
            output.onNewFrame(i);
            playFrame(i);
        }

        // da das Output-Interface eine modifizierbare Liste "verspricht" erzeugen wir eine Kopie und geben diese dann
        // heraus
        output.onGameEnd(new ArrayList<>(players));
    }
}
