package com.lukmaj;

public class Path {
    private String path;

    public String getPath() {
        return path;
    }

    /**
     * <p> Method reads last used path from database </p>
     * @return true if path was read correctly
     */
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

    /**
     * <p> Method checks if path is not null</p>
     * @return true if path is not null, false if it's null
     */
    public boolean isTyped(){
        if(this.path!=null)
            return true;
        return false;
    }
}
