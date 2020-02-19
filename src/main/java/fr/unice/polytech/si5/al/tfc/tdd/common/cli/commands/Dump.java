package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.PolyDiplomaPublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.DumpClient;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.ProfileClient;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Dump extends Command<PolyDiplomaPublicAPI> {


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
		return "Dump !";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
