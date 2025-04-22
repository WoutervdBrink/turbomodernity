package com.woutervdb.turbomodernity.cli;

import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;
import com.woutervdb.turbomodernity.versioning.ranges.VersionRange;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ModernitySignatureLister implements ParseTreeListener {
    private final Parser parser;
    private final ParseTreeProperty<ModernitySignature> signatures;
    private int depth = 0;

    public ModernitySignatureLister(Parser parser, ParseTreeProperty<ModernitySignature> signatures) {
        this.parser = parser;
        this.signatures = signatures;
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        int ruleIndex = ctx.getRuleIndex();
        String ruleName = parser.getRuleNames()[ruleIndex];
        int alternativeNumber = ctx.getAltNumber();

        ModernitySignature sig = signatures.get(ctx);

        System.out.printf(
                "%s%s(rule %d alt %d): %s\n",
                "  ".repeat(depth),
                ruleName,
                ruleIndex, alternativeNumber,
                sig.toString()
        );

        depth++;
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        depth--;
    }
}
