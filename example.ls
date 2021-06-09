# This is an example script for Linescript.
# It should show you the possible statements and features.

printb("Data Types")

print("Numbers")
my_number = 12
my_number = 12.
my_number = 12.2345
print(my_number)

print("Strings")
my_string = "Linescript"
my_string = "This is a longer string with 🤩 inside!!"
print(my_string)

print("String Interpolation")
price = 12.50
my_string = "The price of the product is: ${price}"
print(my_string)

print("Boolean")
my_true = True
my_false = False
print(my_false)

print("")
printb("Arithmetic Operations")

print("Addition")
print(1 + 1)
print("Subtraction")
print(2 - 2)
print("Multiplication")
print(3 * 3)
print("Division")
print(4 / 4)
print("Power")
print(5 ^ 5)
print("Mod")
print(6 % 6)
print("Point before Line")
print(1 + 1 * 2)
print(2 * 1 + 1)
print("Power before Point")
print(2 ^ 2 * 3)
print(3 * 2 ^ 2)

print("")
printb("Assign Statements")
print("Assign")
my_number = 10
print(my_number)
my_string = "Linescript"
print(my_string)
my_boolean = True
print(my_boolean)
print("Addition Assign")
my_number += 10
print(my_number)
print("Subtraction Assign")
my_number -= 10
print(my_number)
print("Multiplication Assign")
my_number *= 10
print(my_number)
print("Division Assign")
my_number /= 10
print(my_number)

print("")
printb("Conditional Statements")

print("Equal")
print(10 == 10)
print("Linescript" == "Linescript")
print("Not Equal")
print(10 != 10)
print("Linescript" != "Linescript")
print("Less Than")
print(5 < 10)
print("abc" < "bcd")
print("Greater")
print(5 > 10)
print("abc" > "bcd")
print("Less Equal")
print(10 <= 10)
print("abc" <= "abc")
print("Greater Equal")
print(10 >= 10)
print("abc" >= "abc")

print("Variables to compare")
small_number = 10
big_number = 100
is_bigger = small_number < big_number
print(is_bigger)

print("")
printb("If-Else Statements")

print("If Statement")
print(if 1 < 2: "Yes")
print("If-Else Statement")
print(if 2 < 2: "Yes" else: "No")
print("If-Else with return Value")
res = if 1 == 1: "Yes" else: "No"
print(res)

print("If-Else Statement combined with Assign Statement")
if 1 == 2: res = "Yes" else: res = "No"
print(res)

print("")
printb("Functions")

print("Sum Function")
sum = lambda a,b: a + b
print(sum(1, 2))

print("Recursive Fibonacci Number Function")
print("Calculates the first 20 numbers of the Fibonacci sequence")
fibo = lambda n: if n <= 1: n else: fibo(n - 1) + fibo(n - 2)
i = 0
end = 20
while i <= end: print("${i}th Fibonacci number", fibo(i)), i += 1