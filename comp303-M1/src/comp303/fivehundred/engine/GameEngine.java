package comp303.fivehundred.engine;

import comp303.fivehundred.ai.IRobotPlayer;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Deck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Zaz on 2016-01-17.
 */
public class GameEngine {

    public final static Logger logger = LoggerFactory.getLogger(GameEngine.class);

    public enum Gamestate {DEALING, DEALING_DONE, BIDDING, BIDDING_DONE, PLAYING, PLAYING_DONE, OVER}

    private Gamestate state;
    private IRobotPlayer[] players;
    private int[] points = new int[4];
    private int bidderPosition;
    private Deck deck;
    private Hand[] hands = new Hand[4];
    private Trick tricks;
    private Bid[] bids = new Bid[4];
    private int[] tricksWon = new int[4];

    private List<GameEventListener> listeners = new ArrayList<>();

    public GameEngine(Gamestate state){
        this.state = state;

    }

    public void newGame(IRobotPlayer[] players){
        this.players = players;

    }

    public boolean isGameOver(){
        for(int i = 0; i < 2; i++){
            int point = points[i] + points[getConversePlayer(i)];
            System.out.println("Team "+i+": "+point);
            if(point >= 500 || point <= -500)
                return true;
        }
        return false;
    }

    public void deal() {
        removeListener(deck);
        deck = new Deck();
        addListener(deck);
        deck.shuffle();
        notifyListeners(new ShuffleEvent());

        for(int i = 0; i < 4; i++){
            List<Card> list = new ArrayList<>();
            for(int j = 0; j < 10; j++){
                list.add(deck.draw());

            }
            Hand h = new Hand();
            h.setCardList(list);
            hands[i] = h;
        }
    }


    public void bid() {

        Bid bidTemp[] = null;

        removeListeners(bids);

        for(int i = 0; i < 4; i++){
            bids[i] = players[i].selectBid(bidTemp, hands[i]);
            addListener(bids[i]);
            bidTemp = new Bid[i+1];
            for(int j = 0; j < i+1; j++)
                bidTemp[j] = bids[i];
        }
        notifyListeners(new BidEvent());
    }

    private void removeListeners(GameEventListener[] listeners) {
        for(int i = 0; i< listeners.length; i++){
            this.listeners.remove(listeners[i]);
        }
    }

    public boolean isAllPasses() {

        int count = 0;

        for(int i = 0; i < 4; i++){
            if(bids[i].isPass())
                count++;
        }
        return count == 4;
    }

    public void exchange() {
        int maxBidPosition = 0;

        for(int i = 0; i < 4; i++){
            if(bids[i].compareTo(bids[maxBidPosition]) > 0)
                maxBidPosition = i;
        }
        bidderPosition = maxBidPosition;

        for (int i = 0; i < 6; i++)
            hands[bidderPosition].add(deck.draw());

        CardList toRemove = players[maxBidPosition].selectCardsToDiscard(bids, maxBidPosition, hands[maxBidPosition]);
        hands[maxBidPosition].getCardList().removeAll(toRemove.getCardList());
    }

    public void playTrick() {
        tricks = new Trick(bids[bidderPosition]);

        for(int i = 0; i < 4; i++){
            tricks.add(players[i].play(tricks, hands[i]));
        }
        for(int i = 0; i < 4; i++){
            hands[i].getCardList().remove(tricks.getCardList().get(i));
        }
        tricksWon[tricks.winnerIndex()]++;
    }

    public void computeScore(){
        int score = bids[bidderPosition].getScore();
        if(bids[bidderPosition].getTricksBid() == (tricksWon[bidderPosition]+tricksWon[getConversePlayer(bidderPosition)])){
            points[bidderPosition] += score;
        }
        else
        {
            points[bidderPosition] -= score;
        }
    }

    private void removeListener(GameEventListener gel){
        listeners.remove(gel);
    }

    private void addListener(GameEventListener gel){
        listeners.add(gel);
    }

    private void notifyListeners(Event e){
        for (GameEventListener g:
             listeners) {
            g.listen(e);
        }
    }

    private int getConversePlayer(int position){
        return ((position+2)%4);
    }
}
