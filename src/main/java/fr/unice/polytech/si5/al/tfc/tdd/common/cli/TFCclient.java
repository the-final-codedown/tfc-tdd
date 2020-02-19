package fr.unice.polytech.si5.al.tfc.tdd.common.cli;

import  fr.unice.polytech.si5.al.tfc.tdd.common.cli.PolyDiplomaPublicAPI;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.Bye;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.Dump;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.Pay;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account.AccountsByType;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account.CreateAccount;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account.GetCap;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account.ViewAccounts;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.CreateProfile;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.framework.Shell;

/**
 * An Interactive shell that interacts with a Cookie on Demand instance
 * Use -Dexec.args="IP_ADDRESS PORT_NUMBER" to change host/port parameters
 */
public class TFCclient extends Shell<PolyDiplomaPublicAPI> {

    /**
     * describe whitch command is available
     *
     */
    public TFCclient() {

        this.system = new PolyDiplomaPublicAPI();
        this.invite = "TFC";
        // Registering the command available for the user
        register(
                Bye.class,
                CreateProfile.class,
                CreateAccount.class,
                AccountsByType.class,
                GetCap.class,
                ViewAccounts.class,
                Pay.class,
                Dump.class
        );
    }

    public static void main(String[] args) {
        System.out.println("\n\nStarting TFC - CLI");

        TFCclient main = new TFCclient();
        main.run();
        System.out.println("Exiting TFC - CLI\n\n");
    }

}
