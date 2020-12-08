package miniplc0java.instruction;

import java.util.Objects;

public class Instruction {
    private Operation opt;
    Integer x;
    String code;

    public Instruction(Operation opt) {
        this.opt = opt;
        this.x = 0;
    }

    public Instruction(Operation opt, Integer x) {
        this.opt = opt;
        this.x = x;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Instruction that = (Instruction) o;
        return opt == that.opt && Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opt, x);
    }

    public Operation getOpt() {
        return opt;
    }

    public void setOpt(Operation opt) {
        this.opt = opt;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    @Override
    public String toString() {

        switch (this.opt) {
            case add_i: return "20";
            case sub_i:return "21";
            case mul_i:return "22";
            case div_i:return "23";
            case add_f:return "24";
            case sub_f:return "25";
            case mul_f:return "26";
            case div_f:return "27";
            case xor:return "2d";
            case not:return "2e";
            case cmp_i:return "30";
            case cmp_u:return "31";
            case cmp_f:return "32";
            case neg_i:return "34";
            case neg_f:return "35";
            case set_lt:return "39";
            case set_gt:return "3a";
            case ret:return "49";
            case store_64:return "17";
            case load_64:return "13";
            case print_c:return "55";
            case print_f:return "56";
            case print_i:return "54";
            case print_ln:return "58";
            case print_s:return "57";
            case scan_c:return "51";
            case scan_i:return "50";
            case scan_f:return "52";
//                return String.format("%s", this.opt);
            case push:return String.format("%s %016x", "01", (long)x);
            case loca:return String.format("%s %016x", "0a", (long)x);
            case arga:return String.format("%s %016x", "0b", (long)x);
            case globa:return String.format("%s %016x", "0c", (long)x);
            case stackalloc:return String.format("%s %016x", "1a", (long)x);
            case br:return String.format("%s %016x", "41", (long)x);
            case br_false:return String.format("%s %016x", "42", (long)x);
            case br_true:return String.format("%s %016x", "43", (long)x);
            case call:return String.format("%s %016x", "48", (long)x);
//                return String.format("%s %016x", this.opt, (long)x);
            default:
                return "ILL";
        }
//        switch (this.opt) {
//            case add_i:
//            case sub_i:
//            case mul_i:
//            case div_i:
//            case add_f:
//            case sub_f:
//            case mul_f:
//            case div_f:
//            case xor:
//            case not:
//            case cmp_i:
//            case cmp_u:
//            case cmp_f:
//            case neg_i:
//            case neg_f:
//            case set_lt:
//            case set_gt:
//            case ret:
//            case store_64:
//            case load_64:
//            case print_c:
//            case print_f:
//            case print_i:
//            case print_ln:
//            case print_s:
//            case scan_c:
//            case scan_i:
//            case scan_f:
//                return String.format("%s", this.opt);
//            case push:
//            case loca:
//            case arga:
//            case globa:
//
//            case stackalloc:
//            case br:
//            case br_false:
//            case br_true:
//            case call:
//                return String.format("%s %016x", this.opt, (long)x);
//            default:
//                return "ILL";
//        }
    }
}
