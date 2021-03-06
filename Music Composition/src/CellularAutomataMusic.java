/*
 * Algorithmic Music Composition Software
 * @author Tom Donald Richmond
 * @version 2.0
 * @since 02/12/17
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ConcurrentModificationException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;

import javax.sound.midi.*;

public class CellularAutomataMusic  extends JFrame{
  
	private static final Color white = Color.WHITE, black = Color.BLACK;
	  
	private Board board;
	private JButton start_pause, medieval, renaissance, baroque, classical, romantic, modern;
	// variables to track total number of interval occurrences
	int t;
    // variables to track the occurrences of each interval for testing
    int[] totals = new int[8];
    // variable to hold string value representing era
    String era;
    // Boolean variable representing
    Boolean analysis = false;
	
	/* 
	* Creates blank board to feature automata, with start button to 
	* commence composition, as well as buttons to select epoch
	* */
	public CellularAutomataMusic(){
	    
		board = new Board();
		board.setBackground(white);
		    
		/* 
		* Create buttons for start/stop
		* */
		start_pause = new JButton("Compose");
		start_pause.addActionListener(board);
	    
	    /* 
	     * Create buttons for epoch selection
	     * */
	    medieval = new JButton("Medieval");
	    medieval.addActionListener(board);
	    renaissance = new JButton("Renaissance");
	    renaissance.addActionListener(board);
	    baroque = new JButton("Baroque");
	    baroque.addActionListener(board);
	    classical = new JButton("Classical");
	    classical.addActionListener(board);
	    romantic = new JButton("Romantic");
	    romantic.addActionListener(board);
	    modern = new JButton("Modern");
	    modern.addActionListener(board);
	    
	    /* 
	     * Subpanel for epoch selection
	     * */
	    JPanel subPanel = new JPanel();
	    subPanel.setLayout(new java.awt.GridLayout(6, 1));
	    subPanel.add(medieval);
	    subPanel.add(renaissance);
	    subPanel.add(baroque);
	    subPanel.add(classical);
	    subPanel.add(romantic);
		subPanel.add(modern);
	    
	    /* 
	     * Add buttons to layout
	     * */
	    this.add(board, BorderLayout.CENTER);
	    this.add(start_pause, BorderLayout.SOUTH);
	    this.add(subPanel, BorderLayout.WEST);
	    //this.setLocationRelativeTo(null);
	    
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.pack();
	    this.setVisible(true);
    
	}
  
	public static void main(String args[]){
	    new CellularAutomataMusic();
	}
  
	/*
	 * Board object featuring 4x15 Automata model, black and white values
	 * */
	private class Board extends JPanel implements ActionListener{
	    
		// Variables for board dimensions
	    private final Dimension DEFAULT_SIZE = new Dimension(15, 4);
	    private final int DEFAULT_CELL = 40, DEFAULT_INTERVAL = 100, DEFAULT_RATIO = 50;
	    private Dimension board_size;
	    private int cell_size, interval, fill_ratio;
	    
	    //boolean whether the composer is active
	    private boolean run;
	    // Timer for playing notes evenly
	    private Timer timer;
	    // variables to ensure the composer runs linearly
	    public int myOctave = 5, currentDiff = 0, range;
	    // variable to store the probability of each interval
	    double uni, step, third, fourth, fifth, sixth, seventh, octave;
	    // boolean to see if an epoch has been selected
	    boolean selected = false;
	    //grid to display automata-model
	    private Color[][] grid;
    
	    
	    /*
	     * Default constructor for Board object
	     */
	    public Board(){
	    		board_size = DEFAULT_SIZE;
	    		cell_size = DEFAULT_CELL;
			interval = DEFAULT_INTERVAL;
			fill_ratio = DEFAULT_RATIO;
			run = false;
			  
			
			grid = new Color[board_size.height + 1][board_size.width + 1];
			for (int h = 0; h < board_size.height; h++)
				for (int w = 0; w < board_size.width; w++){
					//int r = (int)(Math.random() * 100);
					//if (r >= fill_ratio)
					//grid[h][w] = black;
					//else grid[h][w] = white;
					grid[h][w] = white;
				}
			timer = new Timer(interval, this);
	    }

	    @Override
	    public Dimension getPreferredSize(){
	    		return new Dimension(board_size.height * cell_size, board_size.width * cell_size);
	    }
    
	    @Override
	    public void paintComponent(Graphics g){
	    		super.paintComponent(g);
	    		for (int h = 0; h < board_size.height; h++){
	    			for (int w = 0; w < board_size.width; w++){
	    				try{
		    				if (grid[h][w] == black)
		    					g.setColor(black);
		    				else if (grid[h][w] == white) 
		    					g.setColor(white);
		    				g.fillRect(h * cell_size, w * cell_size, cell_size, cell_size);
		    			}
	    				catch (ConcurrentModificationException cme){}
	    			}
	    		}
	    }

	    /*
	     * Method to re-adjust the probability values when new epoch is selected
	     * @param String representing epoch
	     */
	    public void changeEpoch(String epoch) {
		    	if(epoch=="medieval") {
		    		playNote(60);
		    		uni = 0.1484;
		    		step = 0.4998;
		    		third = 0.1178;
		    		fourth = 0.0371;
		    		fifth = 0.0234;
		    		sixth = 0.004;
		    		seventh = 0.0014;
		    		octave = 0.0057;
		    		range = 14;
		    		era = "Medieval";
		    	}
		    	else if(epoch=="renaissance") {
		    		playNote(62);
		    		uni = 0.2571;
		    	    step = 0.4305;
		    	    third = 0.1061;
		    	    fourth = 0.0728;
		    	    fifth = 0.048;
		    		sixth = 0.0048;
		    		seventh = 0.0006;
		    		octave = 0.0094;
		    		range = 22;
		    		era = "Renaissance";
		    	}
		    	else if(epoch=="baroque") {
		    		playNote(64);
		    		uni = 0.2623;
		    	    step = 0.3558;
		    	    third = 0.1114;
		    	    fourth = 0.0728;
		    	    fifth = 0.0442;
		    		sixth = 0.0292;
		    		seventh = 0.0108;
		    		octave = 0.0379;
		    		range = 23;
		    		era = "Baroque";
		    	}
		    	else if(epoch=="classical") {
		    		playNote(66);
		    		uni = 0.148;
		    	    step = 0.3964;
		    	    third = 0.1713;
		    	    fourth = 0.0818;
		    	    fifth = 0.0574;
		    		sixth = 0.0435;
		    		seventh = 0.0195;
		    		octave = 0.0353;
		    		range = 25;
		    		era = "Classical";
		    	}
		    	else if(epoch=="romantic") {
		    		playNote(68);
		    		uni = 0.207;
		    	    step = 0.2791;
		    	    third = 0.1112;
		    	    fourth = 0.0649;
		    	    fifth = 0.0416;
		    		sixth = 0.0282;
		    		seventh = 0.0123;
		    		octave = 0.0217;
		    		range = 30;
		    		era = "Romantic";
		    	}
		    	else if(epoch=="modern") {
		    		playNote(70);
		    		uni = 0.3086;
		    	    step = 0.2153;
		    	    third = 0.1011;
		    	    fourth = 0.1053;
		    	    fifth = 0.0723;
		    		sixth = 0.0591;
		    		seventh = 0.0364;
		    		octave = 0.0571;
		    		range = 37;
		    		era = "Modern";
		    	}	
		    	else {
		    		System.out.println("Woah, how'd you manage that bud?");
		    	}
	    }
    
	    /*
	     * Method designed to generate a new musical note value based on given previous note value
	     * @param int prevVal
	     * @returns int newVal
	     * */
	    public int ruleGenerator(int prevVal){
	    		if (prevVal == 0){
	    			return 1;
	    		}
	    	
		    	/* Sets ascLim and descLim to half of the average range of the 
		    	 * given epoch. DescLim gets the ceiling arbitrarily*/
		    	int ascLim = range/2;
		    int descLim= (range/2) + (range%2);
		      
		    double running = 0.0;
		    double value = Math.random();
		    System.out.println(value);
      
		    int newVal;
			int diff = 0;
			int direction = (int)(Math.random()*2);
  
			/* determines before each note whether it was generated to be ascending
			 * or descending. This process is regulated with ascLim and descLim */
			boolean ascending = false;
			if(direction == 1)
				ascending = true;
  
			/* Resets the valFound var to false for next note generation */
			boolean valFound = false;
  
			/* checks which range the generated number falls in and produces a
			 * note based on this value. Once note is found, valFound is set to
			 * true, and no other if statements are reached. It will access each
			 * if statement until the correct is found, increasing running total
			 * as it goes. */
			if (value <= uni){
				totals[0]+=1;
				t+=1;
				diff = 0;
				valFound = true;
				System.out.println("Unison");
			}
			running += uni;
			if ((value <= step + running) && valFound == false){
				totals[1]+=1;
				t+=1;
				diff =  1;
				valFound = true;
				System.out.println("Step");
			}
			running += step;
			if (value <= third + running && valFound == false){
				totals[2]+=1;
				t+=1;
				diff =  2;
				valFound = true;
				System.out.println("Third");
			}
			running += third;
			if (value <= fourth + running && valFound == false){
				totals[3]+=1;
				t+=1;
				diff =  3;
				valFound = true;
				System.out.println("Forth");
			}
			running += fourth;
			if (value <= fifth + running && valFound == false){
				totals[4]+=1;
				t+=1;
				diff =  4;
				valFound = true;
				System.out.println("Fifth");
			}
			running += fifth;
			if (value <= sixth + running && valFound == false){
				totals[5]+=1;
				t+=1;
				diff =  5;
				valFound = true;
				System.out.println("Sixth");
			}
			running += sixth;
			if (value <= seventh + running && valFound == false){
				totals[6]+=1;
				t+=1;
				diff =  6;
				valFound = true;
				System.out.println("Seventh");
			}
			running += seventh;
			if (value <= octave + running && valFound == false){
				totals[7]+=1;
				t+=1;
				diff =  7;
				valFound = true;
	        		System.out.println("Octave");
			}
			
			//System.out.println((currentDiff+diff) +": total diff");
			if (ascending && currentDiff + diff >= ascLim) {
				System.out.println("Switched, too high");
				ascending = false;
			}
			if (!ascending && -1*(currentDiff - diff) >= descLim) {
				System.out.println("Switched, too low");
				ascending = true;
			}
			System.out.println("Ascending = "+ascending);
			if(ascending){
				currentDiff += diff;
				System.out.println(currentDiff);
				newVal = prevVal;
				for (int i = 0; i < diff; i++){
					if (newVal == 5 || newVal == 12)
						newVal += 1;
					else
						newVal += 2;
					if (newVal > 12) {
						myOctave++;
						newVal -= 12;
					}
				}
			}
			else{
				currentDiff -= diff;
				System.out.println(currentDiff);
				newVal = prevVal;
				for (int i = 0; i < diff; i++){
					if (newVal == 6 || newVal == 13 || newVal == 1)
						newVal -= 1;
					else
						newVal -= 2;
					if (newVal < 1) {
						newVal += 12;
						myOctave--;
					}
				}
			}
			System.out.println(newVal + " " + ascending);
			int noteVal = toNote(newVal, ascending);
	      
			//System.out.println(prevVal);
			//newVal = 1+((int)(Math.random()*12));
			return noteVal;
	    }
	    
	    /*
	     * Method designed to generate a new musical note value based on given previous note value
	     * @param int prevVal
	     * @returns int newVal
	     * */
	    public void ruleGeneratorAnalysis(){
		      
	    		double running = 0.0;
		    double value = Math.random();
  
			/* Resets the valFound var to false for next note generation */
			boolean valFound = false;
  
			/* checks which range the generated number falls in and produces a
			 * note based on this value. Once note is found, valFound is set to
			 * true, and no other if statements are reached. It will access each
			 * if statement until the correct is found, increasing running total
			 * as it goes. */
			if (value <= uni){
				totals[0]+=1;
				t+=1;
				valFound = true;
			}
			running += uni;
			if ((value <= step + running) && valFound == false){
				totals[1]+=1;
				t+=1;
				valFound = true;
			}
			running += step;
			if (value <= third + running && valFound == false){
				totals[2]+=1;
				t+=1;
				valFound = true;
			}
			running += third;
			if (value <= fourth + running && valFound == false){
				totals[3]+=1;
				t+=1;
				valFound = true;
			}
			running += fourth;
			if (value <= fifth + running && valFound == false){
				totals[4]+=1;
				t+=1;
				valFound = true;
			}
			running += fifth;
			if (value <= sixth + running && valFound == false){
				totals[5]+=1;
				t+=1;
				valFound = true;
			}
			running += sixth;
			if (value <= seventh + running && valFound == false){
				totals[6]+=1;
				t+=1;
				valFound = true;
			}
			running += seventh;
			if (value <= octave + running && valFound == false){
				totals[7]+=1;
				t+=1;
	        		valFound = true;
			}
			
			/* When the composer has generated 100 notes, 
			 * it automatically calculates the results and prints 
			 * for analysis process */
			if(t==100) {
				System.out.println(kernResults());
				//JOptionPane.showMessageDialog(null,kernResults());
				clearStats();
			}
	    }
    
	    /*
	     * Method that takes note value representation from binary as integer, prints corresponding
	     * value and plays note using MIDI output
	     * @param int val - Value of note (1-13) generated by the rule system
	     * @returns String letter value equivelant to corresponding int value
	     * */
	    public int toNote(int val, Boolean asc){
	    		int noteVal;
	        int C = myOctave * 12;
	        
	        if(val == 1 || val == 13){
		        	noteVal = C+0;
		        	System.out.println("C");
	        }
	        else if(val == 2){
		        	noteVal = C+1;
		        	System.out.println("C#/D-");
	        }
	        else if(val == 3){
		        	noteVal = C+2;
		        	System.out.println("D");
	        }
	        else if(val == 4){
		        	noteVal = C+3;
		        	System.out.println("D#/E-");
	        }
	        else if(val == 5){
		        	noteVal = C+4;
		        	System.out.println("E");
	        }
	        else if(val == 6){
		        	noteVal = C+5;
		        	System.out.println("F");
	        }
	        else if(val == 7){
		        	noteVal = C+6;
		        	System.out.println("F#/G-");
	        }
	        else if(val == 8){
		        	noteVal = C+7;
		        	System.out.println("G");
	        }
	        else if(val == 9){
		        	noteVal = C+8;
		        	System.out.println("G#/A-");
	        }
	        else if(val == 10){
		        	noteVal = C+9;
		        	System.out.println("A");
	        }
	        else if(val == 11){
		        	noteVal = C+10;
		        	System.out.println("A#/B-");
	        }
	        else if(val == 12){
		        	noteVal = C+11;
		        	System.out.println("B");
	        }
	        else {
	        		return 0;
	        }
	        //System.out.println(noteVal);
	        playNote(noteVal);
	        return val;
	    }
    
	    /*
	     * (non-Javadoc)
	     * Action Listener for all buttons, compose, terminate, medieval,
	     * renaissance, baroque, classical, romantic and modern.
	     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	     */
	    public void actionPerformed(ActionEvent e) {
		    	
		    //reads binary value of last sequence
		    	int a = 0, b = 0, c = 0, d = 0, val = 0;
		      
		    	//counts binary from board for conversion to decimal
		    	if (grid[0][board_size.width-1]  == black)
		    		a = 1;
		    	if (grid[1][board_size.width-1]  == black)
		    		b = 1;
		    	if (grid[2][board_size.width-1]  == black)
		    		c = 1;
		    	if (grid[3][board_size.width-1]  == black)
		    		d = 1;
		    
		    	//converts binary sequence into decimal with variable val
		    	if(a==1)
		    		val+=8;
		    	if(b==1)
		    		val+=4;
		    	if(c==1)
		    		val+=2;
		    	if(d==1)
		    		val+=1;
		      
		    	//shifts bottom n-1 sequences up to make room for next sequence
		    	for (int h = 0; h < board_size.height; h++){
		    		for (int w = 0; w < board_size.width-1; w++){
		    			grid[h][w] = grid[h][w+1];
		    		}
		    	}
	      
		    	//repaints the bottom line sequence based on rule
		    	if (e.getSource().equals(timer) && analysis == false){
		    		int newNote = ruleGenerator(val);
		    		
		    		if (newNote >= 8){
		    			grid[0][board_size.width-1] = black;
		    			newNote = newNote-8;
		    		}
		    		else
		    			grid[0][board_size.width-1] = white;
		    		if (newNote >= 4){
		    			grid[1][board_size.width-1] = black;
		    			newNote = newNote-4;
		    		}
		    		else
		    			grid[1][board_size.width-1] = white;
		    		if (newNote >= 2){
		    			grid[2][board_size.width-1] = black;
		    			newNote = newNote-2;
		    		}
		    		else
		    			grid[2][board_size.width-1] = white;
		    		if (newNote >= 1){
		    			grid[3][board_size.width-1] = black;
		    			newNote = newNote-1;
		    		}
		    		else
		    			grid[3][board_size.width-1] = white;
		    		repaint();
		    		Color[][] newGrid = new Color[board_size.height][board_size.width];
		    	}
		    	
		    	//repaints the bottom line sequence based on rule
		    	if (e.getSource().equals(timer) && analysis == true){
		    		ruleGeneratorAnalysis();
		    	}
	      
			//Start-Pause button processing
			else if(e.getSource().equals(start_pause)){
			    	if(run){
			    		timer.stop();
			    		//JOptionPane.showMessageDialog(null,printResults());
			    		JOptionPane.showMessageDialog(null,printResults());
			    		start_pause.setText("Compose");
			    }
			    else {
			        	if (selected) {
			        		timer.restart();
			        		start_pause.setText("Terminate");
			    }
			    else {
			        		JOptionPane.showMessageDialog(null, "Must first select an epoch from which to compose");
			        		run = !run;
			    }
			}
			run = !run;
			}
		    	
		    	//Medieval button processing
			else if(e.getSource().equals(medieval)){
				medieval.setEnabled(false);
			    renaissance.setEnabled(true);
			    baroque.setEnabled(true);
			    classical.setEnabled(true);
			    romantic.setEnabled(true);
			    modern.setEnabled(true);
			    changeEpoch("medieval");
			    selected = true;
			}
		    	//Renaissance button processing
			else if(e.getSource().equals(renaissance)){
			    medieval.setEnabled(true);
			    renaissance.setEnabled(false);
			    baroque.setEnabled(true);
			    classical.setEnabled(true);
		        romantic.setEnabled(true);
		        modern.setEnabled(true);
		        changeEpoch("renaissance");
		        selected = true;
		    }
		    	//Baroque button processing
		    else if(e.getSource().equals(baroque)){
		        medieval.setEnabled(true);
		        renaissance.setEnabled(true);
		        baroque.setEnabled(false);
		        classical.setEnabled(true);
		        romantic.setEnabled(true);
		        modern.setEnabled(true);
		        changeEpoch("baroque");
		        selected = true;
		    }
		    	//Classical button processing
		    else if(e.getSource().equals(classical)){
		        medieval.setEnabled(true);
		        renaissance.setEnabled(true);
		        baroque.setEnabled(true);
		        classical.setEnabled(false);
		        romantic.setEnabled(true);
		        modern.setEnabled(true);
		        changeEpoch("classical");
		        selected = true;
		    }
		    	//Romantic button processing
		    else if(e.getSource().equals(romantic)){
		        medieval.setEnabled(true);
		        renaissance.setEnabled(true);
		        baroque.setEnabled(true);
		        classical.setEnabled(true);
		        romantic.setEnabled(false);
		        modern.setEnabled(true);
		        changeEpoch("romantic");
		        selected = true;
		    }
		    	//Modern button processing
		    else if(e.getSource().equals(modern)){
		        medieval.setEnabled(true);
		        renaissance.setEnabled(true);
		        baroque.setEnabled(true);
		        classical.setEnabled(true);
		        romantic.setEnabled(true);
		        modern.setEnabled(false);
		        changeEpoch("modern");
		        selected = true;
		    }
	    }
	}
  
	/*
	 * Method to play note value using MIDI synthesizer based upon input note
	 * @param int representing the MIDI value of desired note.
	 */
	public void playNote(int i) { 
	    try{
	    	/* Create a new Synthesizer and open it. 
	    	 */
		    	Synthesizer midiSynth = MidiSystem.getSynthesizer(); 
		    	midiSynth.open();
		    	
		    	//get and load default instrument and channel lists
		    	Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
		    	MidiChannel[] mChannels = midiSynth.getChannels();
		      
		    	midiSynth.loadInstrument(instr[0]);//load an instrument
		    	mChannels[0].noteOff(i);//turn off the previous note
		    	mChannels[0].noteOn(i, 120);//On channel 0, play note number i with velocity 120
		    	try {
		    		//Following line controls duration of notes played. 1000 used for samples of 30 seconds. 750 used for samples of 15 seconds
		    		Thread.sleep(750); // wait time in milliseconds to control duration
		    	}
		    	catch( InterruptedException e ) {}
	    } 
	    catch (MidiUnavailableException e) {}
	}
  
	/*
	 * method that returns string that prints composition statistics for visual analysis
	 * @returns String statistics
	 */
	public String printResults() {
		return "Total length of composition: "+t+"\n"
				+"\tStatistics:\n"
				+"\nUnison:\t "+((double)totals[0]/t)
				+"\nStep:\t "+((double)totals[1]/t)
				+"\nThird:\t "+((double)totals[2]/t)
				+"\nForth:\t "+((double)totals[3]/t)
				+"\nFifth:\t "+((double)totals[4]/t)
				+"\nSixth:\t "+((double)totals[5]/t)
				+"\nSeventh:\t "+((double)totals[6]/t)
				+"\nOctave:\t "+((double)totals[7]/t);
	}
	
	/*
	 * method that returns string that prints composition statistics for analysis
	 * @returns String statistics
	 */
	public String kernResults() {
		//variable to store percentage of most common interval
		int max = 0;
		
		// computes the most common interval
		for(int i = 0; i<8;i++) {
			if(totals[i] > max){
				max = totals[i];
			}
		}
		
		//returns expected String output based on totals array and above computation
		return ""+((double)totals[0]/t)
				+","+((double)totals[1]/t)
				+","+((double)totals[2]/t)
				+","+((double)totals[3]/t)
				+","+((double)totals[4]/t)
				+","+((double)totals[5]/t)
				+","+((double)totals[6]/t)
				+","+((double)totals[7]/t)
				+","+((double)max/t)
				+","+era;
	}
	
	/*
	 * Method to clear the statistics after terminations for next composition
	 */
	public void clearStats() {
		//loops through all saved data and resets to 0 for future processing
		for (int i = 0; i < 8; i++) {
			totals[i] = 0;
		}
		t = 0;
	}
}