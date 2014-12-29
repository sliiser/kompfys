package ee.liiser.siim.galaxies.data;

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
