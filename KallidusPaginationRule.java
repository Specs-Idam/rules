  import java.util.*;
  import sailpoint.tools.Util;
  import connector.common.JsonUtil;
  import java.util.ArrayList;
  import connector.common.JsonUtil;
  import connector.common.Util;
  import sailpoint.connector.webservices.WebServicesClient;
  import sailpoint.connector.webservices.EndPoint;

  Object Id;
  Object userName;
  Object firstName;
  Object lastName;
  Object email;

  Object userFields;
  Object importKey;
  Object country;
  Object primaryStoreNum;
  Object primaryStoreName;
  Object employeeNumber;

  List<String> allowedStatuses = new ArrayList();
  Map header = new HashMap();
  Map updatedMapInfo = new HashMap();
  List<String> FinalList = new ArrayList();
  String baseUrl = "https://gateway.kallidusapi.com/search";
  Map args = new HashMap();
  Map response = (Map) JsonUtil.toMap(rawResponseObject);
  WebServicesClient restClient = new WebServicesClient();

  header.put("Content-Type", "application/json");
  header.put("Ocp-Apim-Subscription-Key", "80cc6f00a09d4123aa48ca67ec77afb0");
  args.put(WebServicesClient.ARG_URL, baseUrl);
  restClient.configure(args);

  Object endCursor = response.get("data").get("person").get("pageInfo").get("endCursor");
  Object hasNext = response.get("data").get("person").get("pageInfo").get("hasNextPage");
  allowedStatuses.add("200");


String payload = "{\"query\": \"{person (first:1000) {edges {cursor node {userName,id,familyName,givenName,importKey,email,userFields {index,value}}}pageInfo{endCursor hasNextPage hasPreviousPage startCursor}totalCount}}\"}";

      Object newPage = restClient.executePost(baseUrl, payload, header, allowedStatuses);
      
      Map newPageResults = (Map) JsonUtil.toMap(newPage);

      for(user : newPageResults.get("data").get("person").get("edges")){

        Id = user.get("node").get("id");
        userName = user.get("node").get("userName");
        firstName = user.get("node").get("givenName");
        lastName = user.get("node").get("familyName");
        email = user.get("node").get("email");
        importKey = user.get("node").get("importKey");
        userFields = user.get("node").get("userFields");

        for(field : userFields) {

          if(field.get("index") == 1) {
            country = field.get("value");
          }
          else if(field.get("index") == 4){
            primaryStoreNum = field.get("value");
          }
          else if(field.get("index") == 5){
            primaryStoreName = field.get("value");
          }
          else if(field.get("index") == 14){
            employeeNumber = field.get("value");
          }

        }

        Map userMap = new HashMap();

        userMap.put("Email", email);
        userMap.put("Id", Id);
        userMap.put("UserName", userName);
        userMap.put("FirstName", firstName);
        userMap.put("LastName", lastName);
        userMap.put("ImportKey", importKey);
        userMap.put("Country", country);
        userMap.put("Primary Store Number", primaryStoreNum);
        userMap.put("Primary Store Name", primaryStoreName);
        userMap.put("Employee Number", employeeNumber);

        FinalList.add(userMap);

      }

    while(hasNext == true){

      String payload = "{\"query\": \"{person (first:1000 after:\\\"" + endCursor + "\\\") {edges {cursor node {userName,id,familyName,givenName,importKey,email,userFields {index,value}}}pageInfo{endCursor hasNextPage hasPreviousPage startCursor}totalCount}}\"}";

      Object newPage = restClient.executePost(baseUrl, payload, header, allowedStatuses);
      
      Map newPageResults = (Map) JsonUtil.toMap(newPage);

      for(user : newPageResults.get("data").get("person").get("edges")){

        Id = user.get("node").get("id");
        userName = user.get("node").get("userName");
        firstName = user.get("node").get("givenName");
        lastName = user.get("node").get("familyName");
        email = user.get("node").get("email");
        importKey = user.get("node").get("importKey");
        userFields = user.get("node").get("userFields");

        for(field : userFields) {

          if(field.get("index") == 1) {
            country = field.get("value");
          }
          else if(field.get("index") == 4){
            primaryStoreNum = field.get("value");
          }
          else if(field.get("index") == 5){
            primaryStoreName = field.get("value");
          }
          else if(field.get("index") == 14){
            employeeNumber = field.get("value");
          }

        }

        Map userMap = new HashMap();

        userMap.put("Email", email);
        userMap.put("Id", Id);
        userMap.put("UserName", userName);
        userMap.put("FirstName", firstName);
        userMap.put("LastName", lastName);
        userMap.put("ImportKey", importKey);
        userMap.put("Country", country);
        userMap.put("Primary Store Number", primaryStoreNum);
        userMap.put("Primary Store Name", primaryStoreName);
        userMap.put("Employee Number", employeeNumber);

        FinalList.add(userMap);

        }

      endCursor = newPageResults.get("data").get("person").get("pageInfo").get("endCursor");
      hasNext = newPageResults.get("data").get("person").get("pageInfo").get("hasNextPage");
      
      }

      updatedMapInfo.put("data", FinalList);
        
      return updatedMapInfo;