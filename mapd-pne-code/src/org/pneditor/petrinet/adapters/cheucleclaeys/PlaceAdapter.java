package org.pneditor.petrinet.adapters.cheucleclaeys;

import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.models.cheucleclaeys.Place;


/**
 * @author Antoine Cheucle
 *A class to implements the AbstractPlace model using place functions of our petriNetwork
 */
public class PlaceAdapter extends AbstractPlace {
	private Place place;

	public PlaceAdapter(String label) {
		super(label);
		this.place = new Place(0);
	}

	/**
	 *Increment the number of tokens on the place by one 
	 */
	@Override
	public void addToken() {
		this.place.add(1);

	}

	/**
	 *Reduce the number of tokens on the pace of one 
	 */
	@Override
	public void removeToken() {
		this.place.remove(1);

	}

	/**
	 * Gives us the number of tokens on this place
	 *@return int number of tokens
	 */
	@Override
	public int getTokens() {
		return this.place.getCountTokens();
	}

	/**
	 * Allows us to change the number of tokens on the place
	 *@param int new number of tokens
	 */
	@Override
	public void setTokens(int tokens) {
		if (tokens > this.getTokens()) {
			this.place.add(tokens - this.getTokens());
		} else {
			this.place.remove(this.getTokens() - tokens);
		}
	}

	public Place getModel() {
		return this.place;
	}

}
