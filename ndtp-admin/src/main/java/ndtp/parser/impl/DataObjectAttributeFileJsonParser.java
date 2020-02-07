package ndtp.parser.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import ndtp.domain.DataObjectAttributeFileInfo;
import ndtp.parser.DataObjectAttributeFileParser;

public class DataObjectAttributeFileJsonParser implements DataObjectAttributeFileParser {

	@Override
	public Map<String, Object> parse(Long dataId, DataObjectAttributeFileInfo dataObjectAttributeFileInfo) {
		
		int parseSuccessCount = 0;
		int parseErrorCount = 0;
		String attribute = null;
		
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get(dataObjectAttributeFileInfo.getFilePath() + dataObjectAttributeFileInfo.getFileRealName()));
			attribute = new String(jsonData);
			
			parseSuccessCount++;
		} catch (Exception e) {
			parseErrorCount++;
			e.printStackTrace();
			throw new RuntimeException("Data Object 속성 파일 파싱 오류!");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("attribute", attribute);
		result.put("totalCount", 1);
		result.put("parseSuccessCount", parseSuccessCount);
		result.put("parseErrorCount", parseErrorCount);
		return result;
	}
}
