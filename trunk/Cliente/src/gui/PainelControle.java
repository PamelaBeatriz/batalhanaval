package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PainelControle extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton navio1 = null;
	private JButton navio2 = null;
	private JButton navio3 = null;
	private JButton navio4 = null;
	private JButton navio5 = null;
	private Vector<ImageIcon> shipImages = null;
	private ActionHandler actionHandler = null;
	private TabuleiroDoInimigo tabuleiroDoInimigo = null;

	protected static final String DIRETORIO_IMAGES = "src/images/";  //  @jve:decl-index=0:
	protected String lastShipName = null;
	protected Image imagemLastShip = null;
	protected int larguraLastShip = 0;
	protected int posicaoLastShip = -1;
	protected boolean verticalShip = false;

	/**
	 * This is the default constructor
	 */
	public PainelControle( /*TabuleiroDoInimigo tabuleiroDoInimigo */) {
		super();
		// this.tabuleiroDoInimigo = tabuleiroDoInimigo;
		this.shipImages = new Vector<ImageIcon>(5);
		this.actionHandler = new ActionHandler();
		this.inicializeImagesShip();
		initialize();

	}

	private void inicializeImagesShip() {
		for (int i = 0; i < this.shipImages.capacity(); i++) {
			this.shipImages.insertElementAt(new ImageIcon(
					PainelControle.DIRETORIO_IMAGES + "navio"
							+ String.valueOf(i + 1) + ".gif"), i);

		}
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(new Dimension(200, 250));
		this.setLayout(null);
		this.setBorder(BorderFactory.createTitledBorder(null, "Navios",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font(
						"Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		this.add(getNavio1(), null);
		this.add(getNavio2(), null);
		this.add(getNavio3(), null);
		this.add(getNavio4(), null);
		this.add(getNavio5(), null);
	}

	/**
	 * This method initializes navio1
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getNavio1() {
		if (navio1 == null) {
			navio1 = new JButton();
			navio1.setBounds(new Rectangle(15, 20, 180, 35));
			navio1.setText("");
			navio1.setToolTipText("O Triturador");
			navio1.addActionListener(actionHandler);
			navio1.setIcon(this.shipImages.get(0));
		}
		return navio1;
	}

	/**
	 * This method initializes navio2
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getNavio2() {
		if (navio2 == null) {
			navio2 = new JButton();
			navio2.setBounds(new Rectangle(15, 65, 180, 35));
			navio2.setText("");
			navio2.setToolTipText("ze Goiaba");
			navio2.addActionListener(this.actionHandler);
			navio2.setIcon(this.shipImages.get(1));
		}
		return navio2;
	}

	/**
	 * This method initializes navio3
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getNavio3() {
		if (navio3 == null) {
			navio3 = new JButton();
			navio3.setBounds(new Rectangle(15, 110, 180, 35));
			navio3.setText("");
			navio3.setToolTipText("Xanxe o fracote");
			navio3.addActionListener(this.actionHandler);
			navio3.setIcon(this.shipImages.get(2));
		}
		return navio3;
	}

	/**
	 * This method initializes navio4
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getNavio4() {
		if (navio4 == null) {
			navio4 = new JButton();
			navio4.setBounds(new Rectangle(15, 155, 180, 35));
			navio4.setText("");
			navio4.setToolTipText("Chris o Andrew");
			navio4.addActionListener(this.actionHandler);
			navio4.setIcon(this.shipImages.get(3));
		}
		return navio4;
	}

	/**
	 * This method initializes navio5
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getNavio5() {
		if (navio5 == null) {
			navio5 = new JButton();
			navio5.setBounds(new Rectangle(14, 200, 180, 35));
			navio5.setText("");
			navio5.setToolTipText("Ultima Esperança");
			navio5.addActionListener(this.actionHandler);
			navio5.setIcon(this.shipImages.get(4));
		}
		return navio5;
	}

	/**
	 * Desabilita o último navio selecionado, caso sua configuração ocorreu com
	 * sucesso. Serviço oferecido para a classe TabuleiroJogo (@see
	 * TabuleiroJogo.java).
	 */
	protected void desativarLastShipSelected() {

		if (this.posicaoLastShip == 0) {
			this.navio1.setEnabled(false);
		} else if (this.posicaoLastShip == 1) {
			this.navio2.setEnabled(false);
		} else if (this.posicaoLastShip == 2) {
			this.navio3.setEnabled(false);
		} else if (this.posicaoLastShip == 3) {
			this.navio4.setEnabled(false);
		} else if (this.posicaoLastShip == 4) {
			this.navio5.setEnabled(false);
		}
		repaint();

	}

	/**
	 * Handler de evento de clique sobre um botão.
	 */
	private class ActionHandler implements ActionListener {

		/**
		 * O método actionPerformed verifica uma ação sobre cada botão.
		 * Dependendo do botão selecionado, configura-se o nome, largura e
		 * posição do navio que este botão representa.
		 */
		public void actionPerformed(ActionEvent ae) {

			if (ae.getSource() == navio1) {
				lastShipName = "Navio1";
				imagemLastShip = (shipImages.get(0)).getImage();
				larguraLastShip = 50;
				posicaoLastShip = 0;
			} else if (ae.getSource() == navio2) {
				lastShipName = "Navio2";
				imagemLastShip = (shipImages.get(1)).getImage();
				larguraLastShip = 75;
				posicaoLastShip = 1;
			} else if (ae.getSource() == navio3) {
				lastShipName = "Navio3";
				imagemLastShip = (shipImages.get(2)).getImage();
				larguraLastShip = 100;
				posicaoLastShip = 2;
			} else if (ae.getSource() == navio4) {
				lastShipName = "Navio4";
				imagemLastShip = (shipImages.get(3)).getImage();
				larguraLastShip = 100;
				posicaoLastShip = 3;
			} else if (ae.getSource() == navio5) {
				lastShipName = "Navio5";
				imagemLastShip = (shipImages.get(4)).getImage();
				larguraLastShip = 125;
				posicaoLastShip = 4;
			}
		}
	}

	public TabuleiroDoInimigo getTabuleiroDoInimigo() {
		return tabuleiroDoInimigo;
	}

	public void setTabuleiroDoInimigo(TabuleiroDoInimigo tabuleiroDoInimigo) {
		this.tabuleiroDoInimigo = tabuleiroDoInimigo;
	}

}  //  @jve:decl-index=0:visual-constraint="6,10"
