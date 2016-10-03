package sample.spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import org.apache.commons.io.input.*;

public class StatTailer {

    public Tailer tailer;
    
    public StatTailer() {
    }

    public void start() {

	class MyTailerListener extends TailerListenerAdapter {
	    public void handle(String line) {
		LoggerFactory.getLogger(this.getClass()).info(line);
	    }
	}

	File file = new File("./xx.txt");
	
	TailerListener listener = new MyTailerListener();
	tailer = new Tailer(file, listener, 1000L, true, true);
	Thread thread = new Thread(tailer);
	thread.setDaemon(true); // optional
	thread.start();
    }

    public void stop() {
	tailer.stop();
    }
}
 


 
