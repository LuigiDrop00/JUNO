package model;

public final class LoginState {
    
    private LoginState(){}
    private static Player loggedPlayer;

    public static Player getLoggedPlayer() {
        return loggedPlayer;
    }
    public static void setLoggedPlayer(Player loggedPlayer) {
        LoginState.loggedPlayer = loggedPlayer;
    }
}
