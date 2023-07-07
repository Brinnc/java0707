package org.sp.app0707.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 *IT분야에서 입력도구는 다양하지만, 그 중 표준입력도구인 키보드로부터 데이터를 읽어보자
 *주의! FileInputStream이 파일을 대상으로 하는 입력스트림이라고 하여 키보드 입력스트림 같은 것이 별도로 존재(지원)하지는 않음 
 *즉, 추상클래스인 InputStream으로 처리해야함
 *why? 당장 내일이라도 어떤 입력 디바이스가 새로 존재할지 알수 없으니까
*/
public class KeyboardTest {
	
	public static void main(String[] args) {
		
		//디바이스, 파일 등 다양한 매체로부터 입력을 받기위한 입력스트림 객체를 사용해야 하고
		//이 클래스는 추상클래스로 지원됨. 따라서 new불가.
		InputStream is=System.in; //윈도우에 존재하는 입력 스트림을 가져온것, in의 자료형이 InputStream
											//윈도우에서 빌려왓기때문에 close()할 필요가 업따!
											//final인데 대문자가 아닌 이유? public이 아니기 떄문
											//public static final
									
		
		//InputStream은 바이트 기반이기 때문에 한글은 깨짐
		//한글이 깨지지 않게하기위해 reader를 이용하자
		InputStreamReader reader=null;
		reader=new InputStreamReader(is);
		
		//버퍼를 씌우자
		BufferedReader buffr=null;
		buffr=new BufferedReader(reader);
		
	
		try {
			//int data=-1;
			String msg=null;
			//read()메서드를 데이터를 읽을 때까지 대기상태에 빠짐
			while(true) {
				msg=buffr.readLine(); //무한대기상태 =임시 break와 같은 역할을 해서 while문과 함께 많이 사용함 
				//System.out.println("읽었어");
				System.out.print(msg);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(); //이미 os가 얻어놓은 출력 스트림을 이용해 모니터에 출력하는 메서드
	}
}
