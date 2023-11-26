package org.pneditor.petrinet.adapters.cheucleclaeys;

import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.models.cheucleclaeys.Transition;

public class TransitionAdapter extends AbstractTransition {
	private Transition transition;

	public TransitionAdapter(String label) {
		super(label);
		this.transition = new Transition();
	}

	public Transition getModel() {
		return this.transition;
	}

}
