package de.fivoroe.challenges.bowling.game.output;

import de.fivoroe.challenges.bowling.game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StdOutput implements OutputSystem {

    @Override
    public void onPlayerStrike(String name) {
        System.out.printf("\t\t\t=> %s hat einen Strike geworfen!%n", name);
    }

    @Override
    public void onPlayerSpare(String name) {
        System.out.printf("\t\t\t=> %s hat einen Spare geworfen!%n", name);
    }

    @Override
    public void onNewFrame(int frame) {
        System.out.printf("%n\tEs beginnt Frame %d:%n", frame);
    }

    @Override
    public void onPlayerRoll(String name, int roll) {
        System.out.printf("\t\t%s hat %d Pins umgehauen%n", name, roll);
    }

    protected String joinWithFinalAnd(String sep, String lastSep, String... parts) {
        if (parts.length < 2) {
            return parts[0];
        }
        String joined = String.join(sep, parts);
        int lastSepAt = joined.lastIndexOf(sep);
        return joined.substring(0, lastSepAt) + lastSep + joined.substring(lastSepAt + sep.length());
    }

    @Override
    public void onGameStart(String... players) {
        System.out.printf("Ein neues Spiel beginnt.%n" +
                "Um den Sieg kämpfen heute %s.%n", joinWithFinalAnd(", ", " und ", players));
    }

    @Override
    public void onGameEnd(List<Player> players) {
        // Für die Ausgabe die Spieler nach erreichtem Score absteigend sortieren:
        players.sort((o1, o2) -> - Integer.compare(o1.getScore(), o2.getScore()));

        // Gibt es einen Sieger?
        if (players.size() == 1) {
            // Da kann man kaum von einem Sieger sprechen:
            System.out.printf("%n\tDas hat %s ganz anständig gemacht!%n", players.get(0).getName());
        } else {
            // Da getScore() kein "einfacher" Getter sondern eine Berechnung des Scores ist, wollen dir diese Methode
            // pro Player nur einmal rufen müssen und speichern die Scores in einer neuen Liste
            List<Integer> scores = players.stream().map(Player::getScore).collect(Collectors.toList());

            if ( scores.get(0).equals(scores.get(1)) ) {
                // Es gibt keinen Sieger, mindestens zwei Player teilen sich den ersten Platz
                List<String> winners = new ArrayList<>();
                for (int i = 0; i < players.size(); i++) {
                    winners.add(players.get(i).getName());
                    if ( ! scores.get(i).equals(scores.get(0)) ) {
                        break;
                    }
                }
                System.out.printf("%n\tDas war ein spannender Kampf und am Ende teilen sich %s mit %d Punkten den " +
                                "ersten Platz .%n",
                        joinWithFinalAnd(", ", " und ", winners.toArray(String[]::new)), scores.get(0));
            } else {
                System.out.printf("%n\tDas Spiel ist vorbei, der Sieger ist %s!%n", players.get(0).getName());
            }
        }

        System.out.println("\tDer Endstand lautet:");
        for (Player player : players) {
            System.out.printf("\t\t%s: %d Punkte%n", player.getName(), player.getScore());
        }
    }
}
