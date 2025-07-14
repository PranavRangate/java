public class LongCommPrefix{
    public String longestCommonPrefix(String[] strs) {
         if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }
    return prefix;
    }
    public static void main(String[] args) {
        LongCommPrefix obj = new LongCommPrefix();
        String[] strs = {"flower","flow","flight"};
        String prefix = obj.longestCommonPrefix(strs);
        System.out.println(prefix);
    }
}