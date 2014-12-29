package ee.liiser.siim.galaxies.data;

/**
 * A helper class to simplify the creation of galaxies.
 * Not used once simulation has started
 *
 */
public class Galaxy {
	
	private Core core;
	private Star[] stars;
	
	Galaxy(Core core, Star[] stars){
		this.core = core;
		this.stars = stars;
	}
	
	public Core getCore(){
		return core;
	}
	
	public Star[] getStars(){
		return stars;
	}

}
