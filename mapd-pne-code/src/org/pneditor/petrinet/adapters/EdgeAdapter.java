package org.pneditor.petrinet.adapters;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.Edge;
import org.pneditor.petrinet.models.EdgeEmpty;
import org.pneditor.petrinet.models.EdgeIn;
import org.pneditor.petrinet.models.EdgeOut;
import org.pneditor.petrinet.models.EdgeZero;
import org.pneditor.petrinet.models.Transition;

public class EdgeAdapter extends AbstractArc {
	private Edge edge;
	private Transition transition;

	@Override
	public AbstractNode getSource() {
		// TODO Auto-generated method stub
		if (this.edge instanceof EdgeIn) {
			return null; //faire qqch ici
		}
		else {
			PlaceAdapter place = new PlaceAdapter("bebou");
			place.setTokens(this.edge.getPlace().getCountTokens());
		}
	}

	@Override
	public AbstractNode getDestination() {
		// TODO Auto-generated method stub
		if (this.edge instanceof EdgeIn) {
			PlaceAdapter place = new PlaceAdapter("bebou");
			place.setTokens(this.edge.getPlace().getCountTokens());
		}
		else {
			return null; //faire qqch ici
		}
	}

	@Override
	//Zero
	public boolean isReset() {
		// TODO Auto-generated method stub
		return (this.edge instanceof EdgeZero);
	}

	@Override
	//In, Out
	public boolean isRegular() {
		// TODO Auto-generated method stub
		return (this.edge instanceof EdgeIn || this.edge instanceof EdgeOut);
	}

	@Override
	//Empty
	public boolean isInhibitory() {
		// TODO Auto-generated method stub
		return (this.edge instanceof EdgeEmpty);
	}

	@Override
	public int getMultiplicity() throws ResetArcMultiplicityException {
		// TODO Auto-generated method stub
		return this.edge.getWeight();
	}

	@Override
	public void setMultiplicity(int multiplicity) throws ResetArcMultiplicityException {
		// TODO Auto-generated method stub
		this.edge.setWeight(multiplicity);

	}

}
