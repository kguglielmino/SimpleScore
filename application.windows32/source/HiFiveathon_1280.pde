/*  
 The MaKey MaKey High-Five-athon
 Kenneth Guglielmino
 KennethGug@gmail.com
 
 Objective: Two teams of two compete against each other in a high stakes, no holds barred,
 sudden death, extreme sports, game of High Fiving. 
 The goal of the game is to high five your teammate like a million times and beat the other
 team as you're doing it. It's as simple and fun as that.
 -----------------------
 -----------------------
 CONTROLS:
 Click or R: Run through game over menus
 0: Automatic game over
 Spacebar: +1 point to Team 1
 Left Arrow: +1 point to Team 2
 -----------------------
 -----------------------
 SET UP:
 There are two teams of two. 
 Team One: One person is connected to Spacebar on the Makey Makey
 (via Alligator clips and a bracelet)and another person is connected to Ground
 Team Two: One person is connected to the Left Arrow on the Makey Makey
 (via Alligator clips and a bracelet)and another person is connected to Ground
 
 Make sure that when the alligator clips are attached, that metal is coming into
 contact with the team member's skin.
 -----------------------
 -----------------------
 */

// Sound library and objects
import ddf.minim.*;
Minim minim;
AudioSample clap;

//score variables
int scoreTeam1 = 0;
int scoreTeam2 = 0;
int highScore = 0;

//timers
int savedTime2;
int totalTime2 = 1000;
int savedTime3;
int totalTime3 = 1000;

//Menu Things
int gameClock = 0;
int gameOver = 1;
int menux = -2;
PFont font;
PFont font2;
PFont font3;

PImage high5_1;  // Declare variable "a" of type PImage
PImage high5_2;  // Declare variable "a" of type PImage
PImage makey;  // Declare variable "a" of type PImage
int show5 = 0;

// To make sure that we don't have repeated key hits, when people just hold the key
int spacebarReleased = 1; 
int leftReleased = 1;

void setup() {
  noCursor();
  //  size(displayWidth, displayHeight);
  size(1280, 720);
  background(255);
  smooth();
  font = loadFont("ProximaNova-SemiboldIt-200.vlw");
  font2 = loadFont("ProximaNova-Semibold-48.vlw");
  font3 = loadFont("ProximaNova-Semibold-100.vlw");
  // Make another font to w/ diff size to get rid of pixelation
  textFont(font, 200);
  textAlign(CENTER);
  fill(255, 255, 255);
  // Timers
  savedTime2 = millis();
  savedTime3 = millis();
  // Audio
  minim = new Minim(this);
  clap = minim.loadSample("808clap.wav", 320);
  high5_1 = loadImage("Hands1.gif");  // Load the image into the program
  high5_2 = loadImage("Hands2.gif");  // Load the image into the program
  makey = loadImage("makey.jpg");  // Load the image into the program
}

void draw() {
  background(255);
  textAlign(CENTER);
  scoreboard(); // calls in the graphics
  // Timer that controls the gameClock
  // Everytime one second passes. the gameclock deceases by one
  int passedTime2 = millis() - savedTime2;
  if (passedTime2 > totalTime2) {
    gameClock = gameClock - 1;
    println(gameClock);
    savedTime2 = millis();
  }

  if (gameClock < 1) {
    //Highscore System
    if (scoreTeam1 > highScore) {
      highScore = scoreTeam1;
      println(highScore);
    }
    if (scoreTeam2 > highScore) {
      highScore = scoreTeam2;
      println(highScore);
    }
    gameOver = 1;  // game over screen
  } else {
    gameOver = 0;
  }
  if (gameOver == 0) {
    menux = 0;
  }

  if (show5 ==1) {
    imageMode(CENTER);
    image(high5_2, width/2, height/2+70, high5_2.width/2, high5_2.height/2);
    int passedTime3 = millis() - savedTime3;
    if (passedTime3 > totalTime3) {
      savedTime3 = millis();
      image(high5_1, width/2, height/2+70, high5_1.width/2-20, high5_1.height/2-20);
      show5 = 0;
    }
  } else if (menux > -1) {
    image(high5_1, width/2, height/2+40, high5_1.width/2 - 30, high5_1.height/2 - 30);
  }
}

void keyPressed() {
  // Key 32 is the Spacebar
  if ( key == 32 ) { 
    if (spacebarReleased == 1) {
      if (gameOver == 0) {
        scoreTeam2 = scoreTeam2 + 1; // Add to the score of Team 1
        clap.trigger(); // Play clap sound effect
        show5 = 1; // Show claping graphic
        spacebarReleased = 0; // Keeps key hit from repeating when held
      } // GameOver
    } // Spacebar Released Check
  } // Spacebar Pressed

  if (key == CODED) {
    if (keyCode == LEFT) {
      if (leftReleased == 1) {
        if (gameOver == 0) {
          scoreTeam1 = scoreTeam1 + 1; // Add to the score of Team 2
          clap.trigger(); // Play clap sound effect
          show5 = 1; // Show clapping graphic
          leftReleased = 0; // Keeps key hit from repeating when held
        } // GameOver
      } // Left Released Check
    } //LEFT Pressed
    if (keyCode == RETURN || keyCode == ENTER) {
      println("Return Pressed");
      if (menux ==-1) {
        menux = menux+1;
        gameClock=20; 
        println("Return Pressed");
      }
    }
  } // key coded

  //menu
  if (key == 's') {
    //    if (gameOver == 1) {
    //      menux = menux +1;
    //      println(menux);
    //      if (menux > 1) {
    //        gameOver = 0;
    //        gameClock = 20;
    //        menux = 0;
    //        scoreTeam1 = 0;
    //        scoreTeam2 = 0;
    //      } //menux
    //    } //gameOver
    if (menux ==-1) {
      //        menux = menux+1;
      gameClock=20; 
      println("Return Pressed");
    }
  } // Enter Keycode

  //Manual Game Over
  if (key == '0') {
    if (gameOver == 0) {
      gameOver = 1;
      gameClock = 0;
    } //gameOver
  } // Enter Keycode
} // key pressed

