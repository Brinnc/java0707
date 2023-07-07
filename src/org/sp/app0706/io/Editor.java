package org.sp.app0706.io;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Editor extends JFrame implements ActionListener {
	JMenuBar bar;
	JMenu[] menu;
	JMenuItem[] item;
	JMenuItem[] fontItem; //폰트 설정 아이템
	JTextArea area;
	JScrollPane scroll;
	JFileChooser chooser;
	
	File file; //현재 열어놓은 파일
	//0707) 열어놓은 파일의 내용을 수시로 저장할 스트림
	FileWriter writer; //문자기반 출력스트림
	//BufferedWriter buffw; //버퍼를 지원하는 문자기반 출력스트림
	
	int i=0;

	public Editor() {
		bar = new JMenuBar();
		menu = new JMenu[5];
		item = new JMenuItem[8];
		area = new JTextArea();
		scroll = new JScrollPane(area);
		chooser = new JFileChooser("D:/morning/javase_workspace");

		// 메뉴 5개 생성
		String[] menuName = { "파일", "편집", "서식", "보기", "도움말" }; // 메뉴 이름에 대한 배열, 선언과 동시에 초기화하자 -> 자바에서는 {}
		for (int i = 0; i < menu.length; i++) {
			menu[i] = new JMenu(menuName[i]);

			// 생성된 메뉴를 바에 부착
			bar.add(menu[i]);
		}

		// 메뉴 아이템 생성
		String[] itemName = { "새로만들기", "새창", "열기", "저장", "다른이름으로 저장", "페이지 설정", "인쇄", "끝내기" };
		// java 5(jdk 1.5) 부터는 개선된(improved) for문을 지원함
		// 반복문의 대상이 컬렉션, 배열 등의 집합인 경우 유용
		// for(String name : itemName) {
		// 오른쪽 집합의 수만큼 자동으로 돌아가고, 자료형만 개발자가 정해주면 됨, 단 속도는 느려서 게임같은 프로그램에는 적합하지 않음
		// 또한 반복문 안에서 집합(배열)의 각 개체에 따로 접근해 조정할 필요가 있을 경우에도 적합하지 않음
		// }
		for (int i = 0; i < itemName.length; i++) {
			// System.out.println(item);
			item[i] = new JMenuItem(itemName[i]);
			menu[0].add(item[i]);

			// 메뉴아이템들과 리스너 연결
			item[i].addActionListener(this);
		}
		
		//0707) 폰트 설정 아이템 생성
		fontItem=new JMenuItem[10];
		int n=10;
		for(int i=0; i<fontItem.length; i++) {
			fontItem[i]=new JMenuItem(Integer.toString(n));
			n+=2;
			menu[2].add(fontItem[i]);
			
			//이벤트 연결
			fontItem[i].addActionListener(this);
			
		}

		// 속성지정
		area.setBackground(Color.BLACK);
		area.setForeground(Color.WHITE);

		// 조립
		setJMenuBar(bar); // 프레임에 바를 부착함
		add(scroll);

		setSize(800, 700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // =margin auto(js)

		setFont(20); //고정??하면 안될거같은..

	}

	public void setFont(int point) {
		Font font = new Font("돋움체", Font.PLAIN, point); 
		area.setFont(font);
	}

	//1) 바이트 기반 스트림으로 파일 열기
	public void openFile() {
		System.out.println("열려라참깨");
		int result = chooser.showOpenDialog(this); //탐색기: showOpenDialog(Component parent)
		FileInputStream fis = null; 
		//스트림은 기본적으로 1바이트씩 처리하기 때문에 영문이외의 문자를 해석할 수 없음(2바이트 문자-영문 제외 전세계 문자)
		
		if (result == JFileChooser.APPROVE_OPTION) { //확인(열기) 버튼을 누르면
			file = chooser.getSelectedFile();
			try {
				// FileInput스트림 생성시, 경로도 가능하지만 파일 자체도 가능함
				fis = new FileInputStream(file); // (파일클래스 or 경로)
				int data = -1;
				
				byte[] b=new byte[1024]; //read()하기전 바이트를 1024개만큼 담는 용기, 즉 버퍼(buffer)
				
				while (true) {
					data = fis.read(b); // 실행중인 프로그램이 스트림으로부터 한 알갱이, 즉 1바이트 읽기
					if(data==-1)break; //더이상 데이터가 없다면(-1이 반환), 반복문 멈추기
					
					//읽어들인 데이터는 b에 담겨져있기에, 바이트형인 b를 append하기전 스트링형으로 변환
					String str=new String(b);
					System.out.println(str+"\n");
					// char-->String
					// 자바의 모든 기본 자료형마다 1:1대응하는 wrapper클래스가 지원됨
					// char(기본자료형) : Character(객체형)
					//setFont(i);
					area.append(str); // 원래 append()는 스트링형(객체)으로 넣어야하지만,
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	//2) 문자 기반 스트림으로 파일 열기
	public void openFileByReader() {
		int result=chooser.showOpenDialog(this); //파일 탐색기 열기
		if(result==JFileChooser.APPROVE_OPTION) {
			File file=chooser.getSelectedFile();
			
			//영문 뿐 아니라 전세계 모든 문자를 해석할 수 있는 능력이 있는 스트림을 이용해보자
			FileReader fr=null;
			
			try {
				fr=new FileReader(file); //문자 전용 스트림
				int data=-1;
				
				while(true) {
					//The character read : 영문도 한글자, 한글도 한글자로 인식함
					//apple맛나요 -> 9회 읽어들임
					data=fr.read(); // 한 문자 읽기
					if(data==-1)break;
					System.out.println((char)data);
					//만약 한글을 지원하지 않는 폰트를 사용하고 있으면 GUI상 area에서 깨지니까
					//한글을 지원하는 폰트로 변경해주자
					area.append(Character.toString((char)data));
					
				}
				
				
			
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	//3) 버퍼까지 처리된 문자기반 스트림으로 파일 열기 
	//버퍼가 처리된 스트림은 접두어에 Buffered~~
	public void openFileByBuffer() {
		
		int result=chooser.showOpenDialog(this);
		FileReader reader=null;
		BufferedReader buffr=null;
		if(result==JFileChooser.APPROVE_OPTION) {
			file=chooser.getSelectedFile();
			try {
				reader=new FileReader(file);
				buffr=new BufferedReader(reader); //(리더인 者)
				//버퍼리더는 줄바꿈이 일어날때마다(\n) 읽어들임
				String msg=null;
				while(true) {
					msg=buffr.readLine(); //한 줄을 읽어들임(->맨 끝에 \n_줄바꿈을 만나면)
					if(msg==null)break; //스트링(객체)가 없을 때가 null이니까
					area.append(msg+"\n");
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
				if(buffr!=null) { //인스턴스가 존재한다면
					try {
						buffr.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				
				if(reader!=null) { //인스턴스가 존재한다면
					try {
						reader.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//0707) 
	//현재 열어놓은 편집기의 내용을 열어놓은 파일에 저장함 -> 덮어쓰기
	public void saveFile() {
		//파일을 대상으로 한 출력 스트림은 empty 파일을 자동으로 생성해버림
		try {
			//탄생과 동시에 기존의파일을 제거해버리지만,
			//비어있는 파일에 area의 내용을 얻어와 곧바로 다시 출력해버리자
			writer=new FileWriter(file);
			writer.write(area.getText()); //getText()
			//setFont(i); //-> 현재 area에 적용되어있는 폰트 사이즈 적용시켜야하는데 해당코드 효과없음
			writer.close(); 
			//저장 누르면 폰트사이즈적용이 안됨
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		// System.out.println(e);
		JMenuItem obj = (JMenuItem) e.getSource();

		if (obj == item[2]) { // 열기 버튼을 눌렀을때
			//openFile();
			//openFileByReader();
			openFileByBuffer();
			
		}else if(obj==item[3]) { //저장 버튼을 누르면
			//area에 작성된 내용을, 열어놓은 파일에 출력(덮어쓰기함)
			System.out.println("저장뿅");
			saveFile();
			
		}else if(obj==item[7]) { //프로그램 종료
			System.exit(0); //프로세스 종료
		} 
		
		// 0707)
		// 폰트 사이즈 설정은 else if 10개만들어야되나??
		//-> 이렇게 하자! : obj.getText(); J메뉴아이템의 텍스트를 추출
		System.out.println(obj.getText());
		
		//0707) sun사에서 강요하는 예외, 강요하지 않는 예외
		//아래는 강요하지 않는 예외(프로그램이 종료될 정도로 크리티컬하지 않은 예외)지만, 정상처리를 위해 try~catch문을 사용
		// "열기", "10"
		//int i=0;
		try {
			i=Integer.parseInt(obj.getText()); //"10" -> 10
			//setFont(Integer.parseInt(obj.getText()));
		}catch(NumberFormatException e2){
			i=20;
		}
		setFont(i);
		
	}

	public static void main(String[] args) {
		new Editor();
	}
}
