package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Hashtable;

public class Controller {

    public Label scoreGame;
    public Button startBouton;
    public GridPane gridPaneBoule;

    private ElementZ_Model EZJeu;
    private int selectedCol = -1;
    private int selectedRow = -1;
    private final String s = "img/boules/";
    private final int TOLERANCE_THRESHOLD = 0x1A;
    private Hashtable<Integer, Image> imageBoules = new Hashtable<>();
    private Hashtable<Integer, Image> imageBoulesHover = new Hashtable<>();
    private Hashtable<Integer, Image> imageBoulesSelected = new Hashtable<>();
    private static final String NORMAL_CLASS = "normal" ;
    private static final String TOP_CLASS = "top" ;
    private static final String BOTTOM_CLASS = "bottom" ;


    //--------------------------------------------------------------------------
    // Constructeur du controller
    //--------------------------------------------------------------------------
    public Controller() {
        try {
            for (int i = 1; i <= 6; i++) {
                Image image = new Image(s + "boule_" + i + ".jpg");
                image = makeTransparent(image);
                Image imageO = new Image(s + "boule_o_" + i + ".jpg");
                imageO = makeTransparent(imageO);
                Image imageS = new Image(s + "boule_s_" + i + ".jpg");
                imageS = makeTransparent(imageS);
                imageBoules.put(i, image);
                imageBoulesHover.put(i, imageO);
                imageBoulesSelected.put(i, imageS);
            }
        } catch (IllegalArgumentException e) {
            System.err.print("Impossible de charger les images\n");
            System.exit(-1);
        }
    }

    //--------------------------------------------------------------------------
    // J'utilise cette methode pour créer mes imagesView
    // Et ajouter les listeners
    //--------------------------------------------------------------------------
    private HBox createBalls(int id) {
        ImageView imageView = new ImageView(imageBoules.get(id));
        imageView.setOnMouseEntered(event -> imageView.setImage(imageBoulesHover.get(id)));
        imageView.setOnMouseExited(event -> imageView.setImage(imageBoules.get(id)));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(imageView);
        hBox.getStyleClass().add(NORMAL_CLASS);

        return hBox;
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

    private void refreshBalls(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Node node = getNodeByRowColumnIndex(i,j,gridPaneBoule);
                int id = EZJeu.getXY(i, j);
                gridPaneBoule.add(createBalls(id), j, i);
                gridPaneBoule.getChildren().remove(node);
            }
        }
    }

    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    //--------------------------------------------------------------------------
    // Ici je viens avec cette méthode lancer le jeu
    //--------------------------------------------------------------------------
    @FXML
    private void jButtonStart() {
        EZJeu = new ElementZ_Model();
        affectBalls();
        scoreGame.setText(String.valueOf(EZJeu.getScore()));

        gridPaneBoule.getRowConstraints().forEach((rowConstraints -> rowConstraints.setPercentHeight(12.5)));
        gridPaneBoule.getColumnConstraints().forEach((columnConstraints -> columnConstraints.setPercentWidth(12.5)));


    }

    //--------------------------------------------------------------------------
    // Ici je viens avec cette méthode selectionner ma boule
    //--------------------------------------------------------------------------
    @FXML
    private void gridPaneClick(MouseEvent e) {
        try {
            Node source = (Node) e.getTarget();
            source = source.getParent();
            Integer colIndex = GridPane.getColumnIndex(source);
            Integer rowIndex = GridPane.getRowIndex(source);
            if (selectedCol == -1) {
                selectedCol = colIndex;
                selectedRow = rowIndex;
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.getChildren().add(new ImageView(imageBoulesSelected.get(EZJeu.getXY(selectedRow, selectedCol))));
                hBox.getStyleClass().add(NORMAL_CLASS);
                gridPaneBoule.add(hBox, selectedCol, selectedRow);
            } else {
                EZJeu.play(selectedRow, selectedCol, rowIndex, colIndex);
                selectedCol = -1;
                selectedRow = -1;
                refreshBalls();
                scoreGame.setText(String.valueOf(EZJeu.getScore()));
            }
        } catch (Exception err) {
            System.err.println(err);
            //System.err.print("Pas une image !!!!!!!!\n");
        }
    }
}
