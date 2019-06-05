import java.util.Arrays;
class Item{
    int weight;
    int profit;
    public Item(int weight,int profit){
        this.weight=weight;
        this.profit=profit;
    }
}


public class Main {

    static double Fractionalknapsack(int W,int n,Item[] items){
        double[] benefitRatio = new double[n];



        for(int i=0;i<=n-1;i++){

            if(items[i].weight !=0)
                benefitRatio[i]=items[i].profit/items[i].weight;
        }
        Arrays.sort(benefitRatio);
        int currentWeight=0;
        double output=0;
        double remainingWeight ;
        for(int i=0;i<=n-1;i++) {
            if (currentWeight + items[i].weight <= W) {
                currentWeight += items[i].weight;
                output += items[i].profit;

            } else {
                remainingWeight = W - currentWeight;
                //System.out.println(remainingWeight);
                output += (items[i].profit) * remainingWeight / items[i].weight;
            }
        }
        return output;


    }


    public static void main(String[] args) {

        Item[] items =new Item[]{new Item(10,60),new Item(20,100),new Item(30,120)};
        System.out.println(Fractionalknapsack(30,3,items));

        Item[] items1 =new Item[]{new Item(5,50),new Item(10,60),new Item(20,140)};
        System.out.println(Fractionalknapsack(30,3,items1));



    }
}