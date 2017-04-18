package FabLab;

/**
 * Created by jonas on 12/04/2017.
 */
public interface RFIDReader {

    boolean isCardPresent();
    String readUID();
    boolean detectReader();
}
