package MoC_Calculations;

import java.util.ArrayList;
import java.util.List;

import static MoC_Calculations.InstrcutionBody.decodeInstrcution;
import static java.lang.Math.pow;

public class PairEncode {

  public static int encodeDoublePair(int x, int y) {
    return (int) (pow(2, x) * (2 * y + 1));
  }
  public static Pair<Integer, Integer> decodeDoublePair(int code) {
    int x = 0, y = 0;

//    if (code % 2 == 1) x = 1;
//    else
    while (code % 2 == 0) {
      /* Shift Right */
      code = code / 2;   //integer division 'div'
      x += 1; // x number of zeroes
    }
    /* y = (newcode - 1) / 2 */

    y =  (code - 1) / 2;

    return new Pair<>(x, y);
  }

  public static int encodeSinglePair(int x, int y) {
    return encodeDoublePair(x, y) - 1;
  }

  public static Pair<Integer, Integer> decodeSinglePair(int code) {
    return decodeDoublePair(code + 1);
  }

  public static int encodeList (List<Integer> list) {
    /* Base case */
    if (list.isEmpty()) return 0;
    int head = list.remove(0);
    return encodeDoublePair(head, encodeList(list));
  }

  public static List<Integer> decodeList(int code) {
    List<Integer> list = new ArrayList<>();
    while (code != 1) {
      Pair<Integer, Integer> pair = decodeDoublePair(code);
      list.add(pair.first());
      code = pair.second();
    }
    return list;
  }


  public static void main(String[] args) {
    InstrcutionBody B0 = InstrcutionBody.minusBody(1, 3, 5);
    InstrcutionBody B1 = InstrcutionBody.minusBody(1, 4, 2);
    InstrcutionBody B2 = InstrcutionBody.haltBody();
    InstrcutionBody B3 = InstrcutionBody.minusBody(1, 1, 2);
    InstrcutionBody B4 = InstrcutionBody.plusBody(0, 0);
    InstrcutionBody B5 = InstrcutionBody.haltBody();

    List<InstrcutionBody> program = List.of(B0, B1, B2, B3, B4, B5);
    List<Integer> encodings = new ArrayList<>();

    program.forEach(System.out::println);
    System.out.println();

    program.forEach(
        b -> {
          System.out.println(b.viewPair());
        });

    program.forEach(
        b -> {
          System.out.println(b.encode());
          encodings.add(b.encode());
        });

    System.out.println(decodeList(encodeList(encodings)));
//    System.out.println();
//    System.out.println(decodeInstrcution(1400));
//    System.out.println();
//    System.out.println(decodeList(786432));
//    System.out.println();
//    System.out.println(decodeSinglePair(0));
//
    double s = pow(2, 94) * 16395;
    System.out.println(s + ": " + decodeList((int) s) );

    System.out.println(encodeList(new ArrayList<Integer>(List.of(94, 0, 1, 10))));
    System.out.println(decodeInstrcution(94));
    System.out.println(decodeInstrcution(0));
    System.out.println(decodeInstrcution(1));
    System.out.println(decodeInstrcution(10));
    System.out.println(decodeInstrcution(94).viewPair());
    System.out.println(decodeInstrcution(0).viewPair());
    System.out.println(decodeInstrcution(1).viewPair());
    System.out.println(decodeInstrcution(10).viewPair());
  }







}

enum BodyType {
  PLUS,
  MINUS,
  HALT
}
