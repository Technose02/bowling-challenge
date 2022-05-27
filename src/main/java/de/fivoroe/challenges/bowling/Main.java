package de.fivoroe.challenges.bowling;

import de.fivoroe.challenges.bowling.game.RandomRoller;
import de.fivoroe.challenges.bowling.game.output.StdOutput;
import de.fivoroe.challenges.bowling.game.Game;
import de.fivoroe.challenges.bowling.game.InvalidGameConfigException;

public class Main {

    /**
     * Das Programm ist für ein Beispiel-Game mit den zwei Mitspielern "Niklas" und "Kathi" vorkonfiguriert, kann
     * aber sehr leicht intuitiv angepasst werden. Als Output-System kommt ein einfacher stdout-Printer zum Einsatz, der
     * entsprechende Nachrichten zu den Spielevents in den Standard-Out schreibt.
     * Die hier konfigurierte Roll-Engine basiert, wie gewünscht, auf einem Pseudo-Zufallzahlen-Generator.
     * (Anmerkung: Eine commandline-api habe ich mir und euch daher "erspart")
     * @param args werden nicht verwendet
     */
    public static void main (String[] args) {
        try {
            run();
        } catch (InvalidGameConfigException e) {
            System.err.println("Ungültige Game-Konfiguration");
            System.exit(1);
        }
    }

    private static void run() throws InvalidGameConfigException {
        new Game(new StdOutput(), new RandomRoller())
                .addPlayer("Niklas")
                .addPlayer("Kathi")
                .run();
    }
}
