//Minesweeper

/**
* Danilo Radovic
* Period 4
* March 23, 2016 
* This lab took me around 2 hours and 30 minutes total
*
* I found the AI lab to be interesting and challenging but very
* educating. I had many problems along the way both with my AI
* logic and what state to choose from, and with having the data from
* the AI correctly transferred to the controller and then to the view.
* However I fixed these problems after doing multiple runs through
* my game and pinpointing my problems. I also utilized debugging
* and printing out values to solve multiple problems. One problem that
* was bothersome but easy to fix was that the AI would sometimes crash
* in the beginning of the game. Through playing the game multiple times
* I found that the problem lied in the board that the AI receives not
* being correctly updated in the AI class. I also had a problem where
* the game would win prematurely, which I also fixed by simply making
* sure that the board and number of tiles uncovered variable were being
* updated in their correct positions.
* 
* NOTE: I discussed with Mr. Ferrante and he said it was fine for me
* to turn in this polished version of Minesweeper AI today past the
* deadline. My earlier, uncompleted version was turned in on the due
* date, but this is the better version.
*/

public class P4_Radovic_Danilo_MSDriver {

	public static void main(String[] args) {
		new P4_Radovic_Danilo_MSController();
	}

}
