package ch.ffhs.pm.fac.parser;

import static ch.ffhs.pm.fac.parser.Terminals.*;
import java_cup.runtime.Symbol;

%%

%public
%apiprivate
%class Scanner
%cupsym Terminals
%cup
%unicode
%line
%column

%{

    private Symbol sym(int type)
    {
         return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symVal(int type)
    {
         return new Symbol(type, yyline, yycolumn, yytext());
    }
   
%}

OPT_SPACE = [ \t]+
BREAK     = [\n\r]+
COMMENT   = "//".*
%%

"if"    { return sym(IF); }
"else"  { return sym(ELSE); }
"func"  { return sym(FUNC); }
"while" { return sym(WHILE); }

"true" { return sym(TRUE); }
"false" { return sym(FALSE); }

":"     { return sym(TERMINATOR); }
","     { return sym(SEPARATOR); }

// "+"     { return sym(PLUS); }
// "-"     { return sym(MINUS); }
// "*"     { return sym(TIMES); }
// "/"     { return sym(DIV); }
// "%"     { return sym(MOD); }
// "**"    { return sym(POW); }
// "("	   { return sym(LPAR); }
// ")"	   { return sym(RPAR); }

"=="    { return sym(EQUAL); }
"!="    { return sym(NOT_EQUAL); }
"<"     { return sym(LESS_THAN); }
">"     { return sym(GREATER); }
"<="    { return sym(LESS_EQUAL); }
">="    { return sym(GREATER_EQUAL); }

"="     { return sym(ASSIGN); }
"+="    { return sym(ASSIGN_PLUS); }
"-="    { return sym(ASSIGN_MINUS); }
"*="    { return sym(ASSIGN_MUL); }
"/="    { return sym(ASSIGN_DIVIDE); }

\+?-?[0-9]+                             { return symVal(NUMBER); }
\+?-?[0-9]+\.                           { return symVal(NUMBER); }
\+?-?[0-9]+\.[0-9]+                     { return symVal(NUMBER); }
\"[a-zA-z0-9 ]*\"                       { return symVal(STRING); }
([:jletter:]|_)([:jletterdigit:]|_)*	{ return symVal(IDENTIFIER); }

{BREAK} { return sym(SEP); }
{OPT_SPACE}	{ }
{COMMENT}   { }	

.		{ throw new RuntimeException("Illegal Symbol '" + yytext() + '\''
             + " in line " + yyline + ", column" + yycolumn); } 






