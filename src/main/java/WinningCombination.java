
public enum WinningCombination {

    FULLHOUSE("Full House"),
    EARLYFIVE("Early Five"),
    TOPLINE("Top Line"),
    NOTHING("Nothing");

    private String combination;
    WinningCombination(String combination) {
        this.combination = combination;
    }

    @Override
    public String toString() {
        return combination;
    }

}
