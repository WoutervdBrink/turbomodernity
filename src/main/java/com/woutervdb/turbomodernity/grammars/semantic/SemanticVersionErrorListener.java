package com.woutervdb.turbomodernity.grammars.semantic;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

public class SemanticVersionErrorListener extends BaseErrorListener {
    private final List<String> errors = new ArrayList<>();

    public void clearErrors() {
        this.errors.clear();
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String getErrorMessage() {
        return String.format("%d error%c trying to parse version string: %s", errors.size(), errors.size() == 1 ? null : 's', String.join(", ", errors));
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errors.add(String.format("%d:%d: %s", line, charPositionInLine, msg));
    }
}
