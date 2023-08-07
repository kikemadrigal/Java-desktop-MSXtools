#region: Spain (Espagna)
#format: <UNICODE>, <ROW><COL>, <MODIFIERS>
# <UNICODE>: hexadecimal unicode number or DEADKEY<N>
# <ROW>: row in keyboard matrix (hexadecimal: 0-B)
# <COL>: column in keyboard matrix (0-7)
# <MODIFIERS>: space separated list of modifiers:
#              SHIFT CTRL GRAPH CODE
#
# Example characters in comments are encoded in UTF-8
#

MSX-Video-Characterset: MSXVID.TXT

DEADKEY1, 25,
00000, 02, CTRL SHIFT  # ^@
00001, 26, CTRL        # ^A
00002, 27, CTRL        # ^B
00003, 30, CTRL        # ^C
00004, 31, CTRL        # ^D
00005, 32, CTRL        # ^E
00006, 33, CTRL        # ^F
00007, 34, CTRL        # ^G
00008, 75,             # Backspace
00009, 73,             # Tab
0000a, 37, CTRL        # ^J
0000b, 81,             # Home (is Home a unicode character?)
0000c, 41, CTRL        # ^L
0000d, 77,             # Enter/CR
0000e, 43, CTRL        # ^N
0000f, 44, CTRL        # ^O
00010, 45, CTRL        # ^P
00011, 46, CTRL        # ^Q
00012, 82,             # Insert (is Insert a unicode character?)
00013, 50, CTRL        # ^S
00014, 51, CTRL        # ^T
00015, 52, CTRL        # ^U
00016, 53, CTRL        # ^V
00017, 54, CTRL        # ^W
00018, 76,             # Select (is Select a unicode character?)
00019, 56, CTRL        # ^Y
0001a, 57, CTRL        # ^Z
0001b, 72,             # Escape(SDL maps ESC and ^[ to this code)
0001c, 87,             # Right (SDL maps ^\ to this code)
0001d, 84,             # Left  (SDL maps ^] to this code)
0001e, 85,             # Up    (SDL maps ^^ to this code)
0001f, 86,             # Down  (SDL maps ^/ to this code)
00020, 80,             # Space
00021, 01, SHIFT       # ! (EXCLAMATION MARK)
00022, 20, SHIFT       # " (QUOTATION MARK)
00023, 03, SHIFT       # # (NUMBER SIGN)
00024, 04, SHIFT       # $ (DOLLAR SIGN)
00025, 05, SHIFT       # % (PERCENT SIGN)
00026, 07, SHIFT       # & (AMPERSAND)
00027, 20,             # ' (APOSTROPHE)
00028, 11, SHIFT       # ( (LEFT PARENTHESIS)
00029, 00, SHIFT       # ) (RIGHT PARENTHESIS)
0002a, 10, SHIFT       # * (ASTERISK)
0002b, 13, SHIFT       # + (PLUS SIGN)
0002c, 22,             # , (COMMA)
0002d, 12,             # - (HYPHEN-MINUS)
0002e, 23,             # . (FULL STOP)
0002f, 24,             # / (SOLIDUS)
00030, 00,             # 0 (DIGIT ZERO)
00031, 01,             # 1 (DIGIT ONE)
00032, 02,             # 2 (DIGIT TWO)
00033, 03,             # 3 (DIGIT THREE)
00034, 04,             # 4 (DIGIT FOUR)
00035, 05,             # 5 (DIGIT FIVE)
00036, 06,             # 6 (DIGIT SIX)
00037, 07,             # 7 (DIGIT SEVEN)
00038, 10,             # 8 (DIGIT EIGHT)
00039, 11,             # 9 (DIGIT NINE)
0003a, 21, SHIFT       # : (COLON)
0003b, 21,             # ; (SEMICOLON)
0003c, 22, SHIFT       # < (LESS-THAN SIGN)
0003d, 13,             # = (EQUALS SIGN)
0003e, 23, SHIFT       # > (GREATER-THAN SIGN)
0003f, 24, SHIFT       # ? (QUESTION MARK)
00040, 02, SHIFT       # @ (COMMERCIAL AT)
00041, 26, SHIFT       # A (LATIN CAPITAL LETTER A)
00042, 27, SHIFT       # B (LATIN CAPITAL LETTER B)
00043, 30, SHIFT       # C (LATIN CAPITAL LETTER C)
00044, 31, SHIFT       # D (LATIN CAPITAL LETTER D)
00045, 32, SHIFT       # E (LATIN CAPITAL LETTER E)
00046, 33, SHIFT       # F (LATIN CAPITAL LETTER F)
00047, 34, SHIFT       # G (LATIN CAPITAL LETTER G)
00048, 35, SHIFT       # H (LATIN CAPITAL LETTER H)
00049, 36, SHIFT       # I (LATIN CAPITAL LETTER I)
0004a, 37, SHIFT       # J (LATIN CAPITAL LETTER J)
0004b, 40, SHIFT       # K (LATIN CAPITAL LETTER K)
0004c, 41, SHIFT       # L (LATIN CAPITAL LETTER L)
0004d, 42, SHIFT       # M (LATIN CAPITAL LETTER M)
0004e, 43, SHIFT       # N (LATIN CAPITAL LETTER N)
0004f, 44, SHIFT       # O (LATIN CAPITAL LETTER O)
00050, 45, SHIFT       # P (LATIN CAPITAL LETTER P)
00051, 46, SHIFT       # Q (LATIN CAPITAL LETTER Q)
00052, 47, SHIFT       # R (LATIN CAPITAL LETTER R)
00053, 50, SHIFT       # S (LATIN CAPITAL LETTER S)
00054, 51, SHIFT       # T (LATIN CAPITAL LETTER T)
00055, 52, SHIFT       # U (LATIN CAPITAL LETTER U)
00056, 53, SHIFT       # V (LATIN CAPITAL LETTER V)
00057, 54, SHIFT       # W (LATIN CAPITAL LETTER W)
00058, 55, SHIFT       # X (LATIN CAPITAL LETTER X)
00059, 56, SHIFT       # Y (LATIN CAPITAL LETTER Y)
0005a, 57, SHIFT       # Z (LATIN CAPITAL LETTER Z)
0005b, 15,             # [ (LEFT SQUARE BRACKET)
0005c, 14,             # \ (REVERSE SOLIDUS)
0005d, 16,             # ] (RIGHT SQUARE BRACKET)
0005e, 06, SHIFT       # ^ (CIRCUMFLEX ACCENT)
0005f, 12, SHIFT       # _ (LOW LINE)
00060, 43, CODE        # ` (GRAVE ACCENT)
00061, 26,             # a (LATIN SMALL LETTER A)
00062, 27,             # b (LATIN SMALL LETTER B)
00063, 30,             # c (LATIN SMALL LETTER C)
00064, 31,             # d (LATIN SMALL LETTER D)
00065, 32,             # e (LATIN SMALL LETTER E)
00066, 33,             # f (LATIN SMALL LETTER F)
00067, 34,             # g (LATIN SMALL LETTER G)
00068, 35,             # h (LATIN SMALL LETTER H)
00069, 36,             # i (LATIN SMALL LETTER I)
0006a, 37,             # j (LATIN SMALL LETTER J)
0006b, 40,             # k (LATIN SMALL LETTER K)
0006c, 41,             # l (LATIN SMALL LETTER L)
0006d, 42,             # m (LATIN SMALL LETTER M)
0006e, 43,             # n (LATIN SMALL LETTER N)
0006f, 44,             # o (LATIN SMALL LETTER O)
00070, 45,             # p (LATIN SMALL LETTER P)
00071, 46,             # q (LATIN SMALL LETTER Q)
00072, 47,             # r (LATIN SMALL LETTER R)
00073, 50,             # s (LATIN SMALL LETTER S)
00074, 51,             # t (LATIN SMALL LETTER T)
00075, 52,             # u (LATIN SMALL LETTER U)
00076, 53,             # v (LATIN SMALL LETTER V)
00077, 54,             # w (LATIN SMALL LETTER W)
00078, 55,             # x (LATIN SMALL LETTER X)
00079, 56,             # y (LATIN SMALL LETTER Y)
0007a, 57,             # z (LATIN SMALL LETTER Z)
0007b, 15, SHIFT       # { (LEFT CURLY BRACKET)
0007c, 14, SHIFT       # | (VERTICAL LINE)
0007d, 16, SHIFT       # } (RIGHT CURLY BRACKET)
0007e, 43, SHIFT CODE  # ~ (TILDE)
0007f, 83,             # Delete
000a0, 80,             # No-break space (&nbsp;)
000a1, 01, SHIFT CODE  # ¡ (INVERTED EXCLAMATION MARK)
000a2, 04, CODE        # ¢ (CENT SIGN)
000a3, 04, SHIFT CODE  # £ (POUND SIGN)
000a5, 05, SHIFT CODE  # ¥ (YEN SIGN)
000a7, 03, CODE        # § (SECTION SIGN)
000aa, 23, CODE        # ª (FEMININE ORDINAL INDICATOR)
000ab, 22, SHIFT GRAPH # « (LEFT-POINTING DOUBLE ANGLE QUOTATION MARK)
000ac, 56, SHIFT GRAPH # ¬ (NOT SIGN)
000b0, 57, SHIFT GRAPH # ° (DEGREE SIGN)
000b1, 13, GRAPH       # ± (PLUS-MINUS SIGN)
000b2, 02, SHIFT GRAPH # ² (SUPERSCRIPT TWO)
000b5, 42, CODE        # µ (MICRO SIGN)
000b6, 03, SHIFT CODE  # ¶ (PILCROW SIGN)
000b7, 30, SHIFT GRAPH # · (MIDDLE DOT)
000ba, 24, CODE        # º (MASCULINE ORDINAL INDICATOR)
000bb, 23, SHIFT GRAPH # » (RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK)
000bc, 01, GRAPH       # ¼ (VULGAR FRACTION ONE QUARTER)
000bd, 02, GRAPH       # ½ (VULGAR FRACTION ONE HALF)
000be, 03, GRAPH       # ¾ (VULGAR FRACTION THREE QUARTERS)
000bf, 24, SHIFT CODE  # ¿ (INVERTED QUESTION MARK)
000c3, 35, SHIFT CODE  # Ã (LATIN CAPITAL LETTER A WITH TILDE)
000c4, 26, SHIFT CODE  # Ä (LATIN CAPITAL LETTER A WITH DIAERESIS)
000c5, 22, SHIFT CODE  # Å (LATIN CAPITAL LETTER A WITH RING ABOVE)
000c6, 37, SHIFT CODE  # Æ (LATIN CAPITAL LETTER AE)
000c7, 11, SHIFT CODE  # Ç (LATIN CAPITAL LETTER C WITH CEDILLA)
000c9, 52, SHIFT CODE  # É (LATIN CAPITAL LETTER E WITH ACUTE)
000d1, 17, SHIFT       # Ñ (LATIN CAPITAL LETTER N WITH TILDE)
000d5, 41, SHIFT CODE  # Õ (LATIN CAPITAL LETTER O WITH TILDE)
000d6, 33, SHIFT CODE  # Ö (LATIN CAPITAL LETTER O WITH DIAERESIS)
000dc, 34, SHIFT CODE  # Ü (LATIN CAPITAL LETTER U WITH DIAERESIS)
000df, 07, CODE        # ß (LATIN SMALL LETTER SHARP S)
000e0, 57, CODE        # à (LATIN SMALL LETTER A WITH GRAVE)
000e1, 56, CODE        # á (LATIN SMALL LETTER A WITH ACUTE)
000e2, 46, CODE        # â (LATIN SMALL LETTER A WITH CIRCUMFLEX)
000e3, 35, CODE        # ã (LATIN SMALL LETTER A WITH TILDE)
000e4, 26, CODE        # ä (LATIN SMALL LETTER A WITH DIAERESIS)
000e5, 22, CODE        # å (LATIN SMALL LETTER A WITH RING ABOVE)
000e6, 37, CODE        # æ (LATIN SMALL LETTER AE)
000e7, 11, CODE        # ç (LATIN SMALL LETTER C WITH CEDILLA)
000e8, 55, CODE        # è (LATIN SMALL LETTER E WITH GRAVE)
000e9, 52, CODE        # é (LATIN SMALL LETTER E WITH ACUTE)
000ea, 54, CODE        # ê (LATIN SMALL LETTER E WITH CIRCUMFLEX)
000eb, 50, CODE        # ë (LATIN SMALL LETTER E WITH DIAERESIS)
000ec, 30, CODE        # ì (LATIN SMALL LETTER I WITH GRAVE)
000ed, 36, CODE        # í (LATIN SMALL LETTER I WITH ACUTE)
000ee, 32, CODE        # î (LATIN SMALL LETTER I WITH CIRCUMFLEX)
000ef, 31, CODE        # ï (LATIN SMALL LETTER I WITH DIAERESIS)
000f1, 17,             # ñ (LATIN SMALL LETTER N WITH TILDE)
000f2, 53, CODE        # ò (LATIN SMALL LETTER O WITH GRAVE)
000f3, 44, CODE        # ó (LATIN SMALL LETTER O WITH ACUTE)
000f4, 47, CODE        # ô (LATIN SMALL LETTER O WITH CIRCUMFLEX)
000f5, 41, CODE        # õ (LATIN SMALL LETTER O WITH TILDE)
000f6, 33, CODE        # ö (LATIN SMALL LETTER O WITH DIAERESIS)
000f7, 24, SHIFT GRAPH # ÷ (DIVISION SIGN)
000f9, 27, CODE        # ù (LATIN SMALL LETTER U WITH GRAVE)
000fa, 45, CODE        # ú (LATIN SMALL LETTER U WITH ACUTE)
000fb, 51, CODE        # û (LATIN SMALL LETTER U WITH CIRCUMFLEX)
000fc, 34, CODE        # ü (LATIN SMALL LETTER U WITH DIAERESIS)
000ff, 05, CODE        # ÿ (LATIN SMALL LETTER Y WITH DIAERESIS)
00128, 40, SHIFT CODE  # Ĩ (LATIN CAPITAL LETTER I WITH TILDE)
00129, 40, CODE        # ĩ (LATIN SMALL LETTER I WITH TILDE)
00132, 20, SHIFT CODE  # Ĳ (LATIN CAPITAL LIGATURE IJ)
00133, 20, CODE        # ĳ (LATIN SMALL LIGATURE IJ)
00168, 17, SHIFT CODE  # Ũ (LATIN CAPITAL LETTER U WITH TILDE)
00169, 17, CODE        # ũ (LATIN SMALL LETTER U WITH TILDE)
00192, 01, CODE        # ƒ (LATIN SMALL LETTER F WITH HOOK)
00393, 10, SHIFT CODE  # Γ (GREEK CAPITAL LETTER GAMMA)
00394, 00, SHIFT CODE  # Δ (GREEK CAPITAL LETTER DELTA)
00398, 13, CODE        # Θ (GREEK CAPITAL LETTER THETA)
003a3, 21, SHIFT CODE  # Σ (GREEK CAPITAL LETTER SIGMA)
003a6, 15, SHIFT CODE  # Φ (GREEK CAPITAL LETTER PHI)
003a9, 16, SHIFT CODE  # Ω (GREEK CAPITAL LETTER OMEGA)
003b1, 06, CODE        # α (GREEK SMALL LETTER ALPHA)
003b4, 00, CODE        # δ (GREEK SMALL LETTER DELTA)
003c0, 45, SHIFT CODE  # π (GREEK SMALL LETTER PI)
003c3, 21, CODE        # σ (GREEK SMALL LETTER SIGMA)
003c4, 10, CODE        # τ (GREEK SMALL LETTER TAU)
003c9, 16, CODE        # ω (GREEK SMALL LETTER OMEGA)
02021, 02, CODE        # ‡ (DOUBLE DAGGER)
02022, 11, GRAPH       # • (BULLET)
02030, 05, GRAPH       # ‰ (PER MILLE SIGN)
0207f, 03, SHIFT GRAPH # ⁿ (SUPERSCRIPT LATIN SMALL LETTER N)
020a7, 02, SHIFT CODE  # ₧ (PESETA SIGN)
02205, 15, CODE        # ∅ (EMPTY SET)
02208, 12, CODE        # ∈ (ELEMENT OF)
02219, 55, SHIFT GRAPH # ∙ (BULLET OPERATOR)
0221a, 07, GRAPH       # √ (SQUARE ROOT)
0221e, 10, GRAPH       # ∞ (INFINITY)
02229, 04, GRAPH       # ∩ (INTERSECTION)
0223d, 21, GRAPH       # ∽ (REVERSED TILDE)
02248, 21, SHIFT GRAPH # ≈ (ALMOST EQUAL TO)
02261, 13, SHIFT GRAPH # ≡ (IDENTICAL TO)
02264, 22, GRAPH       # ≤ (LESS-THAN OR EQUAL TO)
02265, 23, GRAPH       # ≥ (GREATER-THAN OR EQUAL TO)
02302, --,             # ⌂ (HOUSE)
02310, 47, SHIFT GRAPH # ⌐ (REVERSED NOT SIGN)
02320, 06, GRAPH       # ⌠ (TOP HALF INTEGRAL)
02321, 06, SHIFT GRAPH # ⌡ (BOTTOM HALF INTEGRAL)
02500, 12, GRAPH       # ─ (BOX DRAWINGS LIGHT HORIZONTAL)
02502, 14, SHIFT GRAPH # │ (BOX DRAWINGS LIGHT VERTICAL)
0250c, 47, GRAPH       # ┌ (BOX DRAWINGS LIGHT DOWN AND RIGHT)
02510, 56, GRAPH       # ┐ (BOX DRAWINGS LIGHT DOWN AND LEFT)
02514, 53, GRAPH       # └ (BOX DRAWINGS LIGHT UP AND RIGHT)
02518, 43, GRAPH       # ┘ (BOX DRAWINGS LIGHT UP AND LEFT)
0251c, 33, GRAPH       # ├ (BOX DRAWINGS LIGHT VERTICAL AND RIGHT)
02524, 35, GRAPH       # ┤ (BOX DRAWINGS LIGHT VERTICAL AND LEFT)
0252c, 51, GRAPH       # ┬ (BOX DRAWINGS LIGHT DOWN AND HORIZONTAL)
02534, 27, GRAPH       # ┴ (BOX DRAWINGS LIGHT UP AND HORIZONTAL)
0253c, 34, GRAPH       # ┼ (BOX DRAWINGS LIGHT VERTICAL AND HORIZONTAL)
02571, 24, GRAPH       # ╱ (BOX DRAWINGS LIGHT DIAGONAL UPPER RIGHT TO LOWER LEFT)
02572, 14, GRAPH       # ╲ (BOX DRAWINGS LIGHT DIAGONAL UPPER LEFT TO LOWER RIGHT)
02573, 55, GRAPH       # ╳ (BOX DRAWINGS LIGHT DIAGONAL CROSS)
02580, 36, SHIFT GRAPH # ▀ (UPPER HALF BLOCK)
02582, 52, GRAPH       # ▂ (LOWER ONE QUARTER BLOCK)
02584, 36, GRAPH       # ▄ (LOWER HALF BLOCK)
02586, 44, GRAPH       # ▆ (LOWER THREE QUARTERS BLOCK)
02588, 45, GRAPH       # █ (FULL BLOCK)
0258a, 41, GRAPH       # ▊ (LEFT THREE QUARTERS BLOCK)
0258c, 40, GRAPH       # ▌ (LEFT HALF BLOCK)
0258e, 37, GRAPH       # ▎ (LEFT ONE QUARTER BLOCK)
02590, 40, SHIFT GRAPH # ▐ (RIGHT HALF BLOCK)
02596, 35, SHIFT GRAPH # ▖ (QUADRANT LOWER LEFT)
02597, 33, SHIFT GRAPH # ▗ (QUADRANT LOWER RIGHT)
02598, 43, SHIFT GRAPH # ▘ (QUADRANT UPPER LEFT)
0259a, 31, SHIFT GRAPH # ▚ (QUADRANT UPPER LEFT AND LOWER RIGHT)
0259d, 53, SHIFT GRAPH # ▝ (QUADRANT UPPER RIGHT)
0259e, 31, GRAPH       # ▞ (QUADRANT UPPER RIGHT AND LOWER LEFT)
025a0, 26, SHIFT GRAPH # ■ (BLACK SQUARE)
025ac, 26, GRAPH       # ▬ (BLACK RECTANGLE)
025c7, 30, GRAPH       # ◇ (WHITE DIAMOND)
025cb, 00, GRAPH       # ○ (WHITE CIRCLE)
025d8, 11, SHIFT GRAPH # ◘ (INVERSE BULLET)
025d9, 00, SHIFT GRAPH # ◙ (INVERSE WHITE CIRCLE)
0263a, 15, GRAPH       # ☺ (WHITE SMILING FACE)
0263b, 15, SHIFT GRAPH # ☻ (BLACK SMILING FACE)
0263c, 57, GRAPH       # ☼ (WHITE SUN WITH RAYS)
02640, 42, SHIFT GRAPH # ♀ (FEMALE SIGN)
02642, 42, GRAPH       # ♂ (MALE SIGN)
02660, 17, GRAPH       # ♠ (BLACK SPADE SUIT)
02663, 20, GRAPH       # ♣ (BLACK CLUB SUIT)
02665, 20, SHIFT GRAPH # ♥ (BLACK HEART SUIT)
02666, 17, SHIFT GRAPH # ♦ (BLACK DIAMOND SUIT)
0266a, 16, GRAPH       # ♪ (EIGHTH NOTE)
0266b, 16, SHIFT GRAPH # ♫ (BEAMED EIGHTH NOTES)
027ca, 34, SHIFT GRAPH # ⟊ (VERTICAL BAR WITH HORIZONTAL STROKE)
1fb6c, 54, GRAPH       # 🭬 (LEFT TRIANGULAR ONE QUARTER BLOCK)
1fb6d, 32, GRAPH       # 🭭 (UPPER TRIANGULAR ONE QUARTER BLOCK)
1fb6e, 54, SHIFT GRAPH # 🭮 (RIGHT TRIANGULAR ONE QUARTER BLOCK)
1fb6f, 32, SHIFT GRAPH # 🭯 (LOWER TRIANGULAR ONE QUARTER BLOCK)
1fb82, 44, SHIFT GRAPH # 🮂 (UPPER ONE QUARTER BLOCK)
1fb85, 52, SHIFT GRAPH # 🮅 (UPPER THREE QUARTERS BLOCK)
1fb87, 41, SHIFT GRAPH # 🮇 (RIGHT ONE QUARTER BLOCK)
1fb8a, 37, SHIFT GRAPH # 🮊 (RIGHT THREE QUARTERS BLOCK)
1fb96, 45, SHIFT GRAPH # 🮖 (INVERSE CHECKER BOARD FILL)
1fb98, 46, GRAPH       # 🮘 (UPPER LEFT TO LOWER RIGHT FILL)
1fb99, 46, SHIFT GRAPH # 🮙 (UPPER RIGHT TO LOWER LEFT FILL)
1fb9a, 50, SHIFT GRAPH # 🮚 (UPPER AND LOWER TRIANGULAR HALF BLOCK)
1fb9b, 50, GRAPH       # 🮛 (LEFT AND RIGHT TRIANGULAR HALF BLOCK)
1fbaf, 12, SHIFT GRAPH # 🮯 (BOX DRAWINGS LIGHT HORIZONTAL WITH VERTICAL STROKE)
