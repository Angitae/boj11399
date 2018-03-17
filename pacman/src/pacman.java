package quiz;

import javax.sound.sampled.*; // sound를 이용하기 위해 연결.
import java.io.File; 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.applet.*; 
import java.awt.*; 

import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class pacman {
	public static void main(String[] args) { //이미지 삽입
		   Clip clip=null; //배경음악 삽입
		      try{
		         clip=AudioSystem.getClip();
		         File f=new File("C:\\Users\\안기태\\workspace\\quiz\\bgm.wav");
		         AudioInputStream ais=AudioSystem.getAudioInputStream(f);
		         clip.open(ais);
		         clip.start();
		         while(clip.isActive()){
		            try{
		               Thread.sleep(1000);
		            }
		            catch(InterruptedException e){
		               e.printStackTrace();
		            }
		         }
		      }
		      catch(MalformedURLException e){
		         e.printStackTrace();
		      }catch(LineUnavailableException e1){
		         e1.printStackTrace();
		      }catch(UnsupportedAudioFileException e){
		         e.printStackTrace();
		      }catch(IOException e){
		         e.printStackTrace();
		      }
		final JFrame frame = new JFrame();
		final ImageIcon successIcon = new ImageIcon("victory.jpg");
		final ImageIcon coin = new ImageIcon("coin.jpg");
		final ImageIcon wall = new ImageIcon("wall9.png");
		final ImageIcon enemy = new ImageIcon("ghost.png");
		final ImageIcon pacman = new ImageIcon("pac.png");
		final ImageIcon empty = new ImageIcon("empty.png");
		final ImageIcon fail = new ImageIcon("defeat1.jpg");
		final ImageIcon hansung = new ImageIcon("hansung.png");
		final JButton button = new JButton(successIcon);
		final JButton button2 = new JButton(fail);
		final CardLayout card = new CardLayout();
		final JDialog dialog = new JDialog();
		
	
		
		random = new Random();

		dialog.setSize(1930,1090); // 맵 사이즈
		dialog.setVisible(false);

		pacmanUP=12;  pacmanS=7;  enemyUP=7;  enemyS=7;  numOfDot=93;  start=2;  temp=empty;
		// 팩맨과 적 위치 및 코인 수

		final JLabel[][] f = new JLabel[14][14];
		// 맵 배열 크기
		for (int i=0; i<14; i++) {
			for(int j=0; j<14; j++) {
				f[i][j] = new JLabel();
			}
		}

		class QuitListener implements ActionListener { //종료 등록
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		}


		class MoveListener implements ActionListener {   //timer클래스 ActionListener
			public void actionPerformed(ActionEvent event)
			{ //적이 이동하는 거
				if(start<=0) where = 1+random.nextInt(4);
				else { where = 1; start--; }
					switch(where) {
						case 1:
							if(!(f[enemyUP-1][enemyS].getIcon()).equals(wall)) {
								temp1=f[enemyUP-1][enemyS].getIcon();
								f[enemyUP-1][enemyS].setIcon(enemy);
								f[enemyUP][enemyS].setIcon(temp);
								temp=temp1;
								enemyUP--;
							}
							break;
						case 2:
							if(!(f[enemyUP+1][enemyS].getIcon()).equals(wall)) {
								temp2=f[enemyUP+1][enemyS].getIcon();
								f[enemyUP+1][enemyS].setIcon(enemy);
								f[enemyUP][enemyS].setIcon(temp);
								temp=temp2;
								enemyUP++;
							}
							break;
						case 3:
							if(!(f[enemyUP][enemyS-1].getIcon()).equals(wall)) {
								temp3=f[enemyUP][enemyS-1].getIcon();
								f[enemyUP][enemyS-1].setIcon(enemy);
								f[enemyUP][enemyS].setIcon(temp);
								temp=temp3;
								enemyS--;
							}
							break;
						case 4:
							if(!(f[enemyUP][enemyS+1].getIcon()).equals(wall)) {
								temp4=f[enemyUP][enemyS+1].getIcon();
								f[enemyUP][enemyS+1].setIcon(enemy);
								f[enemyUP][enemyS].setIcon(temp);
								temp=temp4;
								enemyS++;
							}
							break;
					}
					if(enemyUP==pacmanUP && enemyS==pacmanS) {
						f[enemyUP][enemyS].setIcon(enemy);
						dialog.add(button2);
						dialog.setVisible(true);
					}

				if(enemyUP==5 && enemyS==7) { f[6][7].setIcon(hansung); }
			
			}
		}
		class KeyListener extends KeyAdapter{  //키보드입력에 따른 KeyListener
			public void keyPressed(KeyEvent e) {
				if(numOfDot<=0) {
					dialog.add(button);
					dialog.setVisible(true);
				}
				int key = e.getKeyCode();
				switch(key) { //팩맨이 동전먹을떈 없어짐
					case KeyEvent.VK_UP:
						if((f[pacmanUP-1][pacmanS].getIcon()).equals(coin) || (f[pacmanUP-1][pacmanS].getIcon()).equals(empty)) {
							if((f[pacmanUP-1][pacmanS].getIcon()).equals(coin) && (f[pacmanUP][pacmanS].getIcon()).equals(pacman)) numOfDot--;
							f[pacmanUP-1][pacmanS].setIcon(pacman);
							f[pacmanUP][pacmanS].setIcon(empty);
							pacmanUP--;
						}
						if((f[pacmanUP-1][pacmanS].getIcon()).equals(enemy)) {
							f[enemyUP][enemyS].setIcon(enemy);
							dialog.add(button2);
							dialog.setVisible(true);
						}
						break;
					case KeyEvent.VK_DOWN: //팩맨이 적을 만나면 게임 종료
						if((f[pacmanUP+1][pacmanS].getIcon()).equals(coin) || (f[pacmanUP+1][pacmanS].getIcon()).equals(empty)) {
							if((f[pacmanUP+1][pacmanS].getIcon()).equals(coin) && (f[pacmanUP][pacmanS].getIcon()).equals(pacman)) numOfDot--;
							f[pacmanUP+1][pacmanS].setIcon(pacman);
							f[pacmanUP][pacmanS].setIcon(empty);
							pacmanUP++;
						}
						if((f[pacmanUP+1][pacmanS].getIcon()).equals(enemy)){
							f[enemyUP][enemyS].setIcon(enemy);
							dialog.add(button2);
							dialog.setVisible(true);
						}
						break;
					case KeyEvent.VK_LEFT:
						if((f[pacmanUP][pacmanS-1].getIcon()).equals(coin) || (f[pacmanUP][pacmanS-1].getIcon()).equals(empty)) {
							if((f[pacmanUP][pacmanS-1].getIcon()).equals(coin) && (f[pacmanUP][pacmanS].getIcon()).equals(pacman)) numOfDot--;
							f[pacmanUP][pacmanS-1].setIcon(pacman);
							f[pacmanUP][pacmanS].setIcon(empty);
							pacmanS--;
						}
						if((f[pacmanUP][pacmanS-1].getIcon()).equals(enemy)){
							f[enemyUP][enemyS].setIcon(enemy);
							dialog.add(button2);
							dialog.setVisible(true);
						}
						break;
					case KeyEvent.VK_RIGHT:
						if((f[pacmanUP][pacmanS+1].getIcon()).equals(coin) || (f[pacmanUP][pacmanS+1].getIcon()).equals(empty)) {
							if((f[pacmanUP][pacmanS+1].getIcon()).equals(coin) && (f[pacmanUP][pacmanS].getIcon()).equals(pacman)) numOfDot--;
							f[pacmanUP][pacmanS+1].setIcon(pacman);
							f[pacmanUP][pacmanS].setIcon(empty);
							pacmanS++;
						}
						if((f[pacmanUP][pacmanS+1].getIcon()).equals(enemy)) {
							f[enemyUP][enemyS].setIcon(enemy);
							dialog.add(button2);
							dialog.setVisible(true);
						}
						break;
				}
			
			}
		}
		KeyListener listener = new KeyListener();
		MoveListener MoveListener = new MoveListener();

		button.addActionListener(new QuitListener());
		button2.addActionListener(new QuitListener());
		Timer t = new Timer(165, MoveListener); //적 속도
		t.start();

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(14,14));
		frame.requestFocus();
		frame.addKeyListener(new KeyListener());


		for(int i=0; i<14; i++) {
			for(int j=0; j<14; j++) {
				f[i][j].setIcon(wall);
				f[i][j].addKeyListener(listener);
				panel.add(f[i][j]);
			}
		} //동전 위치 설정
		f[1][1].setIcon(coin);	
		f[2][1].setIcon(coin);
		f[3][1].setIcon(coin);
		f[4][1].setIcon(coin);
		f[6][1].setIcon(coin);
		f[7][1].setIcon(coin);
		f[8][1].setIcon(coin);
		f[9][1].setIcon(coin);
		f[10][1].setIcon(coin);
		f[11][1].setIcon(coin);
		f[12][1].setIcon(coin);
		f[4][2].setIcon(coin);
		f[6][2].setIcon(coin);
		f[9][2].setIcon(coin);
		f[12][2].setIcon(coin);
		f[1][3].setIcon(coin);
		f[2][3].setIcon(coin);
		f[3][3].setIcon(coin);
		f[4][3].setIcon(coin);
		f[6][3].setIcon(coin);
		f[7][3].setIcon(coin);
		f[8][3].setIcon(coin);
		f[9][3].setIcon(coin);
		f[10][3].setIcon(coin);
		f[12][3].setIcon(coin);
		f[1][4].setIcon(coin);
		f[3][4].setIcon(coin);
		f[4][4].setIcon(coin);
		f[5][4].setIcon(coin);
		f[6][4].setIcon(coin);
		f[9][4].setIcon(coin);
		f[10][4].setIcon(coin);
		f[11][4].setIcon(coin);
		f[12][4].setIcon(coin);
		f[1][5].setIcon(coin);
		f[5][5].setIcon(coin);
		f[6][5].setIcon(coin);
		f[7][5].setIcon(coin);
		f[8][5].setIcon(coin);
		f[9][5].setIcon(coin);
		f[1][6].setIcon(coin);
		f[3][6].setIcon(coin);
		f[4][6].setIcon(coin);
		f[5][6].setIcon(coin);
		f[9][6].setIcon(coin);
		f[10][6].setIcon(coin);
		f[11][6].setIcon(coin);
		f[12][6].setIcon(coin);
		f[1][7].setIcon(coin);
		f[2][7].setIcon(coin);
		f[3][7].setIcon(coin);
		f[4][7].setIcon(coin);
		f[5][7].setIcon(coin);
		f[9][7].setIcon(coin);
		f[10][7].setIcon(coin);
		f[12][7].setIcon(pacman);
		f[2][8].setIcon(coin);
		f[5][8].setIcon(coin);
		f[9][8].setIcon(coin);
		f[12][8].setIcon(coin);
		f[2][9].setIcon(coin);
		f[3][9].setIcon(coin);
		f[4][9].setIcon(coin);
		f[5][9].setIcon(coin);
		f[6][9].setIcon(coin);
		f[7][9].setIcon(coin);
		f[8][9].setIcon(coin);
		f[9][9].setIcon(coin);
		f[11][9].setIcon(coin);
		f[12][9].setIcon(coin);
		f[1][10].setIcon(coin);
		f[2][10].setIcon(coin);
		f[3][10].setIcon(coin);
		f[5][10].setIcon(coin);
		f[8][10].setIcon(coin);
		f[9][10].setIcon(coin);
		f[10][10].setIcon(coin);
		f[11][10].setIcon(coin);
		f[12][10].setIcon(coin);
		f[1][11].setIcon(coin);
		f[3][11].setIcon(coin);
		f[5][11].setIcon(coin);
		f[8][11].setIcon(coin);
		f[10][11].setIcon(coin);
		f[1][12].setIcon(coin);
		f[2][12].setIcon(coin);
		f[3][12].setIcon(coin);
		f[5][12].setIcon(coin);
		f[6][12].setIcon(coin);
		f[7][12].setIcon(coin);
		f[8][12].setIcon(coin);
		f[10][12].setIcon(coin);
		f[11][12].setIcon(coin);
		f[12][12].setIcon(coin);
		f[7][7].setIcon(empty);
		f[6][7].setIcon(hansung); 
		
		frame.add(panel);
		frame.setTitle("1292073 안기태 1292078 이호준");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}

	
	private static Random random;
	private static int pacmanUP, pacmanS, enemyUP, enemyS, numOfDot, where, start;
	private static int fieldMin=0, fieldMax=13;
	private static Icon temp1, temp2, temp3, temp4, temp;

	private static final int FRAME_WIDTH = 690; //맵사이즈
	private static final int FRAME_HEIGHT = 650; //맵사이즈
}