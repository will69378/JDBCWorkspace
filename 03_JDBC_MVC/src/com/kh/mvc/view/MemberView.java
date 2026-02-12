package com.kh.mvc.view;

import java.util.Scanner;

import com.kh.mvc.controller.MemberController;

/*

	View : 사용자가 보게 될 시각적인 요소를 담당

*/


public class MemberView {
	private Scanner sc = new Scanner(System.in);
    private MemberController mc = new MemberController();


    /**
     * 사용자가 보게될 메인화면
     */
    public void mainMenu() {

        while(true) {

            System.out.println("***** 회원 프로그램 *****");
            System.out.println("1. 회원 추가");
            System.out.println("2. 회원 전체 조회");
            System.out.println("3. 회원 아이디로 검색");
            System.out.println("4. 회원 이름 키워드 검색");
            System.out.println("5. 회원 정보 변경");
            System.out.println("6. 회원 탈퇴 기능");
            System.out.println("9. 프로그램 종료");
            System.out.println("----------------------------------------------");
            System.out.print("이용할 메뉴 선택 : ");
            int menu = Integer.parseInt(sc.nextLine());

            switch(menu){
            case 1: insertMember(); break;
//          case 2: selectAll(); break;
//          case 3: selectByUserId(); break;
//          case 4: selectByUserName(); break;
//          case 5: updateMember(); break;
//          case 6: deleteMember(); break;
            case 9: System.out.println("프로그램을 종료합니다.."); return;
            default : System.out.println("잘못된 메뉴를 선택했습니다. 다시입력해주세요.");
            }
        }
    }
    /*
		회원 추가용 View
		추가하고자 하는 회원의 정보를 입력받아, Controller에 회원추가 요청을 보낸다
    */
    
	private void insertMember() {
		System.out.println("----- 회원추가 -----");

        System.out.print("아이디 : ");
        String userId = sc.nextLine();

        System.out.print("비밀번호 : ");
        String userPwd = sc.nextLine();

        System.out.print("이름 : ");
        String userName = sc.nextLine();

        System.out.print("성별(M/F) : ");
        String gender = sc.nextLine().toUpperCase();
        
        System.out.print("나이 : ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("이메일 : ");
        String email = sc.nextLine();

        System.out.print("핸드폰 : ");
        String phone = sc.nextLine();

        System.out.print("주소 : ");
        String address = sc.nextLine();
        
        mc.insertMember(userId,userPwd,userName,gender,age,email,phone,address);
	}

	public void displaySuccess(String string) {
		System.out.println(string);
	}

	public void displayFail(String string) {
		System.out.println(string);
	}

}
