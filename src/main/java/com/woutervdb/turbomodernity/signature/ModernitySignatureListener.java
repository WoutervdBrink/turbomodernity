package com.woutervdb.turbomodernity.signature;

import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.versioning.ranges.VersionRangeDeciderCollection;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Parse tree listener which uses a specified {@link Language} to determine the modernity signature of every node in the
 * parse tree.
 */
public class ModernitySignatureListener implements ParseTreeListener {
    private ParseTreeProperty<ModernitySignature> ranges;
    private final Language language;
    private final VersionRangeDeciderCollection collection;

    /**
     * Construct a new parse tree listener.
     *
     * @param language The language to use.
     */
    public ModernitySignatureListener(Language language) {
        this.language = language;
        this.collection = language.getDeciderRepository();

        this.reset();
    }

    /**
     * Reset the version ranges recorded by this listener.
     *
     * <p>This method is automatically called upon constructing the listener, and should only be called when re-using
     * existing listeners for different parse trees.</p>
     */
    public void reset() {
        this.ranges = new ParseTreeProperty<>();
    }

    /**
     * Get the version ranges recorded by this listener.
     *
     * @return A {@link ParseTreeProperty} instance with {@link ModernitySignature} instances for all nodes in the parse
     * tree. It is guaranteed that every value in the property object is not {@code null}.
     */
    public ParseTreeProperty<ModernitySignature> getSignatures() {
        return ranges;
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {
        // Do nothing.
    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {
        // Do nothing.
    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
        // Do nothing.
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        ranges.put(ctx, collection.decide(ctx).toModernitySignature(language));
    }
}
