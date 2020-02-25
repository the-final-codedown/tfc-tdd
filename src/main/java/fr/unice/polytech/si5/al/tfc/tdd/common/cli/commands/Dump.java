package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.PublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.DumpClient;

import java.io.UnsupportedEncodingException;

public class Dump extends Command<PublicAPI> {


	@Override
	public String identifier() {
		return "dump";
	}

	@Override
	public void execute() throws UnsupportedEncodingException {
		System.out.println(DumpClient.dump());
	}

	@Override
	public String describe() {
		return "";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
