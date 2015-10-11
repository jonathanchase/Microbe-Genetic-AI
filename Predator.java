
import java.util.*;


public class Predator {
	
	int[] wanderX = {0,1,0,-1};
	int[] wanderY = {1,0,-1,0};
	int wanderCtr = 0;
	int locationX = 20;
	int locationY = 20;
	int wanderDir = 4;
	int perception = 5;
	int preyAppr = 2;
	ArrayList<Microbe> micrSeenAr = new ArrayList<Microbe>();
	
	Random random = new Random();
	
	public Predator(){
		
		this.locationX = random.nextInt(35) + 3;
		this.locationY = random.nextInt(95) + 3;
		
	}
	
	public void respond(){
		
		if(this.micrSeenAr.size() > 0){
			
		
			
			//do this each time we move
			for (int j = 0;j < this.preyAppr;j++){
				// figure out which prey is closest
			
				Microbe nearest = null;
				int ctr = 999;
				for (int i = 0;i < micrSeenAr.size();i++){
					int manDist = Math.abs(this.locationX - this.micrSeenAr.get(i).locationX) - Math.abs(this.locationY - this.micrSeenAr.get(i).locationY);
					if (manDist<ctr){
					
						ctr = manDist;
						nearest = this.micrSeenAr.get(i);
					
					}
				
				}
				int dirX;
				int dirY;
				//calculate the direction towards the nearest microbe
				if (this.locationX != nearest.locationX){
					dirX = (this.locationX - nearest.locationX)/(Math.abs(this.locationX - nearest.locationX));
				}else{
					dirX = 0;
				}
				if (this.locationY != nearest.locationY){
					dirY = (this.locationY - nearest.locationY)/(Math.abs(this.locationY - nearest.locationY));
				}else{
					dirY = 0;
				}
				//move the predator
				
				this.locationX = this.locationX - dirX;
				this.locationY = this.locationY - dirY;
			}
		}else{
			
			this.wander();
			
		}

		if (this.locationX < 0){
	
			this.locationX = 0;
	
		}
		if (this.locationX > 40){
	
			this.locationX = 40;
	
		}
		if (this.locationY < 0){
	
			this.locationY = 0;
	
		}
		if (this.locationY > 100){
	
			this.locationY = 100;
	
		}
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
