package com.woutervdb.turbomodernity.grammars.semantic;

import com.woutervdb.turbomodernity.versioning.semantic.PreReleaseIdentifier;
import com.woutervdb.turbomodernity.versioning.semantic.SemanticVersion;

public class SemanticVersionVisitor extends SemanticVersionParserBaseVisitor<Void> {
    private int major;
    private int minor;
    private int patch;

    private PreReleaseIdentifier[] preReleaseIdentifiers;

    @Override
    public Void visitSemanticVersionJustVersion(SemanticVersionParserParser.SemanticVersionJustVersionContext ctx) {
        this.preReleaseIdentifiers = new PreReleaseIdentifier[0];
        return visit(ctx.versionCore());
    }

    @Override
    public Void visitSemanticVersionWithBuild(SemanticVersionParserParser.SemanticVersionWithBuildContext ctx) {
        this.preReleaseIdentifiers = new PreReleaseIdentifier[0];
        return visit(ctx.versionCore());
    }

    @Override
    public Void visitSemanticVersionWithPreRelease(SemanticVersionParserParser.SemanticVersionWithPreReleaseContext ctx) {
        setPreReleaseIdentifiers(ctx.preRelease);
        return visit(ctx.versionCore());
    }

    @Override
    public Void visitSemanticVersionWithPreReleaseAndBuild(SemanticVersionParserParser.SemanticVersionWithPreReleaseAndBuildContext ctx) {
        setPreReleaseIdentifiers(ctx.preRelease);
        return visit(ctx.versionCore());
    }

    @Override
    public Void visitVersionCore(SemanticVersionParserParser.VersionCoreContext ctx) {
        this.major = Integer.parseInt(ctx.major.getText());
        this.minor = Integer.parseInt(ctx.minor.getText());
        this.patch = Integer.parseInt(ctx.patch.getText());

        return null;
    }

    private void setPreReleaseIdentifiers(SemanticVersionParserParser.DottedIdentifierContext ctx) {
        this.preReleaseIdentifiers = new PreReleaseIdentifier[ctx.identifier().size()];

        for (int i = 0; i < ctx.identifier().size(); i++) {
            SemanticVersionParserParser.IdentifierContext idCtx = ctx.identifier(i);
            this.preReleaseIdentifiers[i] = new PreReleaseIdentifier(idCtx.getText(), idCtx.numericIdentifier() != null);
        }
    }

    public SemanticVersion getSemanticVersion() {
        return new SemanticVersion(major, minor, patch, preReleaseIdentifiers);
    }
}
