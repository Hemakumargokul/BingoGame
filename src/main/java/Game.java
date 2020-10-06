import java.util.*;

/**
 * Indian Bingo Game
 */
public class Game {

    public static final String PLAYER_PREFIX = "player#";

    /**
     * print the summary of the players once the game is over.
     * @param playerList
     */
    public void printSummary(List<Player> playerList) {
        System.out.println("***** Game Over *****");
        System.out.println("======================");

        System.out.println("Summary:");


        for(Player player: playerList) {
            StringBuilder str = new StringBuilder();
            if(player.getWins().isEmpty()) {
                str.append("Nothing");
            } else {
                List<String> list = new ArrayList<String>(player.getWins());
                str.append(String.join(" and ", list));
            }
            System.out.println(player.getName() +": "+ str.toString());
        }

        System.out.println("======================");

    }


    public static void main(String[] args) {

        //default rows
        int rows = 3;
        //default columns
        int columns = 10;

        Game game = new Game();
        InputParameters inputParameters = new InputParameters().invoke();


        int limit = inputParameters.getLimit();
        int playersCount = inputParameters.getPlayers();
        String userDimension = inputParameters.getDimension();
        int numbersPerRow = inputParameters.getNumbersPerRow();


        // split the user dimension into rows and columns
        Dimension dimension = new Dimension(userDimension, rows, columns).invoke();
        rows = dimension.getRows();
        columns = dimension.getColumns();

        // validate to check constraints on input parameters
        game.validateParameters(limit, rows, columns, numbersPerRow);




        List<Player> playerList = game.createPlayersWithTickets(limit, playersCount, numbersPerRow, rows, columns);

        System.out.println("***Ticket Created Successfully ****");


        //lets the caller to input number to be marked and displays the status of each player
        game.processNextNumber(playerList, game);
    }


    /**
     * validate input parameters for invalid values
     * @param limit
     * @param columns
     * @param numbersPerRow
     */
    public void validateParameters(int limit, int rows, int columns, int numbersPerRow) {
        if(limit <= 0) {
            throw new RuntimeException("Range is not valid");
        }

        if(limit < rows*numbersPerRow) {
            throw new RuntimeException("Range cannot be less than total number of entries in the ticket");
        }

        if(numbersPerRow > columns) {
            throw new RuntimeException("numbersPerRow cannot be greater than ticket columns");
        }


    }

    /**
     * create ticket for each player
     * @param limit
     * @param players
     * @param numbersPerRow
     * @param rows
     * @param columns
     * @return
     */
    public List<Player> createPlayersWithTickets(int limit, int players, int numbersPerRow, int rows, int columns) {
        List<Player> playerList = new ArrayList<Player>();
        for(int i=0;i<players;i++) {
            //build the ticket
            Ticket t = new Ticket(rows, columns, numbersPerRow, limit);
            t.buildTicket();

            //
            Player p = new Player(t, PLAYER_PREFIX.concat(String.valueOf(i+1)));
            playerList.add(p);
            p.displayDetails();

        }
        return playerList;
    }

    /**
     * lets the caller to input number to be marked and displays the status of each player
     * @param playerList
     */
    public void processNextNumber(List<Player> playerList, Game game) {
        Set<String> uniqueCallerNo = new HashSet<>();
        Set<String> uniqueWins = new HashSet<>();
        int callCount = 0;

        while(true) {
            System.out.println();
            System.out.println("Press 'N/n' to generate next number. ");
            System.out.println("Press 'Q/q' to quit any time. ");
            System.out.println("Press 'D/d' to display tickets of all players. ");
            Scanner sc = new Scanner(System.in);
            String keyEntered = sc.next();



            if(keyEntered.equalsIgnoreCase("N")) {

                System.out.print("Next number is:");
                sc = new Scanner(System.in);
                String nextNumber = sc.next();


                if (game.isNextNumberNotValid(uniqueCallerNo, nextNumber)) {
                    continue;
                }

                // increment caller's valid numbers
                callCount++;

                for(Player player: playerList) {
                    player.getTicket().strikeNumber(nextNumber);
                    String status = player.getTicket().checkStatus(callCount);

                    if(status.equals(WinningCombination.FULLHOUSE.toString())) {

                        player.addWin(WinningCombination.FULLHOUSE.toString());
                        game.printSummary(playerList);
                        return;

                    } else if(status.equals(WinningCombination.EARLYFIVE.toString())) {

                        player.addWin(WinningCombination.EARLYFIVE.toString());
                        printWinMessage(player.getName(), WinningCombination.EARLYFIVE.toString(), uniqueWins);
                    } else if(status.equals(WinningCombination.TOPLINE.toString())) {

                        player.addWin(WinningCombination.TOPLINE.toString());
                        printWinMessage(player.getName(), WinningCombination.TOPLINE.toString(), uniqueWins);
                    }
                }
            } else if(keyEntered.equalsIgnoreCase("Q")) {
                game.printSummary(playerList);
                break;
            } else if(keyEntered.equalsIgnoreCase("D")) {
                for(Player player: playerList) {
                    player.displayDetails();
                }
            }
        }
    }


    public void printWinMessage(String playerName, String winCombination, Set<String> uniqueWins) {
        String winMessage = "We have a winner: "+ playerName +" has won " + winCombination + " winning combination.";
        if(!uniqueWins.contains(winMessage)) {
            uniqueWins.add(winMessage);
            System.out.println(winMessage);
        }
    }

    public boolean isNextNumberNotValid(Set<String> uniqueCallerNo, String nextNumber) {
        //validate nextNumber
        if(isNumeric(nextNumber)) {
            if(uniqueCallerNo.contains(nextNumber)) {
                System.out.println("\nEnter the number which is not already picked\n");
                return true;
            } else {
                uniqueCallerNo.add(nextNumber);
            }
        } else {
            System.out.println("Enter a numeric value");
            return true;
        }
        return false;
    }

    private static class InputParameters {
        private int limit;
        private int players;
        private String dimension;
        private int numbersPerRow;

        public int getLimit() {
            return limit;
        }

        public int getPlayers() {
            return players;
        }

        public String getDimension() {
            return dimension;
        }

        public int getNumbersPerRow() {
            return numbersPerRow;
        }

        public InputParameters invoke() {
            System.out.println("**** Let's Play Housie *****");

            System.out.print("Enter the number range(1-n): ");
            Scanner sc = new Scanner(System.in);
            limit = sc.nextInt();


            System.out.print("Enter Number of players playing the game: ");
            sc = new Scanner(System.in);
            players = sc.nextInt();

            System.out.print("Enter Ticket Size in format(N*M): ");
            sc = new Scanner(System.in);
            dimension = sc.next();

            System.out.print("Enter numbers per row: ");
            sc = new Scanner(System.in);
            numbersPerRow = sc.nextInt();

            return this;
        }

    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


    private static class Dimension {
        private String dimension;
        private int rows;
        private int columns;

        public Dimension(String dimension, int rows, int columns) {
            this.dimension = dimension;
            this.rows = rows;
            this.columns = columns;
        }

        public int getRows() {
            return rows;
        }

        public int getColumns() {
            return columns;
        }

        public Dimension invoke() {
            if(!dimension.isEmpty()) {
                String[] split = dimension.split("\\*", 2);
                if(isNumeric(split[0]) && isNumeric(split[1])) {
                    rows = Integer.parseInt(split[0]);
                    columns = Integer.parseInt(split[1]);
                }
            }
            return this;
        }
    }
}
