package org.pneditor.petrinet.adapters;

import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.models.Place;

public class PlaceAdapter extends AbstractPlace {
	private Place place;

	public PlaceAdapter(String label) {
		super(label);
		this.place = new Place(0);
	}

	@Override
	public void addToken() {
		this.place.add(1);
		
	}

	@Override
	public void removeToken() {
		this.place.remove(1);
		
	}

	@Override
	public int getTokens() {
		return this.place.getCountTokens();
	}

	@Override
	public void setTokens(int tokens) {
		if (tokens > this.getTokens()) {
			this.place.add(tokens - this.getTokens());
		}
		else {
			this.place.remove(this.getTokens() - tokens);
		}
	}
	
	public Place getModel() {
		return this.place;
	}

}
