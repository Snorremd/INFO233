package game.entity;

public class AliasNotRegisteredException extends Exception {
	/**
	 * autogennet
	 */
	private static final long serialVersionUID = -2605916098391997158L;
	
	public AliasNotRegisteredException(){
		super();
	}
	public AliasNotRegisteredException(String msg){
		super(msg);
	}
}
