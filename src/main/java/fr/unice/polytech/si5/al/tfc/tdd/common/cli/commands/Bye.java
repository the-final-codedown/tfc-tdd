package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.api.PolyDiplomaPublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;

public class Bye extends Command<PolyDiplomaPublicAPI> {

	@Override
	public String identifier() {
		return "Bye";
	}

	@Override
	public void execute() { }

	@Override
	public String describe() {
		return "Exit PolyDiploma";
	}

	@Override
	public boolean shouldContinue() { return false; }

}
