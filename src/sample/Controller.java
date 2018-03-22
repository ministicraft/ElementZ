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

    private void initComponents (){
        int k = 0;
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                if (k<=6){
                    Image image = new Image("/boules_isen/boule_"+ k +".jpg");
                    gridPaneBoule.add(new ImageView(image),i,j);
                    k++;
                }else{
                    k=0;
                    Image image = new Image("/boules_isen/boule_"+ k +".jpg");
                    gridPaneBoule.add(new ImageView(image),i,j);
                }
            }
        }
    }

    @FXML
    private void jButtonStart() {
        System.out.println("OK Start");
        EZJeu = new ElementZ_Model();
        initComponents();
        scoreGame.setText("0");
    }
}
