# 28/10/2022 / Beta v0.6.7
  * Change DatePicker for LGoodDatePicker (previous was a pain)

# 17-10-2022 / Beta v 0.6.5/6
  * Bug fix prevent EAN125 decoder from decoding
  * Add recheck on Unknown Item when a part is created or a link created

# 28-09-2022 / Beta v.0.6.3/4 :
  * API error response handling : now the message of the error is showed up.
  * Internal Barcode processing amelioration
  * add right clic to force update on single stockitem in list

# 22-09-2022 / Beta v.0.6.2 :
  * Add scan of internal Inventree Barcode as Command : 
      -  set stocklocation (eg scan barcode type : {'stocklocation':1} )

# 20-09-2022 / Beta v.0.6.1 :
  * Fix log out do not clean password if saved in ini file
  * add Javadoc

# 16-09-2022 / Beta v.0.6 :
  * Fit with barcode api refactoring from Inventree 
      * add barcode link to part, Supplier part
      * remove barcode storage in MPN
  * update readme

# 12-09-2022 / Beta v.0.5 :
  * Add Changelog!
  * update readme
  * Add Export to CSV
  * Add Export to Excel (xlsx format)