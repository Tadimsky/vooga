package vooga.rts.networking.communications.gamemessage;

import vooga.rts.commands.Command;
import vooga.rts.networking.communications.IMessage;


public class RTSMessage extends GameMessage implements IMessage {

    private static final long serialVersionUID = -4741900659154102286L;
    private Command myCommand;
    private int myUnitId;

    public RTSMessage (Command command, int playerId, int unitId) {
        super(playerId);
        myCommand = command;
        myUnitId = unitId;
    }

    public Command getCommand () {
        return myCommand;
    }

    public int getUnitId () {
        return myUnitId;
    }
}
