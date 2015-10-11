
	import java.util.*;


	

public class Microbe {

	boolean[] chromosome = new boolean[18];
	ArrayList<Predator> predSeenAr = new ArrayList<Predator>();
	ArrayList<Prey> preySeenAr = new ArrayList<Prey>();
	ArrayList<Microbe> micrSeenAr = new ArrayList<Microbe>();
	int[] wanderX = {0,1,0,-1};
	int[] wanderY = {1,0,-1,0};
	int wanderCtr = 0;
	int energy = 20;
	int repCost = 65;
	int locationX = 25;
	int locationY = 25;
	int wanderDir = 4;
	boolean inPlay = true;
	boolean twinMe = false;
	Random random = new Random();
	int preyAppr = 1;
	int predFlee = 1;
	int moveCost = 1;
	int chaseCost = 0;
	int fleeCost = 0;
	int baseMCost = 1;
	int perception = 2;
	int kindPriority = 1;
	
	
	public Microbe(){
		
		for (int i = 0;i<chromosome.length;i++){
			
			chromosome[i] = false;
			
		}
		this.locationX = random.nextInt(35) + 3;
		this.locationY = random.nextInt(95) + 3;
		
		generateStats();
			
	}
	
	public void generateStats(){
		int tempPreyAppr = 1;
		int tempChaseCost = 0;
		int tempFleeCost = 0;
		int tempPredFlee = 1;
		int tempPerception = 5;
		int tempKindPriority = 1;
		int tempRepCost = 65;
		if (chromosome[0]){
			
			tempPreyAppr++;
			tempChaseCost = tempChaseCost + 2;
			tempRepCost = tempRepCost + 10;
			
			
		}
		if (chromosome[1]){
			
			tempPreyAppr++;
			tempChaseCost = tempChaseCost + 1;
			tempRepCost = tempRepCost + 10;
		}
		
		if (chromosome[2]){
			
			tempPreyAppr--;
			tempChaseCost = tempChaseCost -2;
			if (tempChaseCost < 0){
				tempChaseCost = 0;
				
			}
			tempRepCost = tempRepCost - 10;
			
		}
		
		if (chromosome[3]){
			
			tempPreyAppr--;
			tempChaseCost = tempChaseCost - 1;
			if (tempChaseCost < 0){
				tempChaseCost = 0;
				
			}
			tempRepCost = tempRepCost - 5;
		}
		if (chromosome[4]){
			
			tempPreyAppr = tempPreyAppr + 2;
			tempChaseCost = tempChaseCost + 3;
			tempRepCost = tempRepCost + 15;
			
		}
		
		if (chromosome[5]){
			
			tempPreyAppr = tempPreyAppr - 2;
			tempChaseCost = tempChaseCost - 3;
			if (tempChaseCost < 0){
				tempChaseCost = 0;
				
			}
			tempRepCost = tempRepCost - 10;
		}
		
		if (chromosome[6]){
			
			tempPredFlee++;
			tempFleeCost = tempFleeCost + 2;
			tempRepCost = tempRepCost + 10;
			
		}
		if (chromosome[7]){
			
			tempPredFlee++;
			tempFleeCost = tempFleeCost + 1;
			tempRepCost = tempRepCost + 10;
			
		}
		if (chromosome[8]){
			
			tempPredFlee = tempPredFlee - 1;
			tempFleeCost = tempFleeCost - 2;
			if (tempFleeCost < 0){
				tempFleeCost = 0;
				
			}
			tempRepCost = tempRepCost - 10;
		}
		if (chromosome[9]){
			
			tempPredFlee = tempPredFlee - 1;
			tempFleeCost = tempFleeCost - 1;
			if (tempFleeCost < 0){
				tempFleeCost = 0;
				
			}
			tempRepCost = tempRepCost - 5;
		}
		if (chromosome[10]){
			
			tempPredFlee = tempPredFlee + 2;
			tempFleeCost = tempFleeCost + 3;
			tempRepCost = tempRepCost + 10;
			
		}
		if (chromosome[11]){
			
			tempPredFlee = tempPredFlee - 2;
			tempFleeCost = tempFleeCost - 3;
			if (tempFleeCost < 0){
				tempFleeCost = 0;
				
			}
			tempRepCost = tempRepCost - 15;
		}
		if (chromosome[12]){
			
			tempPerception++;
			
		}
		if (chromosome[13]){
			
			tempPerception = tempPerception + 2;
			tempRepCost = ((tempRepCost * 12)/10);
			
		}
		if (chromosome[14]){
			
			tempPerception = tempPerception + 2;
			tempRepCost = (tempRepCost *15)/10;
			
		}
		if (chromosome[15]){
			tempKindPriority++;
			
		}
		if (chromosome[16]){
			tempKindPriority++;
			tempRepCost = tempRepCost + 10;
		}
		
		
		this.preyAppr = tempPreyAppr;
		this.predFlee = tempPredFlee;
		
		this.chaseCost = tempChaseCost;
		this.fleeCost = tempFleeCost;
		
		this.perception = tempPerception;
		this.kindPriority = tempKindPriority;
		this.repCost = tempRepCost;
	}
	
