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
    }


}
