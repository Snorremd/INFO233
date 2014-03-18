package game.gfx;


public class PaintingThread extends Thread{
	Lerret l;
	
	public PaintingThread(Lerret l) throws NullPointerException {
		super();
		if(null == l){
			throw new NullPointerException("Lerret kan ikke være null");
		}
		
		this.l = l;
		this.start();
	}
	
	@Override
	public void run(){
		System.out.println("PaintingThread started");
		
		long timestamp = System.nanoTime();
		long paintFrequency = 1_000_000_000L / 60L; // 60 ganger per sekund
		
		while(!Thread.interrupted()){
			long timeSinceLastPaint = System.nanoTime() - timestamp;

			if(timeSinceLastPaint >= paintFrequency){
				l.render();
				timestamp = System.nanoTime();
			}
		}
		
	}
}
