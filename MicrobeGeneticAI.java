//Jonathan Chase
//4-26-2015
//MicrobeGeneticAI.java



import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.JFrame;

//import javax.swing.JTable;
import java.util.*;
//import java.io.*;




public class MicrobeGeneticAI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Making the buttons and text area
	  JButton btnStart = new JButton("Pause");
	  
	  JButton btnAdvance = new JButton(">>");
	  JButton btnPrevious = new JButton("<<");
	  JButton btnSave = new JButton("Faster");
	  JButton btnLoad = new JButton("Slower");
	  
	  
	  Timer timer = new Timer(100, this);
	  Random random = new Random();
	  String[] headers = new String[60];
	  String[][] screen = new String[55][115];
	  JTextArea areaScreen = new JTextArea();
	  
	  int displayCtr = 0;
	  int tickCtr = 1;
	  int speed = 10;
	  
	  
	  ArrayList<Microbe> microbesAr = new ArrayList<Microbe>();
	  ArrayList<Prey> preyAr = new ArrayList<Prey>();
	  ArrayList<Predator> predatorAr = new ArrayList<Predator>();
	  boolean lastToRep[] = new boolean[18];
	 
	  int chromSize = 18;
	  int mutChance = 5;
	  int preyVal = 40;
	  int predNum = 2;
	  
			  
	  
	  //create the array
	  
	  String asciiFrames = "";
	  
	  
	  
	    
	  
	//make global properties
	  	  int curFrame = 0;
	  	  boolean playing = true;
	  	  int ticks = 0;
	  	  
	  
	  
	public MicrobeGeneticAI(){
		
		super("MicrobeGeneticAI");
		
		//generate predators and prey
		for (int i=0;i<20;i++){
			
			this.preyAr.add(new Prey());
			
		}
		
		for (int i=0;i<predNum;i++){
			
			this.predatorAr.add(new Predator());
			
		}
		
		//generate default chromosome
		for (int i = 0;i < this.lastToRep.length;i++){
			
			this.lastToRep[i] = false;
			
		}
		
		//create a single microbe
		
		this.microbesAr.add(new Microbe());
		
		//initiate the screen
		
		JPanel buttons = new JPanel();
		//add buttons to my panel
		buttons.setLayout(new FlowLayout());
		buttons.add(btnPrevious);
		buttons.add(btnStart);
		buttons.add(btnAdvance);
		buttons.add(btnSave);
		buttons.add(btnLoad);
		
		
		//start the timer
		
		timer.start();
		
		
		
		
		
		//set up main layout
		Container shebang = this.getContentPane();
		shebang.setLayout(new BorderLayout());
		shebang.add(areaScreen, BorderLayout.CENTER);
		areaScreen.setLineWrap(true);
		shebang.add(buttons, BorderLayout.SOUTH);
		
		
		//set parameters of window
		this.setSize(1000, 750);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.registerListeners();
		
					
		}
		
	
	public void checkRep(){
		
		for (int i=0;i<this.microbesAr.size();i++){
			
			if (this.microbesAr.get(i).inPlay == true){
				
				if (this.microbesAr.get(i).twinMe == true){
					//set twinMe to not true
					this.microbesAr.get(i).twinMe = false;
					// create a new microbe
					this.microbesAr.add(new Microbe());
				//	placeMicrobe(this.microbesAr.get(this.microbesAr.size()-1),this.microbesAr.get(i));
					//grab the chromosome of the microbe and check for mutation
					for (int j = 0;j < this.microbesAr.get(i).chromosome.length;j++){
						boolean mutation = false;
						lastToRep[j] = this.microbesAr.get(i).chromosome[j];
						int roll = random.nextInt(100) + 1;
						if (roll <= mutChance){
							
							mutation = true;
							
						}
						
						if (mutation){
							
							this.microbesAr.get(this.microbesAr.size()-1).chromosome[j]  = !this.microbesAr.get(i).chromosome[j];
							
						}else{
							
							this.microbesAr.get(this.microbesAr.size()-1).chromosome[j]  = this.microbesAr.get(i).chromosome[j];
							
						}
						
						
						
					}
					//place the new microbe in the same place as the parent
					this.microbesAr.get(this.microbesAr.size()-1).locationX = this.microbesAr.get(i).locationX;
					this.microbesAr.get(this.microbesAr.size()-1).locationY = this.microbesAr.get(i).locationY;
					//both microbes will have energy equal to 1/4 the energy total of the parent
					int origEnergy = this.microbesAr.get(i).energy;
					this.microbesAr.get(i).energy = origEnergy/3;
					this.microbesAr.get(this.microbesAr.size()-1).energy = (origEnergy*10)/45;
					
					//calculate stats for the new microbe
					this.microbesAr.get(this.microbesAr.size()-1).generateStats();
					
					
					
				}
				
				
			}
			
			
		}
			
		
	}
	
	public void clearScreen(){
		
		for (int x = 0;x <40;x++){
			  for (int y = 0; y <100;y++){
				  
				  screen[x][y] = "   ";
				  
			  }
		  }
		
		
	}
	
	public void killThemAll(){
		
		
		
		
		for (int i=0;i<microbesAr.size();i++){
			
				if (microbesAr.get(i).inPlay == false){
					
					microbesAr.remove(i);
					
				}
			
		}
		
		while (this.microbesAr.size() > 300){
			
			microbesAr.remove(0);
			
		}
		
		for (int i=0;i<preyAr.size();i++){
			
			if (preyAr.get(i).inPlay == false){
				
				preyAr.remove(i);
				preyAr.add(new Prey());
			}
		}
		
		if (this.microbesAr.size()<1){
			
			this.microbesAr.add(new Microbe());
			for (int i = 0;i < this.microbesAr.get(0).chromosome.length;i++){
				
				this.microbesAr.get(0).chromosome[i] = lastToRep[i];
				
			}
			
		}
	}
		
	
	
	
	public static void main(String[] args) {
		
		//create initial array of strings for the content of the frames
		
		
				
		new MicrobeGeneticAI();
		
		
		
		//end main, pretty simple
		
	}
	
	public void gatherState(){
		// gather info for microbes
		for (int i=0;i < microbesAr.size();i ++){
			microbesAr.get(i).predSeenAr.clear();
			microbesAr.get(i).preySeenAr.clear();
			microbesAr.get(i).micrSeenAr.clear();
			for (int j = 0;j<predatorAr.size();j++){
				int manDist = (Math.abs(microbesAr.get(i).locationX - predatorAr.get(j).locationX) + Math.abs(microbesAr.get(i).locationY - predatorAr.get(j).locationY));
				
				if (manDist <= microbesAr.get(i).perception){
					
					microbesAr.get(i).predSeenAr.add(predatorAr.get(j));
					
				}
				
			}
			
			for (int j = 0;j<preyAr.size();j++){
				int manDist = (Math.abs(microbesAr.get(i).locationX - preyAr.get(j).locationX) + Math.abs(microbesAr.get(i).locationY - preyAr.get(j).locationY));
				
				if (manDist <= microbesAr.get(i).perception){
					
					microbesAr.get(i).preySeenAr.add(preyAr.get(j));
					
				}
				
			}
			
			for (int j = 0;j<microbesAr.size();j++){
				if (i != j){
					int manDist = (Math.abs(microbesAr.get(i).locationX - microbesAr.get(j).locationX) + Math.abs(microbesAr.get(i).locationY - microbesAr.get(j).locationY));
					if (manDist <= microbesAr.get(i).perception){
						
						microbesAr.get(i).micrSeenAr.add(microbesAr.get(j));
				
					}
				
				}
				
			}
		}
		
		//gather info for predators
		
		for (int i = 0; i<predatorAr.size();i++){
			
			predatorAr.get(i).micrSeenAr.clear();
			
			for (int j = 0;j<microbesAr.size();j++){
				if (i != j){
					int manDist = (Math.abs(predatorAr.get(i).locationX - microbesAr.get(j).locationX) + Math.abs(predatorAr.get(i).locationY - microbesAr.get(j).locationY));
					if (manDist <= predatorAr.get(i).perception){
						
						predatorAr.get(i).micrSeenAr.add(microbesAr.get(j));
				
					}
				
				}
				
			}
			
		}
		
		
		
	}

	public void checkCollisions(){
		
		//check to see if predators have caught a microbe
		
		for (int i = 0;i<this.predatorAr.size();i++){
			
			for (int j = 0; j < this.microbesAr.size();j++){
				
				if (this.predatorAr.get(i).locationX == this.microbesAr.get(j).locationX){
					
					if (this.predatorAr.get(i).locationY == this.microbesAr.get(j).locationY){
						
						this.microbesAr.get(j).inPlay = false;
						
					}
					
				}
				
			}
			
		}
		
		//check to see if a microbe has caught a prey
		for (int i = 0;i<this.microbesAr.size();i++){
			// check to make sure the microbe is not on the edge. if it is, kill it
			
			if (this.microbesAr.get(i).locationX < 1 || this.microbesAr.get(i).locationX > 39){
				
				if (this.microbesAr.get(i).locationY < 1 || this.microbesAr.get(i).locationY > 99){
					
					this.microbesAr.get(i).inPlay = false;
				}
				
			}
			
			for (int j = 0; j < this.preyAr.size();j++){
				
				if (this.microbesAr.get(i).locationX == this.preyAr.get(j).locationX){
					
					if (this.microbesAr.get(i).locationY == this.preyAr.get(j).locationY){
						//System.out.println("Yum!");
						this.preyAr.get(j).inPlay = false;
						this.microbesAr.get(i).energy = this.microbesAr.get(i).energy + preyVal;
					}
					
				}
				
			}
			
		}
		killThemAll();
		
	}
	
	public void actionPerformed(ActionEvent e){
		
	//	System.out.println(e.getActionCommand());
		//assign appropriate response to event
	   if (e.getSource() == btnPrevious){
			doPrevious();
		
		} else if (e.getSource() == btnAdvance){
			doAdvance();
		} else if (e.getSource() == btnPrevious){
		//	doPrevious();
		} else if (e.getSource() == btnSave){
			doSave();
		} else if (e.getSource() == btnLoad){
			doLoad();
		
		} else if (e.getSource() == btnStart){
			doStart();
		} else if (e.getSource() == timer){
			Tick();
		} else {
			areaScreen.setText("something went wrong");
		} // end if
		
		
	} 
	
	public void Tick(){
		this.tickCtr++;
		if (tickCtr >= speed){
			tickCtr = 1;
		
			if (playing){
		
				gatherState();
		
				for (int i=0;i < this.preyAr.size();i++){
			
					this.preyAr.get(i).wander();
						
			
			
				}
		
				for (int i=0;i < this.predatorAr.size();i++){
			
					this.predatorAr.get(i).respond();
						
			
			
				}
		
				for (int i=0;i < this.microbesAr.size();i++){
			
					this.microbesAr.get(i).respond();
						
			
			
				}
		
				checkCollisions();
				checkRep();
		
			
		
			}
		}
		this.showScreen();
	}
	
	public void showScreen(){
		
		clearScreen();
		
		//locate predators and prey
		
		for (int i=0;i < preyAr.size();i++){
			
			this.screen[preyAr.get(i).locationX][preyAr.get(i).locationY] = " f ";
			
		}
		
		for (int i=0;i < predatorAr.size();i++){
			
			this.screen[predatorAr.get(i).locationX][predatorAr.get(i).locationY] = " P ";
			
		}
		
		for (int i=0;i < microbesAr.size();i++){
			
			if (i == this.displayCtr){
				
				this.screen[microbesAr.get(i).locationX][microbesAr.get(i).locationY] = ">#< ";
				
			}else{
				this.screen[microbesAr.get(i).locationX][microbesAr.get(i).locationY] = " @ ";
			}
		}
		if (displayCtr <= this.microbesAr.size() -1){
			this.screen[microbesAr.get(displayCtr).locationX][microbesAr.get(displayCtr).locationY] = ">#< ";
		}else{
			
			displayCtr = 0;
			
		}
			String tempString = "";;
		
		for (int x = 0;x < 40;x++){
			String myString = "";
			for (int y = 0;y < 100; y++){
				
				myString = myString + screen[x][y];
				
				
				
			}
			tempString = tempString + myString + "\r\n";
		}
		
		if (this.displayCtr >= this.microbesAr.size()){
			this.displayCtr = 0;
		}
			int tempKP = this.microbesAr.get(displayCtr).kindPriority;
			if (this.microbesAr.get(displayCtr).chromosome[17]){
				
				tempKP = tempKP * -1;
				
			}
			
			tempString = tempString + "MICROBE: " + this.displayCtr;
			tempString = tempString + "  Energy: " + this.microbesAr.get(displayCtr).energy;
			tempString = tempString + "  PreyAppr: " + this.microbesAr.get(displayCtr).preyAppr;
			tempString = tempString + "  PredFlee: " + this.microbesAr.get(displayCtr).predFlee;
			tempString = tempString + "  ChaseCost: " + this.microbesAr.get(displayCtr).chaseCost;
			tempString = tempString + "  FleeCost: " + this.microbesAr.get(displayCtr).fleeCost;
			tempString = tempString + "  Perception: " + this.microbesAr.get(displayCtr).perception;
			
			tempString = tempString + "  KindPriority: " + tempKP;
			tempString = tempString + "  RepCost: " + this.microbesAr.get(displayCtr).repCost + "\r\n";
			
			String myString = "";
			for (int k = 0;k < this.microbesAr.get(displayCtr).chromosome.length;k++){
				if (this.microbesAr.get(displayCtr).chromosome[k]){
					
					myString = myString + k +  ":1   ";
					
				}else{
					
					myString = myString + k +":0   ";
					
				}
				
			}
			tempString = tempString + myString;
			
		
		
		
		this.areaScreen.setText(tempString);
		
		
		
		
	}
	
	public void doAdvance() {
		
	//make sure we are not going out of bounds, if so, switch to 0
		
		if (this.displayCtr + 1 >= this.microbesAr.size()){
			
			displayCtr = 0;
			
		}else{
			
			displayCtr++;
			
		}
	}

	public void doPrevious(){
		
		//make sure we are not out of bounds, if we are, set to last microbe
		
		if (this.displayCtr <= 0){
			
			displayCtr = this.microbesAr.size() - 1;
			
		}else{
			
			displayCtr--;
			
		}
		
	}
	
public void doSave(){
		if (speed <=1){
			
			speed = 1;
			
		}else{
			
			speed = speed - 2;
			
		}
	}

public void doLoad(){
	
	if (speed >=100){
		
		speed = 100;
		
	}else{
		
		speed = speed + 2;
		
	}
}

	
	
	public void doStart(){
		
		if (playing){
			
			playing = false;
			
		}else{
			
			playing = true;
			
		}
		
		
		
		
		
	}
	
	
	
	public void registerListeners(){
		//register all buttons to self
		btnPrevious.addActionListener(this);
		btnStart.addActionListener(this);
		btnAdvance.addActionListener(this);
		btnSave.addActionListener(this);
		btnLoad.addActionListener(this);
		
		//timer.addActionListener(this);
	} // end registerListeners
	
	
		
	

}
