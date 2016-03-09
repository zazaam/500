package comp303.fivehundred.gui;

import comp303.fivehundred.ai.RandomRobot;
import comp303.fivehundred.engine.GameEngine;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.player.IPlayer;
import comp303.fivehundred.util.Card;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class GameWindows extends Application {


	Canvas canvas;

	MenuBar menuBar = new MenuBar();
	Menu fileMenu = new Menu("File");
	Menu runMenu = new Menu("Run");
	ContextMenu contextMenu = new ContextMenu();
	private Hand[] hand;
	private Trick tricks = new Trick();
	private Bid[] bids;
	private Bid contract;
	private ChoiceBox<String> cbSuit;
	private ChoiceBox<java.io.Serializable> cbRank;
	private Button start;
	private Button bidButton;
	private Button play, exchange;

	private int bidPosition;

	private void buildMenus(Stage stage) {

		//Build Run menu items
		MenuItem startMenuItem = new MenuItem("Start Timer");
		runMenu.getItems().addAll(startMenuItem);
		//TODO:
		startMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		});
		MenuItem stopMenuItem = new MenuItem("Stop Timer");
		runMenu.getItems().addAll(stopMenuItem);
		//TODO:
		stopMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		});

		//Build File menu items
		MenuItem aboutMenuItem = new MenuItem("About This App");
		fileMenu.getItems().addAll(aboutMenuItem);
		aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			//TODO:
			public void handle(ActionEvent e) {

			}
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("500");

		VBox paneContainer = new VBox();
		HBox player4Area = new HBox();
		BorderPane bottomRight = new BorderPane(), topBorderPane = new BorderPane();
		BorderPane playingPane = new BorderPane();
		VBox root = new VBox();

		GameEngine game = new GameEngine(GameEngine.Gamestate.DEALING);
		IPlayer[] players = {new RandomRobot(), new RandomRobot(), new RandomRobot(), new RandomRobot()};

		Scene mainScene = new Scene(paneContainer);
		primaryStage.setResizable(false);
		primaryStage.setScene(mainScene);

		menuBar.getMenus().add(fileMenu);
		menuBar.getMenus().add(runMenu);

		root.getChildren().add(menuBar);
		buildMenus(primaryStage);

		cbRank = new ChoiceBox<>(FXCollections.observableArrayList(
				6, 7, 8, 9, 10, "Pass"
		));
		cbRank.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() == 5) {
				cbSuit.getSelectionModel().select(null);
			}
		});

		cbSuit = new ChoiceBox<>(FXCollections.observableArrayList(
				"SPADES", "CLUBS", "DIAMONDS", "HEARTS"
		));

		start = new Button("Start");
		start.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			distributeCards(playingPane, game);
			start.setVisible(false);
			bidButton.setVisible(true);
		});

		//TODO: if bids are all passes i need to recall that again
		bidButton = new Button("Bid");
		bidButton.setVisible(false);
		bidButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			int index = cbRank.getSelectionModel().getSelectedIndex();
			Object o = cbSuit.getSelectionModel().getSelectedItem();
			if (index == 5) {
				bids = game.bid(new Bid());
				bidButton.setVisible(false);
				cbRank.setVisible(false);
				cbSuit.setVisible(false);
				bidPosition = game.exchangeWithPosition();
				contract = bids[bidPosition];
				drawCards(playingPane, game);
				if (bidPosition != 0)
					play.setVisible(true);
				else
					exchange.setVisible(true);
			} else if (index >= 0 && index < 5) {
				if (o == null)
					bids = game.bid(new Bid(index + 6, null));
				else
					bids = game.bid(new Bid(index + 6, Card.Suit.valueOf((String) o)));
				bidButton.setVisible(false);
				cbRank.setVisible(false);
				cbSuit.setVisible(false);
				bidPosition = game.exchangeWithPosition();
				contract = bids[bidPosition];
				drawCards(playingPane, game);
				if (bidPosition != 0)
					play.setVisible(true);
				else
					exchange.setVisible(true);
			}


		});
		play = new Button("Play");
		play.setVisible(false);
		play.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			int count = 0;
			List<Card> cards = hand[0].getCardList();
			Card toRemove = null;
			for (Card card : cards) {
				if (card.isSelected()) {
					count++;
					toRemove = card;
				}
			}
			if (count == 1) {
				hand[0].remove(toRemove);

				bidButton.setVisible(false);
				tricks = game.playTrickWithHuman(toRemove);
				drawCards(playingPane, game);
				tricks = new Trick();
				if(hand[0].size() == 0){
					game.computeScore();
					if(game.isGameOver())
					{
						game.newGame(players);
					}
					else{
						distributeCards(playingPane, game);
						start.setVisible(false);
						bidButton.setVisible(true);
					}
				}

			}
		});

		exchange = new Button("Exchange");
		exchange.setVisible(false);
		exchange.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			int count = 0;
			List<Card> cards = hand[0].getCardList();
			List<Card> toRemove = new ArrayList<>();
			for (Card card : cards) {
				if (card.isSelected()) {
					count++;
					toRemove.add(card);
				}
			}
			if (count == 6) {
				for (Card c : toRemove) {
					hand[0].remove(c);
				}
				play.setVisible(true);
				exchange.setVisible(false);
				drawCards(playingPane, game);
			}
		});

		game.newGame(players);


