package com.lukmaj;

public class Path {
    private String path;

    public String getPath() {
        return path;
    }
    public boolean readPath() {
            DbConnect dbConnect = new DbConnect();
            dbConnect.connect();
            String tempPath = dbConnect.getPath();
            if(isTyped()) {
                if (!this.path.equals(tempPath)) {
                    this.path = dbConnect.getPath();
                    System.out.println(this.path);
                }
            }else{
                this.path = dbConnect.getPath();
            }

            if (this.path != null){
                return true;
            }
            return false;

        }

    public boolean isTyped(){
        if(this.path!=null)
            return true;
        return false;
    }
}
