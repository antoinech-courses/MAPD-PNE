package org.pneditor.petrinet.models.cheucleclaeys;

/**
 * Represents a specific OUT edge, only activated if the linked place is not
 * empty and removes all tokens from it
 * 
 * @author Claeys
 *
 */
public class EdgeEmpty extends EdgeOut {

	/**
	 * EMPTY edge creation from the place. Doesn't have a weight because we don't
	 * need it to trigger the associated place
	 * 
	 * @param place
	 */
	public EdgeEmpty(Place place) {
		super(place);
	}

	/**
	 * Returns if the edge is triggerable. This edge is only triggerable when not
	 * empty
	 */
	public boolean isTriggerable() {
		return (!this.getPlace().isEmpty());
	}

	/**
	 * Triggers the edge. Removes all tokens from the place
	 */
	public void trigger() {
		this.getPlace().remove(this.getPlace().getCountTokens());
	}

}
