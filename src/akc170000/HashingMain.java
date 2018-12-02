package akc170000;

import rbk.Graph;

import java.util.HashSet;
import java.util.Random;
import rbk.Timer;


public class HashingMain {

    static int testHashing(Random r){
//        RobinHoodHashing<Integer> rh = new RobinHoodHashing<>();
        HashSet<Integer> rh = new HashSet<>();
//        DoubleHashing<Integer> rh = new DoubleHashing<>();

        long counter = 0;
        int sum = 0;
        int min = 1;
        int max = 1000;
        int a = 0,b = 0,c = 0;
        while (counter<10000000){
            int next = r.nextInt(max-min)+1;
            switch (r.nextInt(3)+1){
                case 1:
//                    System.out.println("1");
                    if(rh.add(next)){
                        sum+=next;
                        a++;
                    }
                    else sum+=1;
                    break;
                case 2:
//                    System.out.println("2");
                    if(rh.remove(next)){
                        sum-=next;
                        b++;
                    }
                    else sum-=1;
                    break;
//                case 3:
////                    System.out.println("3");
//                    if(rh.contains(next)){
//                        sum-=2;
//                        c++;
//                    }
//                    sum+=2;
//                    break;
            }
            counter++;
        }
        System.out.println("a b c"+a+" "+b+" "+c);
        return sum;
    }
    public static void main(String[] args){
        Random r = new Random();
        r.setSeed(1);
        Timer t = new Timer();
        t.start();
        System.out.println(testHashing(r));
        t.end();
        System.out.println(t);

//        RobinHoodHashing<Integer> rh = new RobinHoodHashing<>();
//        Character s;
//
//        Scanner in = new Scanner(System.in);
//        whileloop:
//        while(true){
//            s = in.next().charAt(0);
//            switch (s){
//                case 'a':
//                    rh.add(in.nextInt());
//                    break;
//                case 'c':
//                    System.out.println(rh.contains(in.nextInt()));
//                    break;
//                case 'r':
//                    System.out.println(rh.remove(in.nextInt()));
//                    break;
//                case 's':
//                    System.out.println(rh.size());
//                    break;
//                default:
//                    break whileloop;
//            }
//        }
    }
}
