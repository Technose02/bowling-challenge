package de.fivoroe.challenges.bowling.game;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreCounterTest {

    @Test
    void perfectGameShouldResultIn300() {
        ScoreCounter scoreCounter = new ScoreCounter();
        for (int i = 0; i < Game.NUMBER_OF_FRAMES + 2; i++) {
            scoreCounter.startFrame();
            scoreCounter.addRoll(Game.NUMBER_OF_PINS);
        }
        assertEquals(300, scoreCounter.getScore());
    }

    @Test
    void givenRollSequenceShouldResultIn26() {
        ScoreCounter scoreCounter = new ScoreCounter();
        scoreCounter.startFrame();
        scoreCounter.addRoll(4);
        scoreCounter.addRoll(Game.NUMBER_OF_FRAMES-4);
        scoreCounter.startFrame();
        scoreCounter.addRoll(5);
        scoreCounter.addRoll(3);
        scoreCounter.startFrame();
        scoreCounter.addRoll(2);
        scoreCounter.addRoll(1);
        for (int i = 3; i < Game.NUMBER_OF_FRAMES; i++) {
            scoreCounter.startFrame();
            scoreCounter.addRoll(0);
            scoreCounter.addRoll(0);
        }
        assertEquals(26, scoreCounter.getScore());
    }

    @Test
    void scoringNoStrikesOrSparesEqualsSumOverAllRolls() {
        ScoreCounter scoreCounter = new ScoreCounter();
        Random random = new Random(System.currentTimeMillis());
        int sumOfRolls = 0;
        for (int i = 0; i < Game.NUMBER_OF_FRAMES; i++) {
            int firstRoll = random.nextInt(Game.NUMBER_OF_PINS);
            int secondRoll = random.nextInt(Game.NUMBER_OF_PINS - firstRoll);
            scoreCounter.startFrame();
            scoreCounter.addRoll(firstRoll);
            scoreCounter.addRoll(secondRoll);
            sumOfRolls += firstRoll + secondRoll;
        }
        assertEquals(sumOfRolls, scoreCounter.getScore());
    }
}
