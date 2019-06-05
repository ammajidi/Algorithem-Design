import java.util.ArrayList;


public class TSP {

    private ArrayList<Integer> outputArray = new ArrayList<Integer>();
    private int g[][], p[][], npow, N, d[][];


    public TSP() {     }

    public ArrayList<Integer> computeTSP(int[][] inputArray, int n) {



        N = n;
        npow = (int) Math.pow(2, n);
        g = new int[n][npow];
        p = new int[n][npow];
        d = inputArray;

        int i, j, k, l, m, s;

        for (i = 0; i < n; i++) {
            for (j = 0; j < npow; j++) {
                g[i][j] = -1;
                p[i][j] = -1;
            }
        }

        //initialize based on distance matrix
        for (i = 0; i < n; i++) {
            g[i][0] = inputArray[i][0];
        }


        int result = tsp(0, npow - 2);
        System.out.println("result is :"+result);
        outputArray.add(0);
        getPath(0, npow - 2);
        outputArray.add(0);


        return outputArray;     }

    private int tsp(int start, int set) {
        int masked, mask, result = -1, temp;

        if (g[start][set] != -1) {
            return g[start][set];
        } else {
            for (int x = 0; x < N; x++) {
                mask = npow - 1 - (int) Math.pow(2, x);
                masked = set & mask;
                if (masked != set) {
                    temp = d[start][x] + tsp(x, masked);
                    if (result == -1 || result > temp) {
                        result = temp;
                        p[start][set] = x;
                    }
                }
            }
            g[start][set] = result;
            return result;
        }
    }

    private void getPath(int start, int set) {
        if (p[start][set] == -1) {
            return;
        }

        int x = p[start][set];
        int mask = npow - 1 - (int) Math.pow(2, x);
        int masked = set & mask;

        outputArray.add(x);
        getPath(x, masked);
    }





    public static void main(String[] args) {
        // Create adjacency matrix
        int n = 4;
        int[][] wMatrix = new int[n][n];
        for (int[] row : wMatrix) java.util.Arrays.fill(row, 10000);
//        wMatrix[5][0] = 10;
//        wMatrix[1][5] = 12;
//        wMatrix[4][1] = 2;
//        wMatrix[2][4] = 4;
//        wMatrix[3][2] = 6;
//        wMatrix[0][3] = 8;
        wMatrix[0][1] = 2;
        wMatrix[0][2] = 9;
        wMatrix[1][0] = 1;
        wMatrix[1][2] = 6;
        wMatrix[1][3] = 4;
        wMatrix[2][1] = 7;
        wMatrix[2][3] = 8;
        wMatrix[3][1] = 3;
        wMatrix[3][0] = 6;
        TSP t=new TSP();
        ArrayList<Integer> path=t.computeTSP(wMatrix,n);
        int c=1;
        for (Integer i :path){
            System.out.print(i);
            if (c<path.size())
                System.out.print(" -> ");
            c++;

        }


        // Prints: [0, 3, 2, 4, 1, 5, 0]


        // Print: 42.0

    }

}
