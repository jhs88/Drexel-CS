from spellchecker import spellchecker

def get_file():
    try:
        filename = input('Enter the name of the file to read:\n')
        file = open(filename, 'r')
        return file.readlines()
    except:
        print('Could not open file.')
        return get_file()

if __name__ == "__main__":
    counter = 0
    passed = 0
    failed = 0
    print('Welcome to Text File Spellchecker.')
    doc = get_file()
    SP = spellchecker("english_words.txt")
    for lines in doc:
        counter+=1
        words = lines.split()
        for word in words:
            w = SP.check(word)
            if w:
                passed += 1
            else:
                print('Possible spelling error on line ' + str(counter) + ": " + word)
                failed += 1
    print(str(passed) + " words passed spell checker.")
    print(str(failed) + " words failed spell checker.")
    percentage = (passed / (passed + failed)) * 100
    print('%.2f'% percentage + '% of the words passed.') 
