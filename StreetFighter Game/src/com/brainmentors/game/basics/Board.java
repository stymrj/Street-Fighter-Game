package com.brainmentors.game.basics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.brainmentors.game.settings.GameConstants;
import com.brainmentors.game.sprites.Player;
import com.brainmentors.game.sprites.Power;
import com.brainmentors.game.sprites.SumitPlayer;
import com.brainmentors.game.sprites.SatyamPlayer;

import jaco.mp3.player.MP3Player;

public class Board extends JPanel implements GameConstants,ActionListener,KeyListener{
	BufferedImage backgroundimage;
	Player Sumit;
	Player Satyam;
	Timer timer;
	MP3Player player;
	Power player1Power;
	Power player2Power;
	boolean isGameOver;
	Board() throws Exception{

		loadbackground();
	    Sumit = new SumitPlayer();
	    Satyam = new SatyamPlayer();
	    bindEvents();
        setFocusable(true);
	    gameLoop();
	    player = new MP3Player(Board.class.getResource("gamesong.mp3"));
	    player.setRepeat(true);
	    PlayBackGroundMusic();
	    player1Power =new Power(20,"Sumit");
	    player2Power=new Power(BOARD_WIDTH/2+200,"Satyam");
	}
	public void drawPower(Graphics g) {
		player1Power.draw(g);
		player2Power.draw(g);

		
	}
	 void PlayBackGroundMusic() {
		player.play();
	}
	public void bindEvents(){
		this.addKeyListener(this);
	}
	public void gameLoop() {
		timer = new Timer(DELAY,this);
		timer.start();
	}
	void loadbackground() throws Exception {
		backgroundimage=ImageIO.read(Board.class.getResource(BACKGROUND_IMAGE));
	}
    @Override
    protected void paintComponent(Graphics pen) {
    	super.paintComponent(pen);
        pen.drawImage(backgroundimage, 0,0,BOARD_WIDTH,BOARD_HEIGHT,null );
        displayMessage(pen);
        Sumit.draw(pen);
        Satyam.draw(pen);
        drawPower(pen);
        playerAttackHit();
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		Sumit.fall();
		Satyam.fall();
          repaint();		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	public void displayMessage(Graphics pen){
		if(isGameOver) {
			pen.setFont(new Font("times",Font.BOLD,50));
			pen.setColor(Color.RED);
		pen.drawString("Game Over......",BOARD_HEIGHT/2+55,BOARD_WIDTH/4);                                
			timer.stop();
		}

	}
	public boolean playerAttackHit() {
		if(isCollide()) {
		if(Sumit.isAttacking()) {
			Satyam.setAction(HIT);
			Satyam.setPower(Satyam.getPower()-8);
			player2Power.setW(player2Power.getW()-8);
		}
		else if(Satyam.isAttacking()) {
			Sumit.setAction(HIT);
			Sumit.setPower(Sumit.getPower()-7);
			player1Power.setW(player1Power.getW()-7);
		}
		else if(Sumit.isAttacking()&& Satyam.isAttacking()) {
			Satyam.setAction(HIT);
			Sumit.setAction(HIT);
			Satyam.setPower(Satyam.getPower()-8);
			player2Power.setW(player1Power.getW()-8);
			Sumit.setPower(Sumit.getPower()-7);
			player1Power.setW(player1Power.getW()-7);
		}
		if(Sumit.getPower()<=0||Satyam.getPower()<=0) {
			isGameOver = true;
		}
		return true;
		}
		return false;
	}
	public boolean isCollide() {
		int xDistance= Math.abs(Sumit.getX()-Satyam.getX());
		int yDistance = Math.abs(Sumit.getY()-Satyam.getY());
		int maxH = Math.max(Sumit.getH(),Satyam.getH());
		int maxW = Math.max(Sumit.getW(),Satyam.getW());
		return xDistance<=maxW-25&& yDistance<=maxH;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_S) {
			Sumit.setAction(KICK);
			Sumit.setAttacking(true);
		}
		else if(e.getKeyCode()==KeyEvent.VK_D) {
			Sumit.setSpeed(SPEED);
			Sumit.move();
		}
		else if(e.getKeyCode()==KeyEvent.VK_A) {
			Sumit.setSpeed(SPEED*-1);
			Sumit.move();
		     }
		else if(e.getKeyCode()==KeyEvent.VK_W) {
			Sumit.jump();
		}
		else if(e.getKeyCode()==KeyEvent.VK_I) {
			Satyam.jump();
		}
		else if(e.getKeyCode()==KeyEvent.VK_K) {
			Satyam.setAction(KICK);
			Satyam.setAttacking(true);
		}
		else if(e.getKeyCode()==KeyEvent.VK_J) {
			Satyam.setSpeed(SPEED*-1);
			Satyam.move();
		}
		else if(e.getKeyCode()==KeyEvent.VK_L) {
			Satyam.setSpeed(SPEED);
			Satyam.move();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
    
}
