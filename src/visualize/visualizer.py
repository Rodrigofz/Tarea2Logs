import numpy as np 
import matplotlib.pyplot as plt 

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

english_count_locate = np.genfromtxt("../out/english_count_locate_exp.csv", delimiter=",")[:,:-1]
print(english_count_locate)
plt.plot(english_count_locate[1:,1], english_count_locate[1:,2], label="Count")
plt.plot(english_count_locate[1:,1], english_count_locate[1:,3], label="Locate")
plt.title("Tiempo promedio v/s N (English Dataset)")
plt.xlabel("Tamaño del input")
plt.ylabel("Tiempo promedio")
plt.legend()
plt.show()

dna_topkq = np.genfromtxt("../out/dna_topkq_exp.csv", delimiter=",")[:,:-1]

print(dna_topkq)
k_arr = [3,5,10]
q_arr = [4,8,16,32]

k3q4 = []
k3q8 = []
k3q16 = []
k3q32 = []
k5q4 = []
k5q8 = []
k5q16 = []
k5q32 = []
k10q4 = []
k10q8 = []
k10q16 = []
k10q32 = []

i=0
while i<72:
	if(i%12==0):
		k3q4.append(dna_topkq[i])
	elif(i%12==1):
		k3q8.append(dna_topkq[i])
	elif(i%12==2):
		k3q16.append(dna_topkq[i])
	elif(i%12==3):
		k3q32.append(dna_topkq[i])
	elif(i%12==4):
		k5q4.append(dna_topkq[i])
	elif(i%12==5):
		k5q8.append(dna_topkq[i])
	elif(i%12==6):
		k5q16.append(dna_topkq[i])
	elif(i%12==7):
		k5q32.append(dna_topkq[i])
	elif(i%12==8):
		k10q4.append(dna_topkq[i])
	elif(i%12==9):
		k10q8.append(dna_topkq[i])
	elif(i%12==10):
		k10q16.append(dna_topkq[i])
	elif(i%12==11):
		k10q32.append(dna_topkq[i])
	i+=1

k3q4 = np.array(k3q4)
k3q8 = np.array(k3q8)
k3q16 = np.array(k3q16)
k3q32 = np.array(k3q32)
k5q4 = np.array(k5q4)
k5q8 = np.array(k5q8)
k5q16 = np.array(k5q16)
k5q32 = np.array(k5q32)
k10q4 = np.array(k10q4)
k10q8 = np.array(k10q8)
k10q16 = np.array(k10q16)
k10q32 = np.array(k10q32)


plt.title("Tiempo Topkq v/s N (Dna Dataset)")
plt.xlabel("Tamaño del input")
plt.ylabel("Tiempo promedio")

plt.plot(k3q4[:,1], k3q4[:,4], label="topqk k=3 q=4")
plt.plot(k3q8[:,1], k3q8[:,4], label="topqk k=3 q=8")
plt.plot(k3q16[:,1], k3q16[:,4], label="topqk k=3 q=16")
plt.plot(k3q32[:,1], k3q32[:,4], label="topqk k=3 q=32")
plt.plot(k5q4[:,1], k5q4[:,4], label="topqk k=5 q=4")
plt.plot(k5q8[:,1], k5q8[:,4], label="topqk k=5 q=8")
plt.plot(k5q16[:,1], k5q16[:,4], label="topqk k=5 q=16")
plt.plot(k5q32[:,1], k5q32[:,4], label="topqk k=5 q=32")
plt.plot(k10q4[:,1], k10q4[:,4], label="topqk k=10 q=4")
plt.plot(k10q8[:,1], k10q8[:,4], label="topqk k=10 q=8")
plt.plot(k10q16[:,1], k10q16[:,4], label="topqk k=10 q=16")
plt.plot(k10q32[:,1], k10q32[:,4], label="topqk k=10 q=32")

plt.legend()
plt.show()

english_topkq = np.genfromtxt("../out/english_topkq_exp.csv", delimiter=",")[:,:-1]

print(english_topkq)
k_arr = [3,5,10]
q_arr = [4,5,6,7]

k3q4 = []
k3q5 = []
k3q6 = []
k3q7 = []
k5q4 = []
k5q5 = []
k5q6 = []
k5q7 = []
k10q4 = []
k10q5 = []
k10q6 = []
k10q7 = []

i=0
while i<72:
	if(i%12==0):
		k3q4.append(english_topkq[i])
	elif(i%12==1):
		k3q5.append(english_topkq[i])
	elif(i%12==2):
		k3q6.append(english_topkq[i])
	elif(i%12==3):
		k3q7.append(english_topkq[i])
	elif(i%12==4):
		k5q4.append(english_topkq[i])
	elif(i%12==5):
		k5q5.append(english_topkq[i])
	elif(i%12==6):
		k5q6.append(english_topkq[i])
	elif(i%12==7):
		k5q7.append(english_topkq[i])
	elif(i%12==8):
		k10q4.append(english_topkq[i])
	elif(i%12==9):
		k10q5.append(english_topkq[i])
	elif(i%12==10):
		k10q6.append(english_topkq[i])
	elif(i%12==11):
		k10q7.append(english_topkq[i])
	i+=1

k3q4 = np.array(k3q4)
k3q5 = np.array(k3q5)
k3q6 = np.array(k3q6)
k3q7 = np.array(k3q7)
k5q4 = np.array(k5q4)
k5q5 = np.array(k5q5)
k5q6 = np.array(k5q6)
k5q7 = np.array(k5q7)
k10q4 = np.array(k10q4)
k10q5 = np.array(k10q5)
k10q6 = np.array(k10q6)
k10q7 = np.array(k10q7)


plt.title("Tiempo Topkq v/s N (English Dataset)")
plt.xlabel("Tamaño del input")
plt.ylabel("Tiempo promedio")

plt.plot(k3q4[:,1], k3q4[:,4], label="topqk k=3 q=4")
plt.plot(k3q5[:,1], k3q5[:,4], label="topqk k=3 q=5")
plt.plot(k3q6[:,1], k3q6[:,4], label="topqk k=3 q=6")
plt.plot(k3q7[:,1], k3q7[:,4], label="topqk k=3 q=7")
plt.plot(k5q4[:,1], k5q4[:,4], label="topqk k=5 q=4")
plt.plot(k5q5[:,1], k5q5[:,4], label="topqk k=5 q=5")
plt.plot(k5q6[:,1], k5q6[:,4], label="topqk k=5 q=6")
plt.plot(k5q7[:,1], k5q7[:,4], label="topqk k=5 q=7")
plt.plot(k10q4[:,1], k10q4[:,4], label="topqk k=10 q=4")
plt.plot(k10q5[:,1], k10q5[:,4], label="topqk k=10 q=5")
plt.plot(k10q6[:,1], k10q6[:,4], label="topqk k=10 q=6")
plt.plot(k10q7[:,1], k10q7[:,4], label="topqk k=10 q=7")

plt.legend()
plt.show()
