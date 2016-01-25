package comp303.fivehundred.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 * Created by Hamza on 1/23/2016.
 */
public class GameWindows extends Application {


    Canvas canvas;

    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    Menu runMenu = new Menu("Run");
    ContextMenu contextMenu = new ContextMenu();

    private void buildMenus(Stage stage){

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
        primaryStage.setTitle("test");

        VBox root = new VBox();
        Scene mainScene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);

        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(runMenu);

        root.getChildren().add(menuBar);
        buildMenus(primaryStage);

        int canvasWidth = 800;
        int canvasHeight = 600;

        canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);

        //add mouse event handler (for popup menu)
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                    handleMousePressedEvent(e);
                }
        );
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
                    handleMouseReleasedEvent(e);
                }
        );
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {
                    handleMouseDraggedEvent(e);
                }
        );


        primaryStage.show();
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
