package comp303.fivehundred.engine;

import comp303.fivehundred.ai.IRobotPlayer;
import comp303.fivehundred.ai.RandomRobot;

/**
 * Created by Zaz on 2016-01-17.
 */
public class Driver {

    public static void main(String[] args) {

        GameEngine game = new GameEngine(GameEngine.Gamestate.DEALING);
        IRobotPlayer[] robots = {new RandomRobot(), new RandomRobot(), new RandomRobot(), new RandomRobot()};

        game.newGame(robots);
        while (!game.isGameOver()){
            game.deal();
            game.bid();
            while(game.isAllPasses()){
                game.deal();
                game.bid();
            }

            game.exchange();
            for(int i = 0; i < 10; i++){
                game.playTrick();
            }
            game.computeScore();
        }
    }
}
