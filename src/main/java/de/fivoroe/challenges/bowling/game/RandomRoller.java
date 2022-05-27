package de.fivoroe.challenges.bowling.game;

import java.util.Random;

public class RandomRoller implements RollEngine {
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public int roll(int pinsLeft) {
        return random.nextInt(pinsLeft + 1);
    }
}
