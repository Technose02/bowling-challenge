package de.fivoroe.challenges.bowling.game;

import de.fivoroe.challenges.bowling.game.output.OutputSystem;
import lombok.Getter;

/**
 * Der Player modelliert einen Mitspieler im Bowling-Game. Er ist hier sehr einfach gehalten und erhält zur Identifikation
 * nur einen Spielernamen. Über die Composition mit einer ScoreCounter-Instanz wird die Verwaltung seines Scores realisiert
 * und über die Methode getScore() des Players die Auswertung von außen realisiert
 */
public class Player {
    @Getter
    private String name;
    private final ScoreCounter scoreCounter;
    private OutputSystem output;
    private RollEngine roller;

    /**
     * Builder für die Player-Klasse - bei der gegebenen Komplexität nicht notwendig, aber immer schöner
     * im Code und "good practice"
     */
    static class PlayerBuilder {
        private final Player product;
        public PlayerBuilder() {
            product = new Player();
        }
        public PlayerBuilder name(String name) {
            product.name = name;
            return this;
        }
        public PlayerBuilder output(OutputSystem output) {
            product.output = output;
            return this;
        }
        public PlayerBuilder roller(RollEngine roller) {
            product.roller = roller;
            return this;
        }
        Player build() {
            return product;
        }
    }

    private Player(){
        scoreCounter = new ScoreCounter();
    }

    /**
     * Builder für die "fluent"-Erzeugung eines Players
     * @return den PlayerBuilder, der den Player erzeugen wird
     */
    public static PlayerBuilder builder() {
        return new PlayerBuilder();
    }

    private int roll(int pinsLeft) {
        int roll = roller.roll(pinsLeft);
        scoreCounter.addRoll(roll);
        output.onPlayerRoll(name, roll);
        return roll;
    }

    private int playFirstRoll(boolean isFinalFrame) {
        int firstRoll = roll(Game.NUMBER_OF_PINS);

        if (firstRoll == Game.NUMBER_OF_PINS) {
            // Der Spieler hat einen Strike geworfen.
            output.onPlayerStrike(name);
            if (isFinalFrame) {
                // Im letzten Frame erhält der Spieler bei einem Strike zwei zusätzliche Rolls
                int pinsLeftInLastRoll = Game.NUMBER_OF_PINS - roll(Game.NUMBER_OF_PINS);
                if (pinsLeftInLastRoll == 0) {
                    // Wenn beim zweiten Roll im letzten Frame nach einem Strike wieder alle Pins
                    // abgeräumt wurden, so werden für den letzten Wurf wieder 10 Pins hingestellt
                    pinsLeftInLastRoll = Game.NUMBER_OF_PINS;
                }
                roll(pinsLeftInLastRoll);
            }
        }
        return firstRoll;
    }

    private void playSecondRollIfApplicable(int firstRoll, boolean isFinalFrame) {
        if (firstRoll < Game.NUMBER_OF_PINS) {
            // Nach dem ersten Wurf stehen noch Pins, es gibt einen zweiten Roll
            int roll2 = roll(Game.NUMBER_OF_PINS - firstRoll);
            if (firstRoll + roll2 == Game.NUMBER_OF_PINS) {
                // Der Spieler hat einen Spare erzielt
                output.onPlayerSpare(name);
                if (isFinalFrame) {
                    // Im letzten Frame erhält der Spieler bei einem Spare einen zusätzlichen Roll
                    roll(Game.NUMBER_OF_PINS);
                }
            }
        }
    }

    /**
     * Methode die zu rufen ist, wenn der Player in dem aktuellen Frame an der Reihe ist
     * @param frame die Nummer des aktuell zu spielenden Frames
     */
    public void playFrame(int frame) {
        scoreCounter.startFrame();
        boolean isFinalFrame = false;

        if (frame == Game.NUMBER_OF_FRAMES) {
            isFinalFrame = true;
        }
        int firstRoll = playFirstRoll(isFinalFrame);
        playSecondRollIfApplicable(firstRoll, isFinalFrame);
    }

    /**
     * Delegiert Berechnung des Player-Scores und gibt das Ergebnis zurück
     * @return der berechnete Score des Players
     */
    public int getScore() {
        return scoreCounter.getScore();
    }
}
