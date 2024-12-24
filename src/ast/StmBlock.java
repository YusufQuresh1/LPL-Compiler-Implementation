package ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StmBlock extends Stm {

    public final List<Stm> stms;

    public StmBlock(List<Stm> stms) {
        this.stms = Collections.unmodifiableList(stms);
    }
    public StmBlock(Stm ...stms) {
        this(Arrays.asList(stms));
    }

    @Override
    public void compile() {
        for (Stm stm: stms) {
            stm.compile();
        }
    }
}
