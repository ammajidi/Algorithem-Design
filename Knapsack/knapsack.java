import static java.lang.Math.max;
public class knapsack {

    static int [] valueOf;
    static int [] weightOf;
    public static int solve(int n, int w){
        int i,k;
        int p[][] =new int[n+1][w+1];
        for (i = 0; i <= n; i++)
        {
            for (k = 0; k <= w; k++)
            {
                if (i==0 || k==0)
                    p[i][k] = 0;
                else if (weightOf[i-1] <= k)
                    p[i][k] = max(valueOf[i-1] + p[i-1][k-weightOf[i-1]],  p[i-1][k]);
                else
                    //System.out.println(p[i][k]);
                    p[i][k] = p[i-1][k];
            }
        }
        return p[n][w];
    }
    public static void main(String[] args) {
        valueOf = new int[]{50, 60, 140};
        weightOf= new int[]{5, 10, 20};
        System.out.println(solve(3,30));
    }
}
