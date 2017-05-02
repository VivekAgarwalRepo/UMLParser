/**
 * Created by Vivek Agarwal on 5/1/2017.
 */
public class Test4 extends Test1{
     private int a;
     public int b;

    Test4(){
        a=0;
        b=0;
    }

    public void setA(int a){
        this.a=a;
    }

    public void setB(int b){
        this.b=b;
    }

    public int getA(){
        return a;
    }
    public int getB(){
        return b;
    }

    public int addition(){
        return a+b;
    }

    public int subtraction(){
        return a-b;
    }
}
