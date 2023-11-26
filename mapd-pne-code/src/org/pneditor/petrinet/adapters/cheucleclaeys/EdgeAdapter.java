package org.pneditor.petrinet.adapters.cheucleclaeys;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.cheucleclaeys.Edge;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeEmpty;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeIn;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeOut;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeType;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeZero;
import org.pneditor.petrinet.models.cheucleclaeys.Transition;

public class EdgeAdapter extends AbstractArc {
	private Edge edge;
	private AbstractNode source;
	private AbstractNode destination;

	public EdgeAdapter(AbstractNode source, AbstractNode destination, Edge edge) {
		this.edge = edge;
		this.source = source;
		this.destination = destination;
	}

	@Override
	public AbstractNode getSource() {
		// TODO Auto-generated method stub
		return this.source;
	}

	@Override
	public AbstractNode getDestination() {
		// TODO Auto-generated method stub
		return this.destination;

	}

	@Override
	// Zero
	public boolean isReset() {
		// TODO Auto-generated method stub
		return (this.edge instanceof EdgeEmpty);
	}

	@Override
	// In, Out
	public boolean isRegular() {
		// TODO Auto-generated method stub
		return ((this.edge instanceof EdgeIn || this.edge instanceof EdgeOut) && !(this.edge instanceof EdgeZero)
				&& !(this.edge instanceof EdgeEmpty));
	}

	@Override
	// Empty
	public boolean isInhibitory() {
		// TODO Auto-generated method stub
		return (this.edge instanceof EdgeZero);
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
