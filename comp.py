#!/usr/bin/python
# coding=UTF8

import usb

busses = usb.busses()
i = 0
k=0
g=None
for bus in busses :
    print i
    j=0
    for dev in bus.devices :
        k+=1
        print "devices "+str(j)
        print dev.idVendor
        print dev.idProduct
        if dev.idVendor == 0x0694 :
            g=dev
            print "Lego encontrado: "
            print "\tBus = ", i
       
            #while True:
            #    c=sys.stdin.read(1)
            #    h
            #exit()
        j+=1
    i += 1
if not g:
    print "Lego não encontrado."
else:
    print g
    #h=g.open()
    config = g.configurations[0]
    iface = config.interfaces[0][0]
    print iface
    blk_out, blk_in = iface.endpoints
    handle = g.open()
    handle.setConfiguration(1)
    handle.claimInterface(0)
    #handle.reset()

    handle.bulkWrite(blk_out.address, "a")

print str(k)+" devices encontrados."
