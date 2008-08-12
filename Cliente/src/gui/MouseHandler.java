package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class MouseHandler extends MouseAdapter {

	private JPanel panel = null;

	public MouseHandler(JPanel panel){
		this.panel = panel;
	}


	public void mousePressed(MouseEvent me) {

		/*// Posiciona uma imagem no tabuleiro
		if (me.getButton() == me.BUTTON1) {

			if (areaCentral.nomeUltimoNavio == null
					&& areaCentral.posicaoUltimoNavio == -1) {
				JOptionPane
						.showMessageDialog(
								null,
								"SELECIONE UM NAVIO PARA POSICIONAR NO TABULEIRO.",
								"SELECIONE UM NAVIO",
								JOptionPane.INFORMATION_MESSAGE
																 * Icone vem
																 * aqui.
																 );
				return;
			}

			if (areaCentral != null) {

				if (areaCentral.verticalShip) {

					nomeDoNavio = areaCentral.nomeUltimoNavio + "v";
					larguraNavio = 25;
					alturaNavio = areaCentral.larguraUltimoNavio;

				} else {

					nomeDoNavio = areaCentral.nomeUltimoNavio;
					alturaNavio = 25;
					larguraNavio = areaCentral.larguraUltimoNavio;
				}

				configuraImagem(me.getX(), me.getY());
				areaCentral.verticalShip = false;
			}
			return;
		}

		// Altera o modo em que a imagem será colocada
		if (me.getButton() == me.BUTTON3) {

			areaCentral.verticalShip = !areaCentral.verticalShip;
			repaint();
		}*/

	}
}
