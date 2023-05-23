n = 100

f = open("/Users/joelsmith/VSCode/CITS2200Project/testDataN.txt", 'w')

for x in range(n):
    string = "/wiki/test"
    string += str(x)
    string += "\n"
    f.write(string)
    f.write(string)

temp = "/wiki/test"
temp += str(n)
f.write(temp)