import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TCP14 {
    public static void main(String[] args) {

        Map<Character , Integer> map = new LinkedHashMap<>();

        String s = "asassdsdfsd";
        for(int i=0;i<s.length();++i){
            map.put(s.charAt(i) , map.getOrDefault(s.charAt(i),0)+1);
        }

        for(Map.Entry<Character , Integer> m : map.entrySet()){
//            m.getKey() ,
        }

    }
}
