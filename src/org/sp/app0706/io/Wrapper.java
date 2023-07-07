package org.sp.app0706.io;

public class Wrapper {
	public static void main(String[] args) {
		String x="3";
		String y="5";
		
		int k=0; //자바의 기본자료형을 사용해 정수 0을 대입
		Integer i=5; //자바의 객체자료형 중 Integer 자료형으로 정수 5를 대입
		//원래되로라면 new Integer(5);가 정석이지만, 많이 사용하기 때문에 바로 숫자를 대입해도 됨
		//이처럼 객체임에도 불구하고 위처럼 일반 변수 대입방식을 지원하는 기법을 일반 데이터를 객체로 감쌌다고 하여 Boxing이라 함
		
		//System.out.println(x+y); -> 35
		System.out.println(i.parseInt(x)+i.parseInt(y)); //->8
		
		//자바의 모든 기본 자료형마다 1:1로 대응되는 클래스를 가리켜
		//Wrapper클래스라고 부르며, 기본자료형이 아닌 객체자료형이므로,
		//기본자료형보다 더 많은 처리를 지원함
		//왜?? 변수와 메서드를 갖고 있기 때문임
		//byte: Byte , short : Short , int: Integer , long : Long , ... -> 왼쪽의 객체형은 메서드를 사용할 수 있기때문에 더 다양한 조정이 가능함
		
		//wrapper클래스의 사용 목적
		//1) 기본자ㅛ형으로는 할수 없는 더 많은 처리를 위해 사용
		//2) 기본 자료형과 객체 자료형간의 형변환을 지원하기 위해 사용
		
		int p=6;
		Integer.toString(p); // "6" -> 기본자료형이었던 p가 객체로 변했다 !
		
		
	}
}
