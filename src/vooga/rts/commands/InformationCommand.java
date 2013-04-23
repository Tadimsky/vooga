package vooga.rts.commands;

import vooga.rts.util.Information;

public class InformationCommand extends Command {

	private Information myInformation;

	public InformationCommand(String inputName, Information Info) {
		super(inputName);
		myInformation = Info;
	}

	public Information getInfo() {
		return myInformation;
	}

}
