class A{
	int = 10
	def a(){
		print("I am in A.a ", int)
	}
}

ab = 10
def fun(n){
	def f(){
		return 0
	}
	print(ab)
	ab = [1,2,3]
	print("ab in function:",ab)
	while n != 0{
		if(n%2 == 0){
			print(n)
		}
		n = n-1
	}
	return a
}

b= {1:2,3:4,"Hi":"Bye"}

a = [1,2,3,"Hi", fun]
x = fun
if __name__ == "__main__" {
	A.a()
	print(b)
	print(fun(20))
	print(b["Hi"])
	print(a.count(1))
	print(ab)
}