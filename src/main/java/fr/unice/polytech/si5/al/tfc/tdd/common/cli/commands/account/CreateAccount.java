package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.PublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.AccountClient;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CreateAccount extends Command<PublicAPI> {
	private String mail;
	private int money;
	private String AccountType;

	@Override
	public void load(List<String> args){
		this.money = Integer.parseInt(args.get(0));
		this.AccountType = args.get(1);
		this.mail = args.get(2);
	}

	@Override
	public String identifier() {
		return "create_account";
	}

	@Override
	public void execute() throws UnsupportedEncodingException {
		System.out.println(AccountClient.createAccount(this.money, this.AccountType, this.mail));
	}

	@Override
	public String describe() {
		return "{ money, accountType, mail}";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
