import java.util.*;

/**
 * Ticket Sheet
 */
public class Ticket {

    private String[][] ticketSheet;
    private Map<String, int[]> numberMap;
    private int numbersPerRow;
    private int limit;
    private int ticketSize;
    private int rows;
    private int columns;
    private int totalStrikes;
    private Random random;

    public Ticket() {}


    public Ticket(String[][] numbers) {
        this.ticketSheet = numbers;
    }

    public Ticket(int rows, int columns, int numbersPerRow, int limit) {
        this.rows = rows;
        this.columns = columns;
        this.ticketSheet = new String[rows][columns];
        this.numbersPerRow = numbersPerRow;
        this.limit = limit;
        this.ticketSize = rows*numbersPerRow;
    }

    /**
     * Creates ticket with random numbers based on the limit provided by the caller.
     */
    public void buildTicket() {
        int[] rc;
        numberMap = new HashMap<String, int[]>();
        random = new Random();

        // check and add only unique random number already to the map
        while(numberMap.size() != ticketSize) {
            int randomNum = random.nextInt(limit-1) + 1;
            if(!numberMap.containsKey(String.valueOf(randomNum))) {
                numberMap.put(String.valueOf(randomNum), new int[2]);
            }
        }


        //iterate through number map entries and initialize the ticketSheet with indexes
        Iterator<Map.Entry<String, int[]>> itr = numberMap.entrySet().iterator();
        for(int i=0;i<rows;i++) {
            for(int j=0;j<numbersPerRow;j++) {
                String entry  = itr.next().getKey();
                ticketSheet[i][j] = entry;
                rc = new int[2];
                rc[0] = i;
                rc[1] = j;
                numberMap.put(entry, rc);
            }
        }
    }

    /**
     * Strikes off the given number from the ticket sheet.
     * @param number
     * @return true if the number is striked off successfully, else return false.
     */
    public boolean strikeNumber(String number) {
        int[] index;
        int row;
        int column;

        if(numberMap.containsKey(number)) {
            //get the index from the numberMap entry
            index = numberMap.get(number);
            row = index[0];
            column = index[1];


            //strike off the number
            ticketSheet[row][column] = "X";
            totalStrikes++;

            //display the ticketSheet for caller reference.
            displayTicket();
            return true;
        }

        return false;
    }

    /**
     * check the ticket status to find winning combinations
     * @param count
     * @return winning combination
     */
    public String checkStatus(int count) {


        if(totalStrikes == numbersPerRow * rows) {
            return WinningCombination.FULLHOUSE.toString();
        }


        //check if First Five caller entries striked off
        if(count == 5 && totalStrikes == 5) {
            return WinningCombination.EARLYFIVE.toString();
        }


        //check if the top line if the ticket is marked
        int j = 0;
        while (j<numbersPerRow) {
            if(!ticketSheet[0][j].equals("X")) {
                break;
            } else {
                j++;
            }

        }
        if(j == numbersPerRow) {
            return WinningCombination.TOPLINE.toString();
        }

        //default
        return WinningCombination.NOTHING.toString();
    }

    /**
     * displays the ticket sheet.
     */
    public void displayTicket() {

        System.out.println("---------------------");
        for(int i=0;i<rows;i++) {
            for(int j=0;j<numbersPerRow;j++) {
                System.out.print(ticketSheet[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }



    public Ticket setTicketSheet(String[][] ticketSheet) {
        this.ticketSheet = ticketSheet;
        return this;
    }

    public String[][] getTicketSheet() {
        return ticketSheet;
    }

    public Ticket setNumberMap(Map<String, int[]> numberMap) {
        this.numberMap = numberMap;
        return this;
    }

    public Map<String, int[]> getNumberMap(Map<String, int[]> numberMap) {
        return  numberMap;
    }


    public int getTotalStrikes() {
        return totalStrikes;
    }

    public void setTotalStrikes(int totalStrikes) {
        this.totalStrikes = totalStrikes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket that = (Ticket) o;

        if (ticketSheet != that.ticketSheet) return false;
        if (numberMap != null ? !numberMap.equals(that.numberMap) : that.numberMap != null) return false;
        if (numbersPerRow != that.numbersPerRow) return false;
        if (limit != that.limit) return false;
        if (ticketSize != that.ticketSize) return false;
        if (rows != that.rows) return false;
        if (columns != that.columns) return false;
        if (totalStrikes != that.totalStrikes) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = ticketSheet != null ? ticketSheet.hashCode() : 0;
        result = 31 * result + (numberMap != null ? numberMap.hashCode() : 0);
        result = 31 * result + numbersPerRow;
        result = 31 * result + limit;
        result = 31 * result + ticketSize;
        result = 31 * result + rows;
        result = 31 * result + columns;
        result = 31 * result + totalStrikes;
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "TicketSheet=" + ticketSheet +
                ", NumberMap='" + numberMap +
                ", NumbersPerRow='" + numbersPerRow +
                ", Limit='" + limit +
                ", TicketSize='" + ticketSize +
                ", Rows='" + rows +
                ", Columns='" + columns +
                ", TotalStrikes='" + totalStrikes +
                '}';
    }

}
