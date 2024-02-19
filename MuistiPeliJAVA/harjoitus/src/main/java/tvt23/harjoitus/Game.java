package tvt23.harjoitus;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Game {
    private List<Card> cards = new ArrayList<>();
    private int moveCount = 0;
    private boolean gameOver = false;

    public Game(int pairCount) {
        for (int i = 1; i <= pairCount; i++) {
            cards.add(new Card(i));
            cards.add(new Card(i));
        }
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public boolean makeMove(int card1Index, int card2Index) {
        if (card1Index != card2Index && cards.get(card1Index).getId() == cards.get(card2Index).getId() && !cards.get(card1Index).isMatched() && !cards.get(card2Index).isMatched()) {
            cards.get(card1Index).setMatched(true);
            cards.get(card2Index).setMatched(true);
            moveCount++;
            checkGameOver();
            return true;
        }
        moveCount++;
        return false;
    }

    private void checkGameOver() {
        gameOver = cards.stream().allMatch(Card::isMatched);
    }
}