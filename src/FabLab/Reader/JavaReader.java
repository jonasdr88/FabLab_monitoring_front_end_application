package FabLab.Reader;

import javax.smartcardio.*;
import java.util.List;
import FabLab.RFIDReader;
import javafx.concurrent.Task;

/**
 * Created by jonas on 12/04/2017.
 */
public class JavaReader implements RFIDReader {

    String UID = "";
    CardTerminal cardTerminal;

    @Override
    public boolean isCardPresent() {
        return false;
    }

    @Override
    public boolean detectReader()
    {
        TerminalFactory terminalFactory = TerminalFactory.getDefault();
        try
        {
            List<CardTerminal> cardTerminalList = terminalFactory.terminals().list();
            if (cardTerminalList.size() > 0) {
                System.out.println("Congratulations, setup is working. At least 1 cardreader is detected");
                cardTerminal = cardTerminalList.get(0);
                return true;
            }
            else
                System.out.println("No Cardreader Detected");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readUID() {
        try {
            cardTerminal.waitForCardPresent(0);
            handleCard(cardTerminal);
            cardTerminal.waitForCardAbsent(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return UID;
    }

    static byte[] getAddress = new byte[]{(byte) 0xff, (byte) 0xca, 0, 0, 0};

    private void handleCard(CardTerminal cardTerminal) throws InterruptedException {
        Card card;
        try {
            card = cardTerminal.connect("*");
            try {
                CardChannel channel = card.getBasicChannel();
                CommandAPDU getUIDCommand = new CommandAPDU(getAddress);
                ResponseAPDU getUIDResponse = channel.transmit(getUIDCommand);
                byte[] getUIDResponseData = getUIDResponse.getData();
                final String UIDdata = readable(getUIDResponseData);
                UID = UIDdata;

            } catch (CardException e) {
                System.out.println("An exception occured while reading the identifier");
                e.printStackTrace();
            }
        } catch (CardException e) {
            System.out.println("Couldn't read card, try again");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String readable(byte[] src) {
        String answer = "";
        for (byte b : src) {
            answer = answer + String.format("%02X", b);
        }
        return answer;
    }
}

