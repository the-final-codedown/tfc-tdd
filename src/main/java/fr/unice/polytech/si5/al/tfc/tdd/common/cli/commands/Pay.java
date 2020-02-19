package fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands;


import fr.unice.polytech.si5.al.tfc.tdd.common.cli.PublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Command;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.TransferValidatorClient;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Pay extends Command<PublicAPI> {
	private String accountIdOrigin;
	private String accountIdDestination;
	private int value;

	@Override
	public void load(List<String> args){
		this.accountIdOrigin = args.get(0);
		this.accountIdDestination = args.get(1);
		this.value = Integer.parseInt(args.get(2));
	}

	@Override
	public String identifier() {
		return "pay";
	}

	@Override
	public void execute() throws UnsupportedEncodingException {

		TransferValidatorClient transferValidatorClient = new TransferValidatorClient("localhost",50052);
		transferValidatorClient.pay(accountIdOrigin,accountIdDestination,value);
		System.out.println();
	}

	@Override
	public String describe() {
		return "{accountIdOrigin accountIdDestination value}";
	}

	@Override
	public boolean shouldContinue() { return true; }

}
