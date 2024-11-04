# assignment

To write a JUnit test using Mockito for the method removeConditionalFormattingCustom in your TextTransformer.java class, follow the steps below.

Here's an example of a test setup:

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.ConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

public class TextTransformerTest {

    @Mock
    private Workbook templateWorkbook;

    @Mock
    private Sheet destSheet;

    @Mock
    private SheetConditionalFormatting sheetConditionalFormatting;

    @Mock
    private ConditionalFormatting conditionalFormatting;

    @InjectMocks
    private TextTransformer textTransformer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRemoveConditionalFormattingCustom() {
        String sheetName = "TestSheet";
        CellRangeAddress areaRange = new CellRangeAddress(0, 10, 0, 5);
        
        when(templateWorkbook.getSheet(sheetName)).thenReturn(destSheet);
        when(destSheet.getSheetConditionalFormatting()).thenReturn(sheetConditionalFormatting);
        when(sheetConditionalFormatting.getNumConditionalFormattings()).thenReturn(1);
        when(sheetConditionalFormatting.getConditionalFormattingAt(0)).thenReturn(conditionalFormatting);

        List<CellRangeAddress> ranges = new ArrayList<>();
        ranges.add(new CellRangeAddress(0, 10, 0, 5));
        when(conditionalFormatting.getFormattingRanges()).thenReturn(ranges.toArray(new CellRangeAddress[0]));

        // Call the method
        textTransformer.removeConditionalFormattingCustom(new AreaRef(sheetName));

        // Verify
        verify(conditionalFormatting, times(1)).setFormattingRanges(any(CellRangeAddress[].class));
    }
}

Explanation:

1. Mocks Setup:

templateWorkbook, destSheet, sheetConditionalFormatting, and conditionalFormatting are mocked objects. We use @Mock annotations to mock them.

textTransformer is the class under test. We use @InjectMocks to inject the mocked dependencies into it.



2. Method Call and Verification:

In the test, we set up behavior for the mocks. For example, when(templateWorkbook.getSheet(sheetName)).thenReturn(destSheet); instructs the mock to return destSheet when getSheet is called on templateWorkbook.

After calling textTransformer.removeConditionalFormattingCustom, we verify that setFormattingRanges was called on conditionalFormatting with any CellRangeAddress array.



3. Customizing AreaRef:

Replace AreaRef(sheetName) with the correct instantiation if AreaRef has different parameters.




This test uses Mockito to mock dependencies and verify the interactions. Adjustments might be needed



