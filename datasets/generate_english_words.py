file_in = open('english.50MB','r', encoding='windows-1252')
file_out = open('english_clean.50MB','a+')

for line in file_in.readlines():
	txt = ''.join(e for e in line if (e.isalnum() or e == " "))
	final_txt = txt.lower()
	file_out.write(final_txt)

file = open('english_clean.50MB','r')
file2 = open('english_words','w')
a = file.readline()
b = a.split()
c = []
for e in b:
	if len(e)!=0:
		c.append(e)

for e in c:
	file2.write(e +"\n")