/**
 * Created by Vivek Agarwal on 5/1/2017.
 */
public class Test3 {
    private int a=1;
    private int b;

    Integer myInt=new Integer(19);

    SampleClass sc;

    public int getA(){
        return a;
    }
    public int getB(){
        return b;
    }

    public void setA(int a){
        this.a=a;
    }

    public void setB(int b){
        this.b=b;
    }

    public double randomSomeValue(){//does not work for getRandomSomeValue
        return 0.0;
    }
}

class SampleClass{

}