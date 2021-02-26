import os
from os.path import join, getsize
for root, dirs, files in os.walk('docs/Algorithm'):
    print(root,"\n")
    for x in files:
        print("- "+x+": ",root.replace('\\','/').replace('docs/','')+'/'+x)
    print("\n")
    if 'pics' in dirs:
            dirs.remove('pics')  