package com.woutervdb.turbomodernity.versioning.semantic;

import com.woutervdb.turbomodernity.grammars.semantic.SemanticVersionErrorListener;
import com.woutervdb.turbomodernity.grammars.semantic.SemanticVersionParserLexer;
import com.woutervdb.turbomodernity.grammars.semantic.SemanticVersionParserParser;
import com.woutervdb.turbomodernity.grammars.semantic.SemanticVersionVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public final class SemanticVersionParser {
    private static final SemanticVersionErrorListener errorListener = new SemanticVersionErrorListener();

    private static TokenStream lex(String versionString) {
        CharStream charStream = CharStreams.fromString(versionString);

        Lexer lexer = new SemanticVersionParserLexer(charStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        return new CommonTokenStream(lexer);
    }

    private static ParseTree parse(TokenStream tokens) {
        SemanticVersionParserParser parser = new SemanticVersionParserParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        return parser.semanticVersion();
    }

    public static SemanticVersion fromVersionString(String versionString) {
        errorListener.clearErrors();

        ParseTree tree = parse(lex(versionString));

        if (errorListener.hasErrors()) {
            throw new IllegalArgumentException(errorListener.getErrorMessage());
        }

        SemanticVersionVisitor visitor = new SemanticVersionVisitor();
        visitor.visit(tree);

        return visitor.getSemanticVersion();
    }
}
