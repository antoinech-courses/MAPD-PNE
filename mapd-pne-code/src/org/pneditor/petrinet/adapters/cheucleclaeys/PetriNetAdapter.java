package org.pneditor.petrinet.adapters.cheucleclaeys;

import java.security.InvalidParameterException;
import java.util.List;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.PetriNetInterface;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.UnimplementedCaseException;
import org.pneditor.petrinet.models.cheucleclaeys.Edge;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeOut;
import org.pneditor.petrinet.models.cheucleclaeys.EdgeType;
import org.pneditor.petrinet.models.cheucleclaeys.PetriNet;
import org.pneditor.petrinet.models.cheucleclaeys.Place;
import org.pneditor.petrinet.models.cheucleclaeys.Transition;

/**
 * @author Antoine Cheucle A class to extends the PetriNetInterface model using
 *         petriNet functions of our petriNetwork
 */
public class PetriNetAdapter extends PetriNetInterface {

	private PetriNet network;

	public PetriNetAdapter() {
		this.network = new PetriNet();
	}

	/**
	 * Allows us to add a place to our network. The place will start with 0 token
	 */
	@Override
	public AbstractPlace addPlace() {
		PlaceAdapter place = new PlaceAdapter("");
		this.network.add(place.getModel());
		return place;
	}

	/**
	 * Allows us to add a transition to our network
	 */
	@Override
	public AbstractTransition addTransition() {
		TransitionAdapter transtion = new TransitionAdapter("");
		this.network.add(transtion.getModel());
		return transtion;
	}

	/**
	 * Allows us to add a regular edge. The type in or out is defined by the order
	 * of the parameters. If the first one is a transition, it will be an edgeIn. On
	 * the contrary it will be an edgeOut
	 * 
	 * @param two AbstractNode
	 */
	@Override
	// IN, OUT
	public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
		try {
			List<Edge> edgeList;
			// Depending of the type of the source, the edge is IN or OUT
			if (source instanceof AbstractTransition) {
				network.add(((PlaceAdapter) destination).getModel(), ((TransitionAdapter) source).getModel(), 1,
						EdgeType.IN);
				edgeList = ((TransitionAdapter) source).getModel().getEdges();
			} else {
				network.add(((PlaceAdapter) source).getModel(), ((TransitionAdapter) destination).getModel(), 1,
						EdgeType.OUT);
				edgeList = ((TransitionAdapter) destination).getModel().getEdges();
			}
			return new EdgeAdapter(source, destination, edgeList.get(edgeList.size() - 1));
		} catch (InvalidParameterException e) {
			// throw error is edge already exist
			System.err.println("Error : " + e.getMessage());
			return null;
		}
	}

	/**
	 * Allows us to add an inhibitory edge (zero) to our model
	 * 
	 * @param an AbstractPlace and An AbstractTransition
	 */
	@Override
	// ZERO
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		try {
			network.add(((PlaceAdapter) place).getModel(), ((TransitionAdapter) transition).getModel(), 1,
					EdgeType.ZERO);
			List<Edge> edgeList = ((TransitionAdapter) transition).getModel().getEdges();
			return new EdgeAdapter(place, transition, edgeList.get(edgeList.size() - 1));
		} catch (InvalidParameterException e) {
			// throw error is edge already exist
			System.err.println("Error : " + e.getMessage());
			return null;
		}
	}

	/**
	 * Allows us to add an reset edge (empty) to our model
	 * 
	 * @param an AbstractPlace and An AbstractTransition
	 */
	@Override
	// EMPTY
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		try {
			network.add(((PlaceAdapter) place).getModel(), ((TransitionAdapter) transition).getModel(), 1,
					EdgeType.EMPTY);
			List<Edge> edgeList = ((TransitionAdapter) transition).getModel().getEdges();
			return new EdgeAdapter(place, transition, edgeList.get(edgeList.size() - 1));
		} catch (InvalidParameterException e) {
			// throw error is edge already exist
			System.err.println("Error : " + e.getMessage());
			return null;
		}
	}

	/**
	 * Allows us to remove a given place from the network and associated edges
	 * 
	 * @param an AbstractPlace
	 */
	@Override
	public void removePlace(AbstractPlace place) {
		this.network.remove(((PlaceAdapter) place).getModel());
	}

	/**
	 * Allows us to remove a given transition from the network and associated edges
	 * 
	 * @param an AbstractTransition
	 */
	@Override
	public void removeTransition(AbstractTransition transition) {
		this.network.remove(((TransitionAdapter) transition).getModel());

	}

	/**
	 * Allows us to remove a given edge from the network
	 * 
	 * @param an AbstractArc
	 */
	@Override
	public void removeArc(AbstractArc arc) {
		// We get the place and transition from edge, depending of the source and destination
		Place place = (arc.getSource() instanceof PlaceAdapter) ? ((PlaceAdapter) arc.getSource()).getModel()
				: ((PlaceAdapter) arc.getDestination()).getModel();
		Transition transition = (arc.getSource() instanceof TransitionAdapter)
				? ((TransitionAdapter) arc.getSource()).getModel()
				: ((TransitionAdapter) arc.getDestination()).getModel();
		EdgeType type = (arc.getSource() instanceof PlaceAdapter) ? EdgeType.OUT : EdgeType.IN;
		network.remove(place, transition, type);
	}

	/**
	 * Tells us if the transition is triggerable. It means that all associated edges
	 * are ready to be trigger.
	 * 
	 * @param an AbstractTransition
	 */
	@Override
	public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
		Transition transitionModel = ((TransitionAdapter) transition).getModel();
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

	/**
	 * Allows us to trigger a transition and moves tokens between places
	 * 
	 * @param an AbstractTransition
	 */
	@Override
	public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
		this.network.triggerTransition(((TransitionAdapter) transition).getModel());

	}

}
