import java.util.HashSet;
import java.util.Set;

/**
 * This is a simple POJO class of a Player
 */
public class Player {
    private String name;
    private Ticket ticket;
    private Set<String> wins;


    public Player(Ticket ticket, String name) {
        this.ticket = ticket;
        this.name = name;
        wins = new HashSet<String>();
    }

    public void displayDetails() {

        System.out.println("Ticket of "+name+" : ");
        ticket.displayTicket();
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getWins() {
        return wins;
    }

    public void addWin(String win) {
        this.wins.add(win);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player that = (Player) o;

        if (name != that.name) return false;
        if (ticket != null ? !ticket.equals(that.ticket) : that.ticket != null) return false;
        if (wins != null ? !wins.equals(that.wins) : that.wins != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
        result = 31 * result + (wins != null ? wins.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "name=" + name +
                ", Ticket='" + ticket +
                ", Wins='" + wins +
                '}';
    }

}
