/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybot;

/**
 *
 * @author Raúl
 */
import java.awt.*;
import java.awt.event.*;

public class myBot{
  public static void main(String[] args)
                             throws AWTException{
    Robot myRobot = new Robot();
    for(int i  = 0; i<100; i++){
    
        myRobot.delay(100);
        myRobot.mouseMove(1005,i);
    }
    
  }

}
