import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class BlinkLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	private boolean isShowing = true;
	
	public BlinkLabel(){
		new Timer(500,new TimerListener(this)).start();
	}
	
	private class TimerListener implements ActionListener{
		
		private BlinkLabel lb;
		
		public TimerListener(BlinkLabel lb){
			this.lb = lb;	
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(Main.animating){
			lb.setVisible(isShowing);
			isShowing^=true;
			}
		}
	}

}
