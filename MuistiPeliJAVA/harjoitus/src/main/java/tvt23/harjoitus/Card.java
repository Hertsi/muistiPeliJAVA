package tvt23.harjoitus;

public class Card {
    private int id;
    private boolean matched = false;

    public Card(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}