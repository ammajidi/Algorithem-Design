import java.math.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSP {

//    static int[][] path;
//    static int[][] d;
//    static int minlenght;
//
//    public void travel(int n, int [][] w){
//        int i,j,k;
//        d=new int[n][(int)Math.pow(2,n)];
//        for (i=2;i<=n;i++){
//            d[i][0]=w[i][0];
//        }
//    }

    private final int N, start;
    private final double[][] w;
    private List<Integer> tour = new ArrayList<>();
    private double minTourCost = Double.POSITIVE_INFINITY;
    private boolean ranSolver = false;

//    public TSP(double[][] w) {
//        this(0, w);
//    }

    public TSP(int start, double[][] w) {
        N = w.length;

        if (N <= 2) throw new IllegalStateException("N <= 2 not yet supported.");
        if (N != w[0].length) throw new IllegalStateException("Matrix must be square (n x n)");
        if (start < 0 || start >= N) throw new IllegalArgumentException("Invalid start node.");

        this.start = start;
        this.w = w;
    }

    // Returns the optimal tour for the traveling salesman problem.
    public List<Integer> getTour() {
        if (!ranSolver) solve();
        return tour;
    }

    // Returns the minimal tour cost.
    public double getTourCost() {
        if (!ranSolver) solve();
        return minTourCost;
    }

    // Solves the traveling salesman problem and caches solution.
    public void solve() {

        if (ranSolver) return;

        final int END_STATE = (1 << N) - 1;//2**n -1
        Double[][] d = new Double[N][1 << N];

        // Add all outgoing edges from the starting node to d table.
        for (int end = 0; end < N; end++) {
            if (end == start) continue;
            d[end][(1 << start) | (1 << end)] = w[start][end];
        }

        for (int r = 3; r <= N; r++) {
            for (int subset : combinations(r, N)) {
                if (notIn(start, subset)) continue;
                for (int next = 0; next < N; next++) {
                    if (next == start || notIn(next, subset)) continue;
                    int subsetWithoutNext = subset ^ (1 << next);
                    double minDist = Double.POSITIVE_INFINITY;
                    for (int end = 0; end < N; end++) {
                        if (end == start || end == next || notIn(end, subset)) continue;
                        double newDistance = d[end][subsetWithoutNext] + w[end][next];
                        if (newDistance < minDist) {
                            minDist = newDistance;
                        }
                    }
                    d[next][subset] = minDist;
                }
            }
        }

        // Connect tour back to starting node and minimize cost.
        for (int i = 0; i < N; i++) {
            if (i == start) continue;
            double tourCost = d[i][END_STATE] + w[i][start];
            if (tourCost < minTourCost) {
                minTourCost = tourCost;
            }
        }

        int lastIndex = start;
        int state = END_STATE;
        tour.add(start);

        // Reconstruct TSP path from d table.
        for (int i = 1; i < N; i++) {

            int index = -1;
            for (int j = 0; j < N; j++) {
                if (j == start || notIn(j, state)) continue;
                if (index == -1) index = j;
                double prevDist = d[index][state] + w[index][lastIndex];
                double newDist  = d[j][state] + w[j][lastIndex];
                if (newDist < prevDist) {
                    index = j;
                }
            }

            tour.add(index);
            state = state ^ (1 << index);
            lastIndex = index;
        }

        tour.add(start);
        Collections.reverse(tour);

        ranSolver = true;
    }

    private static boolean notIn(int elem, int subset) {
        return ((1 << elem) & subset) == 0;
    }

    // This method generates all bit sets of size n where r bits
    // are set to one. The result is returned as a list of integer masks.
    public static List<Integer> combinations(int r, int n) {
        List<Integer> subsets = new ArrayList<>();
        combinations(0, 0, r, n, subsets);
        return subsets;
    }

    // To find all the combinations of size r we need to recurse until we have
    // selected r elements (aka r = 0), otherwise if r != 0 then we still need to select
    // an element which is found after the position of our last selected element
    private static void combinations(int set, int at, int r, int n, List<Integer> subsets) {

        // Return early if there are more elements left to select than what is available.
        int elementsLeftToPick = n - at;
        if (elementsLeftToPick < r) return;

        // We selected 'r' elements so we found a valid subset!
        if (r == 0) {
            subsets.add(set);
        } else {
            for (int i = at; i < n; i++) {
                // Try including this element
                set |= 1 << i;

                combinations(set, i + 1, r - 1, n, subsets);

                // Backtrack and try the instance where we did not include this element
                set &= ~(1 << i);
            }
        }
    }

    public static void main(String[] args) {
        // Create adjacency matrix
        int n = 6;
        double[][] wMatrix = new double[n][n];
        for (double[] row : wMatrix) java.util.Arrays.fill(row, 10000);
        wMatrix[5][0] = 10;
        wMatrix[1][5] = 12;
        wMatrix[4][1] = 2;
        wMatrix[2][4] = 4;
        wMatrix[3][2] = 6;
        wMatrix[0][3] = 8;

        int startNode = 0;
        TSP solver = new TSP(startNode, wMatrix);

        // Prints: [0, 3, 2, 4, 1, 5, 0]
        System.out.println("Tour: " + solver.getTour());

        // Print: 42.0
        System.out.println("Tour cost: " + solver.getTourCost());
    }
}


