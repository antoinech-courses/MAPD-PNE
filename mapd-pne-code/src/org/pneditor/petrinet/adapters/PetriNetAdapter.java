package org.pneditor.petrinet.adapters;

import java.util.LinkedList;
import java.util.List;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.PetriNetInterface;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.UnimplementedCaseException;
import org.pneditor.petrinet.models.Edge;
import org.pneditor.petrinet.models.EdgeOut;
import org.pneditor.petrinet.models.EdgeType;
import org.pneditor.petrinet.models.PetriNet;
import org.pneditor.petrinet.models.Place;
import org.pneditor.petrinet.models.Transition;

public class PetriNetAdapter extends PetriNetInterface{
	
	private PetriNet network;
	
	public PetriNetAdapter() {
		this.network = new PetriNet();
	}
	
	@Override
	public AbstractPlace addPlace() {
		PlaceAdapter place = new PlaceAdapter("béboué");
		this.network.add(place.getModel());
		return place;
	}

	@Override
	public AbstractTransition addTransition() {
		TransitionAdapter transtion = new TransitionAdapter("béboué");
		this.network.add(transtion.getModel());
		return transtion;
	}

	@Override
	// IN, OUT
	public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
		List<Edge> edgeList;
		if (source instanceof AbstractTransition) {
			network.add(((PlaceAdapter)destination).getModel(), ((TransitionAdapter)source).getModel(), 1, EdgeType.IN);
			edgeList = ((TransitionAdapter)source).getModel().getEdges();
		}
		else {
			network.add(((PlaceAdapter)source).getModel(), ((TransitionAdapter)destination).getModel(), 1, EdgeType.OUT);
			edgeList = ((TransitionAdapter)destination).getModel().getEdges();
		}
		return new EdgeAdapter(source, destination, edgeList.get(edgeList.size() - 1));
	}

	@Override
	// EMPTY
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		network.add(((PlaceAdapter)place).getModel(), ((TransitionAdapter)transition).getModel(), 1, EdgeType.EMPTY);
		List<Edge> edgeList = ((TransitionAdapter)transition).getModel().getEdges();
		return new EdgeAdapter(place, transition, edgeList.get(edgeList.size() - 1));
	}

	@Override
	// ZERO
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		network.add(((PlaceAdapter)place).getModel(), ((TransitionAdapter)transition).getModel(), 1, EdgeType.ZERO);
		List<Edge> edgeList = ((TransitionAdapter)transition).getModel().getEdges();
		return new EdgeAdapter(place, transition, edgeList.get(edgeList.size() - 1));
	}

	@Override
	public void removePlace(AbstractPlace place) {
		this.network.remove(((PlaceAdapter)place).getModel());
	}

	@Override
	public void removeTransition(AbstractTransition transition) {
		this.network.remove(((TransitionAdapter)transition).getModel());
		
	}

	@Override
	public void removeArc(AbstractArc arc) {
		Place place = (arc.getSource() instanceof PlaceAdapter) ? ((PlaceAdapter)arc.getSource()).getModel() : ((PlaceAdapter)arc.getDestination()).getModel();
		Transition transition = (arc.getSource() instanceof TransitionAdapter) ? ((TransitionAdapter)arc.getSource()).getModel() : ((TransitionAdapter)arc.getDestination()).getModel();
		network.remove(place, transition);
	}

	@Override
	public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
		Transition transitionModel = ((TransitionAdapter)transition).getModel();
		boolean triggerable = true;
		for (Edge edge : transitionModel.getEdges()) {
			if (edge instanceof EdgeOut) {
				// Remaines true only if all edges are triggerable
				// Forced cast as EdgeOut as we only check OUT edges for trigger
				triggerable &= (((EdgeOut) edge).isTriggerable());
			}
		}
		return triggerable;
	}

	@Override
	public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
		this.network.triggerTransition(((TransitionAdapter)transition).getModel());
		
	}

}
