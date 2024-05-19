package TriangleMemorize;

import java.util.*;

public class TirangleMemorize {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		int num2 = sc.nextInt();
		int num3 =sc.nextInt();
		int sum = 0;
		
		sum = num+num2+num3;
		
		if(sum == 180) {
			if(num == num2 && num2 == num3) {
				System.out.print("Equilateral");
			}
			else if(num == num2 || num2 == num3 || num == num3) {
				System.out.print("Isosceles");
			}
			else {
				System.out.print("Scalene");
			}
		}
		else {
			System.out.print("Error");
		}
		
	}
}