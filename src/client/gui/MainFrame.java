/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on Apr 22, 2010, 3:43:50 PM
 */

package client.gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import client.controller.ClientConnect;
import client.controller.ClientController;
import client.controller.GUIController;
import client.model.Board;
import client.protocol.C11SendMessage;
import client.protocol.C4SendReady;
import client.protocol.C6Forfeit;
import client.protocol.C7Stalemate;
import javazoom.jl.player.Player;

import javax.swing.*;

public class MainFrame extends javax.swing.JFrame {

	private javax.swing.JMenuItem battleloc;
	private Board board;
	private javax.swing.JLabel bomb1;
	private javax.swing.JLabel bomb2;
	private javax.swing.JPanel bombPanel;
	private javax.swing.JLabel capt1;
	private javax.swing.JLabel capt2;
	private javax.swing.JPanel captPanel;
	private javax.swing.JTextArea chatBox;
	private javax.swing.JPanel colPanel;
	private javax.swing.JLabel colonel1;
	private javax.swing.JLabel colonel2;
	private javax.swing.JMenuItem connect;
	private javax.swing.JMenuItem disconnect;
	private javax.swing.JButton draw;
	private javax.swing.JLabel drawLabel;
	private javax.swing.JLabel drawnum;
	private javax.swing.JMenuItem exit;
	private javax.swing.JLabel flag1;
	private javax.swing.JLabel flag2;
	private javax.swing.JPanel flagPanel;
	private javax.swing.JButton forfeit;
	private javax.swing.JMenu game;
	private javax.swing.JPanel genPanel;
	private javax.swing.JLabel general1;
	private javax.swing.JLabel general2;
	private javax.swing.JMenu help;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JPopupMenu.Separator jSeparator1;
	private javax.swing.JLabel lieut1;
	private javax.swing.JLabel lieut2;
	private javax.swing.JPanel lieutPanel;
	private javax.swing.JMenuItem smusic;
	private javax.swing.JPanel majPanel;
	private javax.swing.JLabel major1;
	private javax.swing.JLabel major2;
	private javax.swing.JPanel marPanel;
	private javax.swing.JLabel marshall1;
	private javax.swing.JLabel marshall2;
	private javax.swing.JMenu menu;
	private javax.swing.JPanel minPanel;
	private javax.swing.JLabel minor1;
	private javax.swing.JLabel minor2;
	private javax.swing.JToggleButton p1Ready;
	private javax.swing.JLabel p1label;
	private javax.swing.JLabel p1left;
	private javax.swing.JToggleButton p2Ready;
	private javax.swing.JLabel p2label;
	private javax.swing.JLabel p2left;
	private javax.swing.JLabel pieceLabel;
	private javax.swing.JLabel remLabel;
	private javax.swing.JLabel sarge1;
	private javax.swing.JLabel sarge2;
	private javax.swing.JPanel sargePanel;
	private javax.swing.JMenuItem change;
	private javax.swing.JLabel sct1;
	private javax.swing.JLabel sct2;
	private javax.swing.JPanel sctPanel;
	private javax.swing.JButton sendButton;
	private javax.swing.JLabel spy1;
	private javax.swing.JLabel spy2;
	private javax.swing.JPanel spyPanel;
	private javax.swing.JMenuItem tutorial;
	private javax.swing.JTextPane typeBox;


	private TimeCapsule tc;
	private ClientController cc;
	private GUIController gc;
	private ClientConnect con;
	private Player player;
	private int music=0;
	private URL path = ClassLoader.getSystemResource("resources/music/halo.mp3");

	/** Creates new form MainFrame */
	public MainFrame() {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		cc=new ClientController((int)Math.round(Math.random()));
		gc = new GUIController(this);

		initComponents();
		updateLabels();
		setLocation(200, 50);	

		tc = new TimeCapsule(this);
		tc.setLocation(1100, 50);
		tc.setVisible(false);
	}

