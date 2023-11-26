package org.pneditor.petrinet.adapters.cheucleclaeys;

import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.models.cheucleclaeys.Transition;

/**
 * @author Nathan Claeys A class to implements the AbstractTransition model
 *         using transition functions of our petriNetwork
 */
public class TransitionAdapter extends AbstractTransition {
	private Transition transition;

	public TransitionAdapter(String label) {
		super(label);
		this.transition = new Transition();
	}

	/**
	 * Allows us to have access to the transition of our model
	 * 
	 * @return the transition model
	 */
	public Transition getModel() {
		return this.transition;
	}

}
