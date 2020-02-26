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
                }
            }else{
                this.path = dbConnect.getPath();
            }

            if (this.path != null){
                dbConnect.close();
                return true;
            }
            dbConnect.close();
            return false;

        }

    /**
     * <p> Method saves new path in Database</p>
     * @param path - String path - this value will be saved as new path in Database
     */
    public void setPath(String path){
        DbConnect dbConnect = new DbConnect();
        dbConnect.connect();
        dbConnect.setPath(path);
        dbConnect.close();
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