void mousePressed() {
  if (mousePressed == true) {
    if (gameOver == 1 && menux != -1) {
      menux = menux +1;
      println(menux);
      if (menux > 1) {
        gameOver = 0;
        gameClock = 20;
        menux = 0;
        scoreTeam1 = 0;
        scoreTeam2 = 0;
        show5 = 0;
      } //menux
    } //gameOver
    //    if(menux ==0){
    //      gameClock = 20;
    //    }
  } // if mousepressed
} // void mousepressed

void keyReleased() {
  if ( key == 32 ) { 
    if (gameOver == 0) {
      spacebarReleased = 1;
    }
  }
  if (key == CODED) {
    if (keyCode == LEFT) {
      leftReleased = 1;
    }
  }
}

//Scoreboard Graphics
void scoreboard() {
  imageMode(CENTER);
  if (gameOver == 0 && menux> -1) {
    text(gameClock, width/2, height/4+40);
  }

  if (gameOver == 1) {
    text("0", width/2, height/4+40);
    textFont(font, 150);
    if (menux == 0) {
      fill(#505051);
      text("game over", width/2, height/2+240);
      noFill();
      stroke(#000000);
      strokeWeight(5);
      rectMode(CENTER);
      rect(width/2, height/2+210, 900, 155);
      textFont(font2, 20);
      text("(click to continue)", width/2, height/2+330);
    }
  }

  //  if (gameOver == 0) {
  //    textFont(font, 30);
  //  }

  if (menux ==1) {
    textFont(font2, 30);
    fill(#505051);
    text("high-five your teammate to get mad points", width/2, height/2+220);
    textFont(font2, 20);
    text("(click to play again)", width/2, height/2+265);
  }

  if (menux == -2) {
    //    gameClock = 20;
    textFont(font, 150);
    fill(#505051);
    text("MaKey MaKey", width/2, height/2-200);
    textFont(font, 150);
    text("High-5-athon", width/2, height/2-50);
    image(high5_2, width/2, height/2+50, high5_2.width/2-30, high5_2.height/2-30);

    textFont(font2, 30);
    fill(#505051);
    text("Two teams of two compete against each other in a high stakes, no holds barred,", width/2, height/2+160);
    text("sudden death, extreme sports game of High Fiving.", width/2, height/2+200);
    text("The goal of the game is to high five your teammate like a million times", width/2, height/2+260);
    text("and beat the other team as you're doing it. It's as simple and fun as that.", width/2, height/2+300);

    textFont(font2, 20);
    text("(Click to Continue)", width/2, height/2+340);
    println("menux = -1");
    textFont(font, 0); //For weird bug that was making the gameclock show up ----- FIX
  }

  if (menux == -1) {
    println("menux = -1");
    image(makey, width/2, height/2-180, makey.width/1.2, makey.height/1.2);
    textFont(font, 130);
    text("Setup", width/2, height/2+60);
    textFont(font2, 30);
    text("Team One: One person is connected to the Left Arrow on the Makey Makey", width/2, height/2+120);
    text("(via Alligator clips and a bracelet) and another person is connected to Ground.", width/2, height/2+160);
    text("Team Two: One person is connected to the Spacebar on the Makey Makey", width/2, height/2+220);
    text("(via Alligator clips and a bracelet) and another person is connected to Ground.", width/2, height/2+260);
    textFont(font2, 25);
    text("Make sure that the alligator clips are coming into contact with the team member's skin.", width/2, height/2+310);
    textFont(font2, 20);
    text("(Press the S key to Start.)", width/2, height/2+335);
    textFont(font, 0);     //For weird bug that was making the gameclock show up ----- FIX
  }

  if (menux > -1) {
    //team names
    fill(#505051);
    textFont(font3, 100);
    text("team 1", width/4 - 40, height/4);
    text("team 2", width/2 + 385, height/4);
    textFont(font2, 46);
    text("high score:", width/2-27, height/4+132);
    text(highScore, width/2+128, height/4+132);

    //team scores
    fill(#505051);
    textFont(font, 200);
    text(scoreTeam1, width/4, height/2 +100);
    text(scoreTeam2, width/2 + 330, height/2 +100);

    imageMode(CENTER);
    noFill();
    stroke(#000000);
    strokeWeight(5);
    rectMode(CENTER);
    rect(width/2, height/5+15, 280, 200);
    line(0, height/4 + 20, width/2-140, height/4 + 20);
    line(width/2+140, height/4 + 20, width, height/4 + 20);
    strokeWeight(3);
    line(width/2-145, height/4+150, width/2+145, height/4+150);
  }
}

boolean sketchFullScreen() {
  return true;
}

