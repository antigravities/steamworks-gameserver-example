package cafe.cutie.steamworks.samplegs;

import com.codedisaster.steamworks.SteamGameServerAPI;
import com.codedisaster.steamworks.SteamException;

import java.util.Scanner;

class SampleGS {
  static boolean running = true;
  static final int TicketSize = 40;
  
  public static void main(String[] args){
    try {
      if( ! SteamGameServerAPI.init((127 << 24) + 1, (short) 27015, (short) 27016, (short) 27017, SteamGameServerAPI.ServerMode.NoAuthentication, "0.0.1") ){
        System.out.println("Failed to initialize.");
      } else {
        System.out.println("OK! Initialized Steam.");
      }
    } catch(SteamException e){
      e.printStackTrace();
    }
    
    new Thread(new Runnable(){
      
      @Override
      public void run(){
        ServerListener l = new ServerListener(3939);
        
        while( running ){
          SteamGameServerAPI.runCallbacks();
        }
        
        SteamGameServerAPI.shutdown();
        l.shutdown();
        
        System.out.println("Sayonara!");
      }
    }).start();
    
    Scanner sc = new Scanner(System.in);
    System.out.print("Press enter to close the server...");
    sc.nextLine();
    sc.close();
    
    running = false;
  }
}