//        Image testimage = new Image("/images/2c.gif", 0, 100, false, false);
//        Button test2 = new Button("", new ImageView(testimage));
//        ImageView v = new ImageView(testimage);
//        v.setRotate(90);
//        v.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> v.setEffect(new DropShadow()));
//        v.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> v.setEffect(null));
//        v.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//            TranslateTransition tt = new TranslateTransition(Duration.millis(200), v);
//            tt.setByX(-50f);
//            tt.setCycleCount(0);
//            tt.setAutoReverse(false);
//
//
//            tt.play();
//            tt = new TranslateTransition(Duration.millis(200), v);
//            tt.setByY(-50f);
//            tt.setCycleCount(0);
//            tt.setAutoReverse(false);
//
//
//            tt.play();
//        });




		player4Area.getChildren().add(start);
		player4Area.getChildren().add(bidButton);
		player4Area.getChildren().add(cbRank);
		player4Area.getChildren().add(cbSuit);
		player4Area.getChildren().add(exchange);
		player4Area.getChildren().add(play);
		bottomRight.setBottom(player4Area);

		paneContainer.getChildren().add(root);
		paneContainer.getChildren().add(playingPane);
		paneContainer.getChildren().add(bottomRight);

		HBox labelPane = new HBox(), bidPane = new HBox(), rightPane = new HBox();

		Label score = new Label("Team 1: " + (game.getPoints()[0]+game.getPoints()[2]) +  " - Team 2: " + (game.getPoints()[1]+game.getPoints()[3]));
		Label topBid = new Label("No winning contract." );
		Label rightLabel = new Label("Temp");

		labelPane.getChildren().add(score);
		bidPane.getChildren().add(topBid);
		rightPane.getChildren().add(rightLabel);

		labelPane.setPrefWidth(800/2 - 50);
		bidPane.setPrefWidth(800/3);


		topBorderPane.setLeft(labelPane);
		topBorderPane.setCenter(bidPane);
		topBorderPane.setRight(rightPane);

		root.getChildren().add(topBorderPane);


		int canvasWidth = 800;
		int canvasHeight = 600;

		canvas = new Canvas(canvasWidth, canvasHeight);
		root.getChildren().add(canvas);

		//add mouse event handler (for popup menu)
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressedEvent
		);
		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleasedEvent
		);
		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDraggedEvent
		);


		primaryStage.show();

//        while (!game.isGameOver()){
//            distributeCards(playingPane, game);
//            game.bid();
//            while(game.isAllPasses()){
//                distributeCards(playingPane, game);
//                game.bid();
//            }
//
//            game.exchange();
//            for(int i = 0; i < 10; i++){
//                game.playTrick();
//            }
//            game.computeScore();
//        }
	}


	private void distributeCards(BorderPane playingPane, GameEngine game) {
		game.deal();

		drawCards(playingPane, game);
	}

	private void drawCards(BorderPane playingPane, GameEngine game) {
		hand = game.getHands();
		playingPane.getChildren().remove(0, playingPane.getChildren().size());
		for (int i = 0; i < 4; i++) {
			hand[i].draw(i, 0);
			for (int j = 0; j < hand[i].size(); j++) {
				if (i == 0)
					playingPane.getChildren().add(hand[i].getCardList().get(j).getImage(true));
				if (i == 1)
					playingPane.getChildren().add(hand[i].getCardList().get(j).getImage(false));
				if (i == 2)
					playingPane.getChildren().add(hand[i].getCardList().get(j).getImage(false));
				if (i == 3)
					playingPane.getChildren().add(hand[i].getCardList().get(j).getImage(false));
			}

		}
		for (int i = 0; i < tricks.getCardList().size(); i++) {
			tricks.draw(i, 0);

			if (i == 0)
				playingPane.getChildren().add(tricks.getCardList().get(i).getImage(true));
			if (i == 1)
				playingPane.getChildren().add(tricks.getCardList().get(i).getImage(true));
			if (i == 2)
				playingPane.getChildren().add(tricks.getCardList().get(i).getImage(true));
			if (i == 3)
				playingPane.getChildren().add(tricks.getCardList().get(i).getImage(true));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void handleMouseDraggedEvent(MouseEvent e) {
	}

	private void handleMouseReleasedEvent(MouseEvent e) {

	}

	private void handleMousePressedEvent(MouseEvent e) {
	}
}
