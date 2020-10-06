
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GameTest {
    Game game;
    int limit;
    int rows;
    int columns;
    int numbersPerRow;
    int players;
    @Before
    public void setup() {
        game = new Game();
        limit = 90;
        rows = 3;
        columns = 10;
        numbersPerRow = 3;
        players = 3;
    }

    @Test
    public void validateParameters_with_negativeLimit() {
        limit = -1;
        try {
            game.validateParameters(limit, rows, columns, numbersPerRow);
        } catch(Exception e) {
            assertEquals("Range is not valid", e.getMessage());
        }

    }

    @Test
    public void validateParameters_with_Limit_greaterThan_totalEntries() {
        limit = 3;
        try {
            game.validateParameters(limit, rows, columns, numbersPerRow);
        } catch(Exception e) {
            assertEquals("Range cannot be less than total number of entries in the ticket", e.getMessage());
        }

    }

    @Test
    public void validateParameters_with_invalidNumberPerRow() {
        numbersPerRow = 11; // greater than columns.

        try {
            game.validateParameters(limit, rows, columns, numbersPerRow);
        } catch(Exception e) {
            assertEquals("numbersPerRow cannot be greater than ticket columns", e.getMessage());
        }
    }

    @Test
    public void createPlayersWithTickets() {
        List<Player> result = game.createPlayersWithTickets(limit, players, numbersPerRow, rows, columns);

        assertTrue(result.size() == 3);

        //check if player is created
        assertNotNull(result.get(0));

        //check if ticket is created
        assertNotNull(result.get(0).getTicket());
    }

    @Test
    public void printWinMessage() {
        Set<String> uniqueWins = new HashSet<>();

        game.printWinMessage("Player#1", WinningCombination.EARLYFIVE.toString(), uniqueWins);
        //check if the win message is added to winning bucket
        assertTrue(uniqueWins.size() == 1);
    }

    @Test
    public void isNextNumberNotValid() {
        Set<String> uniqueCallerNo = new HashSet<>();

        //check with string value as next number
        assertTrue(game.isNextNumberNotValid(uniqueCallerNo, "a"));

        uniqueCallerNo.add("10");
        //check with already called number
        assertTrue(game.isNextNumberNotValid(uniqueCallerNo, "10"));
    }
}
