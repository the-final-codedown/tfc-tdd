package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.PolyDiplomaPublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.ProfileClient;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CreateProfile extends Command<PolyDiplomaPublicAPI> {
	private String mail;

	@Override
	public void load(List<String> args){
		this.mail = args.get(0);
	}

	@Override
	public String identifier() {
		return "create_profile";
	}

	@Override
	public void execute() throws UnsupportedEncodingException {
		System.out.println(ProfileClient.createProfile(this.mail));
	}

	@Override
	public String describe() {
		return "Permit to create profile user";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
