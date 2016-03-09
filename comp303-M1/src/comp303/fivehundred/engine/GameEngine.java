package comp303.fivehundred.engine;

import comp303.fivehundred.ai.IRobotPlayer;
import comp303.fivehundred.engine.Events.BidEvent;
import comp303.fivehundred.engine.Events.Event;
import comp303.fivehundred.engine.Events.ShuffleEvent;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.player.IPlayer;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Deck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaz on 2016-01-17.
 */
public class GameEngine {

    public final static Logger logger = LoggerFactory.getLogger(GameEngine.class);

    public enum Gamestate {DEALING, DEALING_DONE, BIDDING, BIDDING_DONE, PLAYING, PLAYING_DONE, OVER}

    private Gamestate state;



    private IPlayer[] players;
    private int[] points = new int[4];
    private int bidderPosition;
    private Deck deck;
    private Hand[] hands = new Hand[4];
    private Trick tricks;
    private Bid[] bids = new Bid[4];
    private int[] tricksWon = new int[4];

    private List<IGameEventListener> listeners = new ArrayList<>();

    public GameEngine(Gamestate state){
        this.state = state;

    }

    public void newGame(IPlayer[] players){
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


    public Bid[] bid() {

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
        return bids;
    }

    public Bid[] bid(Bid b) {

        Bid bidTemp[] = null;

        removeListeners(bids);
        bids[0] = b;
        addListener(bids[0]);
        bidTemp = new Bid[1];
        bidTemp[0] = bids[0];

        for(int i = 1; i < 4; i++){
            bids[i] = players[i].selectBid(bidTemp, hands[i]);
            addListener(bids[i]);
            bidTemp = new Bid[i+1];
            for(int j = 0; j < i+1; j++)
                bidTemp[j] = bids[i];
        }
        notifyListeners(new BidEvent());
        return bids;
    }

    private void removeListeners(IGameEventListener[] listeners) {
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

    public int exchangeWithPosition() {
        int maxBidPosition = 0;

        for(int i = 0; i < 4; i++){
            if(bids[i].compareTo(bids[maxBidPosition]) > 0)
                maxBidPosition = i;
        }
        bidderPosition = maxBidPosition;

        for (int i = 0; i < 6; i++)
            hands[bidderPosition].add(deck.draw());

        if(bidderPosition != 0)
        {
            CardList toRemove = players[maxBidPosition].selectCardsToDiscard(bids, maxBidPosition, hands[maxBidPosition]);
            hands[maxBidPosition].getCardList().removeAll(toRemove.getCardList());
        }

        return bidderPosition;
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

    public Trick playTrickWithHuman(Card c) {
        tricks = new Trick(bids[bidderPosition]);
        tricks.add(c);

        for(int i = 1; i < 4; i++){
            tricks.add(players[i].play(tricks, hands[i]));
            hands[i].getCardList().remove(tricks.getCardList().get(i));
        }
        tricksWon[tricks.winnerIndex()]++;
        return tricks;
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

    private void removeListener(IGameEventListener gel){
        listeners.remove(gel);
    }

    private void addListener(IGameEventListener gel){
        listeners.add(gel);
    }

    private void notifyListeners(Event e){
        for (IGameEventListener g:
             listeners) {
            g.listen(e);
        }
    }

    private int getConversePlayer(int position){
        return ((position+2)%4);
    }


    public IRobotPlayer[] getPlayers() {
        return players;
    }

    public int[] getPoints() {
        return points;
    }

    public int getBidderPosition() {
        return bidderPosition;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand[] getHands() {
        return hands;
    }

    public Trick getTricks() {
        return tricks;
    }

    public Bid[] getBids() {
        return bids;
    }

    public int[] getTricksWon() {
        return tricksWon;
    }
}
