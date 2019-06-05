import java.util.ArrayList;
import java.util.Collections;

public class GKnapsack {
    static class Item{
        int value;
        int weight;
        float density;
        public Item(int v, int w){
            value=v;
            weight=w;
            density=(float)v/w;
        }

        @Override
        public String toString() {
            return "value is :"+value +"weight is : "+ weight;
        }
    }

//    static Item[] insertionSort(Item[]items){
//        for (int i=2;i<items.length;i++){
//            Item key=items[i];
//            int j=i-1;
//            while (j>0 && isGreater(items[j],key)){
//                items[j+1] = items[j];
//                j--;
//            }
//            items[j+1]=key;
//        }
//        return items;
//    }
public static void insertionSort(ArrayList<Item> arr) {
    int N = arr.size();

    for(int i = 1; i < N; i++) {
        int j = i - 1;
        Item temp = arr.get(i);
        while(j >= 0 && temp.density < arr.get(j).density) {
            arr.set(j+1,arr.get(j));
            j--;
        }
        arr.set(j+1, temp);
    }
    Collections.reverse(arr);
}

    public static int solve(int n,int w,ArrayList<Item> items){
        int curWeight=0;
        int result=0;
        for (int i=0;i<n;i++){
            if (curWeight+items.get(i).weight<=w){
                curWeight+=items.get(i).weight;
                result+=items.get(i).value;
                System.out.println("fully added : "+items.get(i));
            }
            else {
                System.out.println("fracted : "+items.get(i));
//                System.out.println("value"+items.get(i).value);
//                System.out.println("weight"+items.get(i).weight);
//                System.out.println("density is "+items.get(i).value/items.get(i).weight);
                int remain= w-curWeight;
//                System.out.println("remain is "+remain);
                result+=((items.get(i).value/items.get(i).weight)*remain);
                break;
            }
        }
    return result;
    }

    public static void main(String[] args) {
//        Item[]items=new Item[3];
        Item i1=new Item(50,5);
        Item i2=new Item(60,10);
        Item i3=new Item(140,20);
//        items[0]=i1;
//        items[1]=i3;
//        items[2]=i2;

        ArrayList<Item> items=new ArrayList<>();
        items.add(i1);
        items.add(i2);
        items.add(i3);
        System.out.println(items);
        insertionSort(items);
        System.out.println(items);
        System.out.println(solve(3,30,items));

    }
}
