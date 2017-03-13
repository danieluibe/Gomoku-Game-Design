package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import controller.Controller;
import controller.DumbAI;
import controller.RandomAI;
import controller.SmartAI;
import model.Board;
import model.Board.State;
import model.Game;
import model.GameListener;
import model.Location;
import model.Player;

public class Chessboard extends JFrame implements ActionListener {
	BoardSquare[][] board;
	Game g;
	int size = 11;
	String[] type = { "Human", "DumbAI", "RandomAI", "SmartAI" };
	JLabel choosePlayer = new JLabel("Please choose a player");
	JButton start = new JButton("Start");
	JLabel p1 = new JLabel("Player X: ");
	JComboBox<String> jc1 = new JComboBox<>(type);
	JLabel p2 = new JLabel("Player O: ");
	JComboBox<String> jc2 = new JComboBox<>(type);
	info i = new info("Game not start yet");
	Box main = new Box(BoxLayout.Y_AXIS);
	Box chessboard = new Box(BoxLayout.Y_AXIS);
	Box winnerBox = new Box(BoxLayout.Y_AXIS);
	MouseEvents me = new MouseEvents();
	Controller playerX;
	Controller playerO;

	public Chessboard() {
		super("Five in a row ");
		this.addComponents();
		this.getContentPane().add(main, BorderLayout.CENTER);
		this.pack();
		this.setSize(size * 60, size * 60);
		this.setVisible(true);
	}

	public class BoardSquare extends Canvas implements GameListener {
		int row;
		int col;
		boolean f = false;

		public BoardSquare(int row, int col) {
			super();
			this.row = row;
			this.col = col;
			if ((col + row) % 2 == 1)
				setBackground(Color.WHITE);
			else
				setBackground(Color.GRAY);
		}

		public void paint(Graphics gra) {
			if (this.f) {
				gra.setFont(new Font("TimesRoman", Font.BOLD, 30));
				gra.setColor(Color.BLACK);
				gra.drawString(g.getBoard().get(row, col).toString(), 25, 35);
			}
		}

		public Location getLoc() {
			return new Location(row, col);
		}

		public void gameChanged(Game g) {
			Board b = g.getBoard();
			if (b.get(row, col) != null) {
				f = true;
				board[row][col].repaint();
			}
		}
	}

	public class info extends JLabel implements GameListener {
		public void gameChanged(Game g) {
			if (g.getBoard().getState() == State.DRAW) {
				this.setText("Game in a draw");
			} else if (g.getBoard().getState() == State.NOT_OVER) {
				this.setText("Next:    " + g.nextTurn());
			} else if (g.getBoard().getState() == State.HAS_WINNER) {
				this.setText("Winner:     " + g.getBoard().getWinner().winner.toString());
			}
		}

		public info(String s) {
			super(s);
		}
	}

	public void addComponents() {
		board = new BoardSquare[9][9];
		for (int i = 0; i < 9; i++) {
			Box row = new Box(BoxLayout.X_AXIS);
			for (int j = 0; j < 9; j++) {
				board[i][j] = new BoardSquare(i, j);
				board[i][j].addMouseListener(me);
				row.add(board[i][j]);
			}
			chessboard.add(row);
		}

		winnerBox.add(choosePlayer);
		start.addActionListener(this);
		winnerBox.add(start);
		winnerBox.add(p1);
		winnerBox.add(jc1);
		winnerBox.add(p2);
		winnerBox.add(jc2);
		winnerBox.add(i);

		main.add(chessboard);
		main.add(winnerBox);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object p1 = jc1.getSelectedItem();
		System.out.println("Player: " + p1.toString());
		Object p2 = jc2.getSelectedItem();
		System.out.println("Player: " + p2.toString());
		if (p1.toString().equals("DumbAI"))
			playerX = new DumbAI(Player.X);
		else if (p1.toString().equals("RandomAI"))
			playerX = new RandomAI(Player.X);
		else if (p1.toString().equals("SmartAI"))
			playerX = new SmartAI(Player.X);
		if (p2.toString().equals("DumbAI"))
			playerO = new DumbAI(Player.O);
		else if (p2.toString().equals("RandomAI"))
			playerO = new RandomAI(Player.O);
		else if (p2.toString().equals("SmartAI"))
			playerO = new SmartAI(Player.O);
		g = new Game();
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				g.addListener(board[i][j]);
		g.addListener(i);
		if (!p1.toString().equals("Human"))
			g.addListener(playerX);
		if (!p2.toString().equals("Human"))
			g.addListener(playerO);
	}

	class MouseEvents extends MouseInputAdapter {

		public void mouseClicked(MouseEvent e) {
			Object ob = e.getSource();
			if (ob instanceof BoardSquare && g.getBoard().getState() == State.NOT_OVER) {
				if (g.nextTurn() == Player.X && jc1.getSelectedItem().toString().equals("Human")) {
					Location move = ((BoardSquare) ob).getLoc();
					if (move != null && g.getBoard().get(move) == null) {
						g.submitMove(Player.X, move);
					}
				} else if (g.nextTurn() == Player.O && jc2.getSelectedItem().toString().equals("Human")) {
					Location move = ((BoardSquare) ob).getLoc();
					if (move != null && g.getBoard().get(move) == null) {
						g.submitMove(Player.O, move);
					}
				}
			}
		}
	}

}
