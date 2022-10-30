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
		10310 sp$=sp$+chr$(val("&h"+a$))
	10320 next J
	10330 color sprite$(i)=sp$
10340 next I
10350 rem sprites data definitions
10360 rem data definition sprite 0, name: No_name0
10360 data 0,0,0,0,1,3,6,6
10370 data 11,8,14,1,0,0,0,0
10380 data 0,0,0,0,224,16,80,72
10390 data 200,8,24,240,0,0,0,0
10400 rem data definition sprite 1, name: No_name1
10400 data 0,1,1,99,31,15,15,31
10410 data 47,63,78,207,135,3,0,0
10420 data 0,0,4,228,236,252,140,206
10430 data 75,75,136,104,184,0,0,0
10440 rem data definition sprite 2, name: No_name2
10440 data 0,0,0,253,255,0,3,30
10450 data 119,77,71,103,39,16,31,30
10460 data 0,0,0,224,192,64,224,0
10470 data 248,156,240,0,0,224,64,0
10480 rem data colors definitions sprite 0, name: No_name0
10480 data 00,00,00,00,06,06,06,06,06,06,06,06,06,06,06,06
10490 rem data colors definitions sprite 1, name: No_name1
10490 data 00,03,03,03,03,03,03,03,03,03,03,03,03,03,03,03
10500 rem data colors definitions sprite 2, name: No_name2
10500 data 00,00,00,01,01,01,01,01,01,01,01,01,01,01,01,01
10510 put sprite 0,(20*0,100),,0
10520 put sprite 1,(20*1,100),,1
10530 put sprite 2,(20*2,100),,2
10540 goto 10540
