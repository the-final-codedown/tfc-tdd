package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.api.PolyDiplomaPublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.api.AccountClient;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CreateAccount extends Command<PolyDiplomaPublicAPI> {
	private String mail;
	private int money;
	private String idAccount;

	@Override
	public void load(List<String> args){
		this.money = Integer.parseInt(args.get(0));
		this.mail = args.get(1);
		this.idAccount = args.get(2);
	}

	@Override
	public String identifier() {
		return "create_profile";
	}

	@Override
	public void execute() throws UnsupportedEncodingException {
		System.out.println(AccountClient.createAccount(this.money, this.mail, this.idAccount));
	}

	@Override
	public String describe() {
		return "Permit to create profile user {money, mail, idAccount}";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
