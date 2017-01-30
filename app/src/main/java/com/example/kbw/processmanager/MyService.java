package com.example.kbw.processmanager;

+
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.http.SslCertificate;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyService extends Service {
    public static final String HOST="192.168.43.254";
    public static final int ContactPORT=1234;
    TextView textView;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public  void Alertmaking(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Alert Dialog").setMessage("KS")
                .setMessage("Welcome to dear user.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked on Cancel", Toast.LENGTH_SHORT).show();
                    }
                });


        alertDialog.show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)  {


        Toast.makeText(getApplicationContext(),Integer.toString(getpower())+":"+Float.toString(readUsage())+":"+Float.toString(getMemoryUsage()), Toast.LENGTH_LONG).show();
        System.out.println(Integer.toString(getpower())+":"+Float.toString(readUsage())+":"+Float.toString(getMemoryUsage()));
        boolean flag=true;
        while (flag){
            System.out.println(Integer.toString(getpower())+":"+Float.toString(readUsage())+":"+Float.toString(getMemoryUsage())+":"+Integer.toString(getPID()));
            if(getpower()<40 || readUsage() >30 || getMemoryUsage() >80){
                //getPID();
                int pid=getPID();
                //Toast.makeText(getApplicationContext(),Integer.toString(pid), Toast.LENGTH_LONG).show();
                if(checkCRIU(pid)==true){
                    stopProcess(pid);
                    String IP= contctServer( getProcessUsage(pid));
                    System.out.println("Connect to IP:"+IP);
                    //addresss.getHostName();
                    Migrate(IP,1456);
                }


                try {
                    Thread.sleep(1000);
                }catch (Exception e){

                }
            }
            try{
                Thread.sleep(2000);
            }catch (Exception e){}
        }
        return START_STICKY;
    }

    public void migrate(int pid){
        System.out.println("Migrating Process------ WORK IN PROGRESS");

    }
    public  boolean checkCRIU(int pid){

        //It will contain The Process Manager which is not here
        System.out.println("Checking Checkpointing Conpatibility for Process ------ WORK IN PROGRESS");


        return true;
    }
    public  void stopProcess(int pid){
        System.out.println("Stoping Running Proce------ WORK IN PROGRESS");

    }
    public String contctServer(int Usage){
        try {


            Socket client = new Socket(MyService.HOST, MyService.ContactPORT);
            PrintWriter out=new PrintWriter(client.getOutputStream(),true);
            out.println(Integer.toString(Usage));
            System.out.println("Socket is Create And Connected");
            BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
            //ObjectInputStream instream=new ObjectInputStream(client.getInputStream());

            return "192.168.43.122";

        }catch (Exception e) { e.printStackTrace(); }
        return null;
    }



    public void Migrate(String IP,int port){
        System.out.println("Process Need to be Migrated --------WORK IN PROGRESS");

    }
    public int getProcessUsage(int pid){
        java.lang.Process p = null;
        BufferedReader in = null;
        String returnString = "",string="";
        try {
            p = Runtime.getRuntime().exec("ps -p "+pid);
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            //    in.readLine();
            System.out.println("PS"+in.readLine());
            return 1;
            //Integer.parseInt(in.readLine());
        } catch (IOException e) {
            Log.e("executeTop", "error in getting first line of top");
            e.printStackTrace();
        } finally {
            try {
                in.close();
                p.destroy();
            } catch (IOException e) {
                Log.e("executeTop",
                        "error in closing and destroying top process");
                e.printStackTrace();
            }
        }
        return 1;
    }

    public int getPID(){
        System.out.println("Process PID return");
//        java.lang.Process p = null;
//        BufferedReader in = null;
//        String returnString = "",string="";
//        try {
//            p = Runtime.getRuntime().exec("top -m 100 -d 1 -n 1");
//            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//            while((returnString=in.readLine())==null);
//            System.out.println("PID1:"+returnString);
//            //String[] line=in.readLine().split(" ");
//            //return Integer.parseInt(line[3]);
//
//        } catch (IOException e) {
//            Log.e("executeTop", "error in getting first line of top");
//            e.printStackTrace();
//        } finally {
//            try {
//                in.close();
//                //p.destroy();
//            } catch (IOException e) {
//                Log.e("executeTop",
//                        "error in closing and destroying top process");
//                e.printStackTrace();
//            }
//        }
//        return 1;

        try {
            // -m 10, how many entries you want, -d 1, delay by how much, -n 1,
            // number of iterations
            Process p = Runtime.getRuntime().exec("top -m 1 -d 1 -n 1");

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            final Pattern PATTERN = Pattern.compile("\\s+[0-9]+%");
            final Pattern con=Pattern.compile("[a-z]+");
            String line="";
            do{
                 line = reader.readLine();
                Matcher m=con.matcher(line);
                if(m.find())
                    break;
            }while(true);
            while((line=reader.readLine())==null);
            for(int i=0;i<=4;i++){
                line=reader.readLine();
            }
            while (line != null) {
                //System.out.println("CPUTT"+line);
                Matcher m = PATTERN.matcher(line);
                if(m.find()) {
                   // String[] lin = line.split("  ");
                    //System.out.println("\tlength" + m.group());
                }
                line=reader.readLine();
            }

            p.waitFor();

           // Toast.makeText(getBaseContext(), "Got update",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "Caught", Toast.LENGTH_SHORT).show();
        }
        return 100;
    }
    private float getMemoryUsage() {
        final Pattern PATTERN = Pattern.compile("([a-zA-Z]+):\\s*(\\d+)");

        MemorySize result = new MemorySize();
        String line;
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
            while ((line = reader.readLine()) != null) {
                Matcher m = PATTERN.matcher(line);
                if (m.find()) {
                    String name = m.group(1);
                    String size = m.group(2);

                    if (name.equalsIgnoreCase("MemTotal")) {
                        result.total = Long.parseLong(size);
                    } else if (name.equalsIgnoreCase("MemFree") || name.equalsIgnoreCase("Buffers") ||
                            name.equalsIgnoreCase("Cached") || name.equalsIgnoreCase("SwapFree")) {
                        result.free += Long.parseLong(size);
                    }
                }
            }
            reader.close();

            result.total *= 1024;
            result.free *= 1024;

        } catch (IOException e) {
            e.printStackTrace();
        }

        //return result;
        return (result.total-result.free)/result.total;
    }

    private static class MemorySize {
        public long total = 0;
        public long free = 0;
    }
    private  int getpower(){
        float power=0.0f;

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);


        //are we charging / charged?
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//        if(status==BatteryManager.BATTERY_STATUS_CHARGING)
//            return 100;
        return  status;
    }

    public float readUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();


            String[] toks = load.split(" ");

            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {}

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" ");

            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            return (float)(cpu2 - cpu1) *100/ ((cpu2 + idle2) - (cpu1 + idle1));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    private int[] getCpuUsageStatistic() {

        String tempString = executeTop();

        tempString = tempString.replaceAll(",", "");
        tempString = tempString.replaceAll("User", "");
        tempString = tempString.replaceAll("System", "");
        tempString = tempString.replaceAll("IOW", "");
        tempString = tempString.replaceAll("IRQ", "");
        tempString = tempString.replaceAll("%", "");
        for (int i = 0; i < 10; i++) {
            tempString = tempString.replaceAll("  ", " ");
        }
        tempString = tempString.trim();
        String[] myString = tempString.split(" ");
        int[] cpuUsageAsInt = new int[myString.length];
        for (int i = 0; i < myString.length; i++) {
            myString[i] = myString[i].trim();
            cpuUsageAsInt[i] = Integer.parseInt(myString[i]);
        }
        return cpuUsageAsInt;
    }

    private String executeTop() {
        java.lang.Process p = null;
        BufferedReader in = null;
        String returnString = "",string="";
        try {
            p = Runtime.getRuntime().exec("top -n 1");
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            File root = new File(Environment.getExternalStorageDirectory(), "DCIM");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "log.txt");

            FileWriter writer = new FileWriter(gpxfile);
            while ((returnString=in.readLine())!=null) {
                writer.append(returnString+"\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            Log.e("executeTop", "error in getting first line of top");
            e.printStackTrace();
        } finally {
            try {
                in.close();
                p.destroy();
            } catch (IOException e) {
                Log.e("executeTop",
                        "error in closing and destroying top process");
                e.printStackTrace();
            }
        }
        return string;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}
