package sample;

import java.util.Random;

public class ElementZ_Model {

    private static final int SIZE = 8;
    private static final int FAMILLE = 6;


    private int[][] matrix = new int[SIZE][SIZE];
    private boolean[][] matrixb = new boolean[SIZE][SIZE];
    private int score;


    public ElementZ_Model(){
        merge();
        prepar();
        score = 0;
    }

    public int getXY(int i, int j){
        return matrix[i][j];
    }

    public int getScore(){
        return score;
    }

    public void permut(int i, int j, int ip, int jp) {
        if( ((i== ip) && (j==(jp +1))) || ((i== ip) && (j==(jp - 1))) || ((j== jp) && (i==(ip +1))) || ((j== jp) && (i==(ip - 1)))) {
            int s = matrix[i][j];
            matrix[i][j] = matrix[ip][jp];
            matrix[ip][jp] = s;
        }
    }

    public void deleteTheSame() {
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                if ( matrixb[i][j])
                    matrix[i][j]=0;
            }
        }
        initMatrixb();
    }

    public void play(int x, int y, int xp, int yp) {
        permut(x,y,xp,yp);
        detectLinesAndCols();
        if (matrixb[x][y] || matrixb[xp][yp])
            prepar();
        else
            permut(x,y,xp,yp);
    }

    public void prepar() {
        while(notReadyToPlay()) {
            score += somme();
            deleteTheSame();
            gravity();
            fillGaps();
        }
    }

    public int somme() {
        int s = 0;
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                s+=(matrixb[i][j]?1:0);
            }
        }
        return s;
    }

    public void detectLine(int n) {
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<(SIZE - n+1); j++) {
                boolean lineOk = true;
                int first = matrix[i][j];
                int k = j;
                while (k<j+n && lineOk) {
                    if (matrix[i][k] != first && matrix[i][k]!=0)
                        lineOk = false;
                    k++;
                }
                if (lineOk) {
                    for (k=j; k<(j+n);k++)
                        matrixb[i][k]= true;
                    j+=n+1;
                }
            }
        }
    }

    public void detectLines() {
        for (int i=FAMILLE-1; i>2; i--) {
            detectLine(i);
        }
    }

    public void detectCol(int n) {
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<(SIZE - n+1); j++) {
                boolean lineOk = true;
                int first = matrix[j][i];
                int k = j;
                while (k<j+n && lineOk) {
                    if (matrix[k][i] != first && matrix[k][i]!=0)
                        lineOk = false;
                    k++;
                }
                if (lineOk) {
                    for (k=j; k<(j+n);k++)
                        matrixb[k][i]= true;
                    j+=n+1;
                }
            }
        }
    }

    public void detectCols() {
        for (int i=FAMILLE-1; i>2; i--) {
            detectCol(i);
        }
    }

    public void detectLinesAndCols() {
        for (int i=FAMILLE-1; i>2; i--) {
            detectLine(i);
            detectCol(i);
        }
    }

    public void gravity() {
        for(int j=0; j<SIZE; j++) {
            int k = SIZE-1;
            for(int i=SIZE-1; i>=0; i--)
                if (matrix[i][j] != 0) {
                    matrix[k][j] = matrix[i][j];
                    k--;
                }
            for(int i=k; i>=0; i--)
                matrix[i][j] = 0;
        }
    }

    public void merge(){
        Random x = new Random();
        for (int i=0; i<SIZE; i++)
            for(int j=0; j<SIZE; j++)
                matrix[i][j] = x.nextInt(FAMILLE)+1;
    }

    public void fillGaps() {
        Random x = new Random();
        for(int i=0; i<SIZE; i++)
            for(int j=0; j<SIZE; j++)
                if (matrix[i][j] == 0)
                    matrix[i][j] = x.nextInt(FAMILLE)+1;
    }

    public boolean notReadyToPlay() {
        detectLinesAndCols();

        for(int i=0; i<SIZE; i++)
            for(int j=0; j<SIZE; j++)
                if (matrixb[i][j])
                    return true;
        return false;
    }

    public void initMatrixb(){
        for (int i=0; i<SIZE; i++)
            for(int j=0; j<SIZE; j++)
                matrixb[i][j] = false;
    }

    public String toString() {
        String s = "";
        String s1;
        String s2;
        for(int i=0; i<SIZE; i++) {
            s1 = "";
            s2 = "";
            for(int j=0; j<SIZE; j++) {
                s1 += matrix[i][j] + " ";
                s2 += (matrixb[i][j]?1:0) + " ";
            }
            s += s1+"            "+s2+'\n';
        }
        return s;
    }

}
