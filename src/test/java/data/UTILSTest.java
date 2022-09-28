/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package data;

import Inventree.item.StockItem;
import java.time.LocalDate;
import java.time.Month;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author blegendre
 */
public class UTILSTest {
    
    public UTILSTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }
   

    /**
     * Test of isNullOrEmpty method, of class UTILS.
     */
    @org.junit.Test
    public void testIsNullOrEmpty() {
        System.out.println("isNullOrEmpty");
        String str = "thi";
        assertEquals(false, UTILS.isNullOrEmpty("a string"));
        assertEquals(true, UTILS.isNullOrEmpty(null));
        assertEquals(true, UTILS.isNullOrEmpty(""));

    }

    /**
     * Test of getSIInfo method, of class UTILS.
     */
    @org.junit.Test
    public void testGetSIInfo() {
        System.out.println("getSIInfo");
        StockItem si = null;
        assertEquals(null, UTILS.getSIInfo(null));

    }

    /**
     * Test of cleanBC method, of class UTILS.
     */
    @org.junit.Test
    public void testCleanBC() {
        System.out.println("cleanBC");
        String bc = "";
        String expResult = "";
        String result = UTILS.cleanBC(bc);
        assertEquals("AAE", UTILS.cleanBC("AAE"));
        assertEquals("AAE", UTILS.cleanBC("A A   E"));
        assertEquals("AAE", UTILS.cleanBC("A"+System.lineSeparator()+"A"+System.lineSeparator()+"E"));
    }

    /**
     * Test of formatDate method, of class UTILS.
     */
    @org.junit.Test
    public void testFormatDate() {
        System.out.println("formatDate");
        LocalDate ld = LocalDate.of(2000, Month.MARCH, 2);
        String format = "yyyy-LL-dd";
        String expResult = "2000-03-02";
        String result = UTILS.formatDate(ld, format);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkTextField method, of class UTILS.
     */
    @org.junit.Test
    public void testCheckTextField() {
        System.out.println("checkTextField");
        JTextField tf = new JTextField();
        assertEquals(false,  UTILS.checkTextField(tf));
        tf.setText("no blank text");
        assertEquals(true,  UTILS.checkTextField(tf));
    }

    /**
     * Test of checkComboBox method, of class UTILS.
     */
    @org.junit.Test
    public void testCheckComboBox() {
        System.out.println("checkComboBox");
        JComboBox cb = new JComboBox();
        boolean expResult = false;
        boolean result = UTILS.checkComboBox(cb);
        assertEquals(expResult, result);
    }

    /**
     * Test of setCheckBorder method, of class UTILS.
     */
    @org.junit.Test
    public void testSetCheckBorder() {
        System.out.println("setCheckBorder");
        JComponent cmp = new JTextField();
        assertEquals(true, UTILS.setCheckBorder(cmp, true));
        assertEquals(false, UTILS.setCheckBorder(cmp, false));

    }

    /**
     * Test of cstIntbtw method, of class UTILS.
     */
    @org.junit.Test
    public void testCstIntbtw() {
        System.out.println("cstIntbtw");
        assertEquals(5, UTILS.cstIntbtw(5, 0, 12));
        assertEquals(0, UTILS.cstIntbtw(-22, 0, 12));
        assertEquals(12, UTILS.cstIntbtw(22, 0, 12));
        assertEquals(-2, UTILS.cstIntbtw(-2, -5, 12));
        assertEquals(-5, UTILS.cstIntbtw(-22, -5, 12));
        assertEquals(-12, UTILS.cstIntbtw(-2, -25, -12));

    }
    
}
