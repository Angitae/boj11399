import java.util.Arrays;
import java.util.Scanner;
import java.math.*;

public class Solution{
	static int a[];
	static int n;
	static int ans; // ���� ��
	static int temp;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		a = new int[n];
		for(int i = 0; i < n; i++){
			a[i]= sc .nextInt();
		} // ������� �� �ð� �޾��ֱ� 
		temp =0;
		ans = 0;
		Arrays.sort(a); // �迭 ��������
		for(int x = 0; x < n; x++){ 
			temp+= a[x];
			ans += temp;
		}
		System.out.println(ans);
		sc.close();
	}
}