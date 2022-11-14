10200 screen 5,2
10210 for i=0 to 2:sp$=""
	10220 for j=0 to 31
		10230 read a$
		10240 sp$=sp$+chr$(val(a$))
	10250 next J
	10260 sprite$(i)=sp$
10270 next i
10280 for i=0 to 2:sp$=""
	10290 for j=0 to 15
		10300 read a$
		10310 sp$=sp$+chr$(val(a$))
	10320 next J
	10330 color sprite$(i)=sp$
10340 next I
10350 rem sprites data definitions
10360 rem data definition sprite 0, name: Sprite-0
10370 data 0,0,0,1,2,15,31,61
10380 data 62,62,127,127,63,0,0,0
10390 data 0,0,0,192,184,248,188,188
10400 data 236,12,252,240,224,0,0,0
10410 rem data definition sprite 1, name: Sprite-1
10420 data 0,0,0,0,0,3,7,15
10430 data 15,15,15,0,0,0,0,0
10440 data 0,0,0,112,174,254,239,239
10450 data 255,3,255,4,0,0,0,0
10460 rem data definition sprite 2, name: Sprite-2
10470 data 0,0,0,0,0,0,3,7
10480 data 15,15,15,14,1,1,0,0
10490 data 0,0,0,0,112,174,254,239
10500 data 239,255,3,251,252,240,0,0
10510 rem data colors definitions sprite 0, name: Sprite-0
10510 data 0,0,0,10,10,10,10,10,10,10,10,10,10,10,10,10
10520 rem data colors definitions sprite 1, name: Sprite-1
10520 data 0,0,0,6,6,6,6,6,6,6,6,6,6,6,6,6
10530 rem data colors definitions sprite 2, name: Sprite-2
10530 data 0,0,0,0,10,10,10,10,10,10,10,10,10,10,10,10
10540 rem data atrubutes sprite 0, name: Sprite-0
10550 put sprite 0,(1,0),,0
10560 rem data atrubutes sprite 1, name: Sprite-1
10570 put sprite 1,(34,0),,1
10580 rem data atrubutes sprite 2, name: Sprite-2
10590 put sprite 2,(67,0),,2
10600 goto 10600
