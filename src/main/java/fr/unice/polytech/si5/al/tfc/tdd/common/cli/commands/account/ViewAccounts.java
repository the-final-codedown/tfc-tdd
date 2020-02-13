package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.api.PolyDiplomaPublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.api.AccountClient;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ViewAccounts extends Command<PolyDiplomaPublicAPI> {
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
		return "Permit to get account {idAccount}";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
