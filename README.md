# InventreeCompanion
 Java Tool companion for [Inventree](https://github.com/inventree/InvenTree) stock management system, and scanning barcodes

Purpose : Use an hand scanner to decode [EAN-128/GS1-128](https://en.wikipedia.org/wiki/GS1-128) splitted in section.

It will add a temporization between 2 scan decoding for EAN-128 to concat 2 scanned barcode in 1.
A basic decoder (without any transformation) is implemented. 


### Barcode Scanning feature : 

#### EAN-128 :

  - decode every item in EAN-128/GS1-128 barcode, but only use some of them as input for stock item, and implements equivalence between some : 
  
        - EAN-NumberOfTradingUnit or EAN-NumberOfTheWaresInTheShippingUnit as EAN : principal identifyer, use to identify parts
        - ExpiryDate_JJMMDD or DueDate_JJMMDD as Expiricy date
        - Charge_Number or ProductModel as Batch number
        - AmountInParts as number of part : there is a parameter to let you choose to use it or not.
        
  - the start code of the barcode (~FNC1) has to be printed with "]C1", and the break caracter (non printable) should be parametered to print "#" caracter in your handscanner.

#### Inventree Internal Barcode :
it will be identified if the barcode encode for a json object.

  - Inventree Internal barcode is decoded : 
        - if it's a stocklocation BC (eg `{"stocklocation":id}`) it will set the current location.
        - if it's a part, stockitem or a supplier part, it will identify the related part

#### Basic decoder :
  - It will set the entire alphanumeric code as identifyer.



other barcodeDecoder could bee added (extends [BarcodeDecoder.java](https://github.com/Bbillyben/InventreeCompanion/blob/main/src/main/java/barcodeDecoder/BarcodeDecoder.java))

Barcode can belinked directly to part or to supplier item.


### Use :

#### login panel : 

    - server URL : the URL of the inventree server, it will force ssl (https) unless you thick "Force Insercure connection" parameter
    - User Name : the name of the user in Inventree
    - Password : the password of the user
    - Force Insercure connection : allow use of http instead of https connection (eg in my environnement for connection via VPN to VM that have not 443 port open ....)

The Companion will check connection and get the token associated with the user. It do not store the token for an another session.

#### parameter panel : 

This panel is dedicated to parameters over the app. Every parameters will be stored in the 'ini_java.ini" file located in a subfolder : "ext_file/"

##### scan parameters : 
    - Second to wait before scan ending : If the barcode decoder accept multiple scan (see isMultiple() method of barcodedecoder), this set the number of second to wait before processing the barcode
    - radio button : 
        - Force Distant Location : if a stock item is found with the barcode, the location of that stock item will be use instead of those defined in the scan panel
        - Force Local Location : keep the location defined in the scan panel for the scanned item. If a stock item with the same barcode is found in an another location, it will consider the scanned item as a new stock item.


##### App Parameters : 
    - Automatic save on exit : If ticked, the scan list will be save on app exit (in sub folder "ext_file/currentList.ser")


##### User Parameters : 
    - Save Password : to save the user password (in the ini file). The password will be hatched with Base 64 algorithm (not secure at all!!!)
    - Auto login : If password is saved, it will log automatically on app open
    - Play sound : to set wether the app play sounds or not.


#### Scan panel : 
this is the panl used for scanning barcode and manage them. It is divided in 3 zones : 

    - Scan Mode : 
        - to set the scan mode between : add (add a stock item), remove and transfert.
        - to set the current location. In transfert mode, you will set the destination (transfert location) here as well
        - Use barcode quantity checkbox : define if the quantity defined in the barcode will be used or not
    - Barcode Status : here is the list of the scanned barcode. the newest at the top. the darkest column are editable.
        it will display informations collected in the Inventree server if the stock item or part is identified
    - Scan Area : This is the textfield that has to have the focus to scan. You can handwrite the code if needed (hit enter to validate the barcode).

#### Send Panel :

This is the panel to send item to server, set new part or link barcode (EAN) to existing part. This panel is divided in 4 table : 

    - The two first are dedicated to stock item update (a stock item has been identified corrsponding to the barcode) and stock item creation (a part has been identified but no stock item).
    - the third one is dedicated to unknown items. 
        - You could here create a new part (with minimal information). 
        - link to an existing part.
        - You can choose wether you want to link barcode to part or Supplie Item. Be carefull, it will overwritten part barcode if previously defined. By default it's set to supplier item.
        - as a new part or a link is created, the correspondng item will be transfert to one of the upper table.
    - the last one is dedicated to error item. 

The "Send to server" button will send the update and stock item creation to the server. 
A pop up window will appear displaying the advancement and the status of requests for each item.


#### Stock Item Status : 

There is several status displayed in the "status" column in the different panels. 
Some few words about the information workflow. 

    - the barcode is scanned and checked against current list of items already scanned. 
    - If already scanned, add the quantity to the previous item
    - If not already scanned, added to the queue to be checked in the server 
    - it will check if the barcode/EAN is known. If not found in barcode, it will look into MPN (manufacturer part list), and SKU (suppliers part list)
    - If a part is identified, it will check if a corresponding stock item exist, checking against batch number, location (if Force Local location ticked), expiry date

start scanning status :

    - "pending" : in the queu to be checked
    - "on update" : currently in the process of being checked in the server

Item status : 

    - "item found" : stock item identified in the server, it will be updated once sended

    - "new item" : a correponding part has been identified, but no stock item correponding. A new stock item will be created once sended
    - "new part" : no correponding part has been identified, it will ask for a part creation in the "send panel"
    - "deleted" : the item has been deleted

Sending status : 

    - "on_sending" : the current item is being sended to the server
    - "sended" : the item has been sended to the server without error
    - "send_error" : an error has occured during the send process

Error status : 

    - "error no EAN" : No EAN defined once the barcode is decoded
    - "Err: no item identified" : if no stock item has been identified in the server and the scan mode is set to remove or transfert
    - "Err: Stock quantity insufficient" : if the quantity to remove or transfert is over than the quantity found in the stock
    - "Err: location identical" : if current location and transfert location are the same in transfert mode
    - "Err: No Location defined" : if no location has been defined (should'nt happen)


#### Upper Menu :
    - User :
        - log out : to log out current user. it will then switch to the log panel
    - Panels : to navigate between panels : 
        - Scan 
        - Send 
        - Parameters
    - Action 
        - Update Params : to update lists from the server (updated at each app launch) : Stock Location, Suppliers and Manufacturers, Part list
        - Check All Items : to relaunch a server check on all items
        - Remove Sended, Error and Deleted : to remove items in error or deleted, or which has been send already
        - Clean Stock list : to remove all items from the list
    - File  
        - Save List : to save the current list in "ext_file/currentList.ser"
        - Export to CSV : to export current list to csv format (coma separated)
        - Export to Excel : to export to Excel Format (xlsx)

#### Popup Menu in Tables :

Right clic on table will sho popup menu.

Actions : 
    - Copy : to copy the current cell value in the clipboard
    - Force Update : to force update on a single Stockitem


use Library : 
[rotatedIcon](https://tips4java.wordpress.com/2009/04/06/rotated-icon/) and [textIcon](https://tips4java.wordpress.com/2009/04/02/text-icon/) by Rob Camick

and [TableColumnAdjuster](https://gist.github.com/tarple/dfebce9502b92559dd63) by Tarple.

and com.opencsv 
and org.apache.poi / poi-ooxml
