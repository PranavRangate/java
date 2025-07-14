public class Palindrome {
    public boolean isPalindrome(String s) {
        s = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
    public static void main(String[] args) {
        Palindrome obj = new Palindrome();
        String s = "A man, a plan, a canal: Panama";
        boolean ans = obj.isPalindrome(s);
        System.out.println(ans);
        
    }
}
