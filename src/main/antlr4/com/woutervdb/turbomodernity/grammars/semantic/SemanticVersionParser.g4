grammar SemanticVersionParser;

semanticVersion
    : versionCore EOF #semanticVersionJustVersion
    | versionCore HYPHEN preRelease=dottedIdentifier EOF #semanticVersionWithPreRelease
    | versionCore PLUS build=dottedIdentifier EOF #semanticVersionWithBuild
    | versionCore HYPHEN preRelease=dottedIdentifier PLUS build=dottedIdentifier EOF #semanticVersionWithPreReleaseAndBuild
    ;

versionCore
    : major=numericIdentifier DOT minor=numericIdentifier DOT patch=numericIdentifier
    ;

dottedIdentifier
    : identifier (DOT identifier)*
    ;

identifier
    : alphanumericIdentifier
    | numericIdentifier
    ;

alphanumericIdentifier
    : nonDigit
    | nonDigit identifierCharacters
    | identifierCharacters nonDigit
    | identifierCharacters nonDigit identifierCharacters
    ;

numericIdentifier
    : ZERO
    | POSITIVE_DIGIT
    | POSITIVE_DIGIT digit+
    ;

identifierCharacters
    : identifierCharacter+
    ;

identifierCharacter
    : digit
    | nonDigit
    ;

nonDigit
    : LETTER
    | HYPHEN
    ;

digit
    : ZERO
    | POSITIVE_DIGIT
    ;

DOT: '.';
PLUS: '+';
HYPHEN: '-';
ZERO : '0';

POSITIVE_DIGIT: [1-9];
LETTER: [a-zA-Z];