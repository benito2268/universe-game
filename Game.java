/*** awt imports ***/
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * the main class for the universe game.
 * creates a new thread and provides a game loop, frame counter, code for key input,
 * as well as defines a set of coordinates for where we are in the universe
 * default window size is 1280x720 and gpu acceleration is enabled (openGL)
 * The screen is divided by default into 'sectors' of 16x16 pixels, these determine 
 * where the game objects are placed
 * 
 * @author ben staehle
 * @version 3/10/2022
 */
public class Game extends Canvas implements Runnable{
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private Thread t; //main will run in a seperate thread
    private boolean isRunning = false; //tells awt whenther or not the window should close
    private boolean needsToUpdate; //tells the engine that it needs to repopulate the game objects list because something about the game has changed
    private Handler h;

    // universe coordinates, as measured at the center of the screen
    // once Integer.MAX_VALUE or Integer.MIN_VALUE is reached the camera will 
    // just wrap around to the other side of the universe
    private Vector2n<Integer> universeOffset = new Vector2n<Integer>(0, 0);

    //the screen is divided into 16x16 'sectors'
    private Vector2n<Integer> numSectors = new Vector2n<Integer>(WIDTH / 16, HEIGHT / 16);

    /**
     * constructs a new Game and sets it's initial properties
     */
    public Game(){
        //enables gpu acceleration - will be added to future menu
        System.setProperty("sun.java2d.opengl", "true");
        new Window(WIDTH, HEIGHT, "Universe Game", this);
        addKeyListener(new KeyMapper()); 
        addMouseListener(new MouseMapper());
        //the game need to update once upon first load
        needsToUpdate = true;
        h = new Handler();
    }

    /**
     * code that is run when the JVM starts the game thread
     * any excpetion will cause the terminal to print an error message
     */
    public synchronized void start() {
        try{
            t = new Thread(this);
            t.start();
            isRunning = true;
        } catch(Exception e) {
            System.out.println("error:");
            System.out.println("    fatal: could not create thread \"main\"");
        }
    }

    /**
     * code that is run when the JVM stops the game thread
     * any excpetion will cause the terminal to print an error message
     */
    public synchronized void stop() {
        try{
            t.join();
            isRunning = false;
        } catch(Exception e) {
            System.out.println("error:");
            System.out.println("    fatal: crashed stopping thread \"main\"");
        }
    }

    /**
     * is called by the JVM after the game starts, this method contains the game loop
     * and frame counter
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double tps = 60.0;
        double ns = 1000000000 / tps;
        double delta = 0.0;
        long timer = System.currentTimeMillis();
        int numFrames = 0;
        
        while(isRunning) {
            long now = System.nanoTime();
            delta +=  (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(isRunning) {
                render();
            }
            numFrames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + numFrames);
                //System.out.println("fps: " + numFrames);
                //System.out.println("memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000);
                numFrames = 0;
            }
        }
        stop();
    } 

    /**
     * specifies what should happen when one in-game unit of time (tick) happens
     */
    private void tick() {
        h.tick();
    }

    /**
     * renders the background using a buffer, before checking whether the scene has changed
     * and updating accordingly. Will initiate the call to render each game object
     */
    public void render() {
        BufferStrategy b = this.getBufferStrategy();
        if(b == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics gfx = b.getDrawGraphics();
        gfx.setColor(Color.black);
        gfx.fillRect(0, 0, WIDTH, HEIGHT);

        if(needsToUpdate) {
            h.loadObjects(universeOffset, numSectors);
            needsToUpdate = false;
        }

        h.render(gfx);
        
        gfx.dispose();
        b.show();
    }

    public static void main(String[] args) {
        new Game();
    }
 
    /**
    * a key listener to send key press information to the handler
    */
   private class KeyMapper extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent k) {
            int kc = k.getKeyCode();
            switch(kc) {
                case KeyEvent.VK_W:
                    universeOffset.y -= 3;
                    break;
                case KeyEvent.VK_S:
                    universeOffset.y += 3;
                    break;
                case KeyEvent.VK_A:
                    universeOffset.x -= 3;
                    break;
                case KeyEvent.VK_D:
                    universeOffset.x += 3;
                    break;
                default:
                    //h.handleKeyPress(k);
                    break;
            }
            needsToUpdate = true;
        }

        @Override
        public void keyReleased(KeyEvent k) {
            //h.handleKeyRelease(k);
        }
   }

   private class MouseMapper extends MouseAdapter { 
       @Override
       public void mousePressed(MouseEvent e) {

       }

       @Override
       public void mouseReleased(MouseEvent e) {

       }
        
   }
}

