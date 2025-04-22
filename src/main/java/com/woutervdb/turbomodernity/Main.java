package com.woutervdb.turbomodernity;

import com.woutervdb.turbomodernity.grammars.antlr.ANTLRv4Lexer;
import com.woutervdb.turbomodernity.grammars.antlr.ANTLRv4Parser;
import com.woutervdb.turbomodernity.grammars.antlr.ModelCreatingListener;
import com.woutervdb.turbomodernity.grammars.php.PhpLexer;
import com.woutervdb.turbomodernity.grammars.php.PhpParser;
import com.woutervdb.turbomodernity.languages.*;
import com.woutervdb.turbomodernity.languages.php.Php;
import com.woutervdb.turbomodernity.model.Alternative;
import com.woutervdb.turbomodernity.model.Grammar;
import com.woutervdb.turbomodernity.model.Rule;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.signature.ModernitySignatureListener;
import com.woutervdb.turbomodernity.cli.ModernitySignatureLister;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        new Main().run(args);
    }

    private Grammar analyzeGrammar(String grammarFile) throws IOException {
        CharStream charStream = CharStreams.fromFileName(grammarFile);

        ANTLRv4Lexer lexer = new ANTLRv4Lexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ANTLRv4Parser parser = new ANTLRv4Parser(tokens);
        ParseTree tree = parser.grammarSpec();

        return ModelCreatingListener.createGrammar(tree);
    }

    private RuleAndAlternativeCounter count(ParseTree tree) {
        RuleAndAlternativeCounter counter = new RuleAndAlternativeCounter();
        ParseTreeWalker.DEFAULT.walk(counter, tree);
        return counter;
    }

    private ParseTreeProperty<ModernitySignature> getConstraints(Language language, ParseTree tree) {
        ModernitySignatureListener listener = new ModernitySignatureListener(language);
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        return listener.getSignatures();
    }

    private void printModernitySignatures(Parser parser, ParseTree tree, ParseTreeProperty<ModernitySignature> signatures) {
        ParseTreeWalker.DEFAULT.walk(new ModernitySignatureLister(parser, signatures), tree);
    }

    private void printRuleAndAlternativeCounter(Grammar g, RuleAndAlternativeCounter counter) {
        StringBuilder s = new StringBuilder();
        for (int ruleIndex = 0; ruleIndex < g.getSize(); ruleIndex++) {
            s.setLength(0);
            Rule r = g.getRule(ruleIndex);

            for (int alternativeIndex = 0; alternativeIndex < r.getAlternatives().size(); alternativeIndex++) {
                Alternative a = r.getAlternatives().get(alternativeIndex);
                Integer hits = counter.getCount(ruleIndex, alternativeIndex);

                if (hits > 0)
                    s.append(String.format("  Alt %s (idx %d, r %f): %d hit(s)\n", a.rule, alternativeIndex, counter.getRatio(ruleIndex, alternativeIndex), hits));
            }

            if (!s.isEmpty()) {
                System.out.printf("Rule %s (idx %d, r %f): %d hit(s)\n", r.getName(), ruleIndex, counter.getRatio(ruleIndex), counter.getCount(ruleIndex));
                System.out.println(s.toString());
                System.out.println();
            }
        }
    }

    private void run(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: [grammar] [testfile]");
            System.exit(1);
        }

        String grammarFile = args[0];
        String testFile = args[1];

        try {
            CharStream charStream = CharStreams.fromFileName(testFile);
            PhpLexer lexer = new PhpLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            PhpParser parser = new PhpParser(tokens);
            ParseTree tree = parser.htmlDocument();

            Grammar g = analyzeGrammar(grammarFile);
            ParseTreeProperty<ModernitySignature> signatures = getConstraints(Php.INSTANCE, tree);
            RuleAndAlternativeCounter counter = count(tree);
            printModernitySignatures(parser, tree, signatures);
            printRuleAndAlternativeCounter(g, counter);
        } catch (Exception e) {
            System.err.println("Something went wrong: " + e.getMessage());
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}