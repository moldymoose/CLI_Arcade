package Server.Games;

import Server.Game;
import Server.Player;

public class Arcade extends Game {

    public Arcade() {
        super("Arcade Menu", Integer.MAX_VALUE);
    }

    @Override
    public boolean getInput(Player player) {
        String input = player.getInput();
        if (input.startsWith("/msg ")) {
            this.addMessage(input.substring(5), player);
            this.displayChat(5);
        }
        return false;   // Inescapable menu.  Should probably return true if input was "exit"
    }
}