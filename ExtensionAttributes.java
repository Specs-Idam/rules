import java.util.*;
  import sailpoint.tools.Util;
  import connector.common.JsonUtil;
  import java.util.ArrayList;
  import connector.common.Util;

  List<String> allowedStatuses = new ArrayList();
  allowedStatuses.add("200");

  Map header = new HashMap();
  Map updatedMapInfo = new HashMap();
  List dataList = new ArrayList();
  List authDataList = new ArrayList();
  List<String> allowedStatuses = new ArrayList();
  List<String> FinalList = new ArrayList();

  Map response = (Map) JsonUtil.toMap(rawResponseObject);
List dataList = new ArrayList();

    ArrayList<String> dataTime = response.get("d").get("user").get("results");

    String builder = "";
    String eposBuilder = "";

  for (data : dataTime) {

    Object businessUnit;
      Object costCenter;
      Object jobCode;
      Object location;
      Object department;
    
    userId = data.get("empInfo");
    
    if (userId == null) {
        continue;
    }
    for (jobs : data.get("empInfo").get("jobInfoNav").get("results")){ 
      
       businessUnit = jobs.get("businessUnit");
       costCenter = jobs.get("costCenter");
       jobCode = jobs.get("jobCode");
       location = jobs.get("location");
       department = jobs.get("department");

       if (costCenter != null) {
        costCenter = costCenter.replace("-", "");
       }
      
      builder += location + "-" + jobCode + "-" + costCenter + "-" + department  + "-" + businessUnit + ";";
      eposBuilder += location + ";";
      
    }
 
    }

    eposBuilder = eposBuilder.substring(0, eposBuilder.length()-1);

    Map userMap = new HashMap();

    userMap.put("ExtensionAttribute3", builder);

    userMap.put("ExtensionAttribute1", eposBuilder);
    
    FinalList.add(userMap);

  updatedMapInfo.put("data", FinalList);   

  return updatedMapInfo;