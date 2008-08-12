package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class MouseMotionHandler extends MouseMotionAdapter {

	private JPanel panel = null;

	public MouseMotionHandler(JPanel panel) {
		this.panel = panel;
	}

	public void mouseMoved(MouseEvent me) {

		/*panel.posicaoCursor.setLocation(me.getPoint());
		repaint();*/
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

}
