package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sun.rmi.runtime.Log;

public class Controller{

    public Label scoreGame;
    public Button startBouton;
    public GridPane gridPaneBoule;

    private ElementZ_Model EZJeu;
    private int selectedX = -1;
    private int selectedY = -1;
    private Image[] imageBoules = new Image[8];
    private Image[] imageBoulesHover = new Image[8];
    private Image[] imageBoulesSelected = new Image[8];

    //--------------------------------------------------------------------------
    // Je viens dans cette méthode, charger mes images afin de puvoir les
    // utiliser plus tard.
    //--------------------------------------------------------------------------
    private void loadImageSimple(){
        for (int i=1; i<7; i++){
            imageBoules[i]= new Image("/boules_isen/boule_"+ i +".jpg");
        }
    }

    private void loadImageHover(){
        for (int i=1; i<7; i++){
            imageBoulesHover[i]= new Image("/boules_isen/boule_o_"+ i +".jpg");
        }
    }

    private void loadImageSelected(){
        for (int i=1; i<7; i++){
            imageBoulesSelected[i]= new Image("/boules_isen/boule_s_"+ i +".jpg");
        }
    }

    //--------------------------------------------------------------------------
    // Cette méthode me permet quand à elle de venir affecter les boules en
    // fontion de la matrice de jeu.
    //--------------------------------------------------------------------------
    private void affectBalls (){
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                gridPaneBoule.add(new ImageView(imageBoules[EZJeu.getXY(i,j)]),j,i);
            }
        }
    }

    //--------------------------------------------------------------------------
    // Ici je viens avec cette méthode lancer le jeu
    //--------------------------------------------------------------------------
    @FXML
    private void jButtonStart() {
        System.out.println("OK Start");
        loadImageSimple();
        loadImageHover();
        loadImageSelected();
        EZJeu = new ElementZ_Model();
        affectBalls();
        scoreGame.setText(String.valueOf(EZJeu.getScore()));
    }
    //--------------------------------------------------------------------------
    // Ici je viens avec cette méthode selectionner ma boule
    //--------------------------------------------------------------------------
    @FXML
    private void gridPaneClick(MouseEvent e) {
        try {
            Node source = (Node) e.getTarget();
            Integer colIndex = GridPane.getColumnIndex(source);
            Integer rowIndex = GridPane.getRowIndex(source);
            if (selectedX == -1) {
                selectedX = colIndex.intValue();
                selectedY = rowIndex.intValue();
                gridPaneBoule.add(new ImageView(imageBoulesSelected[EZJeu.getXY(selectedY, selectedX)]), selectedX, selectedY);

            } else {
                System.out.println(EZJeu.toString());
                EZJeu.play(selectedY, selectedX, rowIndex.intValue(), colIndex.intValue());
                selectedX = -1;
                selectedY = -1;
                affectBalls();
                scoreGame.setText(String.valueOf(EZJeu.getScore()));
            }

            System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
        }catch (Exception err){
            System.out.printf("Pas une image !!!!!!!!\n");
        }
    }
}
