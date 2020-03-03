package com.lukmaj;

import java.io.File;

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
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * <p> Method verifies and updated path</p>
     * @param path - String typed by user
     * @return true if added correectly
     */
    public boolean updatePath(String path){
        File f = new File(path);
        if(f.exists() && f.isDirectory()){
            this.setPath(path);
            this.addSlash();
            this.writePath();
            return true;
        } else return false;
    }

    /**
     * <p>Method writes path to the database</p>
     */
    public void writePath() {
        DbConnect dbConnect = new DbConnect();
        dbConnect.connect();
        dbConnect.setPath(this.path);
        dbConnect.close();
    }

    /**
     * <p> Method doubles slashes in string for proper input in database</p>
     */
    private void addSlash(){
        String path = getPath();
        for (int i=0;i<path.length();i++){
            if(path.charAt(i) == ((char)92) && path.charAt(i+1)!= ((char)92) && path.charAt(i-1)!=(char)92){
                setPath(path.substring(0, i) + ((char)92) + path.substring(i,path.length()));
                path=getPath();
            }

        }
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

