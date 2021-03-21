import os
from os.path import join, getsize
for root, dirs, files in os.walk('docs/Algorithm'):
    print(root,"\n")
    files = sorted(files, key=lambda x: os.path.getctime((os.path.join(root, x))),reverse=True)
    for x in files:
        print("- "+x+": ",root.replace('\\','/').replace('docs/','')+'/'+x)
    print("\n")
    if 'pics' in dirs:
            dirs.remove('pics')  