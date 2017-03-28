class Child implements D,E{
    int a,b;
    Child(){
        a=0;
        b=0;
    }
    private int getMembers(){
        return a+b;
    }
    private void setMembers(){
        a=-1;
        b=-1;
    }
    int getAttributes(){
        return a*b;
    }
    void setAttributes(){
        a=1;
        b=1;
    }

    private int addition(){
        return a+b;
    }
}