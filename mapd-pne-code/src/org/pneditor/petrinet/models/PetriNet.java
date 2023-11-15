package org.pneditor.petrinet.models;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

import org.pneditor.petrinet.models.Edge;
import org.pneditor.petrinet.models.Place;
import org.pneditor.petrinet.models.Transition;

/**
 * An implementation of a Petri network
 * 
 * @author Cheucle
 *
 */
public class PetriNet {

	/**
	 * List containing all transitions of the network.
	 */
	private List<Transition> listTransitions;

	/**
	 * List containing all the places of the network, whenever or not it is linked
	 * to a transition.
	 */
	private List<Place> listPlaces;

	public PetriNet() {
		this.listTransitions = new LinkedList<Transition>();
		this.listPlaces = new LinkedList<Place>();
	}

	public void triggerTransition(Transition transition) {
		transition.fire();

	}

	public void add(Place place) {
		this.listPlaces.add(place);

	}

	public void add(Transition transition) {
		this.listTransitions.add(transition);

	}

	public void add(Place place, Transition transition, int weight, EdgeType type) {
		Edge edge = null;
		switch (type) {
		case IN:
			edge = new EdgeIn(weight, place);
			break;

		case OUT:
			edge = new EdgeOut(weight, place);
			break;

		case ZERO:
			edge = new EdgeZero(place);
			break;

		case EMPTY:
			edge = new EdgeEmpty(place);
			break;
		
		default:
			throw new InvalidParameterException("Edge type must be IN, OUT, ZERO or EMPTY");
		}

		transition.addEdge(edge);
	}

	public void remove(Place place) {
		this.listPlaces.remove(place);
		// When we remove a place, we remove all edges linked to this place
		this.listTransitions.forEach(transition -> this.remove(place, transition));
	}

	public void remove(Transition transition) {
		// When removing a transition, there is no need to explicitly remove edges
		// because edges are stored by the transition that is removed from memory by the
		// garbage collector
		this.listTransitions.remove(transition);
	}

	public void remove(Place place, Transition transition) {
		transition.removeEdgeFromPlace(place);
	}

	/**
	 * Show the current state of the network. Print the details of transitions
	 * (source, target, weight) and places (current token count)
	 *
	 */
	public String toString() {
		String result = String.format(
				"RAPPORT DE L'ETAT DU RESEAU\nNombre de transitions : %d \nNombre de places : %d\n\n=====================\nEtat des places :\n",
				this.listTransitions.size(), this.listPlaces.size());
		int index = 1;
		// Places information
		for (Place place : listPlaces) {
			result += String.format("Place %d : %d cailloux\n", index++, place.getCountTokens());
		}
		result += "=====================\nEtat des arcs :\n";
		// Transitions information
		index = 1;
		for (Transition transition : listTransitions) {
			result += String.format("  Arcs de la transition %d\n", index++);

			int edgeindex = 1;
			// Edges information
			for (Edge edge : transition.getEdges()) {
				result += String.format("    Arc %d - Poids : %d , Type : %s, Cible : Place %d\n", edgeindex++,
						edge.getWeight(), edge.getClass().getName(), this.listPlaces.indexOf(edge.getPlace()) + 1);
			}
		}
		result += "FIN DU RAPPORT\n----------------------------------------------\n";
		return result;
	}

}
