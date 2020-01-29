package ndtp.domain;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;

import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.files.ShpFiles;
import org.junit.jupiter.api.Test;


public class ShapeFileValidation {
	
	@Test
	public void test() {
		DbaseFileReader r = null;
        try {
            ShpFiles shpFile = new ShpFiles("D:\\data\\boundary\\sk_emd\\sk_emd_20200115100910_230251955674700.shp");
            r = new DbaseFileReader(shpFile, false, Charset.forName("CP949"));
            DbaseFileHeader header = r.getHeader();
            
            // 필드수
            int filedValidCount = 0;
            int numFields = header.getNumFields();
            for(int iField=0; iField < numFields; ++iField) {
                String fieldName = header.getFieldName(iField);
                System.out.println(fieldName);
                if(ShapeFileField.findBy(fieldName) != null) filedValidCount++;
            }
            System.out.println("fieldValidCount ================== " + filedValidCount);
            System.out.println("enum length" + ShapeFileField.values().length);
//            while (r.hasNext()) {
//                Object[] values = r.readEntry();
//                for(int iField=0; iField < numFields; ++iField) {
//                    System.out.println(values[iField].toString());
//                }
//                System.out.println("---------------");
//            }
 
            r.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
}