package de.fivoroe.challenges.bowling.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomRollerTest {

    @Test
    // wir testen hier eigentlich nur für eine große Anzahl an Würfen, können damit
    // aber natürlich keine Garantie für alle möglichen Würfe geben.
    // Der Autor sieht die Wahrscheinlichkeit dafür, dass ein sich bei der Implementierung
    // des RandomRoller eingeschlichener Fehler bei dem jeweiligen Range hier erkannt würde
    // als hinreichend hoch an :-)
    void randomRollerProvidesOnlyValuesInRangeOf0ToConfiguredNumberOfPins() {
        RandomRoller randomRoller = new RandomRoller();
        boolean ok = true;
        for (int i = 0; i < 10000; i++) {
            int roll = randomRoller.roll(Game.NUMBER_OF_PINS);
            if (roll < 0 || roll > Game.NUMBER_OF_PINS) {
                ok = false;
                break;
            }
        }
        assertTrue(ok);
    }

    @Test
    // In ähnlicher Weise zu obigem Test prüfen wir hier, ob die jeweils übergebene Anzahl
    // der vor dem Roll noch stehenden Pins korrekt berücksichtigt wird (so hierfür ein korrekter
    // Wert übergeben wird!)
    void rollPlusPinsLeftNeverExceedsConfiguredNumberOfPins() {
        RandomRoller randomRoller = new RandomRoller();
        boolean ok = true;
        for (int i = 0; i < 10000; i++) {
            int firstRoll = randomRoller.roll(Game.NUMBER_OF_PINS);
            int secondRoll = randomRoller.roll(Game.NUMBER_OF_PINS - firstRoll);
            int sum = firstRoll + secondRoll;
            if (sum > Game.NUMBER_OF_PINS) {
                ok = false;
                System.err.printf("Error: sum is %d", sum);
                break;
            }
        }
        assertTrue(ok);
    }
}
