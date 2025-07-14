public class reverseString {
    public void reverse_String(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }
    public static void main(String[] args) {
        reverseString obj = new reverseString();
        char[] s = {'h','e','l','l','o'};
        obj.reverse_String(s);
        System.out.println(s);
    }
}
