package org.pneditor.petrinet.adapters.cheucleclaeys;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.cheucleclaeys.Edge;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeEmpty;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeIn;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeOut;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeZero;

/**
 * @author Nathan Claeys A class to implements the AbstractArc model using edges
 *         functions of our petriNetwork
 */

public class EdgeAdapter extends AbstractArc {
	private Edge edge;
	private AbstractNode source;
	private AbstractNode destination;

	public EdgeAdapter(AbstractNode source, AbstractNode destination, Edge edge) {
		this.edge = edge;
		this.source = source;
		this.destination = destination;
	}

	/**
	 * Give the source of the edge (place if edgeOut and transition if edgeIn)
	 */
	@Override
	public AbstractNode getSource() {
		return this.source;
	}

	/**
	 * Give the destination of the edge (place if edgeIn and transition if edgeOut)
	 */
	@Override
	public AbstractNode getDestination() {
		return this.destination;

	}

	/**
	 * Tells if the edge is a reset edge In our model it correspond to an EdgeEmpty
	 */
	@Override
	// Empty
	public boolean isReset() {
		return (this.edge instanceof EdgeEmpty);
	}

	/**
	 * Tells if the edge is a simple edge (in or out)
	 */
	@Override
	// In, Out
	public boolean isRegular() {
		// EdgeEmpty and EdgeZoro are of type EdgeOut so we have to exclude them
		return ((this.edge instanceof EdgeIn || this.edge instanceof EdgeOut) && !(this.edge instanceof EdgeZero)
				&& !(this.edge instanceof EdgeEmpty));
	}

	/**
	 * Tells if the edge is an inhibitory edge In our model it correspond to an
	 * EdgeZero
	 */
	@Override
	// Zero
	public boolean isInhibitory() {
		return (this.edge instanceof EdgeZero);
	}

	/**
	 * Give the multiplicity/the weight of the edge
	 * 
	 * @return int weight
	 */
	@Override
	public int getMultiplicity() throws ResetArcMultiplicityException {
		return this.edge.getWeight();
	}

	/**
	 * Allows us to change the weight of an edge
	 * 
	 * @param int new weight
	 */
	@Override
	public void setMultiplicity(int multiplicity) throws ResetArcMultiplicityException {
		this.edge.setWeight(multiplicity);

	}

}
