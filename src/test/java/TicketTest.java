import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class TicketTest {
    Ticket ticket;
    int rows;
    int columns;
    int numbersPerRow;

    @Before
    public void setup() {
        rows = 2;
        columns = 3;
        numbersPerRow = 3;

        ticket = new Ticket(rows, columns, 3, 90);
        HashMap<String, int[]> numberMap = new HashMap<String, int[]>();
        String[][] ticketSheet = createTicket(rows, columns, numberMap);
        this.ticket.setTicketSheet(ticketSheet);
        this.ticket.setNumberMap(numberMap);

    }

    @Test
    public void buildTicket() {
        String uniqueTicketNo;
        ticket.buildTicket();
        HashSet<String> ticketSet = new HashSet<>();
        String[][] ticketSheet = ticket.getTicketSheet();
        //assert if the ticket is created with unique values
        for(int i=0;i<rows;i++) {
            for(int j=0;j<numbersPerRow;j++) {
                uniqueTicketNo = ticketSheet[i][j];

                if(ticketSet.contains(uniqueTicketNo)) {
                    fail("ticket doesn't contain unique numbers");
                } else {
                    ticketSet.add(uniqueTicketNo);
                }
            }
        }
        assertTrue("The ticket is successfully built", true);
    }


    @Test
    public void strikeNumber_that_ExistsInTheTicket() {
        assertTrue(ticket.strikeNumber("0"));
    }

    @Test
    public void strikeNumber_that_NotExistsInTheTicket() {
        assertFalse(ticket.strikeNumber("8"));
    }

    @Test
    public void checkStatus_FullHouse() {
        int defaultCount = 1;
        this.ticket.setTotalStrikes(rows*numbersPerRow);
        String winningCombination = ticket.checkStatus(defaultCount);
        assertEquals(WinningCombination.FULLHOUSE.toString(), winningCombination);
    }

    @Test
    public void checkStatus_EarlyFive() {
        int count = 5;
        this.ticket.setTotalStrikes(count);
        String winningCombination = ticket.checkStatus(count);
        assertEquals(WinningCombination.EARLYFIVE.toString(), winningCombination);
    }

    @Test
    public void checkStatus_TopLine() {
        int defaultCount = 1;
        this.ticket.setTotalStrikes(defaultCount);

        //strike first row
        ticket.strikeNumber("0");
        ticket.strikeNumber("1");
        ticket.strikeNumber("2");

        String winningCombination = ticket.checkStatus(defaultCount);
        assertEquals(WinningCombination.TOPLINE.toString(), winningCombination);
    }

    @Test
    public void checkStatus_Nothing() {
        int defaultCount = 1;
        this.ticket.setTotalStrikes(defaultCount);
        String winningCombination = ticket.checkStatus(defaultCount);
        assertEquals(WinningCombination.NOTHING.toString(), winningCombination);
    }



    private String[][] createTicket(int rows, int columns, HashMap<String, int[]> numberMap) {
        String[][] ticketSheet = new String[2][5];
        int k=0;
        for(int i=0;i<rows;i++) {
            for(int j=0;j<columns;j++) {
                int c = k++;
                ticketSheet[i][j] = String.valueOf(c);
                int[] rc = new int[2];
                rc[0] = i;
                rc[1] = j;
                numberMap.put(String.valueOf(c), rc);
            }
        }
        return ticketSheet;
    }
}
