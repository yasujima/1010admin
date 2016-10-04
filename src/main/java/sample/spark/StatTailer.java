package sample.spark;

import java.io.File;
import java.util.TimerTask;
import java.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.input.*;

public class StatTailer
{
    public Tailer tailer;
    public ServerInitializer server;
    public Thread tailerThread;
    public Timer timer = new Timer();
    public File currentFile;

    public StatTailer(ServerInitializer s) {
	server = s;
	timer.schedule(new Check(this), 1000, 1000);
    }

    /**
     * update target file and renewly start tailer thread.
     */
    protected void update() {
	File latest = findLatest();
	if (currentFile == null || !(currentFile.equals(latest))) {
	    start(latest);
	}
    }

    /**
     * start tailing
     */
    protected void start(File file) {
	LoggerFactory.getLogger(this.getClass()).info("XXXX stattaler.start");
	if (file == null) {
	    file = findLatest();
	}
	this.currentFile = file;
	TailerListener listener = new MyTailerListener(this);
	tailer = new Tailer(file, listener, 1000L, true, true);
	tailerThread = new Thread(tailer);
	tailerThread.setDaemon(true);
	tailerThread.start();
    }

    /**
     * stop tailer
     */
    protected void stop() {
	tailer.stop();
	tailerThread.interrupt();
    }

    /**
     * get latest file in current director
     */
    private static File findLatest() {
	File dir = new File("./");
	File[] files = dir.listFiles();  // (a)
	File latest = null;
	long lastmod = 0;
	for (File f : files) {
	    if (f.isFile()){  // (c)
		long mod = f.lastModified();
		if (mod > lastmod) {
		    lastmod = mod;
		    latest = f;
		}
	    }
	}
	LoggerFactory.getLogger("StatTailer").info("FIND LATEST >>>>> " + latest);
	return latest;
    }

    // inner class
    class MyTailerListener extends TailerListenerAdapter
    {
	public StatTailer parent;

	public MyTailerListener(StatTailer tailer) {
	    parent = tailer;
	}

	@Override
	public void handle(String line) {
	    server.updateStat(line);
	}

	@Override
	public void handle(Exception e) {
	    LoggerFactory.getLogger(this.getClass()).info("handle exception " + e.getMessage());
	    restart();
	}

	@Override
	public void fileNotFound() {
	    LoggerFactory.getLogger(this.getClass()).info("file not found");
	    restart();
	}

	/**
	 * restart the stattailer
	 */
	@Override
	public void fileRotated() {
	    LoggerFactory.getLogger(this.getClass()).info("file rotated");
	}

	/**
	 * restart the stattailer
	 */
	private void restart() {
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		LoggerFactory.getLogger(this.getClass()).info("exception " + e.getMessage());
	    }
	    parent.stop();
	    parent.start(null);

	}
    }
}

class Check extends TimerTask
{
    public StatTailer tailer;
    public Check(StatTailer t) {
	tailer = t;
    }

    @Override
    public void run() {
	tailer.update();
    }
}
