class Test{
    int a,b;

    public void getVariables();
    public void setVaraibles(int x,int y){
        this.a=x;
        this.b=y;
    }
}

class B extends A{
    int x,y;
    B(){
        x=0;
        y=0;
    }
    public void getSum();
    public void display();
}