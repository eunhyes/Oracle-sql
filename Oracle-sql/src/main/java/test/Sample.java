package test; // 패키지 선언

/*
 * 메서드
 * 접근제한자1 public, protected, default, private
 * 접근제한자2 static, final
 * 반환타입(int, String, void, [], List, ...)
 * 이름
 * 매개타입
 * {내용}
 */

// import 문 : 클래스 안에서 사용하게 될 클래스 풀네임을 짧게 사용하는 치환문
//java.lang.* -> 없으면 자동으로 컴파일러에 의해 소스코드에 추가됨
import java.lang.*; 

public class Sample { // 클래스 선언 
	// 클래스의 풀 네임 -> 패키지명.클래스명 ex) test.Sample
	
	// 1) 일반 필드(일반 속성, 객체 멤버 변수)
	public int age;
	
	// 2) 생성자 메서드 : 없으면 자동으로 컴파일러에 의해 소스코드에 추가됨
	public Sample() {
		this.age = 0;
	}
	
	// 3) 일반 메서드
	public void print() {
		System.out.println("야호");
	}
	
	// 4) static(정적)필드(정적 속성, 클래스 변수) : 클래스 소스 공간을 빌려서 사용할 뿐, 독립적인 존재
	public static int adultAge;
	
	// 5) static(정적)메서드(클래스 메서드)
	public static void sPrint() {
		System.out.println("정적 메서드");
	}
	
	// Entry Point
	public static void main(String[] args) {
		Sample.adultAge = 19;
		System.out.println(Sample.adultAge);
		Sample.sPrint();
		
		// Sample.age = 11;
		// Sample.print();
		
		Sample s1 = new Sample();
		s1.age = 10;
		s1.print();
		s1.adultAge = 18;
		
		System.out.println(s1.adultAge + " ===== s1.adultAge");
		System.out.println(Sample.adultAge + " ===== Sample.adultAge");
		
		Sample s2 = new Sample();
		s2.age = 20;
		
		System.out.println(s1.age + " ===== s1.age");
		System.out.println(s2.age + " ===== s2.age");
		
	}
}