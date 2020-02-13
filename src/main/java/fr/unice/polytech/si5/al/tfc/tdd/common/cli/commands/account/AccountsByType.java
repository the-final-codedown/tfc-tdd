package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.api.PolyDiplomaPublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.api.AccountClient;
import org.json.simple.parser.ParseException;

import java.util.List;

public class AccountsByType extends Command<PolyDiplomaPublicAPI> {
	private String accountType;

	@Override
	public void load(List<String> args){
		this.accountType = args.get(0);
	}

	@Override
	public String identifier() {
		return "account_by_type";
	}

	@Override
	public void execute() throws ParseException {
		System.out.println(AccountClient.getAccountByType(this.accountType));
	}

	@Override
	public String describe() {
		return "Permit to get all account of a type {accountType}";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
