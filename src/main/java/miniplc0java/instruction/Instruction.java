package miniplc0java.instruction;

import java.util.Objects;

public class Instruction {
    private Operation opt;
    Integer x;
    String opt_code;

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
            case add_i:
            case sub_i:
            case mul_i:
            case div_i:
            case add_f:
            case sub_f:
            case mul_f:
            case div_f:
            case xor:
            case not:
            case cmp_i:
            case cmp_u:
            case cmp_f:
            case neg_i:
            case neg_f:
            case set_lt:
            case set_gt:
            case ret:
            case store_64:
            case load_64:
            case print_c:
            case print_f:
            case print_i:
            case print_ln:
            case print_s:
            case scan_c:
            case scan_i:
            case scan_f:
                return String.format("%s", this.opt);
            case push:
            case loca:
            case arga:
            case globa:

            case stackalloc:
            case br:
            case br_false:
            case br_true:
            case call:

                return String.format("%s %016x", this.opt, (long)x);
            default:
                return "ILL";
        }
    }
}
