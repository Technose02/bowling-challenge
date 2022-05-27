package de.fivoroe.challenges.bowling.game.output;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StdOutputTest {

    @Test
    void joinMultipleNamesShouldBeJoinedAsExpected() {
        String joined = new StdOutput().joinWithFinalAnd(", ", " and ", "Peter", "Paul", "Mary");
        assertEquals("Peter, Paul and Mary", joined);
    }

    @Test
    void joinSingleNameShouldReturnSingleNameOnly() {
        String name = "Mr. Singleton";
        String joined = new StdOutput().joinWithFinalAnd("nothing really", "whatever", name);
        assertEquals(name, joined);
    }

}
