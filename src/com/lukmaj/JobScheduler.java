package com.lukmaj;

import java.util.Timer;

public class JobScheduler {
    private Path path;
    private Markers markers;

    public JobScheduler(Path path, Markers markers) {
        this.path = path;
        this.markers = markers;
    }

    private Timer t = new Timer();
    private FileReader fileReader = new FileReader(path,markers);


    public void run(){
        t.scheduleAtFixedRate(fileReader,0,10000);
    }

}
