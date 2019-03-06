from Birthday import Birthday

hashtable = []
for entry in range(12):
    hashtable.append([])

text = open('bdaylist.txt', 'r')
bdays = text.readlines()

for i in bdays:
    i = i.replace('\n', '')
    i = i.split('/')
    tBday = Birthday(int(i[0]),int(i[1]),int(i[2]))
    hashval = hash(tBday)
    hashtable[hashval].append(tBday)
    print('Hash location', hashval, 'has', len(hashtable[hashval]), 'elements in it')
