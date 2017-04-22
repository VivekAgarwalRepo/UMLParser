interface E{
    int getAttributes();
    void setAttributes();
    public void display();
}

class Secondary implements D,E{
    int a,b;
    Secondary(){
        a=0;
        b=0;
    }
    int getAttributes(){
    }

    void setAttributes(){

    }

    public void display(){

    }
    public void setMember(){

    }

    public void addition(){
        return a+b;
    }

    public void subtraction(){
        return a-b;
    }

    public int getMember(){

    }

    private int multiplication(){
        return a*b;
    }

    private int division(){
        return a/b;
    }
}