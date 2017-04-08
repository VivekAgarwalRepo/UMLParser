interface E{
    int getAttributes();
    void setAttributes();
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

    int multiplication(){
        return a*b;
    }

    int division(){
        return a/b;
    }
}