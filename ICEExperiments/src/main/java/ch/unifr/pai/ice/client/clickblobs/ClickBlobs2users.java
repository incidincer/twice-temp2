package ch.unifr.pai.ice.client.clickblobs;
/*
 * Copyright 2013 Pascal Bruegger
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/***************************************************************************************************
 *  Project ICE 
 *  -----------
 *  PAI research Group - Dept. of Informatics 
 *  University of Fribourg - Switzerland
 *  Author: Pascal Bruegger 
 * 
 *************************************************************************************************/

import java.util.Collection;
import java.util.Vector;

import ch.unifr.pai.ice.client.ICEMain;
import ch.unifr.pai.ice.client.RequireInitialisation;
import ch.unifr.pai.ice.client.rpc.EventingService;
import ch.unifr.pai.ice.client.rpc.EventingServiceAsync;
import ch.unifr.pai.ice.client.tracking.CursorXY;
import ch.unifr.pai.ice.client.utils.ICEDataLogger;
import ch.unifr.pai.ice.shared.ExperimentIdentifier;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ClickBlobs2users extends VerticalPanel implements ICEDataLogger, RequireInitialisation { 

	int nbIteration = 20;
	int nbUser = 0;
	int iteration = 0;
	int nbExpFinished = 0; 
	boolean init = false;
	
	long finishTime;
	
	String blob1 = GWT.getModuleBaseURL() + "circle_black.png";
	String blob2 = GWT.getModuleBaseURL() + "circle_red.png";
	String blob3 = GWT.getModuleBaseURL() + "circle_blue.png";
	String blob4 = GWT.getModuleBaseURL() + "circle_green.png";
	String blob5 = GWT.getModuleBaseURL() + "circle_magenta.png";
	String blob6 = GWT.getModuleBaseURL() + "circle_violet.png";
	String blob7 = GWT.getModuleBaseURL() + "circle_grey.png";
	String blob8 = GWT.getModuleBaseURL() + "circle.png";

	//AbsolutePanel absPanel;
	
	Vector<CursorXY> userLogVector = new Vector<CursorXY>();
	
	Vector<CursorXY> user1LogVec = new Vector<CursorXY>();  
	Vector<CursorXY> user2LogVec = new Vector<CursorXY>();  
	String [] user1result; 
	String [] user2result; 
	
	String[][] loggedData;
		
	Blob user1;
	Blob user2;

	int panelWidth;
	int panelHeight;
	int nbBlobs;
	boolean randomPos;

	// statically set the position of the blobs
	int[][] blobCoord; 
	
	AbsolutePanel user1Panel = new AbsolutePanel();
	AbsolutePanel user2Panel = new AbsolutePanel();
	AbsolutePanel user3Panel = new AbsolutePanel(); 
	AbsolutePanel user4Panel = new AbsolutePanel(); 
	
	HorizontalPanel hPanel1 = new HorizontalPanel(); 
	HorizontalPanel hPanel2 = new HorizontalPanel(); 
	
	
	
	/***************************************************************************
	 * Constructor
	 * @param nbBoxes : define the number of blobs
	 * @param randomPosition : define if users must be placed randomly or not
	 ***************************************************************************/
	public ClickBlobs2users(int nbBlobs, boolean randomPosition) {
		super();
		this.setSize("100%", "100%");
		this.nbBlobs = nbBlobs;
		
		hPanel1.setSize("100%", "100%"); 
		hPanel2.setSize("100%", "100%"); 
		
		user1Panel.setSize("100%", "100%");
		user2Panel.setSize("100%", "100%");
		user3Panel.setSize("100%", "100%"); 
		user4Panel.setSize("100%", "100%"); 
		
		
		hPanel1.add(user1Panel);  
		hPanel1.setCellWidth(user1Panel, "50%"); 
		hPanel1.setCellHeight(user1Panel, "50%"); 
		hPanel1.add(user3Panel); 
		
		hPanel2.add(user4Panel); 
		hPanel2.setCellWidth(user4Panel, "50%"); 
		hPanel2.setCellHeight(user4Panel, "50%"); 
		hPanel2.add(user2Panel); 
		
		this.add(hPanel1); 
		this.add(hPanel2); 
		
		//this.add(user1Panel); 
		//this.add(user2Panel); 
		
		// forces to maintain the size of the cell
		//this.setCellHeight(user1Panel, "50%"); 
		this.setBorderWidth(1);
		
		hPanel1.setBorderWidth(1); 
		hPanel2.setBorderWidth(1); 
	}

	
	@Override
	public void initialise() {

		if (!init){
		
			panelWidth = user1Panel.getElement().getOffsetWidth();
			panelHeight = user1Panel.getElement().getOffsetHeight();

//		// statically set the position of the 10 blobs
//			blobCoord = new int[][] { 
//					{ panelWidth - 100, panelHeight - 100 },
//					{ (panelWidth / 2)-40, 70 }, 
//					{ 50, panelHeight - 100 }, 
//					{ 100, 70 },
//					{ panelWidth - 130, 90 }, 
//					{ panelWidth - 110, panelHeight - 180 },
//					{ 140, panelHeight / 2 }, 
//					{ panelWidth / 2, panelHeight - 100 },
//					{ (panelWidth / 2) - 50, (panelHeight / 2) - 30 }, 
//					{ 200, 110 },			
//					{ (panelWidth / 2) + 80, panelHeight - 100 },
//					{ (panelWidth / 4) * 3, panelHeight - 100 }, 
//					{ (panelWidth / 4), panelHeight - 120 }, 
//					{ (panelWidth / 4) * 3, panelHeight / 2 }, 
//					{ (panelWidth / 4),  panelHeight - 130 }, 
//					{ panelWidth - 110, panelHeight - 180 },
//					{ 140, panelHeight / 3 }, 
//					{ panelWidth / 2, panelHeight /3 },
//					{ panelWidth  - 70, (panelHeight / 3) }, 
//					{ panelWidth  - 80, (panelHeight / 3) * 2 },
//			};
			
			// set the blob position array around a circle
			blobCoord = initBlobPosInCircle(nbBlobs, panelWidth / 2, panelHeight / 2, (panelHeight/ 2)-50);
			
			setUsers();
			init = true;
		}
	}
	
	private void setUsersRandom() {

		user1 = new Blob(blob1, "User 1", iteration, user1Panel, this, true);
		user1Panel.add(user1);
		user1Panel.setWidgetPosition(user1, randomNum(0, panelWidth - 70),
				randomNum(0, panelHeight - 70));

		user2 = new Blob(blob2, "User 2", iteration, user2Panel, this, true);
		user2Panel.add(user2);		
		user2Panel.setWidgetPosition(user2, randomNum(0, panelWidth - 70),
				randomNum(0, panelHeight - 70));
		
		nbUser = 2;
	}

	private void setUsers() {
				
		user1 = new Blob(blob1, "User 1", iteration, user1Panel, this, blobCoord, 0,0, 0, true);
		user1Panel.add(user1);
		//user1Panel.setWidgetPosition(user1, panelWidth / 2, panelHeight / 2); 
		user1Panel.setWidgetPosition(user1, blobCoord[0][0], blobCoord[0][1]);
		user1.setBlobNumber(0);
		
		user2 = new Blob(blob2, "User 2", iteration, user2Panel, this, blobCoord, 0,0, 0, true); //arrayOffset= 1
		user2Panel.add(user2);		
		//user2Panel.setWidgetPosition(user2, panelWidth / 2, panelHeight / 2); 
		user2Panel.setWidgetPosition(user2, blobCoord[0][0], blobCoord[0][1]);
		user2.setBlobNumber(0);
		
		nbUser = 2;
		
		//PRINTING LOGS//
			System.out.println("Experiment Identifier: " + ICEMain.identifier);
			System.out.println("Name of Experiment Task: " + ExperimentIdentifier.CLICKBLOB);
			System.out.println("User Number: " + nbUser);
			System.out.println();
	}

	
	/**
	 * Callback method used by the Blob class to send the vector of data to be logged.
	 * @param blobData
	 */
	public void setLoggedData(Vector<CursorXY> blobData , boolean finished){ 
		
		if (nbExpFinished <= nbUser){

			if(finished) {
				
				userLogVector.addAll(blobData);
				nbExpFinished++;
			}
			
			else { 
				userLogVector.add(blobData.lastElement());
			}
		}
		
		if (nbExpFinished == nbUser){
			
			if(user1.setFinishTime >= user2.setFinishTime){ // if User2 finishes the actual set first or if they finish at the same time
				
				finishTime = user1.setFinishTime;
			}

			else{ //if user1 finishes the actual set first
				
				finishTime = user2.setFinishTime;
			}
			
			loggedData = new String[userLogVector.size()][5];
			
			for (int i = 0; i < loggedData.length; i++){
				loggedData[i][0] = userLogVector.get(i).getUser();
				loggedData[i][1] = String.valueOf(userLogVector.get(i).getX());
				loggedData[i][2] = String.valueOf(userLogVector.get(i).getY());
				loggedData[i][3] = String.valueOf(userLogVector.get(i).getTimeStamp());
				loggedData[i][4] = String.valueOf(userLogVector.get(i).getblobNumber());
				
			}
			
			System.out.println("-------------------------------------------------------------------");
			System.out.println("******TASK FINISHED******");
			for (int i = 0; i < loggedData.length; i++) {   
				System.out.println( i + ".  user:" + loggedData[i][0] + ";  " + 
				"x:"+ loggedData[i][1] + ";  " +
				"y:"+loggedData[i][2]  + ";  " +
				"time:" +loggedData[i][3] + ";  " +
				"blobNo:" +loggedData[i][4]);
				
				}
			
			System.out.println("");
			System.out.println("-------------------------------------------------------------------");
			System.out.println("User1; " + user1.count + " + "  + user1.trialcount + " times out of blob");
			System.out.println("");
			System.out.println("User2; " + user2.count + " + "  + user2.trialcount + " times out of blob");
			System.out.println("-------------------------------------------------------------------");
			
			System.out.println("User1; Start time:"+ user1.startTime + " ;  Finish time:"+ finishTime);
			System.out.println("User1; Experiment Completion Time: " + (finishTime-(user1.startTime)) );
			System.out.println("User1; Set Finish time:"+ user1.setFinishTime);
			System.out.println("User1; Set Completion Time: " + (user1.setFinishTime-user1.startTime)); 
			System.out.println("");
			System.out.println("User2 Start time:"+ user2.startTime + " ;  Finish time:"+ finishTime);
			System.out.println("User2 Experiment Completion Time: " + (finishTime-(user2.startTime)) ); 
			System.out.println("User2 Set Finish time:"+ user2.setFinishTime);
			System.out.println("User2 Set Completion Time: " + (user2.setFinishTime-user2.startTime) ); 
			System.out.println("-------------------------------------------------------------------");
			
			//********************************************************
			//FORMING USER1 LOG VECTOR
			for(int i=0; i< userLogVector.size() ; i++) {
				
				if(userLogVector.get(i).getUser().equals("User 1") && userLogVector.get(i).getblobNumber() != -5){
					user1LogVec.add(userLogVector.get(i));
				}	
			}
			//********************************************************
			//FORMING USER2 LOG VECTOR
			for(int i=0; i< userLogVector.size() ; i++) {
				
				if(userLogVector.get(i).getUser().equals("User 2") && userLogVector.get(i).getblobNumber() != -5){
					user2LogVec.add(userLogVector.get(i));		
				}	
			}
			
			//********************************************************
			//DIAMETER BLOBS FOR USER1
			user1result = new String[user1LogVec.size() / 2];
			int j=0;
			for(int i=0 ; i< user1LogVec.size() ; i++){
				
				if( (user1LogVec.get(i).getblobNumber() %2 == 0) && ((i+1) < user1LogVec.size()) ){ //if blobNo is even && next element exists
					
					if(user1LogVec.get(i+1).getblobNumber() == (user1LogVec.get(i).getblobNumber() + 1) )
					{
						user1result[j] = user1LogVec.get(i).getUser() + "; Time passed between blob" +user1LogVec.get(i).getblobNumber() + " and blob" + user1LogVec.get(i+1).getblobNumber() + ":  " +
								(user1LogVec.get(i+1).getTimeStamp() - user1LogVec.get(i).getTimeStamp()) + '\n' ;
						
						System.out.println( user1LogVec.get(i).getUser()+ "; Time passed between blob" +user1LogVec.get(i).getblobNumber() + " and blob" + user1LogVec.get(i+1).getblobNumber() + ":  " +
								(user1LogVec.get(i+1).getTimeStamp() - user1LogVec.get(i).getTimeStamp())  );
						j++;
					}
				}
			}
			System.out.println("");
			
			//********************************************************
			//DIAMETER BLOBS FOR USER2	
			user2result = new String[user2LogVec.size() / 2];
			int k=0;
			for(int i=0 ; i< user2LogVec.size() ; i++){
				
				if( (user2LogVec.get(i).getblobNumber() %2 == 0) && ((i+1) < user2LogVec.size()) ){ //if blobNo is even && next element exists
					
					if(user2LogVec.get(i+1).getblobNumber() == (user2LogVec.get(i).getblobNumber() + 1) )
					{
						user2result[k] =user2LogVec.get(i).getUser()+ "; Time passed between blob" +user2LogVec.get(i).getblobNumber() + " and blob" + user2LogVec.get(i+1).getblobNumber() + ":  " +
								(user2LogVec.get(i+1).getTimeStamp() - user2LogVec.get(i).getTimeStamp()) + '\n';
						
						System.out.println( user2LogVec.get(i).getUser()+ "; Time passed between blob" +user2LogVec.get(i).getblobNumber() + " and blob" + user2LogVec.get(i+1).getblobNumber() + ":  " +
								(user2LogVec.get(i+1).getTimeStamp() - user2LogVec.get(i).getTimeStamp())  );
						k++;
						 
					}
				}
			}
			System.out.println("-------------------------------------------------------------------");
					
			log();
		}
	}
	
	/** 
	 * log the data and indicate if logged is OK or not	
	 */
	private void log() {
		EventingServiceAsync svc = GWT.create(EventingService.class);
		svc.log(ICEMain.identifier, getLoggedResult(loggedData), ExperimentIdentifier.CLICKBLOB, 2, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				ClickBlobs2users.this.clear(); 
				Window.alert("Successfully logged! Experiment finished");
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error:", caught);
				ClickBlobs2users.this.clear(); 
				
				caught.printStackTrace();
				   
				Window.alert("Not logged");
			}
		});
	}

	
	/**
	 * Format the logged data: comma separated value!
	 * @param data
	 * @return Array of string containing the user, x, y coord, time stamps of the click
	 */
	
	private String[] getLoggedResult(String[][] data) {
		String[] result = new String[data.length + 1];
		String messageU1 = "";
		String messageU2 = "";

		for (int i=0; i < data.length; i++){
			result[i] = data[i][0] + ";" + data[i][1] + ";" + data[i][2] + ";" + data[i][3] + ";" + data[i][4]  +  " \n";
		}
		
		for(int j = 0; j< user1result.length ; j++){

			 messageU1 = messageU1+ user1result[j];	
		}
		for(int m = 0; m< user2result.length ; m++){

			 messageU2 = messageU2+ user2result[m];	
		}
		
		result[data.length] = '\n'+ "---------------------------------------------------------------"+ '\n' 
	    +"User1; Start Time:" + user1.startTime + " ; Finish Time:" + finishTime +" ; Experiment Completion Time: " + ((finishTime)-user1.startTime) + '\n' 
		+"User1; Set Finish time: " + user1.setFinishTime +  " ; Set Completion Time:" + (user1.setFinishTime-user1.startTime)
		+'\n'
		+"User2; Start Time:" + user2.startTime  + " ; Finish Time:"  +  finishTime +" ; Experiment Completion Time: " + ((finishTime)-user2.startTime) + '\n' 
		+"User2; Set Finish time: " + user2.setFinishTime +  " ; Set Completion Time:" + (user2.setFinishTime-user2.startTime)+ '\n'
		+"---------------------------------------------------------------"+ '\n'
		+"User 1; " + user1.count + " + "  + user1.trialcount + " times out of blob"  + '\n'
		+"User 2; " + user2.count + " + "  + user2.trialcount + " times out of blob"  + '\n'
		+ "---------------------------------------------------------------"+ '\n'
		+ messageU1 + '\n'
		+ messageU2 ; 

		return result;
	
	}
	
	
	/**
	 * Generate a random number with an upper boundary
	 * 
	 * @param upperBond
	 * @return int
	 */
	private int randomNum(int lowerBond, int upperBond) {

		int num = Random.nextInt(upperBond);

		while (num <= lowerBond) {
			num = Random.nextInt(upperBond);
		}

		return num;
	}

	@Override
	public void setLoggedData(String[] blobData) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLoggedData(Vector<String> blobData , boolean finished , boolean check) {
		// TODO Auto-generated method stub
		
	}
	
	private int[][] initBlobPosInCircle(int nbBlob, int xCenter, int yCenter, int radius) {
		
		
		int nbPos = (nbBlob == 0) ? 2 : nbBlob;	
		//check if nbPos is even
		nbPos = (nbPos % 2 == 0) ? nbPos : nbPos + 1;
		
		int [][] blobPos = new int[nbPos][2]; 			
		int dAngle = 360 / nbPos;
		double angle = 0;
		double x = 0; 
		double y = 0;
		
		// set the 2 first pos
		blobPos[0][0] = xCenter;
		blobPos[0][1] = yCenter - radius;

		blobPos[1][0] = xCenter;
		blobPos[1][1] = yCenter + radius;
		
		//System.out.println("Y pos 0: " + blobPos[0][1] + "; Y pos 1: " + blobPos[1][1] + " radius: "+ radius + "dAngle: " + dAngle); 
		
		for (int i = 2; i < nbPos ; i = i + 2){
			angle = angle + dAngle;
						
			if (angle < 90) {
				
				x = radius * Math.sin(Math.toRadians(angle));
				y = radius * Math.cos(Math.toRadians(angle));
				
				blobPos[i][0] = (int) (xCenter + x);
				blobPos[i][1] = (int) (yCenter - y);
				blobPos[i+1][0] = (int) (xCenter - x);
				blobPos[i+1][1] = (int) (yCenter + y);
			
			} else if (angle == 90) {
				blobPos[i][0] = (int) (xCenter + radius);
				blobPos[i][1] = (int) (yCenter);
				blobPos[i+1][0] = (int) (xCenter - radius);
				blobPos[i+1][1] = (int) (yCenter);
			
			} else if (angle > 90) {
				
				x = radius * Math.cos(Math.toRadians(angle-90));
				y = radius * Math.sin(Math.toRadians(angle-90));
				
				blobPos[i][0] = (int) (xCenter + x);
				blobPos[i][1] = (int) (yCenter + y);
				blobPos[i+1][0] = (int) (xCenter - x);
				blobPos[i+1][1] = (int) (yCenter - y);				
			}
			//System.out.println("Y pos " + i + ": " + blobPos[i][1] + "; Y pos "+ (i+1) + ": " + blobPos[i+1][1]  + "; Angle: " + angle); 
		}
		
		return blobPos;
	}

}
