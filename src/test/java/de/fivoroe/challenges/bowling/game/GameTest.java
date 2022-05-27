package de.fivoroe.challenges.bowling.game;

import de.fivoroe.challenges.bowling.game.output.OutputSystem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    private List<Player> endergebnis;
    OutputSystem evalOutputSystem = new OutputSystem() {
        @Override
        public void onGameEnd(List<Player> players) {
            endergebnis = players;
        }
    };

    @Test
    void perfectGameLeadsToScore300() throws InvalidGameConfigException {
        new Game(evalOutputSystem, pinsLeft -> Game.NUMBER_OF_PINS)
                .addPlayer("Striker")
                .run();

        assertEquals(300, endergebnis.get(0).getScore());
    }
}
