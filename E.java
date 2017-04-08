interface E{
    int getAttributes();
    void setAttributes();
    public void display();
}

class Secondary implements D{
    int a,b;

    public void setMember(){

    }
    public int getMember(){

    }
    Secondary(){
        a=0;
        b=0;
    }

    private int multiplication(){
        return a*b;
    }

    private int division(){
        return a/b;
    }
}