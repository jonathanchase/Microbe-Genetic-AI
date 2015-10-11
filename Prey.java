
import java.util.*;


public class Prey {
	
	int[] wanderX = {0,1,0,-1};
	int[] wanderY = {1,0,-1,0};
	int wanderCtr = 0;
	int locationX = 20;
	int locationY = 20;
	int wanderDir = 4;
	boolean inPlay = true;
	Random random = new Random();
	
	public Prey(){
		
		this.locationX = random.nextInt(35) + 3;
		this.locationY = random.nextInt(95) + 3;
		
	}
	
	public void die(){
		
		this.inPlay = false;
		
		
	}
	
	
	public void wander(){
		
		//if there is no chosen direction, choose one randomly
		if ((this.wanderDir == 4) || (this.wanderCtr >= 20)){
			
			this.wanderDir = random.nextInt(4);
			this.wanderCtr = 0;
			
		}
		
		int xAdj = 0;
		int yAdj = 0;
		
		//Pick a "wiggle" adjustment
		
		if (wanderX[wanderDir] ==0){
			
			xAdj = random.nextInt(3) - 1;
			yAdj = wanderY[wanderDir];
		}else{
			
			yAdj = random.nextInt(3) -1;
			xAdj = wanderX[wanderDir];
			
		}
		
		//Move the critter
		
		this.locationX += xAdj;
		this.locationY += yAdj;
		this.wanderCtr++;
		
		//Let's be sure that we are not leaving the area
		
		if (locationX <=2){
			
			wanderDir = 1;
			
		}
		if (locationX >= 38){
			
			wanderDir = 3;
			
		}
		if (locationY <= 2){
			
			wanderDir = 0;
			
		}
		if (locationY >= 98){
			
			wanderDir = 2;
			
		}
		
		
		
	}
	

}
