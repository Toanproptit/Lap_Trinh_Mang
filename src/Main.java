public class Main {
    public static void main(String[] args) {
        int n = 65;

        String binary = Integer.toBinaryString(65);

        String hex = Integer.toHexString(n);

        String oc = Integer.toOctalString(65);

        int s = Integer.parseInt(binary , 2);

        System.out.println(s);

        System.out.println(binary);
        System.out.println(hex);
        System.out.println(oc);

    }
}