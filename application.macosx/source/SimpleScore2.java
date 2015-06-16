import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SimpleScore2 extends PApplet {

/*  
 The MaKey MaKey High-Five-athon
 Kenneth Guglielmino
 KennethGug@gmail.com
 
 
 Objective: Simple Score is a super simple app that creates an easy to use scoreboard system for whatever
 Makey Makey project you're trying to make.
 -----------------------
 -----------------------
 CONTROLS:
 Click: Run through game over menus
 0: Automatic game over
 Spacebar: +1 point

 -----------------------
 -----------------------
 SET UP:

 One alligator clip is connected to Spacebar on the Makey Makey
 And the other alligator clip is connected to Earth on the Makey Makey
 
 -----------------------
 -----------------------
 */

// Sound library and objects

Minim minim;
AudioSample clap;

//score variables
int scoreTeam1 = 0;

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

PImage makerJawn;  // Declare variable "a" of type PImage
PImage freeLibrary;  // Declare variable "a" of type PImage
PImage makey;  // Declare variable "a" of type PImage
int show5 = 0;

// To make sure that we don't have repeated key hits, when people just hold the key
int spacebarReleased = 1; 

//TextEntering
String msg = "";
int msg2;
String tempLine = "";
int MAX = 500;
int len = MAX; // Avoid one letter variable names, particularly with l which is ambiguous (I? l? 1?)


public void setup() {
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
  clap = minim.loadSample("Blop.mp3", 320);
  imageMode(CENTER);
  makerJawn = loadImage("MakerJawn.png");  // Load the image into the program
  freeLibrary = loadImage("freelibrary.jpeg");  // Load the image into the program
  makey = loadImage("makey.jpg");  // Load the image into the program
}

public void draw() {
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
    gameOver = 1;  // game over screen
  } else {
    gameOver = 0;
  }
  if (gameOver == 0) {
    menux = 0;
  }

  if (show5 ==1) {
    imageMode(CENTER);
    //    image(high5_2, width/2, height/2+70, high5_2.width/2, high5_2.height/2);
    int passedTime3 = millis() - savedTime3;
    if (passedTime3 > totalTime3) {
      savedTime3 = millis();
      // image(high5_1, width/2, height/2+70, high5_1.width/2-20, high5_1.height/2-20);
      show5 = 0;
    }
  } else if (menux > -1) {
    // image(high5_1, width/2, height/2+40, high5_1.width/2 - 30, high5_1.height/2 - 30);
  }
}

public void keyPressed() {
  // Key 32 is the Spacebar
  if ( key == 32 ) { 
    if (spacebarReleased == 1) {
      if (gameOver == 0) {
        scoreTeam1 = scoreTeam1 + 1; // Add to the score of Team 1
        clap.trigger(); // Play clap sound effect
        show5 = 1; // Show claping graphic
        spacebarReleased = 0; // Keeps key hit from repeating when held
      } // GameOver
    } // Spacebar Released Check
  } // Spacebar Pressed


  // TIMER VALUE VARIABLE PLACE HERE

  if (keyCode == RETURN || keyCode == ENTER) {
    println("Return Pressed");
    if (menux ==-1) {
      menux = menux+1;
      gameClock=msg2; 
      println("Return Pressed");
    }
  }


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
      gameClock=msg2; 
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


  if (menux ==-2) {
    if (textWidth(tempLine) >= len) {
      tempLine = "";
      msg += "\n";
    }

    if (key != CODED && key != BACKSPACE && '0' <= key && key <= '9') {
      msg += key;
      tempLine += key;

      // for debug
      println ("msg: " + msg);
      println ("tem: " + tempLine);
    } else if (key == BACKSPACE && msg.length() > 0) {
      msg = msg.substring(0, msg.length()-1);
    }
  }
} // key pressed

public void mousePressed() {
  if (mousePressed == true) {
    if (gameOver == 1 && menux > -1) {
      menux = menux +1;
      println(menux);
      if (menux > 1) {
        gameOver = 0;
        gameClock = msg2;
        menux = 0;
        scoreTeam1 = 0;
        show5 = 0;
      } //menux
    } //gameOver
    //    if(menux ==0){
    //      gameClock = 20;
    //    }
    if(menux==-2 && msg2 >0&& msg2 <10000){
      menux = menux +1;
      println(menux);
      if (menux > 1) {
        gameOver = 0;
        gameClock = msg2;
        menux = 0;
        scoreTeam1 = 0;
        show5 = 0;
      } //menux
      
    }
  } // if mousepressed
} // void mousepressed

