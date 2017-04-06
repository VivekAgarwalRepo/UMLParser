class Test2{
    Test2(){

    }
}

class B extends Test2,implements E{
    C c;
    B(C obj){
        this.c=C;
    }

    int getAttributes(){}
    void setAttributes(){}
}

class bbase extends B{
    
}