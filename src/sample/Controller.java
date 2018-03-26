package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Hashtable;

public class Controller {

    public Label scoreGame;
    public Button startBouton;
    public GridPane gridPaneBoule;

    private ElementZ_Model EZJeu;
    private int selectedCol = -1;
    private int selectedRow = -1;
    private final String s = "res/img/boules";
    private final int TOLERANCE_THRESHOLD = 0x1A;
    private Hashtable<Integer, Image> imageBoules = new Hashtable<>();
    private Hashtable<Integer, Image> imageBoulesHover = new Hashtable<>();
    private Hashtable<Integer, Image> imageBoulesSelected = new Hashtable<>();


    //--------------------------------------------------------------------------
    // Constructeur du controller
    //--------------------------------------------------------------------------
    public Controller() {
        try {
            for (int i = 1; i <= 6; i++) {
                Image image = new Image(s + "/boule_" + i + ".jpg");
                image = makeTransparent(image);
                Image imageO = new Image(s + "/boule_o_" + i + ".jpg");
                imageO = makeTransparent(imageO);
                Image imageS = new Image(s + "/boule_s_" + i + ".jpg");
                imageS = makeTransparent(imageS);
                imageBoules.put(i, image);
                imageBoulesHover.put(i, imageO);
                imageBoulesSelected.put(i, imageS);
            }
        } catch (IllegalArgumentException e) {
            System.err.print("Impossible de charger les images\n");
        }
    }

    //--------------------------------------------------------------------------
    // J'utilise cette methode pour créer mes imagesView
    // Et ajouter les listeners
    //--------------------------------------------------------------------------
    private ImageView createBalls(int id) {
        ImageView imageView = new ImageView(imageBoules.get(id));
        imageView.setOnMouseEntered(event -> imageView.setImage(imageBoulesHover.get(id)));
        imageView.setOnMouseExited(event -> imageView.setImage(imageBoules.get(id)));
        return imageView;
    }

    //--------------------------------------------------------------------------
    // Cette methode permet de changer le fond des images noirs en transparent
    //--------------------------------------------------------------------------
    private Image makeTransparent(Image inputImage) {
        int W = (int) inputImage.getWidth();
        int H = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int argb = reader.getArgb(x, y);

                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;

                if (r <= TOLERANCE_THRESHOLD && g <= TOLERANCE_THRESHOLD && b <= TOLERANCE_THRESHOLD) {
                    argb &= 0x00000000;
                }

                writer.setArgb(x, y, argb);
            }
        }

        return outputImage;
    }

    //--------------------------------------------------------------------------
    // Cette méthode me permet quand à elle de venir affecter les boules en
    // fontion de la matrice de jeu.
    //--------------------------------------------------------------------------
    private void affectBalls() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int id = EZJeu.getXY(i, j);
                gridPaneBoule.add(createBalls(id), j, i);
            }
        }
    }

    //--------------------------------------------------------------------------
    // Ici je viens avec cette méthode lancer le jeu
    //--------------------------------------------------------------------------
    @FXML
    private void jButtonStart() {
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
            if (selectedCol == -1) {
                selectedCol = colIndex;
                selectedRow = rowIndex;
                gridPaneBoule.add(new ImageView(imageBoulesSelected.get(EZJeu.getXY(selectedRow, selectedCol))), selectedCol, selectedRow);

            } else {
                EZJeu.play(selectedRow, selectedCol, rowIndex, colIndex);
                selectedCol = -1;
                selectedRow = -1;
                affectBalls();
                scoreGame.setText(String.valueOf(EZJeu.getScore()));
            }
        } catch (Exception err) {
            //System.err.print("Pas une image !!!!!!!!\n");
        }
    }
}
