package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.PublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.AccountClient;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ViewAccounts extends Command<PublicAPI> {
	private String idAccount;

	@Override
	public void load(List<String> args){
		this.idAccount = args.get(0);
	}

	@Override
	public String identifier() {
		return "view_account";
	}

	@Override
	public void execute() throws UnsupportedEncodingException {
		System.out.println(AccountClient.viewAccount( this.idAccount));
	}

	@Override
	public String describe() {
		return " { idAccount }";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
