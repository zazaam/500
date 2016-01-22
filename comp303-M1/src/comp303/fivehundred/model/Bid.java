package comp303.fivehundred.model;

import com.sun.deploy.security.ValidationState;
import comp303.fivehundred.util.Card.Suit;



/**
 * Represents a bid or a contract for a player. Immutable.
 */
public class Bid implements Comparable<Bid>
{
    public enum Type {BID, NO_TRUMP, PASS}

	private int trick = 0, bidIndex;

    public Type getType() {
        return type;
    }

    private Type type;
    private Suit suit;

    private static final int  NUMBER_OF_BIDS = 24;
    private static final int  NUMBER_OF_BID_SUITS = 5;
    private static final int[][] BID_INDEX = new int[][]{
            {6,7,8,9,10},
            {6,7,8,9,10},
            {6,7,8,9,10},
            {6,7,8,9,10},
            {6,7,8,9,10},

    };
    private static final int[] SCORES = new int[]{
            40,60,80,100,120,
            140,160,180,200,220,
            240,260,280,300,320,
            340,360,380,400,420,
            440,460,480,500,520
    };


	/**
	 * Constructs a new standard bid (not a pass) in a trump.
	 * @param pTricks Number of tricks bid. Must be between 6 and 10 inclusive.
	 * @param pSuit Suit bid. 
	 * @pre pTricks >= 6 && pTricks <= 10
	 */
	public Bid(int pTricks, Suit pSuit)
	{
        this.trick = pTricks;
        this.suit = pSuit;
        if(trick == 0 && suit == null)
            this.type = Type.NO_TRUMP;
        else
            this.type = Type.BID;
	}
	
	/**
	 * Constructs a new passing bid.
	 */
	public Bid()
	{
        this.type = Type.PASS;
	}
	
	/**
	 * Creates a bid from an index value between 0 and 24 representing all possible
	 * bids in order of strength.
	 * @param pIndex 0 is the weakest bid (6 spades), 24 is the highest (10 no trump),
	 * and everything in between.
	 * @pre pIndex >= 0 && pIndex <= 24
	 */
	public Bid(int pIndex)
	{
        bidIndex = pIndex;

        int bidSuit = pIndex / NUMBER_OF_BID_SUITS;


        switch (bidSuit){
            case 0:
                trick = 6;
                break;
            case 1:
                trick = 7;
                break;
            case 2:
                trick = 8;
                break;
            case 3:
                trick = 9;
                break;
            default:
                trick = 10;
                break;
        }

        switch (pIndex % NUMBER_OF_BID_SUITS){
            case 0:
                suit = Suit.SPADES;
                type = Type.BID;
                break;
            case 1:
                suit = Suit.CLUBS;
                type = Type.BID;
                break;
            case 2:
                suit = Suit.DIAMONDS;
                type = Type.BID;
                break;
            case 3:
                suit = Suit.HEARTS;
                type = Type.BID;
                break;
            default:
                suit = null;
                type = Type.NO_TRUMP;
                break;
        }

	}
	
	/**
	 * @return The suit the bid is in, or null if it is in no-trump.
	 * @throws ModelException if the bid is a pass.
	 */
	public Suit getSuit() throws ModelException
	{
		if(type == Type.PASS)
            throw new ModelException("Bid is a Pass\n");
        return suit;
	}
	
	/**
	 * @return The number of tricks bid.
	 * @throws ModelException if the bid is a pass.
	 */
	public int getTricksBid() throws ModelException
    {
        if(type == Type.PASS)
            throw new ModelException("Bid is a Pass\n");
        return trick;
	}
	
	/**
	 * @return True if this is a passing bid.
	 */
	public boolean isPass()
	{
		return type == Type.PASS;
	}
	
	/**
	 * @return True if the bid is in no trump.
	 */
	public boolean isNoTrump()
	{
        return type == Type.NO_TRUMP || suit == null;
	}

	@Override
	public int compareTo(Bid pBid)
	{
        if(isPass()){
            if(pBid.isPass())
                return 0;
            return -1;
        }

        if(pBid.isPass())
            return 1;

		int pBidIndex = getBidIndex(pBid);
        int bidIndex = getBidIndex(this);

        if(bidIndex < pBidIndex)
            return -1;
        if(bidIndex > pBidIndex)
            return 1;
        return 0;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
        String s = "Bid: ";
        if(type == Type.PASS)
            s += "Pass";
        if(type == Type.NO_TRUMP)
            s += trick + " " + "No trumps";
        if(type == Type.BID)
            s += trick + " " + suit;
        s+="\n";
		return s;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object pBid)
	{
		if(pBid == null) return false;
        if(pBid == this) return true;
        if(!(pBid instanceof Bid)) return false;
        Bid b = (Bid)pBid;
        return b.suit == this.suit && b.trick == this.trick;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * {@inheritDoc}
	 */
    @Override
    public int hashCode() {
        int result = trick;
        result = 31 * result + type.hashCode();
        result = 31 * result + (suit != null ? suit.hashCode() : 0);
        return result;
    }

    /**
	 * Converts this bid to an index in the 0-24 range.
	 * @return 0 for a bid of 6 spades, 24 for a bid of 10 no-trump,
	 * and everything in between.
	 * @throws ModelException if this is a passing bid.
	 */
	public int toIndex()
	{
		return getBidIndex(this);
	}
	
	/**
	 * Returns the highest bid in pBids. If they are all passing
	 * bids, returns pass.
	 * @param pBids The bids to compare.
	 * @return the highest bid.
	 */
	public static Bid max(Bid[] pBids)
	{
        Bid max = new Bid();

        if(pBids == null)
            return max;

		for(int i = 0; i < pBids.length; i++){
            if(pBids[i].type == Type.PASS)
                continue;
            else{
                if(pBids[i].getType() == Type.NO_TRUMP)
                {
                    if(max.getType() == Type.BID)
                        max = pBids[i];
                    else if(max.getType() == Type.PASS)
                        max = pBids[i];
                    else
                        if(pBids[i].getTricksBid() > max.getTricksBid()){
                            max = pBids[i];
                        }else{
                            continue;
                        }

                }
                else{
                    if(max.getType() == Type.NO_TRUMP)
                        continue;
                    else if(max.getType() == Type.PASS)
                        max = pBids[i];
                    else
                    if(pBids[i].getTricksBid() > max.getTricksBid()){
                        max = pBids[i];
                    }else{
                        continue;
                    }
                }
            }
        }

        return max;
	}
	
	/**
	 * @return The score associated with this bid.
	 * @throws ModelException if the bid is a pass.
	 */
	public int getScore()
	{
		return SCORES[getBidIndex(this)];
	}

    private int getBidIndex(Bid bid){

        if(bid.getSuit() == null)
            return (bid.getTricksBid() - 6) * 5 + 4;

        switch (bid.getSuit()){
            case SPADES:
                int m = (bid.getTricksBid() - 6) * 5;
                return m;

            case CLUBS:
                return (bid.getTricksBid() - 6) * 5  + 1;

            case DIAMONDS:
                return (bid.getTricksBid() - 6) * 5 + 2;

            case HEARTS:
                return (bid.getTricksBid() - 6) * 5 + 3;

            default:
                return (bid.getTricksBid() - 6) * 5 + 4;

        }
    }
}
