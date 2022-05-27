package de.fivoroe.challenges.bowling.game;

public interface RollEngine {

    /**
     * Methode zur simulation eines rolls während des laufenden Spiels
     * @param pinsLeft anzahl der Pins, die in diesem roll umgeworfen werden können
     * @return die Anzahl der bei dem simulierten roll umgeworfenen Pins
     */
    int roll(int pinsLeft);
}
