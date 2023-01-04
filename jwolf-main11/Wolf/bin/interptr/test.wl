#class A{
#	int = 10
#	def a(){
#		print("I am in a", int)
#	}
#}
#A.a()
# 
#A.a()

#A.a()
n=[1,2,4]
#n[1] = "Hi"
def func(a,n=1){
	print("In Function : ",a)
	return n
}
	
print(func(4,a=7),n)
print(func(2,3))