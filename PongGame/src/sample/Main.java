package sample;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{
//game panel size
    private static final int width = 1200;
    private static final int height = 600;
    private static final int platform_width = 10;
    private static final int platform_height = 80;
//ball size and speed
    private static final double ball = 20;
    private double ballYSpeed = 1;
    private double ballXSpeed = 1;
//points
    private int score1 = 0;
    private int score2 = 0;
//position
    private double platformOneYPosition = height/2;
    private double platformTwoYPosition = height/2;
    private double ballXPosition = width/2;
    private double ballYPosition = height/2;
    private int platformOneXPosition = 0;
    private double platformTwoXPosition = width - platform_width;
//game started

    private boolean singleGame;





    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("PONG GAME");
        Canvas canvas = new Canvas(width,height);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(10), event -> play(graphicsContext)));
        timeline1.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseClicked(mouseEvent -> singleGame = true);

        primaryStage.setScene(new Scene(new StackPane(canvas)));
       // move
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, key ->{
            if(key.getCode() == KeyCode.W){
                platformOneYPosition -= 50;
            }
            if(key.getCode() == KeyCode.S){
                platformOneYPosition += 50;
            }
            if(key.getCode() == KeyCode.UP){
                platformTwoYPosition -= 50;
            }
            if(key.getCode() == KeyCode.DOWN){
                platformTwoYPosition += 50;
            }
        });
        primaryStage.show();
        primaryStage.setResizable(false);
        timeline1.play();
    }

    private void play(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.LIMEGREEN);
        graphicsContext.fillRect(0,0,width,height);
        graphicsContext.setFill(Color.RED);
        graphicsContext.setFont(Font.font(40));

        if(singleGame){
            ballXPosition+=ballXSpeed;
            ballYPosition+=ballYSpeed;
            graphicsContext.fillOval(ballXPosition, ballYPosition, ball,ball);
        }
        else {
            graphicsContext.setStroke(Color.RED);
            graphicsContext.setTextAlign(TextAlignment.CENTER);
            graphicsContext.strokeText("Continue",width/2,height/2);

            ballXPosition = width/2;
            ballYPosition = height/2;

            ballXSpeed = new Random().nextDouble() == 0 ? -1 : 1;
            ballYSpeed = new Random().nextDouble() == 0 ? -1: 1;
        }

        if(ballYPosition > height-20 || ballYPosition < 0){
            ballYSpeed *= -1;
        }

        if(ballXPosition < 0){
            score2++;
            singleGame = false;
        }
        if(ballXPosition > width){
            score1++;
            singleGame = false;
        }
        graphicsContext.fillText("Player 1 score: "+score1+ "\t\t\t\t"+"Player 2 score: "+score2,width/2, 60);
        graphicsContext.fillRect(platformTwoXPosition,platformTwoYPosition,platform_width,platform_height);
        graphicsContext.fillRect(platformOneXPosition,platformOneYPosition,platform_width,platform_height);

        if(((ballXPosition + ball > platformTwoXPosition) && ballYPosition >= platformTwoYPosition && ballYPosition <= platformTwoYPosition + platform_height) || ((ballXPosition < platformOneXPosition + platform_width) && ballYPosition >= platformOneYPosition && ballYPosition <= platformOneYPosition + platform_height)){
            ballXSpeed += Math.signum(ballXSpeed);
            ballYSpeed += Math.signum(ballYSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
