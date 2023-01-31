package OestrichPascal;
import robocode.*;

public class Brunhildos extends JuniorRobot {
	
	int moveDistance;
	
  	int hitCounter;
	
  	boolean isDefending;

    public void run() {
	
        setColors(blue, orange, blue, green, blue);
		
		calculateMoveDistance();

        while (true) {
		
			turnGunRight(150);
			backAndForthMovement();

            if (energy >= 50) {
                isDefending = false;
				calculateDistanceToWall();
				
		//If energy under 50% robot will switch in the defence mode
            } else if (energy < 50) {
                isDefending = true;
                DefenceMode();
            }
        }
    }

    public void onScannedRobot() {
        
	turnGunTo(scannedAngle);
			
	//If the scannedDistance is under 100 Pixels then fire
        if (scannedDistance < 100 && isDefending == false) {
            fire(3);
	}
			
        else if (scannedDistance > 0 || scannedDistance < 500) {
		  
		if(isDefending == false){
			trackEnemy();
		}
		fire(1);
        }
    }
	
    public void onHitByBullet() {
        ahead(82);
		
        hitCounter++;
		
        if (hitCounter > 5) {
            turnAheadRight(150, 90);
            back(moveDistance);
			
            hitCounter = 0;
        }
    }

    public void onHitWall() {
        turnRight(120);
        ahead(moveDistance);
    }

    public void DefenceMode() {
		
	//Find out distance to wall
        double moveDefence = fieldHeight - robotY;
		
	//Drive to a wall and go allong it
        if (heading > 90) {
            ahead((int) moveDefence);
            turnRight(90 - heading);
        } else if (heading < 90) {
            back((int) moveDistance);
            turnLeft(heading - 90);
        } else {
            ahead((int) moveDistance);
        }
    }
	public void trackEnemy(){
		int trackAngle;
		
		trackAngle = scannedHeading - gunHeading + scannedAngle;
		turnRight(trackAngle);
		ahead(scannedDistance - 150);
	} 	
	
	public void calculateMoveDistance(){
			moveDistance = Math.max(fieldWidth, fieldHeight) / 2;
		}
		
	public void calculateDistanceToWall() {
	
   		double distanceToBottom = fieldHeight - robotY;
	    	double distanceToRight = fieldWidth - robotX;
	
		avoidWall(distanceToBottom, distanceToRight);
	
		}
	
	public void avoidWall(double distanceToBottom, double distanceToRight ){
	
		if(distanceToBottom <= 100){
		turnRight(180);
		ahead(100);
		}
		else if(distanceToRight <= 100){
		turnRight(180);
		ahead(100);
		}
	
	}
	
	public void backAndForthMovement(){
		back(75);
		ahead(100);
	}
}
