package fr.unice.polytech.si5.al.tfc.tdd.common.cli;

import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.Bye;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account.AccountsByType;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account.CreateAccount;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account.GetCap;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.account.ViewAccounts;
import fr.unice.polytech.si5.al.tfc.tdd.common.cli.commands.profile.CreateProfile;
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
        this.invite = "PolyDiploma";
        // Registering the command available for the user
        register(
                Bye.class,
                CreateProfile.class,
                CreateAccount.class,
                AccountsByType.class,
                GetCap.class,
                ViewAccounts.class
        );
    }

    public static void main(String[] args) {
        System.out.println("\n\nStarting PolyDiploma Admin API");
        TFCclient main = new TFCclient();
        main.run();
        System.out.println("Exiting PolyDiploma Admin API\n\n");
    }

}
