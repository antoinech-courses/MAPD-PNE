package org.pneditor.petrinet.adapters;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.Edge;
import org.pneditor.petrinet.models.EdgeEmpty;
import org.pneditor.petrinet.models.EdgeIn;
import org.pneditor.petrinet.models.EdgeOut;
import org.pneditor.petrinet.models.EdgeType;
import org.pneditor.petrinet.models.EdgeZero;
import org.pneditor.petrinet.models.Transition;

public class EdgeAdapter extends AbstractArc {
	private Edge edge;
	private TransitionAdapter transition;
	private PlaceAdapter place;
	
	public EdgeAdapter(PlaceAdapter place,TransitionAdapter transition,EdgeType edgeType) {
		switch (edgeType) {
		case EMPTY:
			this.place = place;
			this.transition = transition;
			this.edge = new EdgeEmpty(place.getModel());
			this.transition.getModel().addEdge(edge);
			break;
		case IN:
			this.place = place;
			this.transition = transition;
			this.edge = new EdgeIn(place.getModel());
			this.transition.getModel().addEdge(edge);
			break;
		case OUT:
			this.place = place;
			this.transition = transition;
			this.edge = new EdgeOut(place.getModel());
			this.transition.getModel().addEdge(edge);
			break;
		case ZERO:
			this.place = place;
			this.transition = transition;
			this.edge = new EdgeZero(place.getModel());
			this.transition.getModel().addEdge(edge);
			break;
		default:
			break;
			
		}
	}

	@Override
	public AbstractNode getSource() {
		// TODO Auto-generated method stub
		if (this.edge instanceof EdgeIn) {
			return this.transition; 
		}
		else {
			return this.place;
		}
	}

	@Override
	public AbstractNode getDestination() {
		// TODO Auto-generated method stub
		if (this.edge instanceof EdgeIn) {
			return this.place;
		}
		else {
			return this.transition; 
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
