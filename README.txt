Frequently Asked Questions & Answers

1.	How do I join with the Game?
	Flog game host is rest upon the openShift cloud server. Therefore you only need the jar file of the game. To start the game Double click on the jar file.
	
2.	How do I Run the game on a Local server?
	You need JDK version 8.0 and Glassfish version 4.4 or tomcat server installed and configured at the host machine. Open GameClientFx and GameServer on IDE. Then follow the given steps: 
	Go to the:
	GameClientFx --> src --> main -->  java --> com --> nsbm --> common
		Double click on the “CommonData.java” source file. Make changes to the following lines; 
			•	Comment Line number 22 
			•	Uncomment Line number 23
		First Run the GameServer then run GameClientFx. 

3.	How do I reset current players if there is a conflict on the host server?
	Go the following link:
		http://flogame-nsbm.rhcloud.com/WebResources/PlayerService/clearAllPlayers

4.	How to I reset the game if the server crashed?
	Go the following link:
		http://flogame-nsbm.rhcloud.com/WebResources/GameService/resetGame
		

5.	How do I go to the gameServer index?
	Go the following link:
		http://flogame-nsbm.rhcloud.com/WebResources/GameService/resetGame

