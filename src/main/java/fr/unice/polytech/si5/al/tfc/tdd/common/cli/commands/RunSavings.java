package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.PublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.ProfileClient;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.SavingsClient;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RunSavings extends Command<PublicAPI> {

	@Override
	public void load(List<String> args){
	}

	@Override
	public String identifier() {
		return "run_savings";
	}

	@Override
	public void execute() throws UnsupportedEncodingException {
		System.out.println(SavingsClient.computingSavings());
	}

	@Override
	public String describe() {
		return "";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
