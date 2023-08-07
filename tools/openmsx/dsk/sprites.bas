10200 screen 5,2
10210 for i=0 to 0:sp$=""
	10220 for j=0 to 31
		10230 read a$
		10240 sp$=sp$+chr$(val(a$))
	10250 next J
	10260 sprite$(i)=sp$
10270 next i
10280 for i=0 to 0:sp$=""
	10290 for j=0 to 15
		10300 read a$
		10310 sp$=sp$+chr$(val(a$))
	10320 next J
	10330 color sprite$(i)=sp$
10340 next I
10350 rem sprites data definitions
10360 rem data definition sprite 0, name: Sprite_name1
10360 data 255,255,255,255,255,255,255,255
10370 data 255,255,255,255,255,255,255,255
10380 data 255,255,255,255,255,255,255,255
10390 data 255,255,255,255,255,255,255,255
10400 rem data colors definitions sprite 0, name: Sprite_name1
10400 data 2,2,2,2,8,8,10,10,10,10,8,8,2,2,2,2
10410 put sprite 0,(20*0,100),,0
10420 goto 10420
