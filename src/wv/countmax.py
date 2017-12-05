import re


if __name__ == '__main__':
    lan = 'es'
    maxwc = 0

    with open('data/train.{}'.format(lan)) as f:
        lines = f.readlines()
    for line in lines:
        wc = len(re.sub('[,.?!()/:;\'\"\[\]-]', ' ', line.lower()).split())
        if wc > maxwc:
            maxwc = wc
            if wc == 108 or wc == 115:
                print(line)
    print(maxwc)
    
    with open('data/test.{}'.format(lan)) as f:
        lines = f.readlines()
    for line in lines:
        wc = len(re.sub('[,.?!()/:;\'\"\[\]-]', ' ', line.lower()).split())
        if wc > maxwc:
            maxwc = wc
    print(maxwc)
