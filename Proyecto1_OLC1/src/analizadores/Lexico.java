package analizadores;
import java_cup.runtime.Symbol; 
import java.util.ArrayList;


public class Lexico implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

    public ArrayList<Excepcion> Errores; 
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public Lexico (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Lexico (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexico () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
 
    yyline = 1; 
    yychar = 1; 
    Errores = new ArrayList();
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NOT_ACCEPT,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NOT_ACCEPT,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NOT_ACCEPT,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NOT_ACCEPT,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NOT_ACCEPT,
		/* 62 */ YY_NOT_ACCEPT,
		/* 63 */ YY_NOT_ACCEPT,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NOT_ACCEPT,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NOT_ACCEPT,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NOT_ACCEPT,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NOT_ACCEPT,
		/* 75 */ YY_NOT_ACCEPT,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NOT_ACCEPT,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"26:9,15,24,26:2,29,26:18,30,28,7,31:2,13,31,6,31:2,19,20,10,14,17,25,34:10," +
"9,8,27,31,16,21,31,33:2,1,23,33:4,22,4,33:3,3,2,33:11,31,5,31:2,33,31,33:2," +
"1,23,33:4,22,4,33:3,3,2,33:11,11,18,12,32,26:65409,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,83,
"0,1,2,3,4,5:5,6,5:5,1:2,7,1:2,8,9,10,1:2,11,8,1,12,13,5,14,1,15,16,17,18,5," +
"19,20,21,17,10,16,8,22,23,24,7,25,26,27,28,29,30,31,1,32,33,34,4,17,35,29,2" +
"2,18,11,24,7,36,10,15,37,21,28,38,32,39,40,41,40,42")[0];

	private int yy_nxt[][] = unpackFromString(43,35,
"1,2,82:3,3,31,4,5,6,7,8,9,40,46,10,31,11,12,13,14,15,30,82,16,52,57,60,31,-" +
"1,32,31,57,82,39,-1:36,45,80,45:2,-1:5,29,-1:4,38,-1:6,45:2,-1:6,38,-1,44,4" +
"5:2,-1:3,17,-1:2,17:2,-1:2,29,-1:4,38,-1:14,38,-1,44,-1:3,50:4,55,50,18,50:" +
"2,59,50:4,61,50:8,-1,50,62,50:2,62,63,50,64,50:2,-1:10,29,-1:4,38,-1:14,38," +
"-1,44,-1:17,10,-1:14,10,-1:11,25,-1:7,69,-1:14,69,-1:5,45:4,-1:17,45:2,-1:9" +
",45:2,-1,22:23,-1,22:4,-1,22:5,-1:10,29,-1:4,71,-1:14,71,-1:22,26,-1:11,26," +
"-1:3,26,-1,23:14,29,23:8,-1,23,-1,23:2,-1,34,23,-1,23:2,-1,45:4,-1:5,29,-1:" +
"4,38,-1:6,45,21,-1:6,38,-1,44,45:2,-1:10,29,-1:4,32,-1:14,32,-1,44,-1:3,23:" +
"9,34,23:4,72,23:8,-1,23,-1,23:2,-1,34,23,-1,23:2,-1,24:14,44,24:8,-1,24,-1," +
"24:2,-1,35,24,-1,24:2,-1,62:4,68,62,33,62:16,-1,62:10,-1,66:27,70,66:6,-1,4" +
"5:4,-1:5,29,-1:4,38,-1,67,-1:4,45:2,-1:6,38,-1,44,45,51,-1:10,29,-1:2,19,-1" +
",38,-1:14,38,-1,44,-1:3,62:4,68,62,33,62:2,73,62:4,74,62:8,-1,62:5,74,62:4," +
"-1:10,29,-1:4,65,20,-1:13,65,-1,44,-1:5,62,-1:2,62:2,-1:2,29,-1:4,71,-1:14," +
"71,-1:7,62,-1:2,62:2,-1:28,62:4,68,62,25,62:7,50,62:8,-1,62:5,50,62:4,-1,45" +
":4,-1:12,67,-1:4,45:2,-1:9,45,51,-1:10,29,-1:4,38,-1:9,22,-1:4,38,-1,44,-1:" +
"3,41:4,47,41,43,41:2,58,41:4,75,41:8,-1,41,62,41:2,62,53,41,62,41:2,-1,42:4" +
",48,42,24,42:7,64,42:8,-1,42,62,42:2,62,54,42,62,42:2,-1:3,62,-1:2,62,36,-1" +
":7,69,-1:14,69,-1:5,45:3,27,-1:17,45:2,-1:9,45:2,-1,41:4,47,41,23,41:2,58,4" +
"1:4,77,41:8,-1,41,62,41:2,62,58,41,62,41:2,-1,41:4,47,41,43,41:7,59,41:8,-1" +
",41,62,41:2,62,53,41,62,41:2,-1:10,29,-1:4,38,-1:12,66,-1,38,-1,44,-1:3,50:" +
"4,55,50,49,50:2,59,50:4,63,50:8,-1,50,62,50:2,62,63,50,64,50:2,-1,81:15,28," +
"81:11,76,81:6,-1,41:4,47,41,23,41:7,73,41:8,-1,41,62,41:2,62,58,41,62,41:2," +
"-1,66:15,37,66:10,78,70,66:6,-1:27,79,-1:8,66:26,78,70,66:6,-1,45:2,56,45,-" +
"1:17,45:2,-1:9,45:2,-1,45:4,-1:5,29,-1:4,38,-1:6,45:2,-1:6,38,-1,44,45:2");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {
				return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return new Symbol(sym.LEXEMA,yyline,yychar, yytext());}
					case -3:
						break;
					case 3:
						{
         Errores.add(new Excepcion("Léxico", "Caracter no válido detectado: " + yytext(), yyline + "", yychar + ""));
    }
					case -4:
						break;
					case 4:
						{return new Symbol(sym.COMILLAS,yyline,yychar, yytext());}
					case -5:
						break;
					case 5:
						{return new Symbol(sym.PTCOMA,yyline,yychar, yytext());}
					case -6:
						break;
					case 6:
						{return new Symbol(sym.DOSPT,yyline,yychar, yytext());}
					case -7:
						break;
					case 7:
						{return new Symbol(sym.COMA,yyline,yychar, yytext());}
					case -8:
						break;
					case 8:
						{return new Symbol(sym.LLAVEIZQ,yyline,yychar, yytext());}
					case -9:
						break;
					case 9:
						{return new Symbol(sym.LLAVEDER,yyline,yychar, yytext());}
					case -10:
						break;
					case 10:
						{}
					case -11:
						break;
					case 11:
						{return new Symbol(sym.CONCATENACION,yyline,yychar, yytext());}
					case -12:
						break;
					case 12:
						{return new Symbol(sym.DISYUNCION,yyline,yychar, yytext());}
					case -13:
						break;
					case 13:
						{return new Symbol(sym.KLEEN,yyline,yychar, yytext());}
					case -14:
						break;
					case 14:
						{return new Symbol(sym.POSITIVA,yyline,yychar, yytext());}
					case -15:
						break;
					case 15:
						{return new Symbol(sym.UNAOCERO,yyline,yychar, yytext());}
					case -16:
						break;
					case 16:
						{yychar=1;}
					case -17:
						break;
					case 17:
						{return new Symbol(sym.ESPECIAL,yyline,yychar, yytext());}
					case -18:
						break;
					case 18:
						{return new Symbol(sym.LEXEMARECONOCER,yyline,yychar, yytext());}
					case -19:
						break;
					case 19:
						{return new Symbol(sym.PORCENTAJE,yyline,yychar, yytext());}
					case -20:
						break;
					case 20:
						{return new Symbol(sym.ASIGNACION,yyline,yychar, yytext());}
					case -21:
						break;
					case 21:
						{return new Symbol(sym.ID,yyline,yychar, yytext());}
					case -22:
						break;
					case 22:
						{/*Ignore*/}
					case -23:
						break;
					case 23:
						{return new Symbol(sym.DEFINICIONCONJUNTOCOMAS,yyline,yychar, yytext());}
					case -24:
						break;
					case 24:
						{return new Symbol(sym.DEFINICIONCONJUNTO,yyline,yychar, yytext());}
					case -25:
						break;
					case 25:
						{return new Symbol(sym.CARACTER,yyline,yychar, yytext());}
					case -26:
						break;
					case 26:
						{return new Symbol(sym.DECIMAL,yyline,yychar, yytext());}
					case -27:
						break;
					case 27:
						{return new Symbol(sym.CONJ,yyline,yychar, yytext());}
					case -28:
						break;
					case 28:
						{/*Ignore*/}
					case -29:
						break;
					case 30:
						{return new Symbol(sym.LEXEMA,yyline,yychar, yytext());}
					case -30:
						break;
					case 31:
						{
         Errores.add(new Excepcion("Léxico", "Caracter no válido detectado: " + yytext(), yyline + "", yychar + ""));
    }
					case -31:
						break;
					case 32:
						{}
					case -32:
						break;
					case 33:
						{return new Symbol(sym.LEXEMARECONOCER,yyline,yychar, yytext());}
					case -33:
						break;
					case 34:
						{return new Symbol(sym.DEFINICIONCONJUNTOCOMAS,yyline,yychar, yytext());}
					case -34:
						break;
					case 35:
						{return new Symbol(sym.DEFINICIONCONJUNTO,yyline,yychar, yytext());}
					case -35:
						break;
					case 36:
						{return new Symbol(sym.CARACTER,yyline,yychar, yytext());}
					case -36:
						break;
					case 37:
						{/*Ignore*/}
					case -37:
						break;
					case 39:
						{return new Symbol(sym.LEXEMA,yyline,yychar, yytext());}
					case -38:
						break;
					case 40:
						{
         Errores.add(new Excepcion("Léxico", "Caracter no válido detectado: " + yytext(), yyline + "", yychar + ""));
    }
					case -39:
						break;
					case 41:
						{return new Symbol(sym.DEFINICIONCONJUNTOCOMAS,yyline,yychar, yytext());}
					case -40:
						break;
					case 42:
						{return new Symbol(sym.DEFINICIONCONJUNTO,yyline,yychar, yytext());}
					case -41:
						break;
					case 43:
						{return new Symbol(sym.CARACTER,yyline,yychar, yytext());}
					case -42:
						break;
					case 45:
						{return new Symbol(sym.LEXEMA,yyline,yychar, yytext());}
					case -43:
						break;
					case 46:
						{
         Errores.add(new Excepcion("Léxico", "Caracter no válido detectado: " + yytext(), yyline + "", yychar + ""));
    }
					case -44:
						break;
					case 47:
						{return new Symbol(sym.DEFINICIONCONJUNTOCOMAS,yyline,yychar, yytext());}
					case -45:
						break;
					case 48:
						{return new Symbol(sym.DEFINICIONCONJUNTO,yyline,yychar, yytext());}
					case -46:
						break;
					case 49:
						{return new Symbol(sym.CARACTER,yyline,yychar, yytext());}
					case -47:
						break;
					case 51:
						{return new Symbol(sym.LEXEMA,yyline,yychar, yytext());}
					case -48:
						break;
					case 52:
						{
         Errores.add(new Excepcion("Léxico", "Caracter no válido detectado: " + yytext(), yyline + "", yychar + ""));
    }
					case -49:
						break;
					case 53:
						{return new Symbol(sym.DEFINICIONCONJUNTOCOMAS,yyline,yychar, yytext());}
					case -50:
						break;
					case 54:
						{return new Symbol(sym.DEFINICIONCONJUNTO,yyline,yychar, yytext());}
					case -51:
						break;
					case 56:
						{return new Symbol(sym.LEXEMA,yyline,yychar, yytext());}
					case -52:
						break;
					case 57:
						{
         Errores.add(new Excepcion("Léxico", "Caracter no válido detectado: " + yytext(), yyline + "", yychar + ""));
    }
					case -53:
						break;
					case 58:
						{return new Symbol(sym.DEFINICIONCONJUNTOCOMAS,yyline,yychar, yytext());}
					case -54:
						break;
					case 60:
						{
         Errores.add(new Excepcion("Léxico", "Caracter no válido detectado: " + yytext(), yyline + "", yychar + ""));
    }
					case -55:
						break;
					case 80:
						{return new Symbol(sym.LEXEMA,yyline,yychar, yytext());}
					case -56:
						break;
					case 82:
						{return new Symbol(sym.LEXEMA,yyline,yychar, yytext());}
					case -57:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
