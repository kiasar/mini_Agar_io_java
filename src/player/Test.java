//package player;
//
//import org.jetbrains.annotations.Contract;
//
///**
// * Java 1.4+ Compatible
// * The following example code demonstrates converting an Array of Strings to a LinkedList of Strings
// */
//public class Test {
//
//    public static void main(String[] args) {
//        ff i=ff.A;
//        System.out.println(i.getI());
//        i.setI(1);
//        System.out.println(i.getI());
//        ff j=ff.A;
//        j.setI(2);
//        System.out.println(i.getI()+" "+j.getI());
//    }
//}
//
//enum ff{
//    A(0) , b(4) , c(7);
//
//    public int i;
//
//    ff(int i) {
//        this.i = i;
//    }
//
//    @Contract(pure = true)
//    public int getI() {
//        if (this == b) return 8;
//        return i;
//    }
//
//    public void setI(int i) {
//        this.i = i;
//    }
//}