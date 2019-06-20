import numpy as np 
import matplotlib.pyplot as plt 

#buildMemory = np.loadtxt("../out/buildMemory_exp.csv")
buildTime = np.genfromtxt("../out/buildTime_exp.csv", delimiter=",")[:,:-1]
print(buildTime)

dna_count_locate = np.genfromtxt("../out/dna_count_locate_exp.csv", delimiter=",")[:,:-1]
print(dna_count_locate)
"""
m_8 = np.array([])
m_16 = []
m_32 = []
m_64 = []

for i in range(len(dna_count_locate)):
	if dna_count_locate[i,4] == 8:
		m_8.append(dna_count_locate[i])
	elif dna_count_locate[i,4] == 16:
		m_16.append(dna_count_locate[i])
	elif dna_count_locate[i,4] == 32:
		m_32.append(dna_count_locate[i])
	elif dna_count_locate[i,4] == 64:
		m_64.append(dna_count_locate[i])

print(m_8)
m = [m_8,m_16,m_32,m_64]
m_text = ["m=8","m=16","m=32","m=64"]

for i in range(len(m)):
	count_label = "Count" + m_text[i]
	locate_label = "Locate" + m_text[i]
	plt.plot(m[i,:,1], m[i,:,2], label=count_label)
	plt.plot(m[i,:,1], m[i,:,3], label=locate_label)
	plt.title("Tiempo promedio v/s N (DNA Dataset)")
	plt.xlabel("Tamaño del input")
	plt.ylabel("Tiempo promedio")
	plt.legend()

plt.show()
"""
#dna_topkq = np.loadtxt("../out/dna_topkq_exp.csv", delimiter=",")
english_count_locate = np.genfromtxt("../out/english_count_locate_exp.csv", delimiter=",")[:,:-1]
print(english_count_locate)
plt.plot(english_count_locate[:,1], english_count_locate[:,2], label="Count")
plt.plot(english_count_locate[:,1], english_count_locate[:,3], label="Locate")
plt.title("Tiempo promedio v/s N (English Dataset)")
plt.xlabel("Tamaño del input")
plt.ylabel("Tiempo promedio")
plt.legend()
plt.show()

#english_topkq = np.loadtxt("../out/english_topkq_exp.cs", delimiter=",")
  