	public void respond(){
		
		this.energy = this.energy - moveCost;
		//prioritize getting away from the predator
		if (predSeenAr.size() > 0){
			this.energy = this.energy - this.fleeCost;
			
			//do this each time we move
			for (int j = 0;j < this.predFlee;j++){
				// figure out which predator is closest
			
				Predator nearest = null;
				int ctr = 999;
				for (int i = 0;i < predSeenAr.size();i++){
					int manDist = Math.abs(this.locationX - this.predSeenAr.get(i).locationX) - Math.abs(this.locationY - this.predSeenAr.get(i).locationY);
					if (manDist<ctr){
					
						ctr = manDist;
						nearest = this.predSeenAr.get(i);
					
					}
				
				}
				
				//calculate the direction away from the nearest predator
				int dirX;
				int dirY;
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
				//move the microbe
				
				this.locationX = this.locationX + dirX;
				this.locationY = this.locationY + dirY;
			}
		}else if(this.preySeenAr.size() > 0){
			
			this.energy = this.energy - this.chaseCost;
			
			//do this each time we move
			for (int j = 0;j < this.preyAppr;j++){
				// figure out which prey is closest
			
				Prey nearest = null;
				int ctr = 999;
				for (int i = 0;i < preySeenAr.size();i++){
					int manDist = Math.abs(this.locationX - this.preySeenAr.get(i).locationX) - Math.abs(this.locationY - this.preySeenAr.get(i).locationY);
					if (manDist<ctr){
					
						ctr = manDist;
						nearest = this.preySeenAr.get(i);
					
					}
				
				}
				int dirX;
				int dirY;
				//calculate the direction towards from the nearest prey
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
				//move the microbe
				
				this.locationX = this.locationX - dirX;
				this.locationY = this.locationY - dirY;
			}
			
		}else if (this.micrSeenAr.size() > 0){
			
			
			
			
			//check to see if we care
			
			int roll = random.nextInt(5) + 1;
			if (roll > kindPriority){
			
			// figure out which microbe is closest
			
				Microbe nearest = null;
				int ctr = 999;
				for (int i = 0;i < micrSeenAr.size();i++){
					int manDist = Math.abs(this.locationX - this.micrSeenAr.get(i).locationX) - Math.abs(this.locationY - this.micrSeenAr.get(i).locationY);
					if (manDist<ctr){
					
						ctr = manDist;
						nearest = this.micrSeenAr.get(i);
					
					}
				
				}
				
				//calculate the direction away or towards from the nearest microbe
				int dirX;
				int dirY;
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
				if (chromosome[17]){
					
					dirX = dirX * -1;
					dirY = dirY * -1;
					
				}
				//move the microbe
				if (this.locationX + dirX >=2 && this.locationX + dirX <=38){
					this.locationX = this.locationX + dirX;
				}
				if (this.locationY + dirY >=2 && this.locationY <= 98){
					this.locationY = this.locationY + dirY;
				}
			
			}else{
				
				this.wander();
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
		this.checkEnergy();	
	}
		
	
	
	public void setChromo(int index, boolean value ){
		
		this.chromosome[index] = value;
		
	}
	
	public void checkEnergy(){
		//System.out.println(this.energy);
		if (this.energy<0){
			
			this.die();
			
			
		}else if (this.energy > this.repCost){
			
			this.twinMe = true;
			
			
		}
		
		
	}
	
	public void consume(Prey yum){
		
		
		yum.inPlay = false;
		this.energy += 10;
		
		
	}
	
	public void die(){
		
		this.inPlay = false;
		
		
	}
	
	public void wander(){
		
		//reduce energy a little
		
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
