#!/usr/bin/python
# coding=UTF8

import usb

busses = usb.busses()
i = 0
k=0
for bus in busses :
    print i
    j=0
    for dev in bus.devices :
        k+=1
        print "devices "+str(j)
        print dev.idVendor
        print dev.idProduct
        if dev.idVendor == '0694' :
            print "Lego encontrado: "
            print "\tBus = ", i
            exit
        j+=1
    i += 1
print "Lego não encontrado."
print str(k)+" devices encontrados."
