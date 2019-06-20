file_in = open('dna.50MB','r')
file_out = open('dna_clean.50MB','a+')

for line in file_in.readlines():
	txt = ''.join(e for e in line if e.isalnum())
	final_txt = txt.lower()
	file_out.write(final_txt)

file_in = open('english.50MB','r', encoding='windows-1252')
file_out = open('english_clean.50MB','a+')

for line in file_in.readlines():
	txt = ''.join(e for e in line if e.isalnum())
	final_txt = txt.lower()
	file_out.write(final_txt)