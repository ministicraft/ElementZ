package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller{

    public Label scoreGame;
    public Button startBouton;
    public GridPane gridPaneBoule;

    private ImageView[] imageBoules = new ImageView[7];
    private ElementZ_Model EZJeu;

    private void loadImage (){
        //Je charge mes images
        for (int i=0; i<7; i++){
            imageBoules[i] = new ImageView("/boules_isen/boule_" + i + ".jpg");
        }
    }

    @FXML
    private void jButtonStart(){
        System.out.println("OK Start");
        loadImage();
        EZJeu = new ElementZ_Model();
        affectBoules();
        scoreGame.setText("0");
    }

    private void affectBoules(){
        int k = 0;
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){

            }
        }
    }

}
