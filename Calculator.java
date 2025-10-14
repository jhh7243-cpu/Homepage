import java.util.Scanner;

public class Calculator
{
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) 
    {
        System.out.println("=== 환영합니다! ===");
        
        boolean running = true;
        
        while (running) 
        {
            showMenu();
            int choice = getMenuChoice();
            
            switch (choice) 
            {
                case 1:
                    greetUser();
                    break;
                case 2:
                    calculator();
                    break;
                case 3:
                    printNumbers();
                    break;
                case 4:
                    System.out.println("프로그램을 종료합니다. 안녕히 가세요!");
                    running = false;
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 1-4 중에서 선택해주세요.");
            }
            
            if (running) 
            {
                System.out.println("\n계속하려면 Enter를 누르세요...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    // 메뉴 출력
    public static void showMenu() 
    {
        System.out.println("\n=== 메뉴를 선택하세요 ===");
        System.out.println("1. 인사하기");
        System.out.println("2. 계산기");
        System.out.println("3. 숫자 출력");
        System.out.println("4. 종료");
        System.out.print("선택: ");
    }
    
    // 메뉴 선택 받기
    public static int getMenuChoice() 
    {
        try 
        {
            int choice = scanner.nextInt();
            scanner.nextLine(); // 입력 버퍼 정리
            return choice;
        } 
        catch (Exception e) 
        {
            scanner.nextLine(); // 잘못된 입력 제거
            return -1;
        }
    }
    
    // 사용자에게 인사하는 메서드
    public static void greetUser() 
    {
        System.out.print("이름을 입력하세요: ");
        String name = scanner.nextLine();
        System.out.println("안녕하세요, " + name + "님! 반갑습니다!");
    }
    
    // 간단한 계산기 메서드
    public static void calculator() 
    {
        System.out.print("첫 번째 숫자를 입력하세요: ");
        double num1 = scanner.nextDouble();
        
        System.out.print("두 번째 숫자를 입력하세요: ");
        double num2 = scanner.nextDouble();
        scanner.nextLine(); // 입력 버퍼 정리
        
        System.out.println("덧셈: " + (num1 + num2));
        System.out.println("뺄셈: " + (num1 - num2));
        System.out.println("곱셈: " + (num1 * num2));
        System.out.println("나눗셈: " + (num1 / num2));
    }
    
    // 1부터 10까지 숫자 출력하는 메서드
    public static void printNumbers() 
    {
        System.out.println("1부터 10까지의 숫자:");
        for (int i = 1; i <= 10; i++) 
        {
            System.out.print(i + " ");
        }
        System.out.println(); // 줄바꿈
    }
}
