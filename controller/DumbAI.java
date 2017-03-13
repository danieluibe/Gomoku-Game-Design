package controller;


import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 * A DumbAI is a Controller that always chooses the blank space with the
 * smallest column number from the row with the smallest row number.
 */
public class DumbAI extends Controller {

	public DumbAI(Player me) {
		super(me);
	}
	
	protected @Override Location nextMove(Game g) {
		// Note: Calling delay here will make the CLUI work a little more
		// nicely when competing different AIs against each other.

		// TODO Auto-generated method stub
		List<Location> available = new ArrayList<Location>();
		Location nextloc = new Location(Board.NUM_ROWS-1,Board.NUM_COLS-1);
		// find available moves
		for (Location loc : Board.LOCATIONS)
			if (g.getBoard().get(loc) == null)
				available.add(loc);

		// wait a bit
		delay();

		//choose a DumbAI move
		if (!available.isEmpty()){
			for(int i=0; i < available.size(); i++){
				if(available.get(i).row < nextloc.row)
					nextloc = available.get(i);
				if(available.get(i).row == nextloc.row)
					if(available.get(i).col <= nextloc.col)
						nextloc = available.get(i);
			}
			return nextloc;
		}
		return null;

	}
}