	public void initBottomPanel(){
		Color bg = this.getBackground();
		String color = "Blue/B";
		if(cc.getPlayer()==1)
			color = "Red/R";

		flagPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Flag" + color + "flag.gif"),"figure"))).setBounds(0,0,60,60);
		flagPanel.setBackground(bg);

		bombPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/bomb.png"),"figure"))).setBounds(0,0,60,60);
		bombPanel.setBackground(bg);

		spyPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Spy/SpyF1.png"),"figure"))).setBounds(0,0,60,60);
		spyPanel.setBackground(bg);

		marPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Marshal"+color+"marshalF1.png"),"figure"))).setBounds(0,0,60,60);
		marPanel.setBackground(bg);

		genPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/General"+color+"generalF1.png"),"figure"))).setBounds(0,0,60,60);
		genPanel.setBackground(bg);

		colPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Colonel"+color+"colonelF1.png"),"figure"))).setBounds(0,0,60,60);
		colPanel.setBackground(bg);

		majPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Major"+color+"majorF1.png"),"figure"))).setBounds(0,0,60,60);
		majPanel.setBackground(bg);

		captPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Captain"+color+"captainF1.png"),"figure"))).setBounds(0,0,60,60);
		captPanel.setBackground(bg);

		lieutPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Lieutenant"+color+"lieutenantF1.png"),"figure"))).setBounds(0,0,60,60);
		lieutPanel.setBackground(bg);

		sargePanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Sargeant"+color+"sargeantF1.png"),"figure"))).setBounds(0,0,60,60);
		sargePanel.setBackground(bg);

		minPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Miner"+color+"minerF1.png"),"figure"))).setBounds(0,0,60,60);
		minPanel.setBackground(bg);

		sctPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/sprites/Scout"+color+"scoutF1.png"),"figure"))).setBounds(0,0,60,60);
		sctPanel.setBackground(bg);

	}

	public ClientConnect getClientConnect()
	{
		return con;
	}


