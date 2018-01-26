import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;


@SuppressWarnings("serial")
public class Main extends JFrame {
	
	// Set the dimensions of the frame
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth(), HEIGHT = (int) screenSize.getHeight();
	
	// Set the parameters for the slider
	public static final int SLIDER_INIT = 250, SLIDER_MAX = 2 * SLIDER_INIT;
	private JSlider angleSlider;
	
	// Set the parameters for the tree
	private double rotAngle = Math.PI/2;
	private boolean movingRight = true;
	public static boolean animating = true;
	
	public Main(){
		
		//Setting up JFrame
		super("Fractal Tree");
		setVisible(true);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//Setting up the container
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		
		
		//Create nested class of DrawCanvas
		class DrawCanvas extends JPanel{
			
			public static final double INITIAL_LENGTH = 190;
			
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				setBackground(new Color(45,45,45));
				
				// Draw the tree
				g.setColor(Color.WHITE);
				Graphics2D g2d = (Graphics2D) g;
				drawBranch(g2d,cp.getWidth()/2.0,(double)cp.getHeight(), Math.PI/2, INITIAL_LENGTH, 20);
			}

			private void drawBranch(Graphics2D g2d, double x, double y, double angle,double length, float stroke) {
				g2d.setStroke(new BasicStroke(stroke));
				g2d.draw(new Line2D.Double(x, y, x+length*Math.cos(angle), y-length*Math.sin(angle)));
				
				// Stopping condition for the recursion, there is no need to keep going infinetly
				if(length > 4){
					drawBranch(g2d, x+length*Math.cos(angle), y-length*Math.sin(angle),angle-rotAngle*0.3, length*0.70, stroke*0.80f);
					//drawBranch(g2d, x+length*Math.cos(angle), y-length*Math.sin(angle),angle-rotAngle, length*0.80, stroke*0.80f);
					drawBranch(g2d, x+length*Math.cos(angle), y-length*Math.sin(angle),angle+rotAngle, length*0.80, stroke*0.80f);
					//drawBranch(g2d, x+length*Math.cos(angle), y-length*Math.sin(angle),angle+(Math.PI/2-rotAngle), length*0.70, stroke*0.80f);

				}
			}
		}	
		
		
		 
		//Creating the canvas
		DrawCanvas canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(cp.getWidth(),cp.getHeight()));
		cp.add(canvas,BorderLayout.CENTER);
		canvas.setLayout(null);
		
		 
		
		//Setting the JSlider
		angleSlider = new JSlider(0,SLIDER_MAX,SLIDER_INIT);
		angleSlider.setPaintTicks(true);
		angleSlider.setMajorTickSpacing(SLIDER_INIT/10);
		angleSlider.setMinorTickSpacing(SLIDER_MAX/10);
		angleSlider.setBounds(10, 10, 200, 50);
		canvas.add(angleSlider);
		angleSlider.setOpaque(false);
		angleSlider.setVisible(true);
		
		
		//Setting up the action listeners
		
		// Lets the user select the angle between each branch
		angleSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				rotAngle = angleSlider.getValue() * (Math.PI)/(SLIDER_MAX);
				canvas.repaint();
			}
		});
		
		// When the user clicks on the screen, the animation stops
		addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				animating = !animating;
			}		
		});
		
		// Timer to keep the animation going
		ActionListener updateTask = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(animating){
				if(movingRight){
					angleSlider.setValue(angleSlider.getValue()+1);
				}
				else angleSlider.setValue(angleSlider.getValue()-1);
				}

					if(angleSlider.getValue() == SLIDER_MAX || angleSlider.getValue() == 0)
						movingRight = !movingRight;
			}
			
		};
		
		// Event is triggered every 10ms
		new Timer(10,updateTask).start();
	}
	
	// Main call to the frame
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new Main();
			}
			
		});
	}
}