public void keyReleased() {
  if ( key == 32 ) { 
    if (gameOver == 0) {
      spacebarReleased = 1;
    }
  }
}

//Scoreboard Graphics
public void scoreboard() {
  imageMode(CENTER);
  if (gameOver == 0 && menux> -1) {
    //    gameClock = msg2;
    text(gameClock, width/2, height/4+40);
  }

  if (gameOver == 1) {
    text("0", width/2, height/4+40);
    textFont(font, 150);
    if (menux == 0) {
      fill(0xff505051);
      text("game over", width/2, height/2+240);
      noFill();
      stroke(0xff000000);
      strokeWeight(5);
      rectMode(CENTER);
      rect(width/2, height/2+210, 900, 155);
      textFont(font2, 20);
      text("(click to continue)", width/2, height/2+330);
    }
  }

  if (menux ==1) {
    textFont(font2, 30);
    fill(0xff505051);
    text("Woo! Nice Job!", width/2, height/2+220);
    textFont(font2, 20);
    text("(click to play again)", width/2, height/2+265);
  }

  if (menux == -2) {
    image(makerJawn, width/2-100, height/2, makerJawn.width/2, makerJawn.height/2);
    image(freeLibrary, width/2+100, height/2, freeLibrary.width/2, freeLibrary.height/2);
    textFont(font2, 30);
    textdisplay();
    msg2 = PApplet.parseInt(msg);
    textFont(font, 130);
    text("SIMPLE SCORE", width/2, height/4+50);
    textFont(font2, 20);
    if(msg2 > 0 && msg2 < 10000){
    text("(Click to Continue)", width/2, height/2+310);
    }
    if(msg2 > 10000){
      text("(I don't want to be stuck here for an eternity, make the timer a little shorter)", width/2, height/2+310);
      
    }
    textFont(font, 0);     //For weird bug that was making the gameclock show up ----- FIX
  }

  if (menux == -1) {
    println("menux = -1");
    image(makey, width/2, height/2-180, makey.width/1.2f, makey.height/1.2f);
    textFont(font, 130);
    text("Setup", width/2, height/2+60);
    textFont(font2, 30);
    text("Connect one alligator clip to the Spacebar on the Makey Makey", width/2, height/2+120);
    text("Connect the other alligator clip to Earth on the Makey Makey.", width/2, height/2+160);
    text("That's it! Good Luck Have Fun :P", width/2, height/2+200);

    textFont(font2, 20);
    text("(Press the S key to Start.)", width/2, height/2+310);
    textFont(font, 0);     //For a weird bug that was making the gameclock show up ----- FIX
  }

  if (menux > -1) {
    image(makerJawn, width/7-75, height/6-20, makerJawn.width/3, makerJawn.height/3);
    image(freeLibrary, width/7+75, height/6-20, freeLibrary.width/3, freeLibrary.height/3);
    textFont(font2, 46);
    fill(0xff00bc73);
    text("high score:", width/2-27, height/4+132);
    text(highScore, width/2+134, height/4+132);

    //team scores

    fill(0xff00bc73);
    textFont(font, 200);
    text(scoreTeam1, width/2, height/2 +120);
    fill(0xff505051);

    imageMode(CENTER);
    noFill();
    stroke(0xff00bff1);
    strokeWeight(5);
    rectMode(CENTER);
    rect(width/2, height/5+15, 500, 200);
    line(0, height/4 + 20, width/2-250, height/4 + 20);
    line(width/2+250, height/4 + 20, width, height/4 + 20);
    strokeWeight(3);
    line(width/2-180, height/4+150, width/2+180, height/4+150);
    fill(0xff505051);
  }
}

public void textdisplay() {
  fill(255);
  fill(0);
  text("How many seconds long do you want your timer to be?", width/2, height/2+160);
  text("Type in a value: ", width/2, height/2 + 200);
  fill(150);
  text(msg, width/2+200, height/2+200);
  fill(0);
}



public boolean sketchFullScreen() {
  return true;
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "SimpleScore2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
