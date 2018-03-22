package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller{

    public Label scoreGame;
    public Button startBouton;
    public GridPane gridPaneBoule;

    private ElementZ_Model EZJeu;
    private Image[] imageBoules = new Image[8];

    private void loadImage(){
        for (int i=0; i<7; i++){
            imageBoules[i]= new Image("/boules_isen/boule_"+ i +".jpg");
        }
    }

    private void initComponents (){
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                gridPaneBoule.add(new ImageView(imageBoules[EZJeu.getXY(i,j)]),i,j);
            }
        }
    }

    @FXML
    private void jButtonStart() {
        System.out.println("OK Start");
        loadImage();
        EZJeu = new ElementZ_Model();
        initComponents();
        scoreGame.setText("0");
    }
}
