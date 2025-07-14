public class IndexOfFirstOcc {
    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
    public static void main(String[] args) {
        IndexOfFirstOcc obj = new IndexOfFirstOcc();
        String haystack = "sadbutsad";
        String needle = "sad";
        int index = obj.strStr(haystack, needle);
        System.out.println(index);
    }
}
