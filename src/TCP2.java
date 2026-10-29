//import java.io.*;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//class Student implements Serializable {
//    private static final long serialVersionUID = 20151107L;
//
//    int id;
//    String code , gpaLetter;
//    float gpa;
//}
//
//public class TCP2 {
//    public static void main(String[] args) {
//        String host = "123123";
//        int port = 12312;
//
//        String studentCode = "B23DCCN833";
//        String qCode = "asdas";
//
//        try{
//            Socket socket = new Socket(host,port);
//            socket.setSoTimeout(5000);
//
//            InputStream in = socket.getInputStream();
//
//            OutputStream out = socket.getOutputStream();
//
//            byte[] buffer = new byte[1024];
//
//            int bytes = in.read(buffer);
//
//            String response = new String(buffer , 0 , bytes);
//
//            String request = studentCode + ";" + qCode;
//
//            out.writeObject(request);
//            out.flush();
//
//            Student student = (Student) in.readObject();
//
//            student.code= student.code.toUpperCase();
//
//            float gpa = student.gpa;
//
//            if(gpa>=3.6)student.gpaLetter="A";
//            else if(gpa>=3.2) student.gpaLetter="B";
//            else if (gpa>=2.5) student.gpaLetter="C";
//            else if (gpa>=2.0) {
//                student.gpaLetter  = "D";
//            }
//            else student.gpaLetter = "F";
//
//            out.writeObject(student);
//            out.flush();
//
//            out.close();
//            in.close();
//            socket.close();
//
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
