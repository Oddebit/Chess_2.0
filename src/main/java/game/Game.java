package game;

import input.Dispatcher;
import input.MouseInput;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private Thread thread;
    private boolean running;
    private final Dispatcher dispatcher;

    public Game() {
        dispatcher = new Dispatcher();
        new Window(dispatcher.getBoardDimension(), this);
        this.addMouseListener(new MouseInput(dispatcher));
        start();
    }

    public synchronized void start() {
        createBufferStrategy(3);
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(running) {
            BufferStrategy bufferStrategy = getBufferStrategy();
            Graphics graphics = getBufferStrategy().getDrawGraphics();
            dispatcher.setGraphics(graphics);
            dispatcher.render();
            graphics.dispose();
            bufferStrategy.show();
        }
    }
}
