class Test{
    int a,b;
    
    public void setVaraibles(int x,int y){
        this.a=x;
        this.b=y;
    }
}

class B extends A{
    int x;
    B(){
        x=0;
    }
    public void getSum();
    public void display();
}