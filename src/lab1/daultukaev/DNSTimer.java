package lab1.daultukaev;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class DNSTimer {
    public void process() throws Exception {
        System.out.println("Enter 3 addresses: ");
        Scanner scan = new Scanner(System.in);
        ArrayList<String> addrs = new ArrayList<String>();
        Double[] Total = new Double[3];
        for(int i = 0; i < 3; i++)
        {
            Total[i] = 0.0;
            String tmp = scan.nextLine();
            addrs.add(tmp);
        }
        scan.close();

        Double[] Average = new Double[3];

        for(int i = 0; i < 9; i++)
        {
            ProcessBuilder builder = new ProcessBuilder(
                    "/bin/bash", "-c", "ping " + addrs.get(i%3)
            );
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            reader.readLine();
            String tmp = reader.readLine();
            try {
                Total[i % 3] += Double.parseDouble(tmp.substring(tmp.indexOf("time=") + 5, tmp.indexOf(" ms")));
            } catch (Exception e)
            {
                System.out.println("ERROR" + e);
                System.exit(1);
            }
        }
        Map<String, Double> map = new HashMap<String, Double>();
        for(int i = 0; i < 3; i++)
        {
            Average[i] = Total[i]/3;
            map.put(addrs.get(i), Average[i]);
        }
        Arrays.sort(Average, 0, 3);
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(Objects.equals(Average[i], map.get(addrs.get(j))))
                {
                    System.out.println(Average[i] + "(" + addrs.get(j) + ")");
                    break;
                }
            }
        }

    }
}
