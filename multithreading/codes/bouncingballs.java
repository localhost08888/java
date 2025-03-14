import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

// Thread class to move a ball vertically within the panel
class BallThread extends Thread 
{
    private int x;            // Fixed horizontal position (centered)
    private int y;            // Current vertical position
    private final int diameter = 20;
    private int dy = 2;       // Vertical velocity
    private Color color;
    private BallPanel panel;
    
    public BallThread(BallPanel panel, int x, int y, Color color)
     {
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    @Override
    public void run()
     {
        while (true)
         {
            y += dy;
            if (y < 0)
             {
                y = 0;
                dy = -dy;
            }
            // Bounce off the bottom boundary
            if (y > panel.getHeight() - diameter) 
            {
                y = panel.getHeight() - diameter;
                dy = -dy;
            }
            panel.repaint();  // Request the panel to repaint with the new position
            try 
            {
                Thread.sleep(20);
            } 
            catch (InterruptedException e)
             {
                break;
            }
        }
    }
    
    // Getters for drawing the ball
    public int getX()
          { 
            return x;
         }
    public int getY() 
         { 
            return y;
        }
    public int getDiameter() 
    {
         return diameter; 
        }
    public Color getColor()
     {
         return color; 
    }
}

// Panel that keeps track of all balls and draws them
class BallPanel extends JPanel
 {
    private ArrayList<BallThread> balls = new ArrayList<>();
    
    public void addBall(BallThread ball)
     {
        balls.add(ball);
        ball.start();
    }
    
    @Override
    protected void paintComponent(Graphics g)
     {
        super.paintComponent(g);
        for (BallThread ball : balls)
         {
            g.setColor(ball.getColor());
            g.fillOval(ball.getX(), ball.getY(), ball.getDiameter(), ball.getDiameter());
        }
    }
}

// Main frame containing the panel and the Start button
public class bouncingballs extends JFrame
 {
    private BallPanel ballPanel;
    private JButton startButton;
    
    public bouncingballs()
     {
        setTitle("Vertical Moving Balls");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        ballPanel = new BallPanel();
        ballPanel.setBackground(Color.WHITE);
        add(ballPanel, BorderLayout.CENTER);
        
        startButton = new JButton("Start");
        add(startButton, BorderLayout.SOUTH);
        
        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
             {
                // Create a new ball with random vertical position and random color
                Random rand = new Random();
                int panelHeight = ballPanel.getHeight();
                int panelWidth = ballPanel.getWidth();
                // If the panel is not yet visible, use default sizes
                if (panelHeight <= 0)
                 { 
                    panelHeight = 400; 
                 }
                if (panelWidth <= 0) 
                {
                     panelWidth = 400;
                 }
                // For vertical movement, fix the x coordinate to the center
                int x = panelWidth / 2;
                // Random initial y position (ensuring the ball is fully within the panel)
                int y = rand.nextInt(panelHeight - 20);
                // Generate a random color
                Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                BallThread ball = new BallThread(ballPanel, x, y, color);
                ballPanel.addBall(ball);
            }
        });
    }
    
    public static void main(String[] args)
     {
        SwingUtilities.invokeLater(() ->
         {
            bouncingballs frame = new bouncingballs();
            frame.setVisible(true);
        });
    }
}