	private void initComponents() {

		battleloc = new javax.swing.JMenuItem();
		board = new Board(this);
		p1label = new javax.swing.JLabel();
		p2label = new javax.swing.JLabel();
		p1Ready = new javax.swing.JToggleButton();
		p2Ready = new javax.swing.JToggleButton();
		forfeit = new javax.swing.JButton();
		draw = new javax.swing.JButton();
		drawLabel = new javax.swing.JLabel();
		drawnum = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		chatBox = new javax.swing.JTextArea();
		sendButton = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		typeBox = new javax.swing.JTextPane();
		genPanel = new javax.swing.JPanel();
		bombPanel = new javax.swing.JPanel();
		flagPanel = new javax.swing.JPanel();
		marPanel = new javax.swing.JPanel();
		spyPanel = new javax.swing.JPanel();
		majPanel = new javax.swing.JPanel();
		colPanel = new javax.swing.JPanel();
		captPanel = new javax.swing.JPanel();
		lieutPanel = new javax.swing.JPanel();
		sargePanel = new javax.swing.JPanel();
		minPanel = new javax.swing.JPanel();
		sctPanel = new javax.swing.JPanel();
		p1left = new javax.swing.JLabel();
		p2left = new javax.swing.JLabel();
		pieceLabel = new javax.swing.JLabel();
		flag1 = new javax.swing.JLabel();
		bomb1 = new javax.swing.JLabel();
		spy1 = new javax.swing.JLabel();
		marshall1 = new javax.swing.JLabel();
		general1 = new javax.swing.JLabel();
		colonel1 = new javax.swing.JLabel();
		major1 = new javax.swing.JLabel();
		capt1 = new javax.swing.JLabel();
		lieut1 = new javax.swing.JLabel();
		sarge1 = new javax.swing.JLabel();
		flag2 = new javax.swing.JLabel();
		bomb2 = new javax.swing.JLabel();
		spy2 = new javax.swing.JLabel();
		marshall2 = new javax.swing.JLabel();
		general2 = new javax.swing.JLabel();
		colonel2 = new javax.swing.JLabel();
		major2 = new javax.swing.JLabel();
		capt2 = new javax.swing.JLabel();
		lieut2 = new javax.swing.JLabel();
		sarge2 = new javax.swing.JLabel();
		minor1 = new javax.swing.JLabel();
		sct1 = new javax.swing.JLabel();
		minor2 = new javax.swing.JLabel();
		sct2 = new javax.swing.JLabel();
		remLabel = new javax.swing.JLabel();
		jMenuBar1 = new javax.swing.JMenuBar();
		menu = new javax.swing.JMenu();
		connect = new javax.swing.JMenuItem();
		disconnect = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JPopupMenu.Separator();
		exit = new javax.swing.JMenuItem();
		game = new javax.swing.JMenu();
		smusic = new javax.swing.JMenuItem();
		change = new javax.swing.JMenuItem();
		help = new javax.swing.JMenu();
		tutorial = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Stratego");
		setMinimumSize(new java.awt.Dimension(850, 725));
		setResizable(false);

		p1label.setText("Player 1");

		p2label.setText("Player 2");

		p1Ready.setText("Ready");
		p1Ready.setFocusPainted(false);
		p1Ready.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				p1ReadyActionPerformed(evt);
			}
		});

		p2Ready.setText("Ready");
		p2Ready.setFocusPainted(false);
		p2Ready.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				p2ReadyActionPerformed(evt);
			}
		});

		forfeit.setText("Forfeit");
		forfeit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				forfeitActionPerformed(evt);
			}
		});

		draw.setText("Draw");
		draw.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				drawActionPerformed(evt);
			}
		});

		drawLabel.setText("Draw Requests Left: ");

		drawnum.setText("5");

		chatBox.setColumns(20);
		chatBox.setEditable(false);
		chatBox.setFont(new java.awt.Font("Tahoma", 0, 13));
		chatBox.setLineWrap(true);
		chatBox.setRows(5);
		chatBox.setWrapStyleWord(true);
		jScrollPane1.setViewportView(chatBox);

		sendButton.setText("Send");
		sendButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sendButtonActionPerformed(evt);
			}
		});

		typeBox.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				typeBoxKeyPressed(evt);
			}
			public void keyReleased(java.awt.event.KeyEvent evt) {
				typeBoxKeyReleased(evt);
			}
		});
		jScrollPane2.setViewportView(typeBox);

		genPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		genPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		genPanel.setPreferredSize(new java.awt.Dimension(60, 60));
		genPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout genPanelLayout = new javax.swing.GroupLayout(genPanel);
		genPanel.setLayout(genPanelLayout);
		genPanelLayout.setHorizontalGroup(
				genPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		genPanelLayout.setVerticalGroup(
				genPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		bombPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		bombPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		bombPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout bombPanelLayout = new javax.swing.GroupLayout(bombPanel);
		bombPanel.setLayout(bombPanelLayout);
		bombPanelLayout.setHorizontalGroup(
				bombPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		bombPanelLayout.setVerticalGroup(
				bombPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		flagPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		flagPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		flagPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout flagPanelLayout = new javax.swing.GroupLayout(flagPanel);
		flagPanel.setLayout(flagPanelLayout);
		flagPanelLayout.setHorizontalGroup(
				flagPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		flagPanelLayout.setVerticalGroup(
				flagPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		marPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		marPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		marPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout marPanelLayout = new javax.swing.GroupLayout(marPanel);
		marPanel.setLayout(marPanelLayout);
		marPanelLayout.setHorizontalGroup(
				marPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		marPanelLayout.setVerticalGroup(
				marPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		spyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		spyPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		spyPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout spyPanelLayout = new javax.swing.GroupLayout(spyPanel);
		spyPanel.setLayout(spyPanelLayout);
		spyPanelLayout.setHorizontalGroup(
				spyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		spyPanelLayout.setVerticalGroup(
				spyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		majPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		majPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		majPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout majPanelLayout = new javax.swing.GroupLayout(majPanel);
		majPanel.setLayout(majPanelLayout);
		majPanelLayout.setHorizontalGroup(
				majPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		majPanelLayout.setVerticalGroup(
				majPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		colPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		colPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		colPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout colPanelLayout = new javax.swing.GroupLayout(colPanel);
		colPanel.setLayout(colPanelLayout);
		colPanelLayout.setHorizontalGroup(
				colPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		colPanelLayout.setVerticalGroup(
				colPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		captPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		captPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		captPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout captPanelLayout = new javax.swing.GroupLayout(captPanel);
		captPanel.setLayout(captPanelLayout);
		captPanelLayout.setHorizontalGroup(
				captPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		captPanelLayout.setVerticalGroup(
				captPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		lieutPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		lieutPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		lieutPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout lieutPanelLayout = new javax.swing.GroupLayout(lieutPanel);
		lieutPanel.setLayout(lieutPanelLayout);
		lieutPanelLayout.setHorizontalGroup(
				lieutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		lieutPanelLayout.setVerticalGroup(
				lieutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		sargePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		sargePanel.setMinimumSize(new java.awt.Dimension(60, 60));
		sargePanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout sargePanelLayout = new javax.swing.GroupLayout(sargePanel);
		sargePanel.setLayout(sargePanelLayout);
		sargePanelLayout.setHorizontalGroup(
				sargePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		sargePanelLayout.setVerticalGroup(
				sargePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		minPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		minPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		minPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout minPanelLayout = new javax.swing.GroupLayout(minPanel);
		minPanel.setLayout(minPanelLayout);
		minPanelLayout.setHorizontalGroup(
				minPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		minPanelLayout.setVerticalGroup(
				minPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		sctPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		sctPanel.setMinimumSize(new java.awt.Dimension(60, 60));
		sctPanel.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				panelMouseReleased(arg0);
			}
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}      	
		});

		javax.swing.GroupLayout sctPanelLayout = new javax.swing.GroupLayout(sctPanel);
		sctPanel.setLayout(sctPanelLayout);
		sctPanelLayout.setHorizontalGroup(
				sctPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);
		sctPanelLayout.setVerticalGroup(
				sctPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
		);

		p1left.setText("Player 1");

		p2left.setText("Player 2");

		pieceLabel.setText("Pieces");

		remLabel.setText("Remaining");

		menu.setText("Menu");

		connect.setText("Connect");
		menu.add(connect);
		connect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				connectActionPerformed(evt);
			}
		});

		disconnect.setText("Disconnect");
		menu.add(disconnect);
		disconnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				disconnectActionPerformed(evt);
			}
		});
		menu.add(jSeparator1);

		exit.setText("Exit");
		exit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitActionPerformed(evt);
			}
		});
		menu.add(exit);

		jMenuBar1.add(menu);

		game.setText("Game");

		smusic.setText("Stop Music");
		smusic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				smusicActionPerformed(evt);
			}
		});
		game.add(smusic);

		change.setText("Change Music");
		change.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changeActionPerformed(evt);
			}
		});
		game.add(change);

		battleloc.setText("Change Battle Location");
		battleloc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				battlelocActionPerformed(evt);
			}
		});
		game.add(battleloc);

		jMenuBar1.add(game);

		help.setText("Help");

		tutorial.setText("Tutorial");
		help.add(tutorial);

		jMenuBar1.add(help);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
										.addGap(44, 44, 44)
										.addComponent(p1label)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
										.addComponent(p2label)
										.addGap(49, 49, 49))
										.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
												.addGap(28, 28, 28)
												.addComponent(p1Ready, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
												.addComponent(p2Ready, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(30, 30, 30))
												.addGroup(layout.createSequentialGroup()
														.addGap(85, 85, 85)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(draw, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
																.addComponent(forfeit, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
																.addGap(85, 85, 85))
																.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
																		.addGap(51, 51, 51)
																		.addComponent(drawLabel)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(drawnum)
																		.addGap(53, 53, 53))
																		.addGroup(layout.createSequentialGroup()
																				.addGap(18, 18, 18)
																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
																						.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
																						.addContainerGap())
																						.addGroup(layout.createSequentialGroup()
																								.addComponent(sendButton)
																								.addContainerGap())))
																								.addGroup(layout.createSequentialGroup()
																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																												.addGroup(layout.createSequentialGroup()
																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																.addGroup(layout.createSequentialGroup()
																																		.addContainerGap()
																																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																				.addComponent(p2left)
																																				.addComponent(remLabel))
																																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE))
																																				.addGroup(layout.createSequentialGroup()
																																						.addContainerGap()
																																						.addComponent(pieceLabel)
																																						.addGap(42, 42, 42))) 
																																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																								.addComponent(flag2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																								.addComponent(flagPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
																																								.addGroup(layout.createSequentialGroup()
																																										.addContainerGap()
																																										.addComponent(p1left)
																																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
																																										.addComponent(flag1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
																																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																												.addComponent(bombPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																												.addComponent(bomb2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																												.addComponent(bomb1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
																																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																														.addComponent(spyPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																														.addComponent(spy2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																														.addComponent(spy1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
																																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																																																		.addComponent(marshall1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																		.addGap(31, 31, 31)
																																																		.addComponent(general1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																		.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																																																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																																						.addComponent(marshall2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																						.addComponent(marPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																						.addComponent(genPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																						.addComponent(general2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, 1) //comment 9, Short.MAX_VALUE
																																																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																																								.addComponent(colonel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																								.addComponent(colPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																								.addComponent(colonel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																																																												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																														.addComponent(major1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																														.addComponent(major2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																														.addComponent(majPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																.addGroup(layout.createSequentialGroup()
																																																																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																		.addComponent(captPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																																		.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																																																																				.addGap(32, 32, 32)
																																																																				.addComponent(capt1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
																																																																				.addComponent(capt2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																						.addComponent(lieutPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																						.addComponent(lieut1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																						.addComponent(lieut2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																								.addComponent(sarge2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																								.addComponent(sarge1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																								.addComponent(sargePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																																								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																										.addComponent(minPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																										.addComponent(minor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																										.addComponent(minor2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																												.addGroup(layout.createSequentialGroup()
																																																																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																																																																.addComponent(sctPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																																.addGroup(layout.createSequentialGroup()
																																																																																		.addComponent(sct1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																																		.addContainerGap())))
																																																																																		.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																																																																																				.addGap(33, 33, 33)
																																																																																				.addComponent(sct2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																																				.addContainerGap())))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup()
										.addContainerGap()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(p1label)
												.addComponent(p2label))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(p1Ready)
														.addComponent(p2Ready))
														.addGap(18, 18, 18)
														.addComponent(forfeit)
														.addGap(18, 18, 18)
														.addComponent(draw)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(drawLabel)
																.addComponent(drawnum))
																.addGap(18, 18, 18)
																.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(sendButton)))
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																								.addGroup(layout.createSequentialGroup()
																										.addGap(18, 18, 18)
																										.addComponent(p1left)
																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
																										.addGroup(layout.createSequentialGroup()
																												.addGap(15, 15, 15)
																												.addComponent(spy1)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
																												.addGroup(layout.createSequentialGroup()
																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																		.addComponent(colonel1)
																																		.addComponent(capt1)
																																		.addComponent(lieut1)
																																		.addComponent(sarge1)
																																		.addComponent(major1))
																																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																				.addComponent(marshall1)
																																				.addComponent(general1)))
																																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
																																				.addGroup(layout.createSequentialGroup()
																																						.addGap(15, 15, 15)
																																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																								.addComponent(sct1)
																																								.addComponent(minor1))
																																								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
																																								.addGroup(layout.createSequentialGroup()
																																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																												.addComponent(flag1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																												.addComponent(bomb1))
																																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
																																												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																																.addComponent(flagPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(spyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(bombPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(marPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(genPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(colPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(majPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(captPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(lieutPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(sargePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(minPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																.addComponent(sctPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																.addGroup(layout.createSequentialGroup()
																																																		.addGap(10, 10, 10)
																																																		.addComponent(pieceLabel)
																																																		.addGap(13, 13, 13)
																																																		.addComponent(remLabel)))
																																																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																				.addComponent(p2left)
																																																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																																						.addComponent(sct2)
																																																						.addComponent(lieut2)
																																																						.addComponent(capt2)
																																																						.addComponent(major2))
																																																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																																								.addComponent(colonel2)
																																																								.addComponent(general2)
																																																								.addComponent(marshall2)
																																																								.addComponent(spy2))
																																																								.addComponent(bomb2)
																																																								.addComponent(flag2)
																																																								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																																										.addComponent(sarge2)
																																																										.addComponent(minor2)))
																																																										.addContainerGap())
		);

		flag2.getAccessibleContext().setAccessibleName("");
		bomb2.getAccessibleContext().setAccessibleName("");

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void p1ReadyActionPerformed(java.awt.event.ActionEvent evt) {
		if(!cc.isReady())
		{
			p1Ready.setSelected(false);
		}
		else if(p1Ready.isSelected()){
			con.getOutput().send(new C4SendReady(new byte[]{1}));
		}
		else
			p1Ready.setForeground(Color.BLACK);
	}

	private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if(!typeBox.getText().equals(""))
			(con.getOutput()).send(new C11SendMessage((cc.getPName()+": "+typeBox.getText()).getBytes()));
		typeBox.setText("");
	}

	public void setChatText(String text)
	{
		if(chatBox.getText().equals(""))
			chatBox.setText(text);
		else
			chatBox.setText(chatBox.getText()+"\n"+text);
	}

	private void typeBoxKeyPressed(java.awt.event.KeyEvent evt) {
		if(evt.getKeyCode()==10)
			if(!typeBox.getText().equals(""))
				(con.getOutput()).send(new C11SendMessage((cc.getPName()+": "+typeBox.getText()).getBytes()));
	}

	private void typeBoxKeyReleased(java.awt.event.KeyEvent evt) {
		if(evt.getKeyCode()==10)
			typeBox.setText("");
	}

	private void drawActionPerformed(java.awt.event.ActionEvent evt) {
		//send message to server notifying draw quest
		if(cc.getState()==2){
			int value = Integer.parseInt(drawnum.getText());
			if(value>0){
				drawnum.setText(Integer.toString(--value));
				con.getOutput().send(new C7Stalemate());
			}
			if(value==0)
				draw.setEnabled(false);
		}
	}

	private void forfeitActionPerformed(java.awt.event.ActionEvent evt) {
		if(cc.getState()==2){
			int confirm = JOptionPane.showConfirmDialog(null, "Do you really want to forfeit?","Forfeit",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
			if(confirm==0)
				con.getOutput().send(new C6Forfeit(new byte[]{1}));
		}
	}

	private void exitActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	private void battlelocActionPerformed(java.awt.event.ActionEvent evt) {
		board.changeMap();
	}

	private void p2ReadyActionPerformed(java.awt.event.ActionEvent evt) {
		if(!cc.isReady())
		{
			p2Ready.setSelected(false);
		}
		else if(p2Ready.isSelected()){
			con.getOutput().send(new C4SendReady(new byte[]{1}));
		}
		else
			p2Ready.setForeground(Color.BLACK);
	}

	private void connectActionPerformed(ActionEvent evt) {
		String name = JOptionPane.showInputDialog(this,"Player Name?");
		String ip = JOptionPane.showInputDialog(this,"IP Address of Server?");
		if(name==null || name.equals(""))
			JOptionPane.showMessageDialog(this, "Player name can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
		else{
			cc.setName(name);
			con = new ClientConnect(ip,30568, this);
			con.start();
		}
	}

	private void disconnectActionPerformed(ActionEvent evt) {
		System.exit(0);
	}

	private void resetPanels()
	{
		if(cc.getPiece()!=0)
		{
			getPiecePanel(cc.getPiece()).setBackground(this.getBackground());
			cc.setPiece(0);
		}
	}

	private void smusicActionPerformed(ActionEvent e)
	{
		if(cc.getState()!=0){
			if(smusic.getText().equals("Stop Music"))
			{
				stopMusic();
				smusic.setText("Start Music");
			}
			else
			{
				startMusic();
				smusic.setText("Stop Music");
			}
		}
	}

	private void changeActionPerformed(ActionEvent e)
	{
		if(cc.getState()!=0 && smusic.getText().equals("Stop Music"))
			changeMusic();
	}

	private void panelMouseReleased(MouseEvent e){
		if(cc.getState()==1){
			JPanel source = (JPanel)e.getSource();
			Point p = SwingUtilities.convertPoint(source, e.getPoint(), source.getParent());
			Component jc = source.getParent().getComponentAt(p);
			JPanel target = null;
			if(jc instanceof JPanel)
				target = (JPanel)jc;
			if(target == null || source != target )
				return;
			else {
				if(cc.getPiece()==0)
				{
					target.setBackground(Color.green);
					cc.setPiece(getPieceNum(target));
				}
				else
				{
					int pieceNum = getPieceNum(target);
					if(cc.getPiece()== pieceNum)
					{
						target.setBackground(this.getBackground());
						cc.setPiece(0);
					}
					else
					{
						getPiecePanel(cc.getPiece()).setBackground(this.getBackground());
						target.setBackground(Color.GREEN);
						cc.setPiece(pieceNum);
					}
				}
			}
		}
	}

	private JPanel getPiecePanel(int i)
	{
		switch(i){
		case 1: return marPanel;
		case 2: return genPanel;
		case 3: return colPanel;
		case 4: return majPanel;
		case 5: return captPanel;
		case 6: return lieutPanel;
		case 7: return sargePanel;
		case 8: return minPanel;
		case 9: return sctPanel;
		case 10: return spyPanel;
		case 11: return bombPanel;
		case 12: return flagPanel;
		default: return null;
		}
	}

	private int getPieceNum(JPanel jp)
	{
		if(jp==marPanel)
			return 1;
		else if(jp==genPanel)
			return 2;
		else if(jp==colPanel)
			return 3;
		else if(jp==majPanel)
			return 4;
		else if(jp==captPanel)
			return 5;
		else if(jp==lieutPanel)
			return 6;
		else if(jp==sargePanel)
			return 7;
		else if(jp==minPanel)
			return 8;
		else if(jp==sctPanel)
			return 9;
		else if(jp==spyPanel)
			return 10;
		else if(jp==bombPanel)
			return 11;
		else if(jp==flagPanel)
			return 12;
		return 0;
	}
	
	public void setMusicPath(URL p)
	{
		path = p;
	}

	public void startMusic()
	{
		new Thread() {
			public void run() {
				try {
					player = new Player(path.openStream());
					player.play(); }
				catch (Exception e) { System.out.println(e); }
			}
		}.start();
	}

	public void stopMusic()
	{
		player.close();
	}

	public void changeMusic()
	{
		music = (music+1)%6;
		switch(music)
		{
		case 0: path = ClassLoader.getSystemResource("resources/music/halo.mp3"); break;
		case 1: path = ClassLoader.getSystemResource("resources/music/maintheme.mp3"); break;
		case 2: path = ClassLoader.getSystemResource("resources/music/castlesiege.mp3"); break;
		case 3: path = ClassLoader.getSystemResource("resources/music/finaldestination.mp3"); break;
		case 4: path = ClassLoader.getSystemResource("resources/music/waiting.mp3"); break;
		case 5:
			path = ClassLoader.getSystemResource("resources/music/waiting2.mp3"); break;
		}
		player.close();
		new Thread() {
			public void run() {
				try { 
					player = new Player(path.openStream());
					player.play(); }
				catch (Exception e) {System.out.println(e); }
			}
		}.start();
	}

	public void updateLabels()
	{
		//player 1
		int[] text = cc.getnumRem(0);
		flag1.setText(""+text[11]);
		bomb1.setText(""+text[10]);
		spy1.setText(""+text[9]);
		marshall1.setText(""+text[0]);
		general1.setText(""+text[1]);
		colonel1.setText(""+text[2]);
		major1.setText(""+text[3]);
		capt1.setText(""+text[4]);
		lieut1.setText(""+text[5]);
		sarge1.setText(""+text[6]);
		minor1.setText(""+text[7]);
		sct1.setText(""+text[8]);

		//player 2
		text = cc.getnumRem(1);
		flag2.setText(""+text[11]);
		bomb2.setText(""+text[10]);
		spy2.setText(""+text[9]);
		marshall2.setText(""+text[0]);
		general2.setText(""+text[1]);
		colonel2.setText(""+text[2]);
		major2.setText(""+text[3]);
		capt2.setText(""+text[4]);
		lieut2.setText(""+text[5]);
		sarge2.setText(""+text[6]);
		minor2.setText(""+text[7]);
		sct2.setText(""+text[8]);

		repaint();
	}

	public void initReadyButtons()
	{
		if(cc.getPlayer()==0)
			p2Ready.setEnabled(false);
		else
			p1Ready.setEnabled(false);
	}

	public void startState2()
	{
		if(cc.getPlayer()==1){
			p2Ready.setEnabled(false);
			p2Ready.setSelected(false);
		}
		else if(cc.getPlayer()==0){ 
			p1Ready.setEnabled(false);
			p1Ready.setSelected(false);
		}
		cc.setState(2);
		resetPanels();
		cc.resetnumRem();
		updateLabels();
		board.fillEnemies();
	}
	
	public void startTurn()
	{
		tc.resetTimer();
		tc.setVisible(true);
		tc.startTimer();
	}
	
	public void stopTurn()
	{
		tc.setVisible(false);
		tc.stopTimer();
	}

	public GUIController getGC()
	{
		return gc;
	}

	public ClientController getCC()
	{
		return cc;
	}

	public Board getBoard()
	{
		return board;
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try{
					new MainFrame().setVisible(true);
				}
				catch(Exception e)
				{
					System.out.println("An Exception was thrown.");
				}
			}
		});
	}
}