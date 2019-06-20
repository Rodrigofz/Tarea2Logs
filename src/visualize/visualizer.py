import numpy as np 
import matplotlib.pyplot as plt 

#buildMemory = np.loadtxt("../out/buildMemory_exp.csv")
buildTime = np.genfromtxt("../out/buildTime_exp.csv", delimiter=",")[:,:-1]
print(buildTime)
build_arr=[]
i=0
while(i<len(buildTime)):
	build_arr.append([buildTime[i,0],buildTime[i,1],(buildTime[i,2]+buildTime[i+1,2])/2])
	i+=2
build_arr = np.array(build_arr)
plt.plot(build_arr[1:,1], build_arr[1:,2])
plt.title("Tiempo promedio creación v/s N")
plt.xlabel("Tamaño del input")
plt.ylabel("Tiempo promedio")
plt.legend()
plt.show()

dna_count_locate = np.genfromtxt("../out/dna_count_locate_exp.csv", delimiter=",")[:,:-1]
print(dna_count_locate)
m_arr = [8,16,32,64]
for m in m_arr:
	count_label = "Count, m=" + str(m)
	locate_label = "Locate, m=" + str(m)
	plt.plot(dna_count_locate[1:,1][dna_count_locate[1:,4]==m], dna_count_locate[1:,2][dna_count_locate[1:,4]==m], label=count_label)
	plt.plot(dna_count_locate[1:,1][dna_count_locate[1:,4]==m], dna_count_locate[1:,3][dna_count_locate[1:,4]==m], label=locate_label)
	plt.title("Tiempo promedio v/s N (DNA Dataset)")
	plt.xlabel("Tamaño del input")
	plt.ylabel("Tiempo promedio")
	plt.legend()
plt.show()

#dna_topkq = np.loadtxt("../out/dna_topkq_exp.csv", delimiter=",")
english_count_locate = np.genfromtxt("../out/english_count_locate_exp.csv", delimiter=",")[:,:-1]
print(english_count_locate)
plt.plot(english_count_locate[1:,1], english_count_locate[1:,2], label="Count")
plt.plot(english_count_locate[1:,1], english_count_locate[1:,3], label="Locate")
plt.title("Tiempo promedio v/s N (English Dataset)")
plt.xlabel("Tamaño del input")
plt.ylabel("Tiempo promedio")
plt.legend()
plt.show()

#english_topkq = np.loadtxt("../out/english_topkq_exp.cs", delimiter=",")
  