package com.lukmaj;

import java.util.Timer;

public class JobScheduler {
    private Path path;
    private Markers markers;
    private DBManager dbManager;

    public JobScheduler(Path path, DBManager dbManager) {
        this.path = path;
        this.dbManager = dbManager;
    }

    private Timer t = new Timer();
    private FileReader fileReader = new FileReader(path,dbManager);


    public void run(){
        t.scheduleAtFixedRate(fileReader,0,10000);
    }

}
