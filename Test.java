class Test{
    int a,b;
    Test(int x,int y){
        this.a=x;
        this.b=y;
    }

    public void getA(){
        return a;
    }
    public void getB(){
        return b;
    }

}

class B extends Test{
    int x;
    B(){
        x=0;
    }
    public void getSum(){}
    public void display(){}
}