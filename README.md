Here’s a JUnit test that uses Mockito to cover all conditions for the setFormula method in your TextTransformer class.

The method setFormula involves several conditions:

1. If cellRef is null or does not have a valid SheetName, the method should return early.


2. If getSheet returns null, a new sheet should be created.


3. If getRow returns null, a new row should be created.


4. If getCell returns null, a new cell should be created.


5. Finally, the formula should be set on the cell, and an exception should be handled if it occurs.



Here is the JUnit test using Mockito:

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TextTransformerTest {

    @Mock
    private Workbook templateWorkbook;

    @Mock
    private Sheet sheet;

    @Mock
    private Row row;

    @Mock
    private Cell cell;

    @Mock
    private Logger logger; // Assuming there's a getLogger() method returning a Logger

    @InjectMocks
    private TextTransformer textTransformer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSetFormula_NullCellRef() {
        textTransformer.setFormula(null, "SUM(A1:A10)");
        // Verify that nothing happens when cellRef is null
        verifyNoInteractions(templateWorkbook, sheet, row, cell);
    }

    @Test
    public void testSetFormula_InvalidSheetName() {
        CellRef cellRef = mock(CellRef.class);
        when(cellRef.getSheetName()).thenReturn(null);

        textTransformer.setFormula(cellRef, "SUM(A1:A10)");
        // Verify that nothing happens when sheet name is null
        verifyNoInteractions(templateWorkbook, sheet, row, cell);
    }

    @Test
    public void testSetFormula_SheetDoesNotExist() {
        CellRef cellRef = mock(CellRef.class);
        when(cellRef.getSheetName()).thenReturn("TestSheet");

        // Simulate getSheet returning null, so it should try to create a new sheet
        when(templateWorkbook.getSheet("TestSheet")).thenReturn(null);
        when(templateWorkbook.createSheet("TestSheet")).thenReturn(sheet);

        textTransformer.setFormula(cellRef, "SUM(A1:A10)");

        verify(templateWorkbook).createSheet("TestSheet");
    }

    @Test
    public void testSetFormula_RowDoesNotExist() {
        CellRef cellRef = mock(CellRef.class);
        when(cellRef.getSheetName()).thenReturn("TestSheet");
        when(cellRef.getRow()).thenReturn(1);

        when(templateWorkbook.getSheet("TestSheet")).thenReturn(sheet);
        when(sheet.getRow(1)).thenReturn(null); // Simulate row not existing

        when(sheet.createRow(1)).thenReturn(row);

        textTransformer.setFormula(cellRef, "SUM(A1:A10)");

        verify(sheet).createRow(1);
    }

    @Test
    public void testSetFormula_CellDoesNotExist() {
        CellRef cellRef = mock(CellRef.class);
        when(cellRef.getSheetName()).thenReturn("TestSheet");
        when(cellRef.getRow()).thenReturn(1);
        when(cellRef.getCol()).thenReturn(1);

        when(templateWorkbook.getSheet("TestSheet")).thenReturn(sheet);
        when(sheet.getRow(1)).thenReturn(row);
        when(row.getCell(1)).thenReturn(null); // Simulate cell not existing

        when(row.createCell(1)).thenReturn(cell);

        textTransformer.setFormula(cellRef, "SUM(A1:A10)");

        verify(row).createCell(1);
    }

    @Test
    public void testSetFormula_SetFormulaOnExistingCell() {
        CellRef cellRef = mock(CellRef.class);
        when(cellRef.getSheetName()).
