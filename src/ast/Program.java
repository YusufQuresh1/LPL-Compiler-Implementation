package ast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program extends AST {

    public final List<Stm> body;

    public Program(List<Stm> body) {
        this.body = Collections.unmodifiableList(body);
    }

    public Program(Stm ...stms) {
        this(Arrays.asList(stms));
    }

    public void compile() {
        for(Stm stm: body) {
            stm.compile();
        }
        emit("halt");
        emit(".data");
        for (String global: globals) {
            emit(global + ": 0");
        }
    }
}
