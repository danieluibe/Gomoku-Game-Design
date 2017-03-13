package gui;
import model.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controller.*;
import gui.Chessboard.BoardSquare;
public class Human extends Controller implements MouseListener{
	Location loc = null;
	public Human (Player me){
		super(me);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		if(ob instanceof BoardSquare)
			loc = ((BoardSquare)ob).getLoc();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Location nextMove(Game g) {
		// TODO Auto-generated method stub
		return loc;
	}

}
