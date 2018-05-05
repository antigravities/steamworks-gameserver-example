package cafe.cutie.steamworks.samplegs;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;

import com.codedisaster.steamworks.SteamGameServerAPI;
import com.codedisaster.steamworks.SteamAuth.BeginAuthSessionResult;
import com.codedisaster.steamworks.SteamID;

import java.nio.ByteBuffer;

public class ServerListener implements Listener {
  private Server server;
  
  public ServerListener(int port){
    this.server = new Server();
    
    this.server.getKryo().register(SteamworksTicketPacket.class);
    this.server.getKryo().register(SteamworksTicketPacketResponse.class);
    
    this.server.start();
    this.server.bind(port, port);
    
    this.server.addListener(this);
  }
  
  public void received(Connection c, Object object) {
    if( object instanceof SteamworksTicketPacket ){
      SteamworksTicketPacket t = (SteamworksTicketPacket) object;
      BeginAuthSessionResult r = SteamGameServerAPI.beginAuthSession(t.ticket, SteamID.createFromNativeHandle(t.steamid));
      
      SteamworksTicketPacketResponse x = new SteamworksTicketPacketResponse();
      x.status = r.ordinal(); // Normally not recommended but left here for test purposes - see https://stackoverflow.com/questions/8157755/how-to-convert-enum-value-to-int
      
      c.sendTCP(x);
    }
  }
  
  public void shutdown(){
    this.server.stop();
  }
}