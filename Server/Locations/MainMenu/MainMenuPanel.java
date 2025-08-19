package Server.Locations.MainMenu;

import Server.ClientHandler;
import Server.LocationResources.Panel;

public class MainMenuPanel implements Panel {

    @Override
    public String getContents(ClientHandler client) {
        StringBuilder contents = new StringBuilder();
        
        contents.append("Main Menu\n\n");
        contents.append("Welcome " + client.getCurrentUser().getName() + "!\n\n\n");

        contents.append(client.getCurrentLocation().getPlayers().size() + " Players Connected!\n");
        
        return contents.toString();
    }
    
}
