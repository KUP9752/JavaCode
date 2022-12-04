package MoC_Calculations;

import static MoC_Calculations.BodyType.*;
import static MoC_Calculations.PairEncode.*;

public class InstrcutionBody {
  static public int INV_IDX = -1;
  private final BodyType type;
  private int R = INV_IDX;
  private int L1 = INV_IDX;
  private int L2= INV_IDX;

  private InstrcutionBody(BodyType type, int R, int L1, int L2) {
    this.type = type;
    switch (type) {
      case MINUS:
        this.L2 = L2;
      case PLUS:
        this.R = R;
        this.L1 = L1;
      case HALT:
      default:
        break;
    }

  }

  static public InstrcutionBody plusBody(int R, int label) {
    return new InstrcutionBody(PLUS, R, label, INV_IDX );
  }

  static public InstrcutionBody minusBody(int R, int L1, int L2) {
    return new InstrcutionBody(MINUS, R, L1, L2 );
  }

  static public InstrcutionBody haltBody() {
    return new InstrcutionBody(HALT, INV_IDX, INV_IDX, INV_IDX);
  }

  public static InstrcutionBody decodeInstrcution(int code) {
    if (code == 0) return haltBody();
    Pair<Integer, Integer> dPair = decodeDoublePair(code);
    /* For +R we have <<2i, j> */
    if (dPair.first() % 2 == 0)
      return plusBody(dPair.first() / 2, dPair.second());
    //else it is the MINUS CASE
    Pair<Integer, Integer> sPair;
    sPair = decodeSinglePair(dPair.second());
    return minusBody((dPair.first() - 1) / 2, sPair.first(), sPair.second());

  }
  public int encode() {
    return switch (type) {
      case HALT -> 0;
      case PLUS -> encodeDoublePair(2 * R, L1);
      case MINUS -> encodeDoublePair(2 * R + 1, encodeSinglePair(L1, L2));
    };
  }

  public String viewPair(){
    if (type == HALT) return "0";
    StringBuilder sb = new StringBuilder();
    sb.append("<<");
    switch (type) {
      case PLUS -> sb.append(2 * R).append(",").append(L1);
      case MINUS -> sb.append(2 * R + 1).append(",").append("<").append(L1).append(",").append(L2).append(">");
    }
    sb.append(">>");
    return sb.toString();
  }

  @Override
  public String toString() {
    if (type == HALT) return "HALT";
    boolean isMinus = type == MINUS;
    StringBuilder sb = new StringBuilder();
    sb.append(isMinus ? "-" : "+");
    sb.append("R").append(R).append("-> ").append("L").append(L1);
    return isMinus ? sb.append(", L").append(L2).toString()
            : sb.toString();
  }

}
