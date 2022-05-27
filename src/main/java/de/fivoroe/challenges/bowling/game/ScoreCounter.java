package de.fivoroe.challenges.bowling.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScoreCounter kapselt die Berechnungslogik zur Implementierung des beschriebenen Punktesystem für einen Player
 * Es werden alle Rolls des Players in nachvollziehbarer Reihenfolge mitgeschrieben und die Frame-Zugehörigkeit der
 * einzelnen Rolls in einer HashMap festgehalten.
 */
public class ScoreCounter {
    // HashMap der Zugehörigkeiten der Rolls zu dem jeweiligen Frame
    private final Map<Integer, Integer> framesOfRollsMap = new HashMap<>();

    // Die Sequenz der Rolls des Players, dessen Score zu berechnen ist
    private final List<Integer> rolls = new ArrayList<>();

    private int currentFrame = -1;

    /**
     * Muss von außen beim Start eines neuen Frames gerufen werden, damit intern die Framezugehörigkeit
     * korrekt abgebildet werden kann (es ginge auch alleine aus den Rolls, aber das würde hier sehr unübersichtlich
     * werden)
     */
    public void startFrame() {
        currentFrame++;
    }

    /**
     * Muss von außen bei einem Roll gerufen werden, damit dieser in die Berechnung eingehen kann
     * @param roll die Anzahl der bei dem Roll umgeworfenen Pins
     */
    public void addRoll(int roll) {
        rolls.add(roll);
        framesOfRollsMap.put(rolls.size()-1, currentFrame);
    }

    /**
     * Berechnet den Score des Spielers. Die Basis bildet die Summe aller rolls, dazu kommen dann "Extrapunkte" gemäß
     * der Vorgabe (siehe auch erläuternde Kommentare im Body)
     * @return der berechnete Score
     */
    public int getScore() {
        // Zunächst die Summer aller Rolls als Basis bestimmen
        int score = rolls.stream().reduce(0, Integer::sum);

        // Bei den folgenden Berechnungen benötigen wir immer Informationen zu dem jeweils vorherigen Roll, daher müssen
        // wir dort beim zweiten Roll beginnen.
        // Entsprechend behandeln wir den ersten Roll hier "isoliert":

        // Der einzige Sonderfall, der bei alleiniger Betrachtung des ersten Wurfs auftreten kann, ist ein Strike:
        if (rolls.get(0) == Game.NUMBER_OF_PINS) {
            // Strike -> verbuche zusätzlich die Punkte der beiden folgenden Rolls
            score += rolls.get(1) + rolls.get(2);
        }

        // Nun betrachten wir, beginnend beim zweiten Roll, die möglichen Sonderfälle:
        for (int i = 1; i < rolls.size(); i++) {
            // Im letzten Frame kommen keine Sonderfälle mehr zum Einsatz
            if (framesOfRollsMap.get(i) == Game.NUMBER_OF_FRAMES - 1) {
                break;
            }

            // Bestimme den aktuellen roll und dessen frame, sowie den vorherigen roll und dessen frame
            int frame = framesOfRollsMap.get(i);
            int roll = rolls.get(i);
            int framePreviousRoll = framesOfRollsMap.get(i-1);
            int previousRoll = rolls.get(i-1);

            // Liegt ein Spare oder Strike vor?
            if (roll == Game.NUMBER_OF_PINS && framePreviousRoll != frame) {
                // Strike -> verbuche zusätzlich die Punkte der beiden folgenden Rolls
                score += rolls.get(i+1) + rolls.get(i+2);
            } else if (roll + previousRoll == Game.NUMBER_OF_PINS && framePreviousRoll == frame) {
                // Spare -> verbuche zusätzliche die Punkte des folgenden Rolls
                score += rolls.get(i+1);
            }
        }
        return score;
    }
